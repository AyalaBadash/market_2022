package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.ShoppingBasket;

public class SimpleDiscount extends DiscountType{

    public SimpleDiscount(int percentageOfDiscount, DiscountLevelState discountLevelState) {
        super(percentageOfDiscount, discountLevelState);
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) {
        return true;
    }
}
