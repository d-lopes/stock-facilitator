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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yahoofinance.Stock;
import de.dlopes.stocks.facilitator.config.ConfigurationSettings;
import de.dlopes.stocks.facilitator.data.StockInfo;
import de.dlopes.stocks.facilitator.data.StockInfoRepository;
import de.dlopes.stocks.facilitator.data.util.Stock2StockInfoConverter;
import de.dlopes.stocks.facilitator.services.FinanzenNetIndexHTMLISINExtractor;
import de.dlopes.stocks.facilitator.services.StockInfoService;
import de.dlopes.stocks.facilitator.services.impl.util.SFApplicationException;
import de.dlopes.stocks.facilitator.ui.forms.AddStocksForm;

@Service("stockInfoService")
public class StockInfoServiceImpl implements StockInfoService {

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
	public void addStocks(AddStocksForm addStocksForm) {
	    System.out.println("addStocks invoked!");
	    
	    String url = addStocksForm.getUrl();
	    
	    FinanzenNetIndexHTMLISINExtractor fn_isin_extr = new FinanzenNetIndexHTMLISINExtractor();
	    if (!fn_isin_extr.isApplicable(url)) {
	       throw new RuntimeException("provided URL is not applicable for automatic parse of ISINs.");    
	    }
	    
	    // get the ISINs 
	    List<String> isins = fn_isin_extr.getISINs(url);
		// TODO: remove ISINs which already exist in the DB from list
		
		// receive the mapping 
		Map<String,String> symbols2isins = null;
		try {
	       symbols2isins = YahooFinanceDataCollectorImpl.getSymbols4ISINs(isins);
	    } catch (SFApplicationException sfae) {
	        throw new RuntimeException("Yahoo Symbols could not be loaded", sfae);    
	    }
	    
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
        
        // load stock details
        Map<String,Stock> stocks = null;
        try {
	        stocks = YahooFinanceDataCollectorImpl.requestStocks(symbols2isins.keySet());
        } catch (SFApplicationException sfae) {
	        throw new RuntimeException("Stock details could not be loaded", sfae);    
	    }
        
		for (String symbol : stocks.keySet()) {
			
			Stock stock = stocks.get(symbol);	
			String isin = symbols2isins.get(symbol);
			
			StockInfo si = new StockInfo(isin);
			Stock2StockInfoConverter.applyUpdates(stock, si);
			
			siRepo.save(si);	
		}
		siRepo.flush();
		
	}
	
}