package com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

public class PriceCondition extends Condition {
    private double priceNeeded;

    public PriceCondition(double priceNeeded) {
        this.priceNeeded = priceNeeded;
    }

    /**
     *
     * @param shoppingBasket
     * @return price of the shoppingBasket >= priceNeeded
     */
    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        return shoppingBasket.getPrice() >= priceNeeded;
    }
}
