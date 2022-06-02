package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

public abstract class DiscountTypeFacade implements FacadeObject{
    protected int id;
    protected int percentageOfDiscount;
    protected DiscountLevelStateFacade discountLevelState;

    public DiscountTypeFacade(int percentageOfDiscount, DiscountLevelStateFacade discountLevelState) {
        this.percentageOfDiscount = percentageOfDiscount;
        this.discountLevelState = discountLevelState;
    }

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

    @Override
    public abstract Object toBusinessObject() throws MarketException;
}
