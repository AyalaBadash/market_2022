package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.AmountOfItemCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.AndCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.OrCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Cond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.PriceCond;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

public class AmountOfItemConditionFacade extends ConditionFacade {
    private double amount;
    int itemID;

    public AmountOfItemConditionFacade(double amount, int itemID) {
        this.amount = amount;
        this.itemID = itemID;
    }

    public AmountOfItemConditionFacade() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public Cond toBusinessObject() throws MarketException {
        return new AmountOfItemCond(amount, itemID);
    }

    @Override
    public ConditionFacade toFacade(PriceCond conditionFacade) {
        return null;
    }

    @Override
    public ConditionFacade toFacade(AmountOfItemCond conditionFacade) {
        return new AmountOfItemConditionFacade ( conditionFacade.getAmountNeeded (), conditionFacade.getItemNeeded () );
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
