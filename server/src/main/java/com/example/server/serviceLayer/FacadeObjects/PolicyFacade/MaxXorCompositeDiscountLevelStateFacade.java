package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.AndCompositeDiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.MaxXorCompositeDiscountLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

public class MaxXorCompositeDiscountLevelStateFacade extends CompositeDiscountLevelStateFacade{
    public MaxXorCompositeDiscountLevelStateFacade(List<DiscountLevelStateFacade> discountLevelStateFacades) {
        super ( discountLevelStateFacades );
    }

    public MaxXorCompositeDiscountLevelStateFacade(){}

    @Override
    public DiscountLevelState toBusinessObject() throws MarketException {
        List<DiscountLevelState> discountLevelStates = new ArrayList<> (  );
        for(DiscountLevelStateFacade discountLevelStateFacade : discountLevelStateFacades)
            discountLevelStates.add ( discountLevelStateFacade.toBusinessObject () );
        return new MaxXorCompositeDiscountLevelState ( discountLevelStates );
    }
}
