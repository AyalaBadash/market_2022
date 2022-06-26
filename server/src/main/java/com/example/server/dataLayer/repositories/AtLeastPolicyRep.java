package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.AtLeastPurchasePolicyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtLeastPolicyRep extends JpaRepository<AtLeastPurchasePolicyType, Long> {
}
