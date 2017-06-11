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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	// ISIN will serve as unique ID througout the application
	@Column(unique = true)
	private String isin;
	
	private String name;
	
	
	private BigDecimal previousClose;
    private BigDecimal open;
    private BigDecimal price;
	private BigDecimal dayHigh;
    private BigDecimal dayLow;
    
	private BigDecimal bid;
	private Long bidSize;
    
    private BigDecimal ask;
	private Long askSize;
    
    private BigDecimal changeInPercent;
	private BigDecimal change;
    
    private Long volume;

    //private String index;	
	private Calendar lastTradeTime;
    private Long lastTradeSize;
    private Calendar lastChanged;
    
	// dividend dates
	private Calendar dividendExDate;
	private Calendar dividendPayDate;
	
	@OneToMany(mappedBy="stock")
	private List<StockID> extIDs;

	@OneToMany(mappedBy="stock")
	private List<StockKPI> kpis;


/* constructors for safer management of one-to-many relationships */

    public StockInfo() {
	    this(null);
	}
	
	public StockInfo(String isin) {
	    this.isin = isin;		
	    
	    // TODO: move
        this.lastChanged = new GregorianCalendar();
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
	    if (provider == null) {
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
	
	
	public BigDecimal getKPI(StockKPI.TYPE type) {
	    
	    // guard: we can only consider non-null values
	    if (type == null) {
	        return null;
	    }
	    
	    // guard: we can only return null when the list of KPIs has not even been
	    // created
	    if (kpis == null) {
	        return null;
	    }
	    
	    // try to find by the name of the kpi
	    for (StockKPI kpi : kpis) {
	        if (type.equals(kpi.getType())) {
	            return kpi.getValue();
	        }
	    }
	    
	    // per default: return NULL
	    return null;
	}
	
	public void setKPI(StockKPI.TYPE type, BigDecimal value) throws IllegalArgumentException {
	    
	    boolean found = false;
	    
	    // guard: we can only accept non-null providers
	    if (type == null) {
	        throw new IllegalArgumentException("type must not be null");
	    }
	    
	    // guard: we can only accept non-empty values	    
	    if (StringUtils.isEmpty(value)) {
	        return;
	    }
	    
	    // if the list of KPIs does not exist yet, then we have to create it in order to 
	    // avoid a NPE in the next step
	    if (kpis == null) {
	        kpis = new ArrayList<StockKPI>();
	    }
	    
	    // try to find an existing KPI entry in order to carry out an update 
	    for (StockKPI tmp: kpis) {
	        if (type.equals(tmp.getType())) {
	            tmp.setValue(value);
	            found = true;
	            break; // leave the as we have updated the KPI already
            }
	    }
	    
	    // Guard: if existing KPI was found, then skip further processing
	    if (found) {
	       return; 
	    }
	    
	    // create and add a new KPI entry
	    StockKPI kpi = new StockKPI(type, value, this);
	    kpis.add(kpi);
	    	    
	}
	
}