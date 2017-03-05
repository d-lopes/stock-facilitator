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

import java.util.List;


public interface FinanceDataExtractor {
    
    public static enum FinanceDataType{
        YahooSymbol, ISIN
    };

	public boolean isApplicable(String url);
	
	public List<String> getFinanceData(String url, FinanceDataType dataType);	
	
	public List<String> getFinanceData(String url);
	
}