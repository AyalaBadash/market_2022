package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.ShoppingBasket;

public abstract class DiscountType {
    protected int percentageOfDiscount;
    protected DiscountLevelState discountLevelState;

    public DiscountType(int percentageOfDiscount, DiscountLevelState discountLevelState) {
        this.percentageOfDiscount = percentageOfDiscount;
        this.discountLevelState = discountLevelState;
    }

    public double calculateDiscount(ShoppingBasket shoppingBasket){
        if(isDiscountHeld(shoppingBasket))
            return discountLevelState.calculateDiscount(shoppingBasket, percentageOfDiscount);
        return shoppingBasket.getPrice();
    }
    public abstract boolean isDiscountHeld(ShoppingBasket shoppingBasket);
}
