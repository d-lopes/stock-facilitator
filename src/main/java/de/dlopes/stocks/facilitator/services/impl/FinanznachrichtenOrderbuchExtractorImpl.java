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
package de.dlopes.stocks.facilitator.services.impl;

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

import de.dlopes.stocks.facilitator.services.FinanceDataExtractor;

public class FinanznachrichtenOrderbuchExtractorImpl implements FinanceDataExtractor {
    
    public static final String PREFIX = "http://aktienkurs-orderbuch.finanznachrichten.de/";

    @Override
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
    
    @Override
	public List<String> getFinanceData(String url) {
        return getFinanceData(url, FinanceDataType.ISIN);
    }
    
    @Override
	public List<String> getFinanceData(String url, FinanceDataType dataType) {
	    
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
			
			Elements elements = doc.body().select("span[id^=productid] > span"); 
			
			for (Element e : elements) {
				String text = e.text();
				
				// Guard: move on when the text is empty
				if (StringUtils.isEmpty(text)) {
					continue;
				}
				
				text = StringUtils.trimAllWhitespace(text);
				
				// Guard: move on when the text does not contain the ISIN or WKN
				if (!text.startsWith(dataType.name() + ":")) {
				    continue;
			    }

                text = text.replace(dataType.name() + ":", "");
				list.add(text);
		
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
		
    }
   
}
