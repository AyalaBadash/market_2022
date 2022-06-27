package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasePolicyRep extends JpaRepository<PurchasePolicy, Long> {
}
