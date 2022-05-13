package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.ShoppingBasket;

public class PriceCondition extends Condition{
    private double priceNeeded;

    public PriceCondition(double priceNeeded){
        this.priceNeeded = priceNeeded;
    }

    /**
     *
     * @param shoppingBasket
     * @return price of the shoppingBasket >= priceNeeded
     */
    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) {
        return shoppingBasket.getPrice() >= priceNeeded;
    }
}
