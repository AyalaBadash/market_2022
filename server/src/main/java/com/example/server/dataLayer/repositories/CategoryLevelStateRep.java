package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.CategoryLevelState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryLevelStateRep extends JpaRepository<CategoryLevelState, Long> {
}
