package com.example.server.businessLayer.Policies.Discount;

import com.example.server.ResourcesObjects.MarketException;
import com.example.server.businessLayer.ShoppingBasket;

public interface DiscountLevelState {

    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) throws MarketException;
}
