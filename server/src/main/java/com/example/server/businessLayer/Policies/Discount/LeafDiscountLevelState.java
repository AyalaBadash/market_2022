package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingBasket;

public class LeafDiscountLevelState implements DiscountLevelState {
    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) throws MarketException {
        return shoppingBasket.getPrice();
    }
}
