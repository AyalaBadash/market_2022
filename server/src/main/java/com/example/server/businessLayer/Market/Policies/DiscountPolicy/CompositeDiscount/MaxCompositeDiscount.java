package com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.List;

public class MaxComposite extends CompositeDiscount{
    public MaxComposite(int percentageOfDiscount, DiscountLevelState discountLevelState, List<DiscountType> discountTypes) {
        super ( percentageOfDiscount, discountLevelState, discountTypes );
    }

    @Override
    protected Double calculateAllDiscount(double price, List<Double> discounts) throws MarketException {
        if (discounts.size ( ) == 0)
            return price;
        double max = 0;
        for ( Double cur : discounts )
            if (cur > max)
                max = cur;
        return price - max;
    }
}
