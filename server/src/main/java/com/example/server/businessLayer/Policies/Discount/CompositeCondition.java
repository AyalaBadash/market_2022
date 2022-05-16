package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingBasket;

public class CompositeCondition extends Condition{


    public enum CompositeConditionType {
        ORType,
        XORType,
        ANDType;
    }
    protected Condition baseCond;
    protected CompositeConditionType compositeType;
    public CompositeCondition(Condition baseCond, CompositeConditionType compositeType) {
        this.baseCond = baseCond;
        this.compositeType = compositeType;
    }

    public Condition getBaseCond() {
        return baseCond;
    }

    public void setBaseCond(Condition baseCond) {
        this.baseCond = baseCond;
    }

    public CompositeConditionType getCompositeType() {
        return compositeType;
    }

    public void setCompositeType(CompositeConditionType compositeType) {
        this.compositeType = compositeType;
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        return false;
    }
}