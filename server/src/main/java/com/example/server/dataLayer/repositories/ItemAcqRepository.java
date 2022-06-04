package com.example.server.dataLayer.repositories;

import com.example.server.dataLayer.entities.DalItemAcquisitionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemAcqRepository extends JpaRepository<DalItemAcquisitionHistory,Integer> {
}
