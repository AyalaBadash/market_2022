package com.example.server.businessLayer.Market.Policies.PurchasePolicy;

import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.businessLayer.Market.Users.Visitor;

public abstract class PurchasePolicy {
    public abstract boolean isPolicyHeld(Visitor visitor, ShoppingBasket shoppingBasket);
}
