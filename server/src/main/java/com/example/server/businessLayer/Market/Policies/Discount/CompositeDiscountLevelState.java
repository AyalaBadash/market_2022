package com.example.server.businessLayer.Market.Policies.Discount;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

public abstract class CompositeDiscountLevelState implements DiscountLevelState{

    public enum CompositeDiscountLevelType{
        XORType,
        ANDType
    }
    DiscountLevelState discountLevelState;
    CompositeDiscountLevelType compositeDiscountLevelType;

    public CompositeDiscountLevelState(DiscountLevelState discountLevelState, CompositeDiscountLevelType compositeDiscountLevelType) {
        this.discountLevelState = discountLevelState;
        this.compositeDiscountLevelType = compositeDiscountLevelType;
    }
    @Override
    public abstract double calculateDiscount(ShoppingBasket shoppingBasket, int percentageOfDiscount) throws MarketException;

    protected Double calculateBothDiscount(double price, double discount1, double discount2) throws MarketException {
        switch (compositeDiscountLevelType) {
            case XORType -> {
                if (discount1 > discount2)
                    return price - discount1;
                return price - discount2;
            }
            case ANDType -> {
                return price - discount1 - discount2;
            }
            default -> {
                throw new MarketException("there is no such level composite type");
            }
        }
    }
}
