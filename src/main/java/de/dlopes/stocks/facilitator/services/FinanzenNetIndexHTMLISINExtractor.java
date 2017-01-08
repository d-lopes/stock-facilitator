/*******************************************************************************
 * Copyright (c) 2016 Dominique Lopes.
 * All rights reserved. 
 *
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Dominique Lopes - initial API and implementation
 *******************************************************************************/
package de.dlopes.stocks.facilitator.services;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

public class FinanzenNetIndexHTMLISINExtractor implements ISINExtractor {

    public static final String PREFIX = "http://www.finanzen.net/index/";

    public boolean isApplicable(String url) {
        
        // guard: ensure an URL is given
		if (url == null) {
		    return false;
		}
	
		
		// guard: ensure this method is only used for it's intended purpose - to load ISINs from 
		// the german website www.finanzen.net
		if (!url.startsWith(PREFIX)) {
		    return false;
		}
		
		return true;
    }
    
	public List<String> getISINs(String url) {

		List<String> list = new ArrayList<String>();
		
		try {
			
			Document doc = null;
			if (url.startsWith("file://")) {
				File input = new File(url.replaceFirst("file://",""));
				doc = Jsoup.parse(input, "UTF-8");
			} else {
				URL input = new URL(url);
				doc = Jsoup.parse(input, 30000);
			}
			
			String index = doc.body().select("div#mainWrapper > div.main h2 > a").text();
			Elements elements = doc.body().select("form#realtime_chart_list > table tr"); 
			
			for (Element e : elements) {
				String text = e.select("td > a.content_one_line").text();
				
				// Guard: move on when the text is empty or represents the whole index
				if (StringUtils.isEmpty(text) || index.equals(text)) {
					continue;
				}
				
				Element e2 = e.select("td > div[field='bid']").first();
				text = e2.attr("item");
				list.add(convert2ISIN(text));
		
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	private String convert2ISIN(String input) {
		String res = null;
		try {
			res = input.substring(input.length() - 12);
		} catch (IndexOutOfBoundsException e) {
			// do nothing -> we just want to be a little more robust
		}
		
		return res;
	}


}