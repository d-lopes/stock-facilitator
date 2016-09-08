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
package de.dlopes.stocks.data;

import java.time.LocalTime;

public interface StockInfo {
	
	public String getIndex();
	public void setIndex(String index);
	
	public String getWKN();
	public void setWKN(String wKN);
	
	public String getISIN();
	public void setISIN(String iSIN);
	
	public String getName();
	public void setName(String name);
	
	public Double getPrice();
	public void setPrice(Double price);

	public Double getBid();
	public void setBid(Double bid);

	public Double getAsk();
	public void setAsk(Double ask);

	public Double getChangePercentage();
	public void setChangePercentage(Double changePercentage);

	public Double getChangeAbsolute();
	public void setChangeAbsolute(Double changeAbsolute);
	
	public void setTime(LocalTime timestamp);
	public LocalTime getTime();

}