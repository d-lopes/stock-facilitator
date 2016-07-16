package de.dlopes.stocks.facilitator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import de.dlopes.stocks.facilitator.services.HTMLFileExtractor;
import de.dlopes.stocks.facilitator.services.StockDataCollector;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="config")
public class ConfigurationSettings {

	private String url;
	
	@Bean
	public StockDataCollector getDataCollector() {
		return new HTMLFileExtractor(url);
	}

}