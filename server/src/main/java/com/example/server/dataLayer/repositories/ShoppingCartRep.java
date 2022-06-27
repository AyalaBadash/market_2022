package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRep extends JpaRepository<ShoppingCart, Long> {
}
