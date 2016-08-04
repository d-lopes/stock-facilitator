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