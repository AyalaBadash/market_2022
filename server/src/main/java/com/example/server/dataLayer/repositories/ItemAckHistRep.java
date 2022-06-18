package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.ItemAcquisitionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemAckHistRep extends JpaRepository<ItemAcquisitionHistory, Long> {
}
