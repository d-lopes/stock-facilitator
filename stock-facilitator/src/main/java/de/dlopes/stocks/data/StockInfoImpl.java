package de.dlopes.stocks.data;

import java.time.LocalTime;

import lombok.Data;

@Data
public class StockInfoImpl implements StockInfo {
	
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getISIN());
		sb.append(" - " + getWKN());
		sb.append(" - " + getName());
		sb.append(": " + getPrice());
		sb.append("(Ask: " + getAsk());
		sb.append("/Bid: " + getBid());
		sb.append(") -> " + getChangePercentage());
		sb.append("% (" + getChangeAbsolute());
		sb.append(" EUR) | " + getTime());
		
		return sb.toString();
	}

}