package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.SimpleDiscount;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.DiscountLevelStateFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.DiscountTypeFacade;

public class SimpleDiscountFacade extends DiscountTypeFacade {
    public SimpleDiscountFacade(int percentageOfDiscount, DiscountLevelStateFacade discountLevelState) {
        super (percentageOfDiscount, discountLevelState );
    }

    public SimpleDiscountFacade(){}

    @Override
    public DiscountType toBusinessObject() throws MarketException {
        return new SimpleDiscount ( percentageOfDiscount, discountLevelState.toBusinessObject () );
    }
}
