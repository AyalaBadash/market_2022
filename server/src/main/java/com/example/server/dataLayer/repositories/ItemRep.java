package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRep extends JpaRepository<Item, Integer> {
}
