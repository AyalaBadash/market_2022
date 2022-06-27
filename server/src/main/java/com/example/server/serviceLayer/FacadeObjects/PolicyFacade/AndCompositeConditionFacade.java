package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.AmountOfItemCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.AndCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.OrCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Cond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.PriceCond;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

public class AndCompositeConditionFacade extends CompositeConditionFacade{
    public AndCompositeConditionFacade(List<ConditionFacade> conditionFacadeList) {
        super (conditionFacadeList );
    }

    public AndCompositeConditionFacade(){}
    @Override
    public Cond toBusinessObject() throws MarketException {
        List<Cond> conditions = new ArrayList<> (  );
        for(ConditionFacade conditionFacade: conditionFacadeList)
            conditions.add ( conditionFacade.toBusinessObject () );
        return new AndCompositeCond( conditions );
    }

    @Override
    public ConditionFacade toFacade(PriceCond conditionFacade) {
        return null;
    }

    @Override
    public ConditionFacade toFacade(AmountOfItemCond conditionFacade) {
        return null;
    }

    @Override
    public ConditionFacade toFacade(AndCompositeCond condition) {
        List<Cond> conditions = condition.getConditions ();
        List<ConditionFacade> conditionFacades = new ArrayList<> (  );
        for(Cond cur : conditions)
            conditionFacades.add ( getConditionFacade ( cur ) );
        return new AndCompositeConditionFacade ( conditionFacades );
    }

    @Override
    public ConditionFacade toFacade(OrCompositeCond condition) {
        return null;
    }

    @Override
    public ConditionFacade toFacade(Cond condition) {
        return condition.visitToFacade(this);
    }
}
