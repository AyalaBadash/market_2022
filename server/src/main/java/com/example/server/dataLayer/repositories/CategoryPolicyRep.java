package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.CategoryPurchasePolicyLevelState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryPolicyRep extends JpaRepository<CategoryPurchasePolicyLevelState, Long> {
}
