package de.dlopes.stocks.facilitator.data.util;

import yahoofinance.Stock;
import yahoofinance.quotes.stock.StockQuote;
import de.dlopes.stocks.facilitator.data.StockID;
import de.dlopes.stocks.facilitator.data.StockInfo;

public class Stock2StockInfoConverter {
    
    public static void applyUpdates(Stock stock, StockInfo stockInfo) {
        
        // name must not be overwritten
        if (stockInfo.getName() == null) {
            stockInfo.setName(stock.getName());
        }

        // Yahoo Symbol must not be overwritten        
        if (stockInfo.getExtID(StockID.PROVIDER.YAHOO) == null) {
            stockInfo.setExtID(StockID.PROVIDER.YAHOO, stock.getSymbol());
        }

        // update of remaining header data
        StockQuote q = stock.getQuote();
        stockInfo.setPrice(q.getPrice());
	    stockInfo.setBid(q.getBid());
	    stockInfo.setAsk(q.getAsk());
	    stockInfo.setChangeInPercent(q.getChangeInPercent());
        stockInfo.setChange(q.getChange());
        
    }
    
}
