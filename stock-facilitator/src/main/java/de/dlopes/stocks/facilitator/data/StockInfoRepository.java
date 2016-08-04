package de.dlopes.stocks.facilitator.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockInfoRepository extends JpaRepository<StockInfoImpl, Long> {

	// CRUD operations inherited from parent interface
	
}
