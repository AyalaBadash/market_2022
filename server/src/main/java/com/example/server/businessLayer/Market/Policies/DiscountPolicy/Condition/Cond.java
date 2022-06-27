package com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "Cond",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class Cond {
    @Id
    @GeneratedValue
    private long id;

    public Cond(){}
    public abstract boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException;
    public abstract boolean equals(Object object);

    public boolean isAmountOfItem(){
        return false;
    }

    public boolean isPrice(){
        return false;
    }

    public boolean isOr(){
        return false;
    }

    public boolean isAnd(){
        return false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract ConditionFacade visitToFacade(AmountOfItemConditionFacade conditionFacade);
    public abstract  ConditionFacade visitToFacade(PriceConditionFacade conditionFacade);
    public abstract ConditionFacade visitToFacade(AndCompositeConditionFacade conditionFacade);
    public abstract ConditionFacade visitToFacade(OrCompositeConditionFacade conditionFacade);
}
