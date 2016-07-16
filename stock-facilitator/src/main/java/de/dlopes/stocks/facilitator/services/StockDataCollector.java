package de.dlopes.stocks.facilitator.services;

import java.util.List;

import de.dlopes.stocks.data.StockInfo;

public interface StockDataCollector {

	public String getSource();
	
	public List<StockInfo> getData();
	
}