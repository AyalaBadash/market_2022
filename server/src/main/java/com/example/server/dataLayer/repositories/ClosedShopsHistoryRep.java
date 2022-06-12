package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.ClosedShopsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosedShopsHistoryRep extends JpaRepository<ClosedShopsHistory, Long> {
}
