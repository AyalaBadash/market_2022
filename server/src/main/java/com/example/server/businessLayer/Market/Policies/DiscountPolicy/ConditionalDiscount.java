package com.example.server.businessLayer.Market.Policies.Discount;

import com.example.server.businessLayer.Market.Policies.Discount.Condition.Condition;
import com.example.server.businessLayer.Market.Policies.Discount.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

public class ConditionalDiscount extends DiscountType{
    private Condition condition;

    public ConditionalDiscount(int percentageOfDiscount, DiscountLevelState discountLevelState, Condition condition) {
        super ( percentageOfDiscount, discountLevelState );
        this.condition = condition;
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        return condition.isDiscountHeld(shoppingBasket);
    }
}
