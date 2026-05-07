package org.app.app.repository;

import org.app.app.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {

    boolean existsByNameIgnoreCase(String name);
}
