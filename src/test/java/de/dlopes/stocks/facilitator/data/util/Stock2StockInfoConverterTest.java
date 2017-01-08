package de.dlopes.stocks.facilitator.data.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.dlopes.stocks.facilitator.data.StockID;
import de.dlopes.stocks.facilitator.data.StockInfo;
import yahoofinance.Stock;
import yahoofinance.quotes.stock.StockQuote;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Stock2StockInfoConverterTest {
    
    @Test
    public void testApplyUpdates() {
        
        // prepare test data:
        String isin = "DE000BAY0017";
        String symbol = "BAY.DE";
        String name = "Bayer";
        
        BigDecimal price = null;
        BigDecimal bid = null;
        BigDecimal ask = null;
        BigDecimal changeInPercent = null;
        BigDecimal change = null;
        
        StockInfo si = new StockInfo(isin);
        
        Stock mockedStock = mock(Stock.class);
        when(mockedStock.getSymbol()).thenReturn(symbol);
        when(mockedStock.getName()).thenReturn(name);
        
        StockQuote mockedStockQuote = mock(StockQuote.class);
        when(mockedStock.getQuote()).thenReturn(mockedStockQuote);
        
        when(mockedStockQuote.getPrice()).thenReturn(price);
        when(mockedStockQuote.getBid()).thenReturn(bid);
        when(mockedStockQuote.getAsk()).thenReturn(ask);
        when(mockedStockQuote.getChangeInPercent()).thenReturn(changeInPercent);
        when(mockedStockQuote.getChange()).thenReturn(change);
         
        // run test:
        Stock2StockInfoConverter.applyUpdates(mockedStock, si);
   
        assertEquals("ISIN has not remained!", isin, si.getIsin());
        assertEquals("Symbol was not taken over!", symbol, si.getExtID(StockID.PROVIDER.YAHOO));
        assertEquals("Name was not taken over!", name, si.getName());
        
        assertEquals("Price was not taken over!", price, si.getPrice());
        assertEquals("Bid was not taken over!", bid, si.getBid());
        assertEquals("Ask was not taken over!", ask, si.getAsk());
        assertEquals("Change (in percent) was not taken over!", changeInPercent, si.getChangeInPercent());
        assertEquals("Change (absolute)was not taken over!", change, si.getChange());
        
    }
    
}
