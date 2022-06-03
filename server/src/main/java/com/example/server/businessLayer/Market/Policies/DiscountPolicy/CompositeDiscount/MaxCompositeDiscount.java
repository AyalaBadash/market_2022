package com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.awt.event.MouseAdapter;
import java.util.List;

public class MaxCompositeDiscount extends CompositeDiscount{
    public MaxCompositeDiscount(List<DiscountType> discountTypes) {
        super (discountTypes);
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

    @Override
    public boolean equals(Object object) {
        if(object instanceof MaxCompositeDiscount){
            MaxCompositeDiscount toCompare = (MaxCompositeDiscount) object;
            for(DiscountType discountType: this.discountTypes){
                if (toCompare.discountTypes.contains ( discountType ))
                    return false;
            }
            for(DiscountType discountType: toCompare.discountTypes){
                if (this.discountTypes.contains ( discountType ))
                    return false;
            }
            return true;
        }
        return false;
    }
}
