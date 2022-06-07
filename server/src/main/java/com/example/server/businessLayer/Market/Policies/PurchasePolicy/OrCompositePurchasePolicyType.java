package com.example.server.businessLayer.Market.Policies.PurchasePolicy;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.PurchasePolicyLevelState;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.ShopPurchasePolicyLevelState;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.businessLayer.Market.Users.Visitor;

import java.util.List;

public class OrCompositePurchasePolicyType extends CompositePurchasePolicyType {

    public OrCompositePurchasePolicyType(List<PurchasePolicyType> policies) {
        super ( new ShopPurchasePolicyLevelState (), policies );
    }

    @Override
    public boolean isPolicyHeld(Visitor visitor, ShoppingBasket shoppingBasket) {
        return false;
    }
}
