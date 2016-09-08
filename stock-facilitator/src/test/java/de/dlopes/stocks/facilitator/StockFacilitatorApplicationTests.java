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
package de.dlopes.stocks.facilitator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StockFacilitatorApplication.class)
//@WebAppConfiguration <-- removed because this avoids context sharing with 
// class HTMLFileExtractorTest (this is crucial because otherwise the SQL 
// to create the database schema is run twice and will product on erro on 
// the second execution)
public class StockFacilitatorApplicationTests {

	@Test
	public void contextLoads() {
	}

}
