package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.AcquisitionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquisitionHistoryRepository extends JpaRepository<AcquisitionHistory,Integer> {
}
