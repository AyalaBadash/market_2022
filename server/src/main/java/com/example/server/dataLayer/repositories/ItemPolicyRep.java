package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.ItemPurchasePolicyLevelState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPolicyRep extends JpaRepository<ItemPurchasePolicyLevelState, Long> {
}
