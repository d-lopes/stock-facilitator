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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dlopes.stocks.facilitator.config.ConfigurationSettings;
import de.dlopes.stocks.facilitator.data.StockInfo;
import de.dlopes.stocks.facilitator.data.StockInfoRepository;
import de.dlopes.stocks.facilitator.services.StockDataCollector;
import de.dlopes.stocks.facilitator.services.StockInfoService;

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
	public List<StockInfo> loadAll() {
		StockDataCollector dataCollector = cs.getDataCollector();
		List<StockInfo> siList = dataCollector.getData();
		
		for (StockInfo si : siList) {
			siRepo.save(si);	
		}
		siRepo.flush();
		
		return findAll();
	}

}