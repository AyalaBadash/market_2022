package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.AmountOfItemCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.AndCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.OrCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Cond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.PriceCond;
import com.example.server.serviceLayer.FacadeObjects.FacadeObject;

public abstract class ConditionFacade implements FacadeObject<Cond> {
    public abstract ConditionFacade toFacade(PriceCond condition);

    public abstract ConditionFacade toFacade(AmountOfItemCond condition);

    public abstract ConditionFacade toFacade(AndCompositeCond condition);

    public abstract ConditionFacade toFacade(OrCompositeCond condition);

    public abstract ConditionFacade toFacade(Cond condition);

    protected ConditionFacade getConditionFacade(Cond condition){
        ConditionFacade conditionFacade;
        if(condition.isAnd ()){
            conditionFacade = new AndCompositeConditionFacade (  );
        } else if(condition.isOr ()){
            conditionFacade = new OrCompositeConditionFacade (  );
        }else if(condition.isAmountOfItem ()){
            conditionFacade = new AmountOfItemConditionFacade (  );
        }else{
            conditionFacade = new PriceConditionFacade (  );
        }
        conditionFacade = conditionFacade.toFacade ( condition );
        return conditionFacade;
    }

}
