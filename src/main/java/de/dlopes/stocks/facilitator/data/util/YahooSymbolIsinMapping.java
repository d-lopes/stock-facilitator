package de.dlopes.stocks.facilitator.data.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;


public class YahooSymbolIsinMapping {

    // due to the fact that we always have to request at least to mappings, we have to define a known
    // dummy value which we can neglect again
    public static final String DUMMY_ISIN = "XX0000000000";

    @Getter
    public class Query {

        @JsonProperty(value = "count")
        public Integer count;
        
        @JsonProperty(value = "created")
        public String created;
        
        @JsonProperty(value = "lang")
        public String lang;
    
        @JsonProperty(value = "results")
        public Map<String,List<Map<String,String>>> results;

    }

    @JsonProperty(value = "query")
    public Query query;
    
    // this is only a helper variable 
    // we need to tell jackson to ignore this field otherwise it will try to map something to this
    //@JsonIgnore
    @Getter
    private List<String> isinsWithoutMapping;
    
    public YahooSymbolIsinMapping() {
        isinsWithoutMapping = new ArrayList<String>();
    }
  
    /**
     * convinience method: to provide easier access on the content
     * The map consist of key-value-pairs of isins and symbols. 
     * 
     */
    public Map<String,String> covertToMap() {
        return covertToMap(true);
    }

    /**
     * convinience method: to provide easier access on the content
     * 
     * The map consist of key-value-pairs of isins and symbols. The direction can be controlled
     * with the param @symbol2isin
     * 
     * @symbol2isin - indicates if the direction of the mapping shall be symbol => isin / default: false
     */
    public Map<String,String> covertToMap(boolean symbol2isin) {
        
        Map<String,String> result = new HashMap<String,String>();
        
        // avoid NPE when convertToMap is used although query and query-results were not populated 
        // with JSON result 
        if (query == null || query.results == null) {
            return result;
        }
        
        // avoid NPE when convertToMap is used although stock were not populated as part of the 
        // query-results from above
        List<Map<String,String>> stocks = query.results.get("stock");
        if (stocks == null) {
            return result;
        }
        
        // transform the results
        for (Map<String,String> s : stocks) {
            
            // for some reason ISIN and symbol are mixed up in the response from yahoo, so that ISIN 
            // represents the symbol and symbol represents the ISIN - example:
            // 
            // ... "results":{"stock":[{"symbol":"DE000A1EWWW0","Isin":"ADS.DE"}, ...
            // 
            // later we want to request symbols by ISIN. Therefore we have to use the data fields in the 
            // "wrong" way:
            String symbol = s.get("Isin");
            String isin = s.get("symbol");
            
            // due to the fact that we always have to request at least to mappings, there can be a dummy
            // value in the list which must not be considered further
            if (DUMMY_ISIN.equals(isin)) {
                continue;
            }
            
            // Appearently, not all symbols for the requested ISINs are returned in the JSON response 
            // from Yahoo always. Occasionally, there is NULL returned for some ISINs - example:
            // 
            // ... "results":{"stock":[{...,{"symbol":"DE000PR1LNX1","Isin":null}]}
            // 
            // This is not a permanent issue. Sometimes the information is there and sometimes it isn't. 
            // Therefore, we will store the ISINs without mapping in a seperate list so that the class,
            // that has initially invoked the request for ISIN to symbol mappings, can ask for the 
            // missing ISINs again.
            if (symbol == null) {
                isinsWithoutMapping.add(isin);
                continue;
            } 
            
            // currently, it is not entirely clear which was it is better
            // the direction of the mapping can be controlled with a param
            if (symbol2isin) {
                result.put(symbol, isin);    
            } else {
                result.put(isin, symbol);    
            }
            
        }
        
        return result;
    }
    
}
