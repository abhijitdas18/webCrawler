package com.abhijit.webcrawler.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * Process the Json as a LinkedHashMap. Finding the success/skipped/error pages with multi thread.
 * @author Abhijit
 *
 */
public class WebPageCrawlerThread implements Runnable{
	
	static Set<String> successPages = new HashSet<>();
	static Set<String> skippedPages = new HashSet<>();
	static Set<String> errorPages = new HashSet<>();

	private LinkedHashMap<String, ArrayList<String>> myPages;
	private String aLlink;
	
	public WebPageCrawlerThread()
	{
		// default constructor
	}
	
	public WebPageCrawlerThread(String aLlink,LinkedHashMap<String, ArrayList<String>> myPages)
	{
		this.aLlink = aLlink;
		this.myPages = myPages;
	}
	
	
	/**
	 * convert a set into Array	
	 * @param addresses
	 * @return
	 */
	private String[] convertSetToArray(Set<String> addresses)
	{
		return addresses.toArray(new String[addresses.size()]);
	}
	
	/**
	 * The first page always be part of successPage list.
	 * @param addresses
	 * @param myPages
	 */
	private void processFirstPageDefault(Set<String> addresses, LinkedHashMap<String, ArrayList<String>> myPages)
	{
		String[] addressArray = convertSetToArray(addresses);

		List<String> firstPageLinks = myPages.get(addressArray[0]);

		successPages.add(addressArray[0]);
		
		// send each of the links of the 1st page/address to process		
		for(String aLink : firstPageLinks)
		{
			process(aLink, myPages);
		}

	}
	
	/**
	 * API process the given Map of Pages.
	 * Iterate the addresses and find the array of link for each address.
	 * create thread for each link of a address/page. Each thread process async.
	 * Sleep is required to get the updated vlaue in the success list.
	 * Thread will be created to process further only for the pages found in success list.
	 * 
	 * @param myPages
	 */
	public void processPages(LinkedHashMap<String, ArrayList<String>> myPages)
	{
		Set<String> addresses = myPages.keySet();

		processFirstPageDefault(addresses, myPages);

		List<String> linkArray = null;

		String[] addressArray = convertSetToArray(addresses);

		for (int i = 1; i < addressArray.length; i++) {
			try {
				Thread.sleep(1000); //sleep to update the success list
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// a page will be processed if it is part of successPage list.
			if (successPages.contains(addressArray[i])) {
				linkArray = myPages.get(addressArray[i]);
				for (String link : linkArray) {
					Runnable myRuuanble = new WebPageCrawlerThread(link, myPages);
					Thread t = new Thread(myRuuanble);
					t.start();
				}

			}
		}

		// sleep is required to finish all the threads before print output.
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		printOutput();

	}
	
	
	public void run()
	{
		process(aLlink, myPages);
	}
	
	/**
	 * Finding the link is present in the page or not.
	 * Update all the output buckets.
	 * @param aLlink
	 * @param myPages
	 */
	private void process(String aLlink, HashMap<String, ArrayList<String>> myPages) {
		
		// take each element(link) from the linkArray
		// check the element is present as a key in HashMap

		if (myPages.containsKey(aLlink) && !successPages.contains(aLlink)) {
			successPages.add(aLlink);
		
		} else if (!myPages.containsKey(aLlink)) {
			errorPages.add(aLlink);
		} else if (successPages.contains(aLlink)) {
			skippedPages.add(aLlink);
		}

	}
	
	/**
	 * print output
	 */
	public void printOutput()
	{
		System.out.println("Success: " + successPages);
		System.out.println();
		System.out.println("Skipped: " + skippedPages);
		System.out.println();
		System.out.println("Error:" + errorPages);
	}
	

}
