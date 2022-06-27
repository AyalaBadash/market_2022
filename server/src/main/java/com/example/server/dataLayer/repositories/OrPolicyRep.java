package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.OrCompositePurchasePolicyLevelState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrPolicyRep extends JpaRepository<OrCompositePurchasePolicyLevelState, Long> {
}
