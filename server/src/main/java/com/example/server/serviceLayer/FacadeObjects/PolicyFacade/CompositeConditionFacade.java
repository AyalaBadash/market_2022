package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import java.util.List;

public abstract class CompositeConditionFacade extends ConditionFacade{
    protected List<ConditionFacade> conditionFacadeList;

    public CompositeConditionFacade(List<ConditionFacade> conditionFacadeList) {
        this.conditionFacadeList = conditionFacadeList;
    }

    public CompositeConditionFacade(){}

    public List<ConditionFacade> getConditionFacadeList() {
        return conditionFacadeList;
    }

    public void setConditionFacadeList(List<ConditionFacade> conditionFacadeList) {
        this.conditionFacadeList = conditionFacadeList;
    }


}
