package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.XorCompositePurchasePolicyLevelState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XorPolicyRep extends JpaRepository<XorCompositePurchasePolicyLevelState, Long> {
}
