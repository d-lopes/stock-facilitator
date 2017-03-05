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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import de.dlopes.stocks.facilitator.services.impl.YahooFinanceComIndexHTMLExtractorImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class YahooFinanceComIndexHTMLExtractorTest {
	
	// test isins: DAX
	public static final List<String> DAX_TEST_YSYM = new ArrayList<String>();
	static {
	    DAX_TEST_YSYM.add("VOW3.DE");
        DAX_TEST_YSYM.add("ALV.DE");
        DAX_TEST_YSYM.add("FRE.DE");
        DAX_TEST_YSYM.add("BAS.DE");
        DAX_TEST_YSYM.add("DAI.DE");
        DAX_TEST_YSYM.add("SAP.DE");
        DAX_TEST_YSYM.add("HEI.DE");
        DAX_TEST_YSYM.add("BMW.DE");
        DAX_TEST_YSYM.add("EOAN.DE");
        DAX_TEST_YSYM.add("LHA.DE");
        DAX_TEST_YSYM.add("SIE.DE");
        DAX_TEST_YSYM.add("FME.DE");
        DAX_TEST_YSYM.add("HEN3.DE");
        DAX_TEST_YSYM.add("BEI.DE");
        DAX_TEST_YSYM.add("TKA.DE");
        DAX_TEST_YSYM.add("BAYN.DE");
        DAX_TEST_YSYM.add("RWE.DE");
        DAX_TEST_YSYM.add("MUV2.DE");
        DAX_TEST_YSYM.add("DTE.DE");
        DAX_TEST_YSYM.add("IFX.DE");
        DAX_TEST_YSYM.add("PSM.DE");
        DAX_TEST_YSYM.add("VNA.DE");
        DAX_TEST_YSYM.add("CON.DE");
        DAX_TEST_YSYM.add("DPW.DE");
        DAX_TEST_YSYM.add("ADS.DE");
        DAX_TEST_YSYM.add("MRK.DE");
        DAX_TEST_YSYM.add("DBK.DE");
        DAX_TEST_YSYM.add("LIN.DE");
        DAX_TEST_YSYM.add("DB1.DE");
        DAX_TEST_YSYM.add("CBK.DE");
    };

	private static final String URL = "file://./src/test/resources/testYahoo.html";
	        
	private static YahooFinanceComIndexHTMLExtractorImpl _classUnderTest;
	
	@BeforeClass
	public static void setup() {
		_classUnderTest = new YahooFinanceComIndexHTMLExtractorImpl();
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
	    String _url = YahooFinanceComIndexHTMLExtractorImpl.PREFIX + "/aksjdkjalsd";
		assertTrue("URL '" + _url + "'is appliable", _classUnderTest.isApplicable(_url));	    
    }
	
	@Test
	public void testGetYahooSymbols() {
		
		List<String> isins = _classUnderTest.getFinanceData(URL);
		assertEquals("unexpected number of stocks", DAX_TEST_YSYM.size(), isins.size());
		
		String strArr = "[" + StringUtils.collectionToCommaDelimitedString(isins) + "]";
		
		for (String isin : DAX_TEST_YSYM) {
			
			// assert all expected values are found in the result 
			assertTrue("ISIN " + isin + " is not included in " + strArr, CollectionUtils.contains(isins.iterator(), isin));
			
		}
	}

}