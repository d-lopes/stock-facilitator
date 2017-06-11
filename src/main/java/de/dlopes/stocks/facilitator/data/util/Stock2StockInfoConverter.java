package de.dlopes.stocks.facilitator.data.util;

import java.math.BigDecimal;
import java.util.Calendar;

import yahoofinance.Stock;
import yahoofinance.quotes.stock.StockDividend;
import yahoofinance.quotes.stock.StockQuote;
import yahoofinance.quotes.stock.StockStats;
import de.dlopes.stocks.facilitator.data.StockID;
import de.dlopes.stocks.facilitator.data.StockInfo;
import de.dlopes.stocks.facilitator.data.StockKPI;

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

        // currency 
        stock.getCurrency();        
        
        // stock exchange
        stock.getStockExchange();

        // update of remaining header data
        StockQuote q = stock.getQuote();
        
        if (q != null) {
            
            BigDecimal price = q.getPrice();
            if (price != null) {
                stockInfo.setPrice(price);
            }
            
            BigDecimal bid = q.getBid();
            if (bid != null) {
                stockInfo.setBid(bid);
            }
            
            Long askSize = q.getAskSize();
            if (askSize != null) {
                stockInfo.setAskSize(askSize);
            }
            
            Long bidSize = q.getBidSize();
            if (bidSize != null) {
                stockInfo.setBidSize(bidSize);
            }
            
            BigDecimal previousClose = q.getPreviousClose();            
            if (previousClose != null) {
                stockInfo.setPreviousClose(previousClose);
            }
            
            BigDecimal open = q.getOpen();
            if (open != null) {
                stockInfo.setOpen(open);
            }
            
            BigDecimal dayHigh = q.getDayHigh();
            if (dayHigh != null) {
                stockInfo.setDayHigh(open);
            }
            
            BigDecimal dayLow = q.getDayLow();
            if (dayLow != null) {
                stockInfo.setDayLow(dayLow);
            }
            
            Long volume = q.getVolume();
            if (volume != null) {
                stockInfo.setVolume(volume);
            }
            
            Long lastTradeSize = q.getLastTradeSize();
            if (lastTradeSize != null) {
                stockInfo.setLastTradeSize(lastTradeSize);
            }
            
            Calendar lastTradeTime = q.getLastTradeTime();
            if (lastTradeTime != null) {
                stockInfo.setLastTradeTime(lastTradeTime);
            }
            
            BigDecimal ask = q.getAsk();
            if (ask != null) {
                stockInfo.setAsk(ask);
            }
            
            BigDecimal change = q.getChange();
            if (change != null) {
                stockInfo.setChange(change);
                
                BigDecimal changeInPercent = q.getChangeInPercent();
                if (changeInPercent != null) {
                    stockInfo.setChangeInPercent(changeInPercent);
                }
    
            }
            
            // long values -> need to be transformed into BigDecimal in order to have the fitting data type
            Long avgVolumn = q.getAvgVolume();
            if (avgVolumn != null) {
                stockInfo.setKPI(StockKPI.TYPE.AVGVOLUME, BigDecimal.valueOf(avgVolumn));
            }
            
            stockInfo.setKPI(StockKPI.TYPE.CHANGEFROMAVG200, q.getChangeFromAvg200());
            stockInfo.setKPI(StockKPI.TYPE.CHANGEFROMAVG200INPERCENT, q.getChangeFromAvg200InPercent());
            stockInfo.setKPI(StockKPI.TYPE.CHANGEFROMAVG50, q.getChangeFromAvg50());
            stockInfo.setKPI(StockKPI.TYPE.CHANGEFROMAVG50INPERCENT, q.getChangeFromAvg50InPercent());
            stockInfo.setKPI(StockKPI.TYPE.CHANGEFROMYEARHIGH, q.getChangeFromYearHigh());
            stockInfo.setKPI(StockKPI.TYPE.CHANGEFROMYEARHIGHINPERCENT, q.getChangeFromYearHighInPercent());
            stockInfo.setKPI(StockKPI.TYPE.CHANGEFROMYEARLOW, q.getChangeFromYearLow());
            stockInfo.setKPI(StockKPI.TYPE.CHANGEFROMYEARLOWINPERCENT, q.getChangeFromYearLowInPercent());            
                        
            stockInfo.setKPI(StockKPI.TYPE.PRICEAVG200, q.getPriceAvg200());
            stockInfo.setKPI(StockKPI.TYPE.PRICEAVG50, q.getPriceAvg50());
            
            stockInfo.setKPI(StockKPI.TYPE.YEARHIGH, q.getYearHigh());
            stockInfo.setKPI(StockKPI.TYPE.YEARLOW, q.getYearLow());            

        }

        // get dividend data 
        StockDividend d = stock.getDividend();
        if (d != null) {
            stockInfo.setKPI(StockKPI.TYPE.DIV_ANNUALYIELD, d.getAnnualYield());
            stockInfo.setKPI(StockKPI.TYPE.DIV_ANNUALYIELDPERCENT, d.getAnnualYieldPercent());            

            stockInfo.setDividendExDate(d.getExDate());
            stockInfo.setDividendPayDate(d.getPayDate());
        }

        // load KPIs
        StockStats s = stock.getStats();
        if (s != null) {
            stockInfo.setKPI(StockKPI.TYPE.BOOKVALUEPERSHARE, s.getBookValuePerShare());
            stockInfo.setKPI(StockKPI.TYPE.EBITDA, s.getEBITDA());
            stockInfo.setKPI(StockKPI.TYPE.EPS, s.getEps());
            stockInfo.setKPI(StockKPI.TYPE.EPSESTIMATECURRENTYEAR, s.getEpsEstimateCurrentYear());
            stockInfo.setKPI(StockKPI.TYPE.EPSESTIMATENEXTQUARTER, s.getEpsEstimateNextQuarter());
            stockInfo.setKPI(StockKPI.TYPE.EPSESTIMATENEXTYEAR, s.getEpsEstimateNextYear());
            stockInfo.setKPI(StockKPI.TYPE.MARKETCAP, s.getMarketCap());
            stockInfo.setKPI(StockKPI.TYPE.ONEYEARTARGETPRICE, s.getOneYearTargetPrice());
            stockInfo.setKPI(StockKPI.TYPE.PE, s.getPe());
            stockInfo.setKPI(StockKPI.TYPE.PEG, s.getPeg());
            stockInfo.setKPI(StockKPI.TYPE.PRICEBOOK, s.getPriceBook());
            stockInfo.setKPI(StockKPI.TYPE.PRICESALES, s.getPriceSales());
            stockInfo.setKPI(StockKPI.TYPE.REVENUE, s.getRevenue());
            stockInfo.setKPI(StockKPI.TYPE.ROE, s.getROE());
            stockInfo.setKPI(StockKPI.TYPE.SHORTRATIO, s.getShortRatio());
            
            // long values -> need to be transformed into BigDecimal in order to have the fitting data type
            Long sharesFloat = s.getSharesFloat();
            if (sharesFloat != null) {
                stockInfo.setKPI(StockKPI.TYPE.SHARESFLOAT, BigDecimal.valueOf(sharesFloat));
            }

            Long sharesOutstanding = s.getSharesOutstanding();
            if (sharesOutstanding != null) {
                stockInfo.setKPI(StockKPI.TYPE.SHARESOUTSTANDING, BigDecimal.valueOf(sharesOutstanding));
            }
            
            Long sharesOwned = s.getSharesOwned();
            if (sharesOwned != null) {
                stockInfo.setKPI(StockKPI.TYPE.SHARESOWNED, BigDecimal.valueOf(sharesOwned));
            }

        }
        
    }
    
}
