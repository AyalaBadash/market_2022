package com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.List;

public class OrCompositeCondition extends CompositeCondition{

    public OrCompositeCondition(List<Condition> conditions) {
        super ( conditions );
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        for ( Condition condition: conditions )
            if(condition.isDiscountHeld ( shoppingBasket ))
                return true;
        return false;
    }
}
