package com.example.server.businessLayer.Market.Policies.DiscountPolicy;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.ShopLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

public abstract class DiscountType {
    protected int percentageOfDiscount;
    protected DiscountLevelState discountLevelState;

    public DiscountType(int percentageOfDiscount, DiscountLevelState discountLevelState) {
        this.percentageOfDiscount = percentageOfDiscount;
        this.discountLevelState = discountLevelState;
    }

    public DiscountType(){
        percentageOfDiscount = 0;
        discountLevelState = new ShopLevelState ();
    }

    public int getPercentageOfDiscount() {
        return percentageOfDiscount;
    }

    public void setPercentageOfDiscount(int percentageOfDiscount) {
        this.percentageOfDiscount = percentageOfDiscount;
    }

    public DiscountLevelState getDiscountLevelState() {
        return discountLevelState;
    }

    public void setDiscountLevelState(DiscountLevelState discountLevelState) {
        this.discountLevelState = discountLevelState;
    }

    public double calculateDiscount(ShoppingBasket shoppingBasket) throws MarketException {
        if(isDiscountHeld(shoppingBasket))
            return discountLevelState.calculateDiscount(shoppingBasket, percentageOfDiscount);
        return shoppingBasket.getPrice();
    }
    public abstract boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException;

    public abstract boolean equals(Object object);
}
