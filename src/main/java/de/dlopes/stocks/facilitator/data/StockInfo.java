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

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

import org.springframework.util.StringUtils;

@Data
@Entity
@Table(name="stockinfo")
public class StockInfo implements Serializable {
	
	private static final long serialVersionUID = 2854745888464616931L;

	// moved to stockID.java:
	//
	// private String WKN;

	@Id
	private Long id;

	// ISIN will serve as unique ID througout the application
	@Column(unique = true)
	private String isin;
	
	private String name;
	private BigDecimal price;
	private BigDecimal bid;
	private BigDecimal ask;
	private BigDecimal changeInPercent;
	private BigDecimal change;
    
    //private String index;	
	//private LocalTime time;
	
	@OneToMany(mappedBy="stock")
	private List<StockID> extIDs;

	@OneToMany(mappedBy="stock")
	private List<StockKPI> kpis;


/* constructors for safer management of one-to-many relationships */
	
	public StockInfo(String isin) {
	    this.isin = isin;		
	}
	
/*
 * additional useful methods 
 */
	
	public String getExtID(StockID.PROVIDER provider) {
	    
	    // guard: we can only consider non-null values
	    if (provider == null) {
	        return null;
	    }
	    
	    // guard: we can only return null when the list of external IDs has not even been
	    // created
	    if (extIDs == null) {
	        return null;
	    }
	    
	    // try to find by the name of the provider
	    for (StockID sid : extIDs) {
	        if (provider.equals(sid.getProvider())) {
	            return sid.getValue();
	        }
	    }
	    
	    // per default: return NULL
	    return null;
	}
	
	public void setExtID(StockID.PROVIDER provider, String value) throws IllegalArgumentException {
	    
	    // guard: we can only accept non-null providers
	    if (provider == null || StringUtils.isEmpty(value)) {
	        throw new IllegalArgumentException("provider must not be null");
	    }
	    
	    // guard: we can only accept non-empty values	    
	    if (StringUtils.isEmpty(value)) {
	        throw new IllegalArgumentException("external ID value must not be empty");
	    }
	    
	    // if the list of StockIDs does not exist yet, then we have to create it in order to 
	    // avoid a NPE in the next step
	    if (extIDs == null) {
	        extIDs = new ArrayList<StockID>();
	    }
	    
	    // guard: avoid updates to an external ID
	    for (StockID sid : extIDs) {
	        if (provider.equals(sid.getProvider())) {
	            throw new IllegalArgumentException("external ID must not be overwritten with a new value");
	        }
	    }
	    
	    // add the new external ID
	    extIDs.add(new StockID(provider, value, this));
	    
	}
	
}