package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.ItemLevelState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemLevelStateRep extends JpaRepository<ItemLevelState, Long> {
}
