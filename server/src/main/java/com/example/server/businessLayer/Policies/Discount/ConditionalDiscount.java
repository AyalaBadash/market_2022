package com.example.server.businessLayer.Policies.Discount;

import com.example.server.ResourcesObjects.MarketException;
import com.example.server.businessLayer.ShoppingBasket;

public class ConditionalDiscount extends CompositeDiscount{
    private Condition condition;

    public ConditionalDiscount(int percentageOfDiscount, DiscountLevelState discountLevelState, DiscountType discountType, CompositeDiscountType compositeDiscountType) {
        super(percentageOfDiscount, discountLevelState, discountType, compositeDiscountType);
        this.condition = condition;
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        return condition.isDiscountHeld(shoppingBasket);
    }
}
