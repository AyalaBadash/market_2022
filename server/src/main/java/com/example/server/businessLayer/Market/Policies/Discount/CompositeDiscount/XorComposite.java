package com.example.server.businessLayer.Market.Policies.Discount.CompositeDiscount;

import com.example.server.businessLayer.Market.Policies.Discount.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.Policies.Discount.DiscountType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.List;

public class XorComposite extends CompositeDiscount {

    public XorComposite(int percentageOfDiscount, DiscountLevelState discountLevelState, List<DiscountType> discountTypes) {
        super ( percentageOfDiscount, discountLevelState, discountTypes );
    }

    @Override
    protected Double calculateBothDiscount(double price, List<Double> discounts) throws MarketException {
        return null;
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        return false;
    }
}
