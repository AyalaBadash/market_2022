package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.AndCompositeDiscountLevelState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AndCompDRep extends JpaRepository<AndCompositeDiscountLevelState, Long> {
}
