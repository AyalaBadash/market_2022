package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.ShoppingBasket;

public class AmountOfItemCondition extends Condition{
    private double amountNeeded;
    private Class itemNeeded;

    public AmountOfItemCondition(double amountNeeded, Class itemNeeded) {
        this.amountNeeded = amountNeeded;
        this.itemNeeded = itemNeeded;
    }

    /**
     *
     * @param shoppingBasket
     * @return there is at least amountNeeded of itemNeeded in the shoppingBasket
     */
    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) {
        throw new UnsupportedOperationException();
    }
}
