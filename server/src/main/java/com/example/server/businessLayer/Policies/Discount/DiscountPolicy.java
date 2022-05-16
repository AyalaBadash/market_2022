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
        //By default - will take the best discount
        throw new UnsupportedOperationException();
    }

    public void createDiscountWithOr(List<DiscountType> discountTypes){
        //By default - will take the best discount
        throw new UnsupportedOperationException();
    }

    public void createDiscountWithAnd(List<DiscountType> discountTypes){
        //By default - will take the best discount
        throw new UnsupportedOperationException();
    }

    public void createMaxDiscount(List<DiscountType> discountTypes){
        //By default - will take the best discount
        throw new UnsupportedOperationException();
    }

    public void createAddedDiscount(List<DiscountType> discountTypes){
        //By default - will take the best discount
        throw new UnsupportedOperationException();
    }
}
