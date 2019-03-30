package com.abhijit.webcrawler.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import com.abhijit.webcrawler.helper.JsonToMapConverter;
import com.abhijit.webcrawler.processor.WebPageCrawlerRecursion;
import com.abhijit.webcrawler.processor.WebPageCrawlerThread;
public class WebCrawlerMain {
	

	public static void main(String[] args) throws Exception {
		
		
		System.out.println("Enter input file name with relative path : ");
		Scanner myFile = new Scanner(System.in);
		String file = myFile.nextLine();
		
			
		BufferedReader br = new BufferedReader(new FileReader("useCases" + "/" + file));
		
		LinkedHashMap<String, ArrayList<String>> myPages = JsonToMapConverter.convertJsonToMap(br);
		
		boolean isMultiThreaded = false;
		if(!isMultiThreaded)
		{
			WebPageCrawlerRecursion obj = new WebPageCrawlerRecursion();
			obj.processPages(myPages);
			obj.printOutput();
		}
		else
		{
			WebPageCrawlerThread obj = new WebPageCrawlerThread();
			obj.processPages(myPages);
		}
		
	}

}
