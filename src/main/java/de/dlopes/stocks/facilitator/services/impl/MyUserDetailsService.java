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
package de.dlopes.stocks.facilitator.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.dlopes.stocks.facilitator.config.ConfigurationSettings;
import de.dlopes.stocks.facilitator.data.User;
import de.dlopes.stocks.facilitator.data.UserRepository;
import de.dlopes.stocks.facilitator.data.UserRole;
import de.dlopes.stocks.facilitator.data.UserRoleRepository;

@Service
public class MyUserDetailsService implements UserDetailsService, InitializingBean {

	@Autowired
	private ConfigurationSettings config;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserRoleRepository userRoleRepo;
	
	@Transactional // important: otherwise we can't store the many-to-many rel. 
	@Override	   // between user and userRole
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(username + " does not exist");
		} else if (user.getRoles().isEmpty()) {
			throw new UsernameNotFoundException("User" + username + " has no authorities");
		}

		return buildUserDetails(user);
	}
	
	// populate the default user in the database 
	// (otherwise we wouldn't be able to ever login)
	@Override
	@SuppressWarnings("serial")
	public void afterPropertiesSet() throws Exception {

		// get default settings from configuration
		String username = config.getDefaultUser();
		String password = config.getDefaultPassword();
		String role = config.getDefaultRole();
		
		// check if default user and role exist
		UserRole userRole = userRoleRepo.findByName(role);
		User user = userRepo.findByUsername(username);
		
		// create default user and role if both does not exist in the DB yet
		if (user == null && userRole == null) {
				
			userRole = new UserRole(role);			
			userRoleRepo.save(userRole);
			final UserRole tmpRole = userRoleRepo.findByName(role);
			
			userRepo.save(new HashSet<User>(){{
				add(new User(username, password, new ArrayList<UserRole>(){{
					add(tmpRole);
				}}));
			}});
			
		}

	}

	private org.springframework.security.core.userdetails.User buildUserDetails(User user) {
		List<GrantedAuthority> auth = buildUserAuthority(user.getRoles());
		
		org.springframework.security.core.userdetails.User res = 
			new org.springframework.security.core.userdetails.User(
				user.getUsername(), 
				user.getPassword(), 
				user.isEnabled(), 
				true, 
				true, 
				true, 
				auth
			);
		
		return res;
	}

	private List<GrantedAuthority> buildUserAuthority(List<UserRole> userRoles) {

		List<GrantedAuthority> res = new ArrayList<GrantedAuthority>();
		for (UserRole userRole : userRoles) {
			// prefix 'ROLE_' is required and this has to be added for the granted authority
			res.add(new SimpleGrantedAuthority("ROLE_" + userRole.getName()));
		}

		return res;
	}

}
