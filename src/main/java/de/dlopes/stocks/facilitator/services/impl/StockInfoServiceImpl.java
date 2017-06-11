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

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageContextErrors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import yahoofinance.Stock;
import de.dlopes.stocks.facilitator.config.ConfigurationSettings;
import de.dlopes.stocks.facilitator.data.StockInfo;
import de.dlopes.stocks.facilitator.data.StockInfoRepository;
import de.dlopes.stocks.facilitator.data.util.Stock2StockInfoConverter;
import de.dlopes.stocks.facilitator.services.FinanceDataExtractor;
import de.dlopes.stocks.facilitator.services.StockInfoService;
import de.dlopes.stocks.facilitator.services.impl.util.SFApplicationException;
import de.dlopes.stocks.facilitator.ui.forms.AddStocksForm;

@Service("stockInfoService")
public class StockInfoServiceImpl implements StockInfoService, Serializable {

    private static final long serialVersionUID = -4432833131963935623L;

    // Define the logger object for this class
    private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ConfigurationSettings cs;
	
	@Autowired
	StockInfoRepository siRepo;
	
	@Override
	public List<StockInfo> findAll() {
		List<StockInfo> siList = siRepo.findAll();	
		return siList;
	}
	
	@Override
	public int count() {
	    // type casing long to int which is fine because number of stocks will never 
	    // exceed 2147483647 (max. integer value)
	    return (int) siRepo.count();
	}

	@Override
	public void addStocks(AddStocksForm addStocksForm) {
	    
	    List<String> yahooSymbols = null;
	    String url = addStocksForm.getUrl();
	    if (StringUtils.isEmpty(url)) {
            
            String yhooSymString = addStocksForm.getListOfYahooSymbols();
            yhooSymString = yhooSymString.replace("\n", ",");
            yhooSymString = yhooSymString.replace("\r", ",");
            yhooSymString = yhooSymString.replace(",,", ",");
            yhooSymString = StringUtils.trimAllWhitespace(yhooSymString);
            yahooSymbols = Arrays.asList(StringUtils.delimitedListToStringArray(yhooSymString, ","));
            
	    } else {
/*	    	    
    	    // get the ISINs 
    	    FinanzenNetIndexHTMLExtractorImpl fn_isin_extr = new FinanzenNetIndexHTMLExtractorImpl();
    	    List<String> isins = fn_isin_extr.getFinanceData(url);
    		// TODO: remove ISINs which already exist in the DB from list
    
    	    log.info("ISINs extracted!");
    	    
            // guard: abort when there is nothing to add
            if (isins.size() < 1) {
    	       throw new RuntimeException("no ISINs found!.");   
    	       // TODO: propagate error message to UI
    	    }
    	    
    	    log.info("ISINs found!");		
    	    
    		// receive the mapping 
    		Map<String,String> symbols2isins = null;
    		try {
    	       symbols2isins = YahooFinanceDataCollectorImpl.getSymbols4ISINs(isins);
    	    } catch (SFApplicationException sfae) {
    	        throw new RuntimeException("Yahoo Symbols could not be loaded", sfae);    
    	    }
    
    	    log.info("Yahoo symbols loaded!");
    	    
            // when there was no mapping was found for one or more ISIN, the we 
            // have to propagate this as a warning message
            if (symbols2isins.size() < isins.size()) {
                // remove all ISINs that returned a valid mapping and see what stays in the list
                for (String s : symbols2isins.keySet()) {
                    String isin = symbols2isins.get(s);
                    isins.remove(isin);
                }
                
                // TODO: propagate a message to the UI
            }
    
    	    log.info("Yahoo symbols cleaned up!");
            
            // guard: do not proceed when we don't have new symbols to add 
            if (symbols2isins.size() < 1) {
    	       throw new RuntimeException("no YAHOO symbols found!.");   
    	       // TODO: propagate error message to UI
    	    }
    	    
    	    log.info("still have Yahoo symbols to load!");        
    */
            
            FinanceDataExtractor finDataExtr = cs.getFinanceDataExtractor();        
            yahooSymbols = finDataExtr.getFinanceData(url);
            
        }

        // guard: abort when there is nothing to add
        if (yahooSymbols == null || yahooSymbols.isEmpty()) {
	       throw new RuntimeException("no Yahoo Symbols found!.");   
	       // TODO: propagate error message to UI
	    }

        // load stock details from yahoo finance api
        Map<String,Stock> stocks = null;
        try {
	        stocks = YahooFinanceDataCollectorImpl.requestStocks(yahooSymbols);
        } catch (SFApplicationException sfae) {
	       throw new RuntimeException("Stock details could not be loaded", sfae);    
	    }

	    log.info("Stock info requested from Yahoo!");
        
		for (String symbol : stocks.keySet()) {
			
			Stock stock = stocks.get(symbol);	
			// TODO: refactor 
			FinanznachrichtenOrderbuchExtractorImpl foei = new FinanznachrichtenOrderbuchExtractorImpl();
			List<String> isins = foei.getFinanceData(FinanznachrichtenOrderbuchExtractorImpl.PREFIX + symbol.split("\\.")[0] + ".htm");
			String isin = null;			
			if (isins != null && isins.size() > 0) {
			    isin = isins.get(0);
		        }
	    
        	        log.debug("creating StockInfo record for ISIN '" + isin + "'");			
        		
        		StockInfo si = new StockInfo(isin);
        	        Stock2StockInfoConverter.applyUpdates(stock, si);
        
        	        log.debug("yahoo data applied to StockInfo record for ISIN '" + isin + "'");						
        	        
        			siRepo.save(si);	
        			
        	        log.debug("StockInfo record for ISIN '" + isin + "' saved");						
		}
		siRepo.flush();

	    log.info("Stock info committed to database!");		
	    
	}
	
}