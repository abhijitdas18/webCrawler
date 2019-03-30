package com.abhijit.webcrawler.helper;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.abhijit.webcrawler.helper.gsonperser.Internet;
import com.abhijit.webcrawler.helper.gsonperser.Page;
import com.google.gson.Gson;

/**
 * A utility class
 * @author AbDas
 *
 */

public class JsonToMapConverter {
	
	/**
	 * Convert the Json into HashMap.
	 * Use the generated java object from JSON using http://www.jsonschema2pojo.org/
	 * @param br
	 * @return
	 */
	public static LinkedHashMap<String, ArrayList<String>> convertJsonToMap(BufferedReader br) {
		Gson gson = new Gson();
		String address = null;
		List<String> link = null;
		LinkedHashMap<String, ArrayList<String>> mapPages = new LinkedHashMap<>();
		Internet result = gson.fromJson(br, Internet.class);
		if (result != null) {
			for (Page page : result.getPages()) {
				address = page.getAddress();
				link = page.getLinks();

				mapPages.put(address, (ArrayList<String>) link);
			}
		}

		//System.out.println("Map of Pages : " + mapPages);

		return mapPages;

	}
	

}
