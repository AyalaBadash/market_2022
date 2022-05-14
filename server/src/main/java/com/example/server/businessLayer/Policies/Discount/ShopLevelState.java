package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.ShoppingBasket;

import java.util.Map;

public class ShopLevelState implements DiscountLevelState{
    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) {
        Map<Item,Double> items = shoppingBasket.getItems();
        for (Map.Entry<Item,Double> entry:items.entrySet())
        {
            entry.setValue(entry.getValue()*(100-percentageOfDiscount)/100);
        }
        return shoppingBasket.getPrice();
    }
}
