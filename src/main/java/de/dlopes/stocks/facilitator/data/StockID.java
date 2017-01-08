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
@Table(name="stockid")
public class StockID implements Serializable {
    
    private static final long serialVersionUID = 2754745868464626971L;
    
    public enum PROVIDER { YAHOO };
    
    @Id
	@GeneratedValue
	private Long id;
	
	private PROVIDER provider;
    
    private String value;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="stockinfo_ref")
	private StockInfo stock;
	
	public StockID(PROVIDER provider, String value, StockInfo stock) {
        this.provider = provider;
        this.value = value;
        this.stock = stock;
    }
}
