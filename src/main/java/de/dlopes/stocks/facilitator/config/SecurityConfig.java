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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService myUserDetailsService;
	
	@Autowired
	private ConfigurationSettings config;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// retrieve configured contextPath for dispatcher servlet
		String cxtpth = config.getDispatcherServletCxtpth();
		
		http.formLogin()
				.loginPage(cxtpth + "/login")
				.loginProcessingUrl(cxtpth + "/loginProcess")
				.defaultSuccessUrl(cxtpth + "/stock-info")
				.failureUrl(cxtpth + "/login?login_error=1")
			.and().logout()
				.logoutUrl(cxtpth + "/logout")
				.logoutSuccessUrl(cxtpth + "/index")
				
		// Disable CSRF (won't work with JSF) but ensure last HTTP POST request is saved
		// See https://jira.springsource.org/browse/SEC-2498

				.and().csrf()
					.disable()
					.requestCache()
						.requestCache(new HttpSessionRequestCache());
		
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// use the userDetailsService for login
		auth.userDetailsService(myUserDetailsService); 
	}

}