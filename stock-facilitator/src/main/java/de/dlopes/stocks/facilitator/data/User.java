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
package de.dlopes.stocks.facilitator.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue
	private Long userId;

	@Column(unique=true)
	private String username;
	
	@NotNull
	private String password;
	
	private boolean enabled;
	private boolean hasValidCredentials;
	private boolean notExpired;
	private boolean notLocked;

	@ManyToMany
	@JoinTable(name = "users_roles")
	private List<UserRole> roles;

/* constructors for simpler management of many-to-many relationships */
	
	public User() {
		
	}
	
	public User(String name, String password, List<UserRole> roles){
        this.username = name;
        this.password = password;
        this.enabled = true;
    	this.hasValidCredentials = true;
    	this.notExpired = true;
    	this.notLocked = true;
        this.roles = roles;
    }

}
