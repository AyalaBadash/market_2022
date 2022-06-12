package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Acquisition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquisitionRep extends JpaRepository<Acquisition, Long> {
}
