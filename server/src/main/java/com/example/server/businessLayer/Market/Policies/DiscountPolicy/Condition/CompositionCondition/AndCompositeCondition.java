package com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount.MaxCompositeDiscount;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.List;

public class AndCompositeCondition extends CompositeCondition{
    public AndCompositeCondition(List<Condition> conditions) {
        super ( conditions );
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        for ( Condition condition: conditions )
            if(!condition.isDiscountHeld ( shoppingBasket ))
                return false;
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof AndCompositeCondition){
            AndCompositeCondition toCompare = (AndCompositeCondition) object;
            for( Condition condition: this.conditions){
                if (!toCompare.conditions.contains ( condition ))
                    return false;
            }
            for( Condition condition: toCompare.conditions){
                if ( !this.conditions.contains ( condition ))
                    return false;
            }
            return true;
        }
        return false;
    }
}