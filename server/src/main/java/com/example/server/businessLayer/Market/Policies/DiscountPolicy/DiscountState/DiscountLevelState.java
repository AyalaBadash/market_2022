package com.example.server.businessLayer.Market.Policies.Discount.DiscountState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

public interface DiscountLevelState {

    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) throws MarketException;
}
