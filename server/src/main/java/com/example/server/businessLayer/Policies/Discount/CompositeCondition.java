package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingBasket;

public class CompositeCondition extends Condition{


    public enum CompositeType {
        ORType,
        XORType,
        ANDType;
    }
    protected Condition baseCond;
    protected CompositeType compositeType;
    public CompositeCondition(Condition baseCond, CompositeType compositeType) {
        this.baseCond = baseCond;
        this.compositeType = compositeType;
    }

    public Condition getBaseCond() {
        return baseCond;
    }

    public void setBaseCond(Condition baseCond) {
        this.baseCond = baseCond;
    }

    public CompositeType getCompositeType() {
        return compositeType;
    }

    public void setCompositeType(CompositeType compositeType) {
        this.compositeType = compositeType;
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        return false;
    }
}
