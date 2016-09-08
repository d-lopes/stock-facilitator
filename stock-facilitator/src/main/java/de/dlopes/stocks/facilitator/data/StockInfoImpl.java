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
package de.dlopes.stocks.facilitator.data;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import de.dlopes.stocks.data.StockInfo;
import lombok.Data;

@Data
@Entity
public class StockInfoImpl implements StockInfo {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String index;
	private String WKN;
	private String ISIN;
	private String name;
	private Double price;
	private Double bid;
	private Double ask;
	private Double changePercentage;
	private Double changeAbsolute;
	private LocalTime time;
	
}