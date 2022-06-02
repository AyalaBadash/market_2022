package com.example.server.businessLayer.Market.Policies.DiscountPolicy;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.ShoppingBasket;

public class SimpleDiscount extends DiscountType{

    public SimpleDiscount(int percentageOfDiscount, DiscountLevelState discountLevelState) {
        super(percentageOfDiscount, discountLevelState);
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) {
        return true;
    }

    public boolean equals(Object object){
        if(object instanceof SimpleDiscount){
            SimpleDiscount toCompare = (SimpleDiscount) object;
            return this.discountLevelState.equals(toCompare.discountLevelState) && this.percentageOfDiscount == toCompare.percentageOfDiscount;
        }
        return false;
    }
}
