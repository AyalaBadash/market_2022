package com.example.server.businessLayer.Market.Policies.Discount;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

public abstract class CompositeDiscount extends DiscountType{
    public enum CompositeDiscountType{
        MAXType,
        PLUSType;
    }
    protected DiscountType discountType;
    protected CompositeDiscountType compositeDiscountType;

    public CompositeDiscount(int percentageOfDiscount, DiscountLevelState discountLevelState, DiscountType discountType, CompositeDiscountType compositeDiscountType) {
        super(percentageOfDiscount, discountLevelState);
        this.discountType = discountType;
        this.compositeDiscountType = compositeDiscountType;
    }

    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket) throws MarketException {
        Double price = shoppingBasket.getPrice();
        Double curPrice = super.calculateDiscount(shoppingBasket);
        Double curDiscount = price - curPrice;
        Double basePrice = discountType.calculateDiscount(shoppingBasket);
        Double baseDiscount = price - basePrice;
        return calculateBothDiscount(price, curDiscount, baseDiscount);
    }

    protected Double calculateBothDiscount(double price, double discount1, double discount2) throws MarketException {
        switch (compositeDiscountType) {
            case MAXType -> {
                if (discount1 > discount2)
                    return price - discount1;
                return price - discount2;
            }
            case PLUSType -> {
                return price - discount1 - discount2;
            }
            default -> {
                throw new MarketException("there is no such level composite type");
            }
        }
    }

    @Override
    public abstract boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException;
}
