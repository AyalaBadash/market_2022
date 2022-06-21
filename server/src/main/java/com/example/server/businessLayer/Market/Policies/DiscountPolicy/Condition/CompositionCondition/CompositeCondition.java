package com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import javax.persistence.*;
import java.util.List;
//@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "CompositeCondition",
        discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue(value = "CompositeCondition")
public abstract class CompositeCondition extends Condition {
    @ManyToMany
    List<Condition> conditions;

    public CompositeCondition(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public CompositeCondition(){}

    @Override
    public abstract boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException;

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
