package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRep extends JpaRepository<Bid, Long> {
}
