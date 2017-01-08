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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FinanzenNetIndexHTMLISINExtractorTest {
	
	// test isins: DAX
	public static final List<String> DAX_TEST_ISINS = new ArrayList<String>();
	static {
	    DAX_TEST_ISINS.add("DE000A1EWWW0"); 
	    DAX_TEST_ISINS.add("DE0008404005");
        DAX_TEST_ISINS.add("DE000BASF111");
        DAX_TEST_ISINS.add("DE000BAY0017");
        DAX_TEST_ISINS.add("DE0005200000");
        DAX_TEST_ISINS.add("DE0005190003");
        DAX_TEST_ISINS.add("DE000CBK1001");
        DAX_TEST_ISINS.add("DE0005439004");
        DAX_TEST_ISINS.add("DE0007100000");
        DAX_TEST_ISINS.add("DE0005140008");
        DAX_TEST_ISINS.add("DE0005810055");
        DAX_TEST_ISINS.add("DE0008232125");
        DAX_TEST_ISINS.add("DE0005552004");
        DAX_TEST_ISINS.add("DE0005557508");
        DAX_TEST_ISINS.add("DE000ENAG999");
        DAX_TEST_ISINS.add("DE0005785802");
        DAX_TEST_ISINS.add("DE0005785604");
        DAX_TEST_ISINS.add("DE0006047004");
        DAX_TEST_ISINS.add("DE0006048432");
        DAX_TEST_ISINS.add("DE0006231004");
        DAX_TEST_ISINS.add("DE0006483001");
        DAX_TEST_ISINS.add("DE0006599905");
        DAX_TEST_ISINS.add("DE0008430026");
        DAX_TEST_ISINS.add("DE000PSM7770");
        DAX_TEST_ISINS.add("DE0007037129");
        DAX_TEST_ISINS.add("DE0007164600");
        DAX_TEST_ISINS.add("DE0007236101");
        DAX_TEST_ISINS.add("DE0007500001");
        DAX_TEST_ISINS.add("DE0007664039");
        DAX_TEST_ISINS.add("DE000A1ML7J1");
    };

	private static final String URL = "file://./src/test/resources/test.html";
	        
	private static FinanzenNetIndexHTMLISINExtractor _classUnderTest;
	
	@BeforeClass
	public static void setup() {
		_classUnderTest = new FinanzenNetIndexHTMLISINExtractor();
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
	    String _url = FinanzenNetIndexHTMLISINExtractor.PREFIX + "/aksjdkjalsd";
		assertTrue("URL '" + _url + "'is appliable", _classUnderTest.isApplicable(_url));	    
    }
	
	@Test
	public void testGetISINs() {
		
		List<String> isins = _classUnderTest.getISINs(URL);
		assertEquals("unexpected number of stocks", DAX_TEST_ISINS.size(), isins.size());
		
		String strArr = StringUtils.collectionToCommaDelimitedString(isins);
		
		for (String isin : DAX_TEST_ISINS) {
			
			// assert all expected values are found in the result 
			assertTrue("ISIN " + isin + " is not included in " + strArr, CollectionUtils.contains(isins.iterator(), isin));
			
		}
	}

}