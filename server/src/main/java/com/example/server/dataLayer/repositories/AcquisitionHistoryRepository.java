package com.example.server.dataLayer.repositories;

import com.example.server.dataLayer.entities.DalAcquisitionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquisitionHistoryRepository extends JpaRepository<DalAcquisitionHistory,Integer> {
}
