package com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState;

import com.example.server.businessLayer.Market.ShoppingBasket;

public class ShopLevelState implements DiscountLevelState {
    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) {
        return shoppingBasket.getPrice()*(100-percentageOfDiscount)/100;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof CategoryLevelState) return true;
        return false;
    }
}
