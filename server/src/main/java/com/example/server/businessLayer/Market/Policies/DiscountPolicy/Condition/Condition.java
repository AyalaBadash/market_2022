package com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

public abstract class Condition {
    public abstract boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException;
    public abstract boolean equals(Object object);
}
