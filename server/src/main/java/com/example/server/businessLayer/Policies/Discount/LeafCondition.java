package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.ShoppingBasket;

public class LeafCondition extends Condition{
    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) {
        return true;
    }
}
