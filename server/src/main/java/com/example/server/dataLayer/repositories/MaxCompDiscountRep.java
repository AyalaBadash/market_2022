package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount.MaxCompositeDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaxCompDiscountRep extends JpaRepository<MaxCompositeDiscount, Long> {
}
