package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.AndCompositePurchasePolicyLevelState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AndPolicyRep extends JpaRepository<AndCompositePurchasePolicyLevelState, Long> {
}
