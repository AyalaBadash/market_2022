package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.PriceCondition;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.ConditionFacade;

public class PriceConditionFacade extends ConditionFacade {
    private int price;

    public PriceConditionFacade(int price) {
        this.price = price;
    }

    public PriceConditionFacade(){}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public Condition toBusinessObject() throws MarketException {
        return new PriceCondition ( price );
    }
}
