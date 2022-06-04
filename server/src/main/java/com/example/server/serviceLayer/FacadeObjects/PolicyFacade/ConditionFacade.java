package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.FacadeObject;

public abstract class ConditionFacade implements FacadeObject {
    String type;
    @Override
    public abstract Condition toBusinessObject() throws MarketException;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
