package com.example.server.businessLayer.Market.Policies.Discount;

import com.example.server.businessLayer.Market.ShoppingBasket;

public class LeafCondition extends Condition{
    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) {
        return true;
    }
}
