package de.dlopes.stocks.facilitator.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import de.dlopes.stocks.facilitator.config.ConfigurationSettings;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ConfigurationSettings config;
	
	@Autowired
	private UserDetailsService myUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// allow access to styles, images and the index page to everybody
				.antMatchers("/css/**", "/img/**", "/index.html").permitAll()
				// restrict secured area to users with a certain role
				.antMatchers("/secured/**").hasRole(config.getDefaultRole())			
				.and()	
			.formLogin()
				// set login and logout page
				.loginPage("/login").failureUrl("/login-error");	
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// use the userDetailsService for login
		auth.userDetailsService(myUserDetailsService); 
	}

}