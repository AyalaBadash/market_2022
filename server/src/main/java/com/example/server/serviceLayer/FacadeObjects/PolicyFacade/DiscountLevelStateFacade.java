package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.FacadeObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize (using = LevelDeserializer.class)
public abstract class DiscountLevelStateFacade implements FacadeObject {

    @Override
    public abstract DiscountLevelState toBusinessObject() throws MarketException;
}
