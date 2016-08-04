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

	// member varialbles are automatically bound to application.yaml config due to
	// @ConfigurationProperties annotation of the class
	private String url;
	private String defaultUser;
	private String defaultPassword;
	private String defaultRole;
		
	@Bean
	public StockDataCollector getDataCollector() {
		return new HTMLFileExtractor(url);
	}

}