package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingBasket;

import java.util.Map;

public class CategoryLevelState implements DiscountLevelState{
    private Item.Category category;

    public CategoryLevelState(Item.Category category) {
        this.category = category;
    }

    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) throws MarketException {
        Map<java.lang.Integer, Double> items = shoppingBasket.getItems();
        double generalDiscount = 0;
        double discount = 0;
        for (Map.Entry<java.lang.Integer, Double> entry : items.entrySet()) {
            Item item = Market.getInstance().getItemByID(entry.getKey());
            if (item.getCategory().equals(this.category))
                discount = item.getPrice() * entry.getValue() * ((100 - percentageOfDiscount) / 100);
            generalDiscount += discount;
        }
        return shoppingBasket.getPrice() - generalDiscount;

    }
}
