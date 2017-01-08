package de.dlopes.stocks.facilitator.data;

import java.io.Serializable;

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
    
    @Id
	@GeneratedValue
	private Long id;
	
	private String kpi;
    
    private String value;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="stockinfo_ref")
	private StockInfo stock;
	
}
