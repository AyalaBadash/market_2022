package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountPolicyRep extends JpaRepository<DiscountPolicy, Long> {
}
