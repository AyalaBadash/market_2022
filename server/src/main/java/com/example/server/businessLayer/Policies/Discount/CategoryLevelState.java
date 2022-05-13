package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.ShoppingBasket;

public class CategoryLevelState implements DiscountLevelState{
    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) {
        throw new UnsupportedOperationException();
    }
}
