package com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.CategoryLevelState;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.Map;

public class AmountOfItemCondition extends Condition {
    private double amountNeeded;
    private Integer itemNeeded;

    public AmountOfItemCondition(double amountNeeded, Integer itemNeeded) {
        this.amountNeeded = amountNeeded;
        this.itemNeeded = itemNeeded;
    }

    /**
     *
     * @param shoppingBasket
     * @return there is at least amountNeeded of itemNeeded in the shoppingBasket
     */
    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) {
        Map<Integer,Double> map = shoppingBasket.getItems();
        return (map.containsKey(itemNeeded)&&map.get(itemNeeded)>=amountNeeded);
    }
    @Override
    public boolean equals(Object object){
        if(object instanceof AmountOfItemCondition){
            AmountOfItemCondition toCompare = (AmountOfItemCondition) object;
            return this.amountNeeded == toCompare.amountNeeded && this.itemNeeded == toCompare.itemNeeded;
        }
        return false;
    }

}
