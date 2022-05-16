package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingBasket;

import java.util.Map;

public class ItemLevelState implements DiscountLevelState{
    private Integer itemID;

    public ItemLevelState(Integer itemID) {
        this.itemID = itemID;
    }

    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) throws MarketException {
        Item item=Market.getInstance().getItemByID(itemID);
        Map<java.lang.Integer,Double> items= shoppingBasket.getItems();
        double price = item.getPrice();
        if (items.containsKey(item.getID()))
        {
            Double amount = items.get(item.getID());
            price = price * amount * ((100-percentageOfDiscount)/100);

        }
        return shoppingBasket.getPrice()-price;
    }
}
