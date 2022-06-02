package com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

public class KindOfCondition extends Condition{
    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof AmountOfItemCondition) return true;
        return false;
    }
}
