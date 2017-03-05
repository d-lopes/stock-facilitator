package de.dlopes.stocks.facilitator.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import de.dlopes.stocks.facilitator.services.impl.util.SFApplicationException;

public class YahooFinanceDataCollectorImpl {
    	
/*  CAUTION: this code is not usable anymore due to serious issues with unreachable Yahoo datatables   	
    
    private static final String BASE_URL = "http://query.yahooapis.com/v1/public/yql?q=";	    
	private static final String DFLT_SUFFIX = "&format=json&env=store://datatables.org/alltableswithkeys";
	
    public static Map<String,String> getSymbols4ISINs(List<String> isins) throws SFApplicationException {

        // For some reason, not all symbols for the requested ISINs are returned in the JSON response 
        // from Yahoo always. Occasionally, there is NULL returned for some ISINs - example:
        // 
        // ... "results":{"stock":[{...,{"symbol":"DE000PR1LNX1","Isin":null}]}
        // 
        // This is not a permanent issue. Sometimes the information is there and sometimes it isn't. 
        // 
        // So, the strategy here is to collect the mappings that have been returned correctly and to 
        // request the missing mappings again. We repeat that process until we have all mappings 
        // together or until the 5th attempt - if we still don't have all data together, then we most 
        // likely will never receive it (probably it is then not contained in the yahoo data tables).
        //
        // the whole logic needed, is contained in the recursive internal method: 
	    return getISIN2SymbolMapping(isins, 0);
    }

	private static Map<String,String> getISIN2SymbolMapping(List<String> isins, int run)throws SFApplicationException  {
	
	    // Define the logger object for this class
        Logger log = LoggerFactory.getLogger(YahooFinanceDataCollectorImpl.class);	
 
	    Map<String,String> result = new HashMap<String,String>();
        
        log.debug("ISINs without mapping: " + isins);
        log.debug("run: " + run);
        
	    // exit condition: if no isins are provided or we are running this method for the 5th time,
	    // then we return immediately with an empty result
        if (isins.size() < 1 || run >= 5) {
            return result;
        }
        
        // unfortunately, the yahoo data tables API does not generate its JSON response consistently:
        // when MULTIPLE results are returned, then they are wrapped in a LIST, 
        // but when only a SINGLE result is returned, then this is just a PLAIN mapping. 
        //
        // example 1 (multiple):
        //
        // ... "results":{"stock":
        //                  [ <-- there is a bracket
        //                      {"symbol":"DE000A1EWWW0","Isin":"ADS.DE"}, ...
        //
        //      vs.
        //
        // example 2 (single):
        //
        // ... "results":{"stock": 
        //                      <-- there is NO bracket
        //                  {"symbol":"DE000A1EWWW0","Isin":"ADS.DE"} } ...
        // 
        // This issue can only be solved here, by making sure that we always request at least 2 
        // mappings at a time
        if (isins.size() == 1) {
            // we add a special value here which we can later neglegt again
            isins.add(YahooSymbolIsinMapping.DUMMY_ISIN);
        }
        
	    // build query to yahoo finance data tables based on ISIN
	    String qry = (new StringBuilder())
	               .append("select * from yahoo.finance.isin where symbol in ('")
	               .append(StringUtils.collectionToDelimitedString(isins, "','"))
	               .append("')").toString();
	    
	    // get the data
	    try {
	        
	        // prepare jackson object mapper for automatic mapping of JSON result to a POJO
	        ObjectMapper mapper = new ObjectMapper();
            //mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY));

	        // build URL
	        String urlStr = BASE_URL + URLEncoder.encode(qry.toString(), "UTF-8") + DFLT_SUFFIX;    
	        log.info("requesting URL: {}", urlStr);
	        URL url = new URL(urlStr);    	    
	        
	        // request and parse the result
    	    YahooSymbolIsinMapping ysim = mapper.readValue(url, YahooSymbolIsinMapping.class);	        	        
    	    
    	    // add all mappings that we could determine with this run
    	    result.putAll(ysim.covertToMap());
    	    
    	    // additionally, add the outcome of the another invocation of this method based on 
    	    // the ISINs where we did not get a mapping in the first place
    	    result.putAll(getISIN2SymbolMapping(ysim.getIsinsWithoutMapping(), ++run));
    	    
	    } catch (MalformedURLException mue) {
	        throw new SFApplicationException("Error during load of symbols from yahoo finance data URL", mue);
	    } catch (JsonParseException jpe) {
	        throw new SFApplicationException("Error during parse of yahoo finance data request", jpe);	        
	    } catch (JsonMappingException jme) {
	        throw new SFApplicationException("Error during mapping of yahoo finance data request", jme);	        
	    } catch (IOException ioe) {
	        throw new SFApplicationException("Low-level I/O problem occured during mapping of response from yahoo finance data URL", ioe);	        
	    }
	    
    	return result;
    	    
	}
	*/
	
	public static Map<String,Stock> requestStocks(List<String> symbols) throws SFApplicationException {
	    Map<String,Stock> result = new HashMap<String,Stock>();
	    
	    String[] yhooSymbols = symbols.toArray(new String[symbols.size()]);
	    try {
            Map<String,Stock> quotes = YahooFinance.get(yhooSymbols);
            result = quotes;
        } catch (IOException ioe) {
            throw new SFApplicationException("Error during load of stock details for symbols from yahoo finance API", ioe);
        }
	    
        return result;
    }
	
}
