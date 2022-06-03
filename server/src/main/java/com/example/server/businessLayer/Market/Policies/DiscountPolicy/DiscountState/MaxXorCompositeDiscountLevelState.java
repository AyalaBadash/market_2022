package com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.List;

public class MaxXorCompositeDiscountLevelState extends CompositeDiscountLevelState{
    public MaxXorCompositeDiscountLevelState(List<DiscountLevelState> discountLevelStates) {
        super ( discountLevelStates );
    }

    @Override
    protected Double calculateAllDiscount(double price, List<Double> discounts) throws MarketException {
        double max = 0;
        for(Double discount : discounts)
            if(discount > max)
                max = discount;
        return price - max;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof MaxXorCompositeDiscountLevelState){
            MaxXorCompositeDiscountLevelState toCompare = (MaxXorCompositeDiscountLevelState) object;
            for( DiscountLevelState discountLevelState: this.discountLevelStates){
                if (toCompare.discountLevelStates.contains ( discountLevelState ))
                    return false;
            }
            for(DiscountLevelState discountLevelState: toCompare.discountLevelStates){
                if (this.discountLevelStates.contains ( discountLevelState ))
                    return false;
            }
            return true;
        }
        return false;
    }

}
