package com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.Map;

public class ItemLevelState implements DiscountLevelState{
    private Integer itemID;

    public ItemLevelState(Integer itemID) {
        this.itemID = itemID;
    }
    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) throws MarketException {
        double price = shoppingBasket.getPrice();
        Item item=Market.getInstance().getItemByID(itemID);
        Map<java.lang.Integer,Double> items= shoppingBasket.getItems();
        double itemDiscount = 0;
        if (items.containsKey(item.getID()))
        {
            Double amount = items.get(item.getID());
            itemDiscount = amount * (percentageOfDiscount/100);

        }
        return price - itemDiscount;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof ItemLevelState){
            ItemLevelState toCompare = (ItemLevelState) object;
            return this.itemID == toCompare.itemID;
        }
        return false;
    }
}
