package de.dlopes.stocks.facilitator.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="roles")
public class UserRole {

	@Id
	@GeneratedValue
	private Long roleId;
	
	@Column(unique=true)
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private List<User> users;

/* constructors for simpler management of many-to-many relationships */
	
	public UserRole() {
		
	}
	
	public UserRole(String name) {
		this.name = name;
	}
	
}
