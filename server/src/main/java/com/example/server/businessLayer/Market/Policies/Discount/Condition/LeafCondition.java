package com.example.server.businessLayer.Market.Policies.Discount.Condition;

import com.example.server.businessLayer.Market.Policies.Discount.Condition.Condition;
import com.example.server.businessLayer.Market.ShoppingBasket;

public class LeafCondition extends Condition {
    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) {
        return true;
    }
}
