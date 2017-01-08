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

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockInfoRepository extends JpaRepository<StockInfo, Long> {

	// CRUD operations inherited from parent interface
	
	// get stockinfo record by its 
	StockInfo findFirstByIsin(String isin);
	
	// get all stock info records with missing ext. IDs of a certain provider (currently just YAHOO)
	List<StockInfo> findAllByExtIDsProviderAndExtIDsValueIsNull(StockID.PROVIDER provider);
	
	// get all stock info records with existing ext. IDs of a certain provider (currently just YAHOO)
	List<StockInfo> findAllByExtIDsProviderAndExtIDsValueIsNotNull(StockID.PROVIDER provider);
	
}
