package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.AndCompositeDiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.CompositeDiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

public class AndCompositeDiscountLevelStateFacade extends CompositeDiscountLevelStateFacade {
    @Override
    public DiscountLevelState toBusinessObject() throws MarketException {
        List<DiscountLevelState> discountLevelStates = new ArrayList<> (  );
        for(DiscountLevelStateFacade discountLevelStateFacade : discountLevelStateFacades)
            discountLevelStates.add ( discountLevelStateFacade.toBusinessObject () );
        return new AndCompositeDiscountLevelState ( discountLevelStates );
    }
}
