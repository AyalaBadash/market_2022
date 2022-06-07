package com.example.server.businessLayer.Market.Policies.PurchasePolicy;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.PurchasePolicyLevelState;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.businessLayer.Market.Users.Visitor;

public class AtMostPurchasePolicyType extends PurchasePolicyType {
    private double amount;

    public AtMostPurchasePolicyType(PurchasePolicyLevelState purchasePolicyLevelState, double amount) {
        super ( purchasePolicyLevelState );
        this.amount = amount;
    }
    @Override
    public boolean isPolicyHeld(Visitor visitor, ShoppingBasket shoppingBasket) {
        return false;
    }
}
