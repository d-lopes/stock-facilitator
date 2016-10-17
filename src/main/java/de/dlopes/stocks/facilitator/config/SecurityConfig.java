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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.UI;

import de.dlopes.stocks.facilitator.controller.PublicController;
import de.dlopes.stocks.facilitator.ui.views.StockInfoView;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements ViewAccessControl {

    @Autowired
    private ConfigurationSettings config;
		
	@Autowired
	private UserDetailsService myUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// disable Spring Securities' CSRF functionality (done in favor of Vaadin's implementation)
			.csrf().disable() // Use Vaadin's CSRF protection
            .authorizeRequests().anyRequest().authenticated() // User must be authenticated to access any part of the application
            .and()
            .formLogin().loginPage(PublicController.LOGIN_URL).permitAll() // Login page is accessible to anybody
            .and()
            .logout().logoutUrl(PublicController.LOGOUT_URL).logoutSuccessUrl("/").permitAll() // Logout success page is accessible to anybody
            .and()
            .sessionManagement().sessionFixation().newSession(); // Create completely new session	
	}

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/*"); // Static resources are ignored
    }

    @Override
    public boolean isAccessGranted(UI ui, String beanName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (beanName.equals(StockInfoView.VIEW_NAME)) {
                return authentication.getAuthorities().contains(new SimpleGrantedAuthority(config.getDefaultRole()));
            }
        }
        return false;
    }

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// use the userDetailsService for login
		auth.userDetailsService(myUserDetailsService); 
	}

}