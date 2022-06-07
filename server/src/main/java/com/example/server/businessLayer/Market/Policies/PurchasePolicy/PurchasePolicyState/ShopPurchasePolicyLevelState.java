package com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.ArrayList;
import java.util.List;

public class ShopPurchasePolicyLevelState implements PurchasePolicyLevelState{
    @Override
    public boolean isPolicyHeld(ShoppingBasket shoppingBasket, double amount, boolean greater) throws MarketException {
        double curAmount = getAmount ( shoppingBasket ).get ( 0 );
        return (greater && curAmount > amount) || curAmount <= amount;
    }

    @Override
    public List<Double> getAmount(ShoppingBasket shoppingBasket) throws MarketException {
        List<Double> amount = new ArrayList<> (  );
        double curAmount = 0;
        for(Double itemAmount : shoppingBasket.getItems ().values ())
            curAmount += itemAmount;
        amount.add ( curAmount );
        return amount;
    }
}
