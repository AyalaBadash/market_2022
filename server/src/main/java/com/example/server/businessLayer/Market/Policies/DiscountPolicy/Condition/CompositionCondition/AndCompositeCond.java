package com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Cond;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.*;

import javax.persistence.DiscriminatorValue;
import java.util.List;
//@Entity
@DiscriminatorValue(value = "AndCompositeCondition")
public class AndCompositeCond extends CompositeCond {
    public AndCompositeCond(List<Cond> conditions) {
        super ( conditions );
    }

    public AndCompositeCond(){}

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        for ( Cond condition: conditions )
            if(!condition.isDiscountHeld ( shoppingBasket ))
                return false;
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof AndCompositeCond){
            AndCompositeCond toCompare = (AndCompositeCond) object;
            for( Cond condition: this.conditions){
                if (!toCompare.conditions.contains ( condition ))
                    return false;
            }
            for( Cond condition: toCompare.conditions){
                if ( !this.conditions.contains ( condition ))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isAnd(){
        return true;
    }

    @Override
    public ConditionFacade visitToFacade(AmountOfItemConditionFacade conditionFacade) {
        return null;
    }

    @Override
    public ConditionFacade visitToFacade(PriceConditionFacade conditionFacade) {
        return null;
    }

    @Override
    public ConditionFacade visitToFacade(AndCompositeConditionFacade conditionFacade) {
        return conditionFacade.toFacade ( this );
    }

    @Override
    public ConditionFacade visitToFacade(OrCompositeConditionFacade conditionFacade) {
        return null;
    }
}
