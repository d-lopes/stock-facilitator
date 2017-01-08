package de.dlopes.stocks.facilitator.data.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class YahooSymbolIsinMappingTest {

    YahooSymbolIsinMapping _classUnderTest;
    List<Map<String,String>> stocks;
    
    @Before
    public void setup() {
        _classUnderTest = new YahooSymbolIsinMapping();
        _classUnderTest.query = _classUnderTest.new Query();
        stocks = new ArrayList<Map<String,String>>();
        
        Map<String, List<Map<String,String>>> results = new HashMap<String, List<Map<String,String>>>();
        results.put("stock", stocks);
        
        _classUnderTest.query.results = results;

    }
    
    @Test
    public void testConvertToMap() {
        
        // setup test data: 
        //   ... "results":{"stock":
        //                      [
        //                          {"symbol":"DE0005785802","Isin":"FME.DE"},
        //                          {"symbol":"DE000BAY0017","Isin":"BAYN.DE"},
        //                          {"symbol":"DE000PSM7770","Isin":"PSM.DE"},
        //                          {"symbol":"DE0006231004","Isin":"DTB.DE"},
        //                          {"symbol":"DE000CBK1001","Isin":"DBK.DE"},
        //                          {"symbol":"DE000A1ML7J1","Isin":"VON.DE"}
        //                      ]}}}        
        String[] isins = new String[] { "DE0005785802", "DE000BAY0017", "DE000PSM7770", "DE0006231004", 
                                        "DE000CBK1001", "DE000A1ML7J1"};
        String[] symbols = new String[] { "FME.DE", "BAYN.DE", "PSM.DE", "DTB.DE", "DBK.DE", "VON.DE" };
        for (int i = 0; i < isins.length; i++) {
            createTestDataEntry(isins[i], symbols[i]);
        }

        // run the test:
        Map<String,String> map = _classUnderTest.covertToMap();
        
        // assert there is only one value in the list
        assertEquals("unexpected amount of results!", isins.length, map.size());
        
        // assert only all mappings have been transfered
        for (int i = 0; i < isins.length; i++) {
            assertEquals("Wrong ISIN for symbol " + symbols[i] + " found!", isins[i], map.get(symbols[i]));
        }
        
    }
    
    @Test
    public void testConvertToMapWithDummyISIN() {
        
        // setup test data: 
        //   ... "results":{"stock":
        //                      [
        //                          {"symbol":"DE0005785802","Isin":"FME.DE"},
        //                          {"symbol":"XX0000000000","Isin":null}
        //                      ]}}}        
        createTestDataEntry("DE0005785802", "FME.DE");
        createTestDataEntry(YahooSymbolIsinMapping.DUMMY_ISIN, null);
        
        // run the test:
        Map<String,String> map = _classUnderTest.covertToMap();
        
        // assert there is only one value in the list
        assertEquals("unexpected amount of results!", 1, map.size());
        
        // assert only the mapping for the valid ISIN is in the list => that means the dummy ISIN was 
        // neglected
        assertEquals("Mapping for valid ISIN was not found in the result!", "DE0005785802", map.get("FME.DE"));
        
    }
    
    @Test
    public void testGetIsinsWithoutMapping() {
        
        // setup test data: 
        //   ... "results":{"stock":
        //                      [
        //                          {"symbol":"DE0005785802","Isin":"FME.DE"},
        //                          {"symbol":"DE000BAY0017","Isin":null},
        //                          {"symbol":"DE000PSM7770","Isin":"PSM.DE"},
        //                          {"symbol":"DE0006231004","Isin":null"},
        //                          {"symbol":"DE000CBK1001","Isin":null"},
        //                          {"symbol":"DE000A1ML7J1","Isin":"VON.DE"}
        //                      ]}}}        
        String[] isins = new String[] { "DE0005785802", "DE000BAY0017", "DE000PSM7770", "DE0006231004", 
                                        "DE000CBK1001", "DE000A1ML7J1"};
        String[] symbols = new String[] { "FME.DE", null, "PSM.DE", null, null, "VON.DE" };
        int nullValues = 0;
        for (int i = 0; i < isins.length; i++) {
            createTestDataEntry(isins[i], symbols[i]);
            nullValues += symbols[i] == null ? 1 : 0; // count the null values in the symbols
        }

        // run the test:
        Map<String,String> map = _classUnderTest.covertToMap();
        List<String> list = _classUnderTest.getIsinsWithoutMapping();
        
        // assert there is only one value in the list
        assertEquals("unexpected amount of results!", isins.length - nullValues, map.size());
        
        // assert only the valid mapping have been transfered
        for (int i = 0; i < isins.length; i++) {
            if (symbols[i] == null) {
                continue;
            }
            assertEquals("Wrong ISIN for symbol " + symbols[i] + " found!", isins[i], map.get(symbols[i]));
        }
        
        // assert that all missing mapping are found
        for (int i = 0; i < isins.length; i++) {
            if (symbols[i] != null) {
                continue;
            }
            assertTrue("ISIN " + isins[i] + " not recognized as missing mapping!", list.contains(isins[i]));
        }
        
    }
    
    /* ------------------- convinience methods ----------------------------*/
   
    private void createTestDataEntry(String isin, String symbol) {
        Map<String,String> entry = new HashMap<String,String>();
        entry.put("symbol", isin);
        entry.put("Isin", symbol);
        
        stocks.add(entry);
    }
    
    
}
