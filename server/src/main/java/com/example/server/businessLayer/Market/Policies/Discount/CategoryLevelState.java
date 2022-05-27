package com.example.server.businessLayer.Market.Policies.Discount;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.Map;

public class CategoryLevelState extends CompositeDiscountLevelState {
    private Item.Category category;

    public CategoryLevelState(DiscountLevelState discountLevelState, CompositeDiscountLevelType compositeDiscountLevelType, Item.Category category) {
        super(discountLevelState, compositeDiscountLevelType);
        this.category = category;
    }

    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) throws MarketException {
        Map<java.lang.Integer, Double> items = shoppingBasket.getItems();
        double generalDiscount = 0;
        double discount = 0;
        for (Map.Entry<java.lang.Integer, Double> entry : items.entrySet()) {
            Item item = Market.getInstance().getItemByID(entry.getKey());
            double amount = entry.getValue();
            if (item.getCategory().equals(this.category))
                discount = item.getPrice() * amount * (percentageOfDiscount / 100);
            generalDiscount += discount;
        }
        double basePrice = discountLevelState.calculateDiscount(shoppingBasket, percentageOfDiscount);
        double baseDiscount = shoppingBasket.getPrice() - basePrice;
        return calculateBothDiscount(shoppingBasket.getPrice(), baseDiscount, generalDiscount);

    }
}
