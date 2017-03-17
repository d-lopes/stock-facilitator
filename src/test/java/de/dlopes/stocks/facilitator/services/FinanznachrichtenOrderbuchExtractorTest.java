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

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.dlopes.stocks.facilitator.services.FinanceDataExtractor.FinanceDataType;
import de.dlopes.stocks.facilitator.services.impl.FinanznachrichtenOrderbuchExtractorImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FinanznachrichtenOrderbuchExtractorTest {
 
private static final String URL = "file://./src/test/resources/testOrderbuch.html";

private static final String ISIN = "DE0007100000";
private static final String WKN = "710000";
	
private static FinanznachrichtenOrderbuchExtractorImpl _classUnderTest;
	
	@BeforeClass
	public static void setup() {
		_classUnderTest = new FinanznachrichtenOrderbuchExtractorImpl();
	}
	
	@Test
	public void testIsApplicableFails1() {
		assertFalse("NULL is not detected as invalid value!", _classUnderTest.isApplicable(null));
    }

    @Test
    public void testIsApplicableFails2() {
        String _url = "http://this.is-never-going.to/work";
		assertFalse(_url + "'is not detected as invald URL!", _classUnderTest.isApplicable(_url));	    
    }

    @Test
	public void testIsApplicable() {
	    String _url = FinanznachrichtenOrderbuchExtractorImpl.PREFIX + "/aksjdkjalsd";
		assertTrue("URL '" + _url + "'is appliable", _classUnderTest.isApplicable(_url));	    
    }
	
	@Test
	public void testGetISIN() {
	    
	    List<String> isins = _classUnderTest.getFinanceData(URL);
		assertNotNull("ISIN could not be extracted", isins);
		
		assertEquals("unexpected number of ISINs", 1, isins.size());
		
		assertEquals("unexpected ISIN extracted", ISIN, isins.get(0));		
	
    }
    
    @Test
	public void testGetWKN() {
	    
	    List<String> wkns = _classUnderTest.getFinanceData(URL, FinanceDataType.WKN);
		assertNotNull("WKN could not be extracted", wkns);
		
		assertEquals("unexpected number of WKNs", 1, wkns.size());
		
		assertEquals("unexpected WKN extracted", WKN, wkns.get(0));		
	
    }
    
}
