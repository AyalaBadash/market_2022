package com.example.server.businessLayer.Market.Policies.Discount.CompositeDiscount;

import com.example.server.businessLayer.Market.Policies.Discount.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.Policies.Discount.DiscountType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeDiscount extends DiscountType{
    List<DiscountType> discountTypes;
    public CompositeDiscount(int percentageOfDiscount, DiscountLevelState discountLevelState, List<DiscountType> discountTypes) {
        super(percentageOfDiscount, discountLevelState);
        this.discountTypes = discountTypes;
    }

    public double calculateDiscount(ShoppingBasket shoppingBasket) throws MarketException {
        List<Double> discounts = new ArrayList<> (  );
        Double price = shoppingBasket.getPrice();
        for(DiscountType discountType : discountTypes){
            Double curPrice = discountType.calculateDiscount(shoppingBasket);
            discounts.add ( price - curPrice );
        }
        return calculateBothDiscount(price, discounts);
    }

    protected abstract Double calculateBothDiscount(double price, List<Double> discounts) throws MarketException;
}
