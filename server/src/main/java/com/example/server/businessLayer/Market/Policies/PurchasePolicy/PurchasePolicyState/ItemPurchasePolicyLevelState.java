package com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.ArrayList;
import java.util.List;

public class ItemPurchasePolicyLevelState implements PurchasePolicyLevelState {
    Integer itemId;

    public ItemPurchasePolicyLevelState(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean isPolicyHeld(ShoppingBasket shoppingBasket, double amount, boolean greater) throws MarketException {
        double curAmount = getAmount ( shoppingBasket ).get ( 0 );
        return (greater && curAmount > amount) || curAmount <= amount;
    }

    @Override
    public List<Double> getAmount(ShoppingBasket shoppingBasket) {
        List<Double> amount = new ArrayList<> (  );
        Double curAmount;
        curAmount = shoppingBasket.getItems ().get ( itemId );
        if(curAmount == null)
            amount.add ( Double.valueOf ( 0 ) );
        amount.add ( curAmount );
        return amount;
    }
}
