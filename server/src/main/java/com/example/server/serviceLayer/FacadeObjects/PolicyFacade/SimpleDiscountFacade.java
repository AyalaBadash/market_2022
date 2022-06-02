package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.SimpleDiscount;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

public class SimpleDiscountFacade extends DiscountTypeFacade{
    public SimpleDiscountFacade(int percentageOfDiscount, DiscountLevelStateFacade discountLevelState) {
        super ( percentageOfDiscount, discountLevelState );
    }

    @Override
    public Object toBusinessObject() throws MarketException {
        return new SimpleDiscount ( percentageOfDiscount, discountLevelState.toBusinessObject () );
    }
}
