package com.abhijit.webcrawler.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class WebPageCrawlerThread implements Runnable{
	
	static Set<String> successPages = new HashSet<>();
	static Set<String> skippedPages = new HashSet<>();
	static Set<String> errorPages = new HashSet<>();

	LinkedHashMap<String, ArrayList<String>> myPages;
	String aLlink;
	
	public WebPageCrawlerThread()
	{
		
	}
	
	public WebPageCrawlerThread(String aLlink,LinkedHashMap<String, ArrayList<String>> myPages)
	{
		this.aLlink = aLlink;
		this.myPages = myPages;
	}
	
	
		
	public String[] convertSetToArray(Set<String> addresses)
	{
		return addresses.toArray(new String[addresses.size()]);
	}
	
	public void processFirstPageDefault(Set<String> addresses, LinkedHashMap<String, ArrayList<String>> myPages)
	{
		String[] addressArray = convertSetToArray(addresses);
		//System.out.println("Array of addresses : " + addressArray);

		//System.out.println("First element of the array of address : " + addressArray[0]);

		List<String> firstPageLinks = myPages.get(addressArray[0]);
		//System.out.println("firstPageLinks :" + firstPageLinks);

		successPages.add(addressArray[0]);
		// send the 1st element ( list of links) to process
		
		for(String aLink : firstPageLinks)
		{
			process(aLink, myPages);
		}

		//System.out.println("1st page done_______________");
	}
	
	public void processPages(LinkedHashMap<String, ArrayList<String>> myPages)
	{
		Set<String> addresses = myPages.keySet();
		//System.out.println("Addresses :" + addresses);

		processFirstPageDefault(addresses, myPages);

		List<String> linkArray = null;

		String[] addressArray = convertSetToArray(addresses);
		//System.out.println("Pages to be processed :  " + addressArray);

		for (int i = 1; i < addressArray.length; i++) {
			//System.out.println("Address :::: " + addressArray[i]);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// a page will be processed if it is part of successPage list.
			//System.out.println("Temrorary Success pages  : " + successPages);
			if (successPages.contains(addressArray[i])) {
				linkArray = myPages.get(addressArray[i]);
				//System.out.println("Links ::::: " + linkArray);
				for (String link : linkArray) {
					//System.out.println("Thread started for a link : " + link);
					Runnable myRuuanble = new WebPageCrawlerThread(link, myPages);
					Thread t = new Thread(myRuuanble);
					t.start();
				}

			}
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		printOutput();

	}
	
	
	public void run()
	{
		//System.out.println("In run() ::: Thread name :" + Thread.currentThread().getName());
		//System.out.println("In run() ::: A link : " + aLlink);
		//System.out.println("In run() ::: myPages : " + myPages);
		process(aLlink, myPages);
	}
	
	public void process(String aLlink, HashMap<String, ArrayList<String>> myPages) {
		
		

		//System.out.println("Process is called.....................");
		//System.out.println("In process() :::: aLlink : " + aLlink);
		//System.out.println("In process() :::: MyPages :" + myPages);

		// take each element(link) from the linkArray
		// check the element is present as a key in HashMap

		if (myPages.containsKey(aLlink) && !successPages.contains(aLlink)) {
			//System.out.println("The link :::: < " + aLlink + " > is present as key of the HashMap.");
			successPages.add(aLlink);
		
		} else if (!myPages.containsKey(aLlink)) {
			//System.out.println("The link :::: < " + aLlink + " > is NOT present as key of the HashMap.");
			errorPages.add(aLlink);
		} else if (successPages.contains(aLlink)) {
			//System.out.println("The link :::: < " + aLlink + " > is traversed more than one.");
			skippedPages.add(aLlink);
		}

	}
	
	public void printOutput()
	{
		System.out.println("Success: " + successPages);
		System.out.println();
		System.out.println("Skipped: " + skippedPages);
		System.out.println();
		System.out.println("Error:" + errorPages);
	}
	

}
