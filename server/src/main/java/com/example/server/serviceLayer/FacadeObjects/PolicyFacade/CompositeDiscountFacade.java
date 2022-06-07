package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount.KindOfCompositeDiscount;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount.MaxCompositeDiscount;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeDiscountFacade extends DiscountTypeFacade{
    protected List<DiscountTypeFacade> discountTypes;

    public CompositeDiscountFacade(int percentageOfDiscount, DiscountLevelStateFacade discountLevelState, List<DiscountTypeFacade> discountTypes) {
        super (percentageOfDiscount, discountLevelState );
        this.discountTypes = discountTypes;
    }

    public CompositeDiscountFacade(){}

    public List<DiscountTypeFacade> getDiscountTypes() {
        return discountTypes;
    }

    public void setDiscountTypes(List<DiscountTypeFacade> discountTypes) {
        this.discountTypes = discountTypes;
    }
}
