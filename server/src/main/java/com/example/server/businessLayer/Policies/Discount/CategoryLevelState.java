package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.ShoppingBasket;

import java.util.HashMap;
import java.util.Map;

public class CategoryLevelState implements DiscountLevelState{
    private Item.Category category; //TODO verify with ayala

    public CategoryLevelState(Item.Category category) {
        this.category = category;
    }

    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) {
        Map<Item,Double> items = shoppingBasket.getItems();
        for (Map.Entry<Item,Double> entry:items.entrySet())
        {
            if (entry.getKey().getCategory().equals(this.category))
                entry.setValue(entry.getValue()*((100-percentageOfDiscount)/100));
        }
        return shoppingBasket.getPrice();

    }
}
