package com.example.server.serviceLayer.FacadeObjects.PolicyFacade.Wrappers;

import java.util.List;

public class ConditionWrapper {

    enum CompositeConditionWrapperType{
        AndCompositeConditionFacade,
        OrCompositeConditionFacade,
        AmountOfItemConditionFacade,
        PriceConditionFacade;
    }

    private CompositeConditionWrapperType compositeConditionWrapperType;

    private List<ConditionWrapper> conditionWrappers;
    private int itemID;
    private double amount;
    private double price;

    public ConditionWrapper(CompositeConditionWrapperType compositeConditionWrapperType, List<ConditionWrapper> conditionWrappers, int itemID, double amount, double price) {
        this.compositeConditionWrapperType = compositeConditionWrapperType;
        this.conditionWrappers = conditionWrappers;
        this.itemID = itemID;
        this.amount = amount;
        this.price = price;
    }

    public ConditionWrapper() {
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

    public CompositeConditionWrapperType getCompositeConditionWrapperType() {
        return compositeConditionWrapperType;
    }

    public void setCompositeConditionWrapperType(CompositeConditionWrapperType compositeConditionWrapperType) {
        this.compositeConditionWrapperType = compositeConditionWrapperType;
    }
}
