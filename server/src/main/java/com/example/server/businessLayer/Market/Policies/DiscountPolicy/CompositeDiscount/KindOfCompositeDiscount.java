package com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.SimpleDiscount;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.List;

public class KindOfCompositeDiscount extends CompositeDiscount {

    public KindOfCompositeDiscount(List<DiscountType> discountTypes) {
        super (discountTypes);
    }

    @Override
    protected Double calculateAllDiscount(double price, List<Double> discounts) throws MarketException {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof KindOfCompositeDiscount){
            KindOfCompositeDiscount toCompare = (KindOfCompositeDiscount) object;
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
