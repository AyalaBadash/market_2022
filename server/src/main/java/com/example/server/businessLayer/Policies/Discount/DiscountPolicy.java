package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.ShoppingBasket;

import java.util.List;

public class DiscountPolicy {
    private List<SimpleDiscount> simpleDiscounts;
    private List<ConditionalDiscount> conditionalDiscounts;

    public double calculateDiscount(ShoppingBasket shoppingBasket){
        throw new UnsupportedOperationException();
    }

    //TODO - create for each rule
    public void createDiscountWithXor(List<DiscountType> discountTypes){
        throw new UnsupportedOperationException();
    }
}