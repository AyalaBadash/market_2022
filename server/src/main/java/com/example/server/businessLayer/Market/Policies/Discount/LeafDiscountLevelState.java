package com.example.server.businessLayer.Market.Policies.Discount;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

public class LeafDiscountLevelState implements DiscountLevelState {
    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) throws MarketException {
        return shoppingBasket.getPrice();
    }
}
