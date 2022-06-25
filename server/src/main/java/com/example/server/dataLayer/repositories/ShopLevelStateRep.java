package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.ShopLevelState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopLevelStateRep extends JpaRepository<ShopLevelState, Long> {
}
