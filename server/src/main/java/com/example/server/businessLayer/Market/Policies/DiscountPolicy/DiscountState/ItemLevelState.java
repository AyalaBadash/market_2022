package com.example.server.businessLayer.Market.Policies.Discount.DiscountState;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.Map;

public class ItemLevelState extends CompositeDiscountLevelState {
    private Integer itemID;

    public ItemLevelState(DiscountLevelState discountLevelState, CompositeDiscountLevelType compositeDiscountLevelType, Integer itemID) {
        super(discountLevelState, compositeDiscountLevelType);
        this.itemID = itemID;
    }
    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) throws MarketException {
        double firstCalculation = discountLevelState.calculateDiscount(shoppingBasket, percentageOfDiscount);
        double discount = shoppingBasket.getPrice() - firstCalculation;
        Item item=Market.getInstance().getItemByID(itemID);
        Map<java.lang.Integer,Double> items= shoppingBasket.getItems();
        double itemDiscount = 0;
        if (items.containsKey(item.getID()))
        {
            Double amount = items.get(item.getID());
            itemDiscount = amount * (percentageOfDiscount/100);

        }
        return calculateBothDiscount(shoppingBasket.getPrice(), discount, itemDiscount);
    }
}
