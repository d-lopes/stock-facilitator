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

public class YahooFinanceComIndexHTMLExtractorImpl implements FinanceDataExtractor {
        
    public static final String PREFIX = "https://de.finance.yahoo.com/quote/";
    
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
        return getFinanceData(url, FinanceDataType.YahooSymbol);
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
			
			Elements elements = doc.body().select("div#main-0-Quote-Proxy table > tbody > tr"); 
			
			for (Element e : elements) {
			    
				String text = e.select("td > a").text();
				
				// Guard: move on when the text is empty
				if (StringUtils.isEmpty(text)) {
					continue;
				}
				
				text = StringUtils.trimAllWhitespace(text);
				list.add(text);
		
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
    }
}
