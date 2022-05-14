package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.ShoppingBasket;

import java.util.Map;

public class AmountOfItemCondition extends Condition{
    private double amountNeeded;
    private Item itemNeeded;//TODO verify we change it to Item?

    public AmountOfItemCondition(double amountNeeded, Item itemNeeded) {
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
        Map<Item,Double> map = shoppingBasket.getItems();
        return (map.containsKey(itemNeeded)&&map.get(itemNeeded)>=amountNeeded);
    }
}
