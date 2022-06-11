package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.ShoppingBasket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingBasketRep extends JpaRepository<ShoppingBasket, Long> {
}
