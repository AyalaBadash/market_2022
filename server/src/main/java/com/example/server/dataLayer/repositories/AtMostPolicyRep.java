package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.AtMostPurchasePolicyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtMostPolicyRep extends JpaRepository<AtMostPurchasePolicyType, Long> {
}
