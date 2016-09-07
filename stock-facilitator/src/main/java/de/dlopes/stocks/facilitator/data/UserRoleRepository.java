package de.dlopes.stocks.facilitator.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	// CRUD operations inherited from parent interface
	
	public UserRole findByName(String name);
}
