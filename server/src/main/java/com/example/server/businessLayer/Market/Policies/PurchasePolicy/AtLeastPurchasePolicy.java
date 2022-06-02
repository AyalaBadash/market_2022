package com.example.server.businessLayer.Market.Policies.PurchasePolicy;

import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.businessLayer.Market.Users.Visitor;

public class AtLeastPurchasePolicy extends PurchasePolicy{
    @Override
    public boolean isPolicyHeld(Visitor visitor, ShoppingBasket shoppingBasket) {
        return false;
    }
}
