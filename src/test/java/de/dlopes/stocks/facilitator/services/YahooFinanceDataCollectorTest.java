package de.dlopes.stocks.facilitator.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import yahoofinance.Stock;
import yahoofinance.quotes.stock.StockQuote;
import de.dlopes.stocks.facilitator.services.impl.YahooFinanceDataCollectorImpl;
import de.dlopes.stocks.facilitator.services.impl.util.SFApplicationException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class YahooFinanceDataCollectorTest {
    
    public static final List<String> TEST_ISINS = FinanzenNetIndexHTMLExtractorTest.DAX_TEST_ISINS;

    
    /*
     * removed from YahooFinanceDataCollectorImpl class due to usablility issues
     *
    @Test
    public void testGetSymbol4ISIN() {
                
        // request yahoo symbols
        Map<String,String> symbols4isins = null;
        try {
            symbols4isins = YahooFinanceDataCollectorImpl.getSymbols4ISINs(TEST_ISINS);
        } catch (SFApplicationException sfae) {
            sfae.printStackTrace();
            fail("SFApplicationException: " + sfae.getMessage() + " --> " + sfae.getCause().getMessage());
        }
        
        // ensure map is not null
        assertNotNull("no mapping returned at all!", symbols4isins);
                
        // ensure enough results were returned
        assertEquals("not enough mappings for ISINS returned!", TEST_ISINS.size(), symbols4isins.size());
        
        // ensure all symbols relate to the country germany (this is ensured by the ending '.DE')
        for (String s : symbols4isins.keySet()) {
            
            assertTrue("symbol " + s + " does not relate to the country germany !", s.endsWith(".DE"));            
        }
        
        // ensure each symbol belongs to an isin that we have requested
        for (String s : symbols4isins.keySet()) {
            
            String isin = symbols4isins.get(s);
            assertNotNull("no mapping for ISIN " + isin + " returned!", 
                                CollectionUtils.contains(TEST_ISINS.iterator(), isin));            
        }
    }    
    */
   
    @Test
    public void testRequestStocks() {
        
        // prepate test data:
        String[] syms = new String[] { "FME.DE", "BAYN.DE", "DBK.DE" };
        String[] names = new String[] {
            "FRESEN.MED.CARE KGAA O.N.", 
            "BAYER AG NA O.N.", 
            "DEUTSCHE BANK AG NA O.N." 
        };
        
        List<String> symbols = new ArrayList<String>();
        for (int i = 0; i < syms.length; i++) {
            symbols.add(syms[i]);
        }

        // run test
        Map<String,Stock> stocks = null;
        try {
            stocks = YahooFinanceDataCollectorImpl.requestStocks(symbols);
            
        } catch (SFApplicationException sfae) {
            sfae.printStackTrace();
            fail("SFApplicationException: " + sfae.getMessage() + " --> " + sfae.getCause().getMessage());
        }
        
        // ensure results were returned
        assertNotNull("No results received!", stocks);
        
        // ensure results for all requested stocks were returned
        assertEquals("Not enough results received!", symbols.size(), stocks.size());
        
        // check contents
        for (int i = 0; i < syms.length; i++) {
            Stock s = stocks.get(syms[i]);
            
            // ensure symbols match 
            assertEquals("Symbol " + syms[i] + " does not match!", syms[i] , s.getSymbol());
            
            // ensure names are correct
            assertEquals("Name for symbol " + syms[i] + "results received!", names[i], s.getName());
            
            // ensure stock quote was returned
            StockQuote q = s.getQuote();
            assertNotNull("No quote received for symbol " + syms[i] + "!", q);
            
            // ensure stock quote details were returned (there is no way to check the content as it is 
            // changing constantly. All we can do is check it anything was returned)
            assertNotNull("No price received for symbol " + syms[i] + "!", q.getPrice());
            
            // for some reason there is no info about Bid and Ask in the response - for all quotes:
            //  assertNotNull("No bid received for symbol " + syms[i] + "!", q.getBid());
            //  assertNotNull("No ask received for symbol " + syms[i] + "!", q.getAsk());
            
            assertNotNull("No change (in percent) received for symbol " + syms[i] + "!", q.getChangeInPercent());
            assertNotNull("No change (absolute) received for symbol " + syms[i] + "!", q.getChange());
            
        }
    }

    
}
