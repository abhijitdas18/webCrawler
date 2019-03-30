package com.abhijit.webcrawler.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class WebPageCrawlerRecursion {

	static Set<String> successPages = new HashSet<>();
	static Set<String> skippedPages = new HashSet<>();
	static Set<String> errorPages = new HashSet<>();

	
	public void processPages(LinkedHashMap<String, ArrayList<String>> myPages) {

		Set<String> addresses = myPages.keySet();
		//System.out.println("Addresses :" + addresses);

		// Iterate the LinkedHashMap.
		// Get the 1st element.
		// Get the value of the 1st element.
		String[] addressArray = addresses.toArray(new String[addresses.size()]);
		List<String> linkArray = null;

		//System.out.println("For the 1st, An Address : " + addressArray[0]);
		// Store the 1st page into success List  
		successPages.add(addressArray[0]);
		linkArray = myPages.get(addressArray[0]);
		
		//System.out.println("For the 1st, links for the page : " + linkArray);
		//System.out.println();
		//System.out.println();
		process(linkArray, myPages) ;
		
		
	}

	
	public void process(List<String> linkArray, HashMap<String, ArrayList<String>> myPages) {

		//System.out.println("Process is called.....................");
		//System.out.println("Array of links : "  + linkArray);
		//System.out.println("MyPages :" + myPages);
		
		// take each element(link) from the linkArray
		// check the element is present as a key in HashMap
		for (String aLlink : linkArray) {
			if (myPages.containsKey(aLlink) && !successPages.contains(aLlink)) {
				//System.out.println("The link " + aLlink + " is present as key of the HashMap." );
				successPages.add(aLlink);				
				
				// Find the links in the page (i.e. map)
				List<String> links = myPages.get(aLlink);
				
				// Process the page with given aLink
				process(links, myPages);
				
			} else if (!myPages.containsKey(aLlink)) {
				//System.out.println("The link "+ aLlink + " is NOT present as key of the HashMap.");
				errorPages.add(aLlink);
			} else if(successPages.contains(aLlink)) {
				//System.out.println("The link "+ aLlink + " is traversed more than one." );
				skippedPages.add(aLlink);
			}
		}		

	}
	
	public void printOutput()
	{
		System.out.println("Success: " + successPages);
		System.out.println();
		System.out.println("Skipped: " + skippedPages);
		System.out.println();
		System.out.println("Error: " + errorPages);
	}

}
