package com.example.server.businessLayer.Market.Policies.PurchasePolicy;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.PurchasePolicyLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.businessLayer.Market.Users.Visitor;

public abstract class PurchasePolicyType {
    public PurchasePolicyType(PurchasePolicyLevelState purchasePolicyLevelState) {
        this.purchasePolicyLevelState = purchasePolicyLevelState;
    }

    PurchasePolicyLevelState purchasePolicyLevelState;

    public abstract boolean isPolicyHeld(Visitor visitor, ShoppingBasket shoppingBasket) throws MarketException;
}
