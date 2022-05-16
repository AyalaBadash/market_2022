package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.ShoppingBasket;

import java.util.Map;

public class AmountOfItemCondition extends CompositeCondition{
    private double amountNeeded;
    private Integer itemNeeded;

    public AmountOfItemCondition(Condition baseCond, CompositeType compositeType, double amountNeeded, Integer itemNeeded) {
        super(baseCond, compositeType);
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
}
