package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRep extends JpaRepository<Shop, String> {
}
