package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.AmountOfItemCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.AndCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.OrCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Cond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.PriceCond;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

public class OrCompositeConditionFacade extends CompositeConditionFacade{

    public OrCompositeConditionFacade(List<ConditionFacade> conditionFacadeList) {
        super ( conditionFacadeList );
    }

    public OrCompositeConditionFacade(){}
    @Override
    public Cond toBusinessObject() throws MarketException {
        List<Cond> conditions = new ArrayList<> (  );
        for(ConditionFacade conditionFacade: conditionFacadeList)
            conditions.add ( conditionFacade.toBusinessObject () );
        return new OrCompositeCond( conditions );
    }

    @Override
    public ConditionFacade toFacade(PriceCond condition) {
        return null;
    }

    @Override
    public ConditionFacade toFacade(AmountOfItemCond condition) {
        return null;
    }

    @Override
    public ConditionFacade toFacade(AndCompositeCond condition) {
        return null;
    }

    @Override
    public ConditionFacade toFacade(OrCompositeCond condition) {
        List<Cond> conditions = condition.getConditions ();
        List<ConditionFacade> conditionFacades = new ArrayList<> (  );
        for(Cond cur : conditions)
            conditionFacades.add ( getConditionFacade ( cur ) );
        return new OrCompositeConditionFacade ( conditionFacades );
    }

    @Override
    public ConditionFacade toFacade(Cond condition) {
        return condition.visitToFacade ( this );
    }
}
