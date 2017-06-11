package de.dlopes.stocks.facilitator.data;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="stock_kpi")
public class StockKPI implements Serializable {
    
    private static final long serialVersionUID = 2754745868464626971L;
    
    // list of available KPIs 
    public enum TYPE {
        AVGVOLUME,
        BOOKVALUEPERSHARE,
        CHANGEFROMAVG200,
        CHANGEFROMAVG200INPERCENT,
        CHANGEFROMAVG50,
        CHANGEFROMAVG50INPERCENT,
        CHANGEFROMYEARHIGH,
        CHANGEFROMYEARHIGHINPERCENT,
        CHANGEFROMYEARLOW,
        CHANGEFROMYEARLOWINPERCENT,
        DIV_ANNUALYIELD,
        DIV_ANNUALYIELDPERCENT,        
        EBITDA,
        EPS,
        EPSESTIMATECURRENTYEAR,
        EPSESTIMATENEXTQUARTER,
        EPSESTIMATENEXTYEAR,
        MARKETCAP,
        ONEYEARTARGETPRICE,
        PE,
        PEG,
        PRICEAVG200,
        PRICEAVG50,
        PRICEBOOK,
        PRICESALES,
        REVENUE,
        ROE,
        SHARESFLOAT,
        SHARESOUTSTANDING,
        SHARESOWNED,
        SHORTRATIO,
        YEARHIGH,
        YEARLOW
    };
    
    @Id
	@GeneratedValue
	private Long id;
	
	private TYPE type;
    
    private BigDecimal value;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="stockinfo_ref")
	private StockInfo stock;
	
	public StockKPI(TYPE type, BigDecimal value, StockInfo stock) {
        this.type = type;
        this.value = value;
        this.stock = stock;
    }
	
}
