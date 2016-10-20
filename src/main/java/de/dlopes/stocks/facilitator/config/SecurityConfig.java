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
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import de.dlopes.stocks.facilitator.controller.PublicController;

@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ConfigurationSettings config;
		
	@Autowired
	private UserDetailsService myUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
    		.csrf().disable() // disable Spring Securities' CSRF functionality (done in favor of Vaadin's implementation)
            .authorizeRequests().anyRequest().authenticated() // User must be authenticated to access any part of the application
            .and()
            .formLogin().loginPage(PublicController.LOGIN_URL).permitAll() // Login page is accessible to anybody
            .and()
            .logout().logoutUrl(PublicController.LOGOUT_URL)
            .logoutSuccessUrl(PublicController.LOGIN_URL).permitAll() // Logout success page is accessible to anybody
            .and()
            .sessionManagement().sessionFixation().newSession(); // Create completely new session	
	}

    /* 
     * define some exceptions from the generally secured application
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring().antMatchers("/index.html") // access index
            .and().ignoring().antMatchers("/VAADIN/vaadinBootstrap.js") // access to vaadin specific bootstrap JS
            .and().ignoring().antMatchers("/VAADIN/widgetsets/**") // access to vaadin specific widgets
            .and().ignoring().antMatchers("/css/*"); // Static resources are ignored
    }

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// use the userDetailsService for login
		auth.userDetailsService(myUserDetailsService); 
	}

}