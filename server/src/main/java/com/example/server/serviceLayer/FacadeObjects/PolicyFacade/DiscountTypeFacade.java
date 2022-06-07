package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.FacadeObject;

public abstract class DiscountTypeFacade implements FacadeObject<DiscountType> {
    protected int percentageOfDiscount;
    protected DiscountLevelStateFacade discountLevelState;

    public DiscountTypeFacade(int percentageOfDiscount, DiscountLevelStateFacade discountLevelState) {
        this.percentageOfDiscount = percentageOfDiscount;
        this.discountLevelState = discountLevelState;
    }

    public DiscountTypeFacade(){}

    public int getPercentageOfDiscount() {
        return percentageOfDiscount;
    }

    public void setPercentageOfDiscount(int percentageOfDiscount) {
        this.percentageOfDiscount = percentageOfDiscount;
    }

    public DiscountLevelStateFacade getDiscountLevelState() {
        return discountLevelState;
    }

    public void setDiscountLevelState(DiscountLevelStateFacade discountLevelState) {
        this.discountLevelState = discountLevelState;
    }
}
