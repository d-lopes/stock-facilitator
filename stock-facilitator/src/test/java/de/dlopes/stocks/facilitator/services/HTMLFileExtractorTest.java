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
package de.dlopes.stocks.facilitator.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.dlopes.stocks.data.StockInfo;
import de.dlopes.stocks.facilitator.StockFacilitatorApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(StockFacilitatorApplication.class)
public class HTMLFileExtractorTest {
	
	private static final String url = "file://./src/test/resources/test.html";
	
	private static HTMLFileExtractor _classUnderTest;
	
	@BeforeClass
	public static void setup() {
		_classUnderTest = new HTMLFileExtractor(url);
	}
	
	@Test
	public void testGetData() {
		
		String source = _classUnderTest.getSource();
		assertNotNull("Source is NUll!", source);
		
		List<StockInfo> stocks = _classUnderTest.getData();
		assertEquals("unexpected number of stocks", 30, stocks.size());
		
		for (StockInfo si : stocks) {
			
			// Guard: avoid empty names
			if ("".equals(si.getName())) {
				fail("empty name for Object" + si.toString());
			}
			
			// Guard: avoid DAX index
			if ("DAX".equals(si.getName())) {
				fail("DAX included in list" + si.toString());
			}
			
			// check some values
			if ("adidas".equals(si.getName())) {
				// check values for addidas
				assertEquals("unexpected WKN for adidas", "A1EWWW" , si.getWKN());
				assertEquals("unexpected ISIN for adidas", "DE000A1EWWW0" , si.getISIN());
				assertEquals("unexpected Price for adidas", new Double(126.55) , si.getPrice());
				assertEquals("unexpected Bid for adidas", new Double(126.38) , si.getBid());
				assertEquals("unexpected Ask for adidas", new Double(126.83) , si.getAsk());
				assertEquals("unexpected Change (percentage) for adidas", new Double(-0.13) , si.getChangePercentage());
				assertEquals("unexpected Change (absolute) for adidas", new Double(-0.17) , si.getChangeAbsolute());
				assertEquals("unexpected Time for adidas", "18:59:59" , si.getTime().format(DateTimeFormatter.ISO_TIME));
			} else if ("E.ON".equals(si.getName())) {
				// check values for E.ON
				assertEquals("unexpected WKN for E.ON", "ENAG99" , si.getWKN());
				assertEquals("unexpected ISIN for E.ON", "DE000ENAG999" , si.getISIN());
				assertEquals("unexpected Price for E.ON", new Double(9.06) , si.getPrice());
				assertEquals("unexpected Bid for E.ON", new Double(9.21) , si.getBid());
				assertEquals("unexpected Ask for E.ON", new Double(9.24) , si.getAsk());
				assertEquals("unexpected Change (percentage) for E.ON", new Double(1.66) , si.getChangePercentage());
				assertEquals("unexpected Change (absolute) for E.ON", new Double(0.15) , si.getChangeAbsolute());
				assertEquals("unexpected Time for E.ON", "18:59:59" , si.getTime().format(DateTimeFormatter.ISO_TIME));
			} else if ("Vonovia".equals(si.getName())) {
				// check values for Vonovia
				assertEquals("unexpected WKN for Vonovia", "A1ML7J" , si.getWKN());
				assertEquals("unexpected ISIN for Vonovia", "DE000A1ML7J1" , si.getISIN());
				assertEquals("unexpected Price for Vonovia", new Double(31.92) , si.getPrice());
				assertEquals("unexpected Bid for Vonovia", new Double(32.68) , si.getBid());
				assertEquals("unexpected Ask for Vonovia", new Double(32.82) , si.getAsk());
				assertEquals("unexpected Change (percentage) for Vonovia", new Double(2.38) , si.getChangePercentage());
				assertEquals("unexpected Change (absolute) for Vonovia", new Double(0.76) , si.getChangeAbsolute());
				assertEquals("unexpected Time for Vonovia", "18:59:59" , si.getTime().format(DateTimeFormatter.ISO_TIME));
			}
			
			
		}
	}

}