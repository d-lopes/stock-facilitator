package de.dlopes.stocks.facilitator.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import de.dlopes.stocks.facilitator.config.ConfigurationSettings;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// DataSource is already defined automatically by Spring Boot
	// This datasource points to a H2 database in a local environment but in the 
	// cloud environment it's bean definition gets automatically rewritten by Spring's 
	// Cloud Foundry Connector to a relational SQL database (ElephantSQL in our case) 
	// and is bound to the corresponding cloud service
	@Autowired
	private DataSource dataSource;

	@Autowired
	private ConfigurationSettings config;
	
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
		auth.jdbcAuthentication()
			.dataSource(dataSource) // points this config to datasource provided by Spring
			.withDefaultSchema()	// returns Jdbc-based userDetailsService
			.withUser(config.getDefaultUser()) // set username
			.password(config.getDefaultPassword()) // set password
			.roles(config.getDefaultRole()); // assign user role 
	}
	
}