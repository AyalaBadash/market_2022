package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.AndCompositeCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.OrCompositeCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

    public class CompositeConditionFacade extends ConditionFacade{
    enum CompositeConditionType{
        or,
        and
    }
    private CompositeConditionType compositeConditionType;
    private List<ConditionFacade> conditionFacadeList;

    public CompositeConditionFacade(){};
    public CompositeConditionFacade(CompositeConditionType compositeConditionType, List<ConditionFacade> conditionFacadeList) {
        this.compositeConditionType = compositeConditionType;
        this.conditionFacadeList = conditionFacadeList;
        this.type = "CompositeConditionFacade";

    }

    public CompositeConditionType getCompositeConditionType() {
        return compositeConditionType;
    }

    public void setCompositeConditionType(CompositeConditionType compositeConditionType) {
        this.compositeConditionType = compositeConditionType;
    }

    public List<ConditionFacade> getConditionFacadeList() {
        return conditionFacadeList;
    }

    public void setConditionFacadeList(List<ConditionFacade> conditionFacadeList) {
        this.conditionFacadeList = conditionFacadeList;
    }

    @Override
    public Condition toBusinessObject() throws MarketException {
        List<Condition> conditions = new ArrayList<> (  );
        for(ConditionFacade conditionFacade: conditionFacadeList)
            conditions.add ( conditionFacade.toBusinessObject () );
        switch (compositeConditionType){
            case or -> {
                return new OrCompositeCondition ( conditions );
            }
            case and -> {
                return new AndCompositeCondition ( conditions );
            }
            default -> {
                throw new MarketException ( "type of composite condition does not exist" );
            }
        }
    }
}
