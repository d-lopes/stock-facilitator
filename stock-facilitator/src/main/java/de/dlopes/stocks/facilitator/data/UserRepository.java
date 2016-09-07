package de.dlopes.stocks.facilitator.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	// CRUD operations inherited from parent interface
	
	public User findByUsername(String username);
	
}
