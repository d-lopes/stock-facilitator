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
package de.dlopes.stocks.facilitator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.dlopes.stocks.data.StockInfo;
import de.dlopes.stocks.facilitator.config.ConfigurationSettings;
import de.dlopes.stocks.facilitator.data.StockInfoImpl;
import de.dlopes.stocks.facilitator.data.StockInfoRepository;
import de.dlopes.stocks.facilitator.services.StockDataCollector;

@Controller
public class HomeController {

	@Autowired
	ConfigurationSettings cs;
	
	@Autowired
	StockInfoRepository siRepo;
	
	@RequestMapping("/secured/home")
    public String home(Model model) {
		
		List<StockInfoImpl> siList = siRepo.findAll();
		model.addAttribute("stocks", siList);
		
		return "home";
		
    }
	
	@RequestMapping("/secured/stocks/update")
	public String update(Model model) {
		
		StockDataCollector dataCollector = cs.getDataCollector();
		List<StockInfo> siList = dataCollector.getData();
		
		for (StockInfo si : siList) {
			siRepo.save((StockInfoImpl) si);
		}
		siRepo.flush();
		
		// reload homepage with updated values again
		return home(model);
    }
	
	
}