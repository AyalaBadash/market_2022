package com.example.server.businessLayer.Market.Policies.DiscountPolicy;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount.CompositeDiscount;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.ArrayList;
import java.util.List;

public class DiscountPolicy {
    private List<DiscountType> validDiscounts;
    public DiscountPolicy() {
        this.validDiscounts = new ArrayList<> (  );
    }
    public double calculateDiscount(ShoppingBasket shoppingBasket) throws MarketException {
        double price = shoppingBasket.getPrice ();
        double priceAfterDiscount = price;
        for(DiscountType discountType: validDiscounts){
            double curPrice = discountType.calculateDiscount ( shoppingBasket );
            priceAfterDiscount -= (price - curPrice);
        }
        return priceAfterDiscount;
    }

    public void addNewDiscount(DiscountType discountType) throws MarketException {
        if(discountType == null)
            throw new MarketException ( "discount is null" );
        if (validDiscounts.contains ( discountType ))
            throw new MarketException ( "discount is already exist in the shop" );
        if(discountType instanceof CompositeDiscount){
            pureDiscounts ( (CompositeDiscount) discountType );
        }
        validDiscounts.add ( discountType );
    }

    private void pureDiscounts(CompositeDiscount discount){
        for(DiscountType discountType: discount.getDiscountTypes ()){
            if(validDiscounts.contains ( discountType )){
                validDiscounts.remove ( discountType );
            } else if(discountType instanceof CompositeDiscount){
                pureDiscounts ((CompositeDiscount) discountType);
            }
        }
    }
}