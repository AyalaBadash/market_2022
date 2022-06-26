package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.ShopPurchasePolicyLevelState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopPolicyRep extends JpaRepository<ShopPurchasePolicyLevelState, Long> {
}
