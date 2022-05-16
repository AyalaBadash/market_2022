package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingBasket;

public abstract class Condition {
    public abstract boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException;
}
