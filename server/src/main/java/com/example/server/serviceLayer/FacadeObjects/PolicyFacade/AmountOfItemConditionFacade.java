package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.AmountOfItemCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.ConditionFacade;

public class AmountOfItemConditionFacade extends ConditionFacade {
    private double amount;
    int itenID;

    public AmountOfItemConditionFacade(double amount, int itenID) {
        this.amount = amount;
        this.itenID = itenID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getItenID() {
        return itenID;
    }

    public void setItenID(int itenID) {
        this.itenID = itenID;
    }

    @Override
    public Condition toBusinessObject() throws MarketException {
        return new AmountOfItemCondition (amount, itenID);
    }
}
