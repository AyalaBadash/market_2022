package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.AmountOfItemCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.AndCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.OrCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Cond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.PriceCond;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

public class PriceConditionFacade extends ConditionFacade {
    private double price;

    public PriceConditionFacade(double price) {
        this.price = price;
    }

    public PriceConditionFacade(){}

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public Cond toBusinessObject() throws MarketException {
        return new PriceCond( price );
    }

    @Override
    public ConditionFacade toFacade(PriceCond condition) {
        return new PriceConditionFacade ( condition.getPriceNeeded () );
    }

    @Override
    public ConditionFacade toFacade(AmountOfItemCond condition) {
        return null;
    }

    @Override
    public ConditionFacade toFacade(AndCompositeCond condition) {
        return null;
    }

    @Override
    public ConditionFacade toFacade(OrCompositeCond condition) {
        return null;
    }

    @Override
    public ConditionFacade toFacade(Cond condition) {
        return condition.visitToFacade ( this );
    }
}
