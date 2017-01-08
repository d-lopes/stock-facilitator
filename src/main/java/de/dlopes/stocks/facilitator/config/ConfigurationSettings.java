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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import de.dlopes.stocks.facilitator.services.FinanzenNetIndexHTMLISINExtractor;
import de.dlopes.stocks.facilitator.services.ISINExtractor;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="config")
public class ConfigurationSettings {

	// member variables are automatically bound to application.yaml config due to
	// @ConfigurationProperties annotation of the class
	private String defaultUser;
	private String defaultPassword;
	private String defaultRole;
	private String dispatcherServletCxtpth;
		
	@Bean
	public ISINExtractor getISINExtractor() {
		return new FinanzenNetIndexHTMLISINExtractor();
	}

}