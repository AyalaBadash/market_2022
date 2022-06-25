package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.ConditionalDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionalDiscountRep extends JpaRepository<ConditionalDiscount, Long> {
}
