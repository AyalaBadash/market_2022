package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.FacadeObject;

public abstract class DiscountLevelStateFacade implements FacadeObject {
    String type;
    @Override
    public abstract DiscountLevelState toBusinessObject() throws MarketException;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
