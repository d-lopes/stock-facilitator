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
package de.dlopes.stocks.facilitator.config;

import java.io.Serializable;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import de.dlopes.stocks.facilitator.services.HTMLFileExtractor;
import de.dlopes.stocks.facilitator.services.StockDataCollector;

@Data
@Component
@ConfigurationProperties(prefix="config")
public class ConfigurationSettings implements Serializable {

    private static final long serialVersionUID = -6219024369615821590L;

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