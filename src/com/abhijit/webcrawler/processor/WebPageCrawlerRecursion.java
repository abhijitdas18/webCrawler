package com.abhijit.webcrawler.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * Process the Json as a LinkedHashMap. Finding the success/skipped/error pages with recursion.
 * 
 * @author Abhijit
 *
 */
public class WebPageCrawlerRecursion {

	static Set<String> successPages = new HashSet<>();
	static Set<String> skippedPages = new HashSet<>();
	static Set<String> errorPages = new HashSet<>();

	/**
	 * Find first address/page and its links associated. Call the process function
	 * with 1st page and complate map of pages.
	 * 
	 * @param myPages
	 */
	public void processPages(LinkedHashMap<String, ArrayList<String>> myPages) {

		// get the set of addresses
		Set<String> addresses = myPages.keySet();

		// Get the 1st address. 1st address always be a part of successPage list.
		// Get the Links of the 1st address.
		String[] addressArray = addresses.toArray(new String[addresses.size()]);
		List<String> linkArray = null;

		// Store the 1st page into success List
		successPages.add(addressArray[0]);

		// array of links for the 1st address/page
		linkArray = myPages.get(addressArray[0]);

		process(linkArray, myPages);

	}

	/**
	 * Check the each link is present in the complete Pages of HashMap.
	 * Evaluate ecach link and store into different buckets.
	 * @param linkArray
	 * @param myPages
	 */
	public void process(List<String> linkArray, HashMap<String, ArrayList<String>> myPages) {

		// take each element(link) from the linkArray
		// check the element is present as a key in HashMap
		for (String aLlink : linkArray) {
			if (myPages.containsKey(aLlink) && !successPages.contains(aLlink)) {
				successPages.add(aLlink);

				// Find the links in the page (i.e. map)
				List<String> links = myPages.get(aLlink);

				// Process the page with given aLink. 
				//recursion call the function.
				process(links, myPages);

			} else if (!myPages.containsKey(aLlink)) {
				errorPages.add(aLlink);
			} else if (successPages.contains(aLlink)) {
				skippedPages.add(aLlink);
			}
		}
	}

	/**
	 * Print the output
	 */
	public void printOutput() {
		System.out.println("Success: " + successPages);
		System.out.println();
		System.out.println("Skipped: " + skippedPages);
		System.out.println();
		System.out.println("Error: " + errorPages);
	}

}
