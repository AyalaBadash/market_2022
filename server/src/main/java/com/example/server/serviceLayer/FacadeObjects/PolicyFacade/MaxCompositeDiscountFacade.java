package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount.MaxCompositeDiscount;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

public class MaxCompositeDiscountFacade extends CompositeDiscountFacade{
    public MaxCompositeDiscountFacade(int percentageOfDiscount, DiscountLevelStateFacade discountLevelState, List<DiscountTypeFacade> discountTypes, CompositeDiscountType compositeDiscountType) {
        super ( percentageOfDiscount, discountLevelState, discountTypes, compositeDiscountType );
    }

    @Override
    public DiscountType toBusinessObject() throws MarketException {
        List<DiscountType> discountTypeList = new ArrayList<> (  );
        for(DiscountTypeFacade discountTypeFacade: discountTypes)
            discountTypeList.add ( discountTypeFacade.toBusinessObject () );
        return new MaxCompositeDiscount ( discountTypeList );
    }
}
