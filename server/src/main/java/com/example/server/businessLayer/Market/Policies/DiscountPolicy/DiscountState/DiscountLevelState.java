package com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

public interface DiscountLevelState {

    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) throws MarketException;
    public boolean equals(Object object);
}
