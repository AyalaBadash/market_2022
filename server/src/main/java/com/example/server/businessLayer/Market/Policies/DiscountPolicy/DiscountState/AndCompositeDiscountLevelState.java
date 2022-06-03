package com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.List;

public class AndCompositeDiscountLevelState extends CompositeDiscountLevelState{
    public AndCompositeDiscountLevelState(List<DiscountLevelState> discountLevelStates) {
        super ( discountLevelStates );
    }

    @Override
    protected Double calculateAllDiscount(double price, List<Double> discounts) throws MarketException {
        double priceAfterDiscount = price;
        for ( Double discount: discounts )
            price -= discount;
        return price;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof AndCompositeDiscountLevelState){
            AndCompositeDiscountLevelState toCompare = (AndCompositeDiscountLevelState) object;
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
