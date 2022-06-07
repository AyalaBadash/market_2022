package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.AndCompositeCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.OrCompositeCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeConditionFacade extends ConditionFacade{
    enum CompositeConditionType{
        or,
        and
    }
    protected CompositeConditionType compositeConditionType;
    protected List<ConditionFacade> conditionFacadeList;

    public CompositeConditionFacade(List<ConditionFacade> conditionFacadeList) {
        this.conditionFacadeList = conditionFacadeList;
    }

    public CompositeConditionFacade(){}

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
}
