package de.dlopes.stocks.facilitator.services;

import java.util.List;

import de.dlopes.stocks.facilitator.data.StockInfo;

public interface StockInfoService {

	public List<StockInfo> findAll();
	
	public List<StockInfo> loadAll();
	
	//public void persist(StockInfoImpl stockInfo);

}
