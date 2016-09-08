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
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import de.dlopes.stocks.data.StockInfo;
import de.dlopes.stocks.facilitator.data.StockInfoImpl;

public class HTMLFileExtractor implements StockDataCollector {

	private String url;
	
	public HTMLFileExtractor(String url) {
		this.url = url;
	}
	
	@Override
	public String getSource() {
		return this.url;
	}
	
	@Override
	public List<StockInfo> getData() {
		List<StockInfo> res = new ArrayList<StockInfo>();
		
		try {
			
			Document doc = null;
			if (url.startsWith("file://")) {
				File input = new File(url.replaceFirst("file://",""));
				doc = Jsoup.parse(input, "UTF-8");
			} else {
				URL input = new URL(url);
				doc = Jsoup.parse(input, 30000);
			}
			
			Elements elements = doc.body().select("form#realtime_chart_list > table tr"); 
			for (Element e : elements) {
				String text = e.select("td > a.content_one_line").text();
				
				// Guard: move on when the text is empty or represents the whole index
				if (StringUtils.isEmpty(text) || "DAX".equals(text)) {
					continue;
				}
				StockInfo si = new StockInfoImpl();
				si.setName(text);
				text = e.select("td > div.display_none").text();
				if (StringUtils.isEmpty(text)) {
					text = e.select("td ").eq(3).text();	
				}
				si.setPrice(convert2Double(text));
				Element e2 = e.select("td > div[field='bid']").first();
				text = e2.attr("item");
				si.setISIN(convert2ISIN(text));
				si.setWKN(convert2WKN(text));
				
				text = e2.text();
				si.setBid(convert2Double(text));
				
				text = e.select("td > div[field='ask']").text();
				si.setAsk(convert2Double(text));
				
				text = e.select("td > div[field='changeabs']").text();
				si.setChangeAbsolute(convert2Double(text));
				
				text = e.select("td > div[field='changeper']").text();
				si.setChangePercentage(convert2Double(text));
				
				text = e.select("td > div[field='quotetime']").text();
				si.setTime(convert2Timestamp(text));
				
				// by default: index = DAX
				si.setIndex("DAX");
				res.add(si);		
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
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

	private String convert2WKN(String input) {
		String res = null;
		try {
			res = input.substring(input.length() - 7, input.length() - 1);
		} catch (IndexOutOfBoundsException e) {
			// do nothing -> we just want to be a little more robust
		}
		
		return res;
	}
	
	private LocalTime convert2Timestamp(String input) {
		LocalTime lt = null;
		try {
			lt = LocalTime.parse(input, DateTimeFormatter.ISO_LOCAL_TIME);
			
		} catch (DateTimeParseException e) {
			// do nothing -> we just want to be a little more robust
		}
		
		return lt;
	}

	private Double convert2Double(final String input) {
		Double res = null;
		try {
			String str = input.replaceAll("%", "");
			NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
			res = nf.parse(str).doubleValue();
		} catch (ParseException e) {
			// do nothing -> we just want to be a little more robust
		}	
		
		return res;
	}

}