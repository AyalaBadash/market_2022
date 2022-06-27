package com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Cond;
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
public abstract class CompositeCond extends Cond {
    @ManyToMany
    List<Cond> conditions;

    public CompositeCond(List<Cond> conditions) {
        this.conditions = conditions;
    }

    public CompositeCond(){}

    @Override
    public abstract boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException;

    public List<Cond> getConditions() {
        return conditions;
    }

    public void setConditions(List<Cond> conditions) {
        this.conditions = conditions;
    }
}
