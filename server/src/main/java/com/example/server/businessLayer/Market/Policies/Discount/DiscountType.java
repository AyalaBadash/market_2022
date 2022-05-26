package com.example.server.businessLayer.Market.Policies.Discount;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

public abstract class DiscountType {
    protected int percentageOfDiscount;
    protected DiscountLevelState discountLevelState;

    public DiscountType(int percentageOfDiscount, DiscountLevelState discountLevelState) {
        this.percentageOfDiscount = percentageOfDiscount;
        this.discountLevelState = discountLevelState;
    }

    public double calculateDiscount(ShoppingBasket shoppingBasket) throws MarketException {
        if(isDiscountHeld(shoppingBasket))
            return discountLevelState.calculateDiscount(shoppingBasket, percentageOfDiscount);
        return shoppingBasket.getPrice();
    }
    public abstract boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException;
}
