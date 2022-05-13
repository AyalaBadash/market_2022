package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.ShoppingBasket;

public class ConditionalDiscount extends DiscountType{
    private Condition condition;

    public ConditionalDiscount(int percentageOfDiscount, DiscountLevelState discountLevelState, Condition condition) {
        super(percentageOfDiscount, discountLevelState);
        this.condition = condition;
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) {
        return condition.isDiscountHeld(shoppingBasket);
    }
}
