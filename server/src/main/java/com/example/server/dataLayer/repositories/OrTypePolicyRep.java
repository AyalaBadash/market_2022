package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.OrCompositePurchasePolicyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrTypePolicyRep extends JpaRepository<OrCompositePurchasePolicyType, Long> {
}
