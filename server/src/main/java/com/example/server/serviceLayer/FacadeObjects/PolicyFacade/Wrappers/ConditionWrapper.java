package com.example.server.serviceLayer.FacadeObjects.PolicyFacade.Wrappers;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.AmountOfItemCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.AndCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.OrCompositeCond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Cond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.PriceCond;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.FacadeObject;

import java.util.ArrayList;
import java.util.List;

public class ConditionWrapper implements FacadeObject<Cond> {

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
    public Cond toBusinessObject() throws MarketException {
        switch (conditionWrapperType){
            case PriceConditionFacade -> {
                return new PriceCond( price );
            }
            case AmountOfItemConditionFacade -> {
                return new AmountOfItemCond( amount, itemID );
            }
            case AndCompositeConditionFacade -> {
                List<Cond> conditions = new ArrayList<> (  );
                for(ConditionWrapper conditionWrapper : conditionWrappers){
                    conditions.add ( conditionWrapper.toBusinessObject () );
                }
                return new AndCompositeCond( conditions );
            }
            case OrCompositeConditionFacade -> {
                List<Cond> conditions = new ArrayList<> (  );
                for(ConditionWrapper conditionWrapper : conditionWrappers){
                    conditions.add ( conditionWrapper.toBusinessObject () );
                }
                return new OrCompositeCond( conditions );
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

    public ConditionWrapperType getConditionWrapperType() {
        return conditionWrapperType;
    }

    public void setConditionWrapperType(ConditionWrapperType conditionWrapperType) {
        this.conditionWrapperType = conditionWrapperType;
    }

    public ConditionWrapperType getCompositeConditionWrapperType() {
        return conditionWrapperType;
    }

    public void setCompositeConditionWrapperType(ConditionWrapperType conditionWrapperType) {
        this.conditionWrapperType = conditionWrapperType;
    }

    public static ConditionWrapper createConditionWrapper(Cond condition) {
        ConditionWrapper conditionWrapper = new ConditionWrapper (  );
        if(condition.isPrice ()){
            PriceCond priceCondition = (PriceCond) condition;
            conditionWrapper.setCompositeConditionWrapperType ( ConditionWrapper.ConditionWrapperType.PriceConditionFacade );
            conditionWrapper.setPrice ( priceCondition.getPriceNeeded () );
        } else if (condition.isAmountOfItem ()) {
            AmountOfItemCond amountOfItemCondition = (AmountOfItemCond) condition;
            conditionWrapper.setCompositeConditionWrapperType ( ConditionWrapper.ConditionWrapperType.AmountOfItemConditionFacade );
            conditionWrapper.setAmount ( amountOfItemCondition.getAmountNeeded () );
            conditionWrapper.setItemID ( amountOfItemCondition.getItemNeeded () );
        } else if (condition.isAnd ()) {
            AndCompositeCond andCompositeCondition = (AndCompositeCond) condition;
            List<ConditionWrapper> conditionWrappers = new ArrayList<> (  );
            for(Cond cur : andCompositeCondition.getConditions ()){
                conditionWrappers.add ( createConditionWrapper ( cur ) );
            }
            conditionWrapper.setCompositeConditionWrapperType ( ConditionWrapper.ConditionWrapperType.AndCompositeConditionFacade );
            conditionWrapper.setConditionWrappers ( conditionWrappers );
        } else{
            OrCompositeCond orCompositeCondition = (OrCompositeCond) condition;
            List<ConditionWrapper> conditionWrappers = new ArrayList<> (  );
            for(Cond cur : orCompositeCondition.getConditions ()){
                conditionWrappers.add ( createConditionWrapper ( cur ) );
            }
            conditionWrapper.setCompositeConditionWrapperType ( ConditionWrapper.ConditionWrapperType.OrCompositeConditionFacade );
            conditionWrapper.setConditionWrappers ( conditionWrappers );
        }
        return conditionWrapper;
    }


}
