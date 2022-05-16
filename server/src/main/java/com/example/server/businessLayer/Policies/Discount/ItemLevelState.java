package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.ShoppingBasket;

import java.util.Map;

public class ItemLevelState implements DiscountLevelState{
    private Item item;

    public ItemLevelState(Item item) {
        this.item = item;
    }

    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) {
        Map<Item,Double> items= shoppingBasket.getItems();
        if (items.containsKey(item))
        {
            Double val = items.get(item);
            val = val * ((100-percentageOfDiscount)/100);
            items.put(item,val);
        }
        return shoppingBasket.getPrice();
    }
}
