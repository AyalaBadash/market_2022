package com.example.server.businessLayer.Market.Policies.PurchasePolicy;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.PurchasePolicyLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.businessLayer.Market.Users.Visitor;

public class AtLeastPurchasePolicyType extends PurchasePolicyType {
    public AtLeastPurchasePolicyType(PurchasePolicyLevelState purchasePolicyLevelState, double amount) {
        super ( purchasePolicyLevelState );
        this.amount = amount;
    }

    double amount;
    @Override
    public boolean isPolicyHeld(Visitor visitor, ShoppingBasket shoppingBasket) throws MarketException {
        return purchasePolicyLevelState.isPolicyHeld ( shoppingBasket, amount, true);
    }
}
