package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.ShopLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.DiscountLevelStateFacade;

public class ShopLevelStateFacade extends DiscountLevelStateFacade {
    @Override
    public DiscountLevelState toBusinessObject() throws MarketException {
        return new ShopLevelState ();
    }
}
