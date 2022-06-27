package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.SimpleDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleDiscountRep extends JpaRepository<SimpleDiscount, Long> {
}
