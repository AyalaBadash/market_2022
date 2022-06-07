package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.AndCompositeCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.OrCompositeCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

public class OrCompositeConditionFacade extends CompositeConditionFacade{

    public OrCompositeConditionFacade(List<ConditionFacade> conditionFacadeList) {
        super ( conditionFacadeList );
    }

    @Override
    public Condition toBusinessObject() throws MarketException {
        List<Condition> conditions = new ArrayList<> (  );
        for(ConditionFacade conditionFacade: conditionFacadeList)
            conditions.add ( conditionFacade.toBusinessObject () );
        return new OrCompositeCondition ( conditions );
    }
}
