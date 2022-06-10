package com.example.server.serviceLayer.FacadeObjects.PolicyFacade.Wrappers;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.AmountOfItemCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.AndCompositeCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.OrCompositeCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.PriceCondition;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.FacadeObject;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.AmountOfItemConditionFacade;

import java.util.ArrayList;
import java.util.List;

public class ConditionWrapper implements FacadeObject<Condition> {

    public enum ConditionWrapperType {
        AndCompositeConditionFacade,
        OrCompositeConditionFacade,
        AmountOfItemConditionFacade,
        PriceConditionFacade;

    }
    private ConditionWrapperType conditionWrapperType;

    private List<ConditionWrapper> conditionWrappers;

    private int itemID;
    private double amount;
    private double price;
    public ConditionWrapper(ConditionWrapperType conditionWrapperType, List<ConditionWrapper> conditionWrappers, int itemID, double amount, double price) {
        this.conditionWrapperType = conditionWrapperType;
        this.conditionWrappers = conditionWrappers;
        this.itemID = itemID;
        this.amount = amount;
        this.price = price;
    }

    public ConditionWrapper() {
    }

    @Override
    public Condition toBusinessObject() throws MarketException {
        switch (conditionWrapperType){
            case PriceConditionFacade -> {
                return new PriceCondition ( price );
            }
            case AmountOfItemConditionFacade -> {
                return new AmountOfItemCondition ( amount, itemID );
            }
            case AndCompositeConditionFacade -> {
                List<Condition> conditions = new ArrayList<> (  );
                for(ConditionWrapper conditionWrapper : conditionWrappers){
                    conditions.add ( conditionWrapper.toBusinessObject () );
                }
                return new AndCompositeCondition ( conditions );
            }
            case OrCompositeConditionFacade -> {
                List<Condition> conditions = new ArrayList<> (  );
                for(ConditionWrapper conditionWrapper : conditionWrappers){
                    conditions.add ( conditionWrapper.toBusinessObject () );
                }
                return new OrCompositeCondition ( conditions );
            }
            default -> {
                return null;
            }
        }
    }

    public List<ConditionWrapper> getConditionWrappers() {
        return conditionWrappers;
    }

    public void setConditionWrappers(List<ConditionWrapper> conditionWrappers) {
        this.conditionWrappers = conditionWrappers;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ConditionWrapperType getCompositeConditionWrapperType() {
        return conditionWrapperType;
    }

    public void setCompositeConditionWrapperType(ConditionWrapperType conditionWrapperType) {
        this.conditionWrapperType = conditionWrapperType;
    }
}
