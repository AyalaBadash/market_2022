package com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.ArrayList;
import java.util.List;

public class CategoryPurchasePolicyLevelState implements PurchasePolicyLevelState{
    Item.Category category;

    public CategoryPurchasePolicyLevelState(Item.Category category) {
        this.category = category;
    }

    @Override
    public boolean isPolicyHeld(ShoppingBasket shoppingBasket, double amount, boolean greater) throws MarketException {
        double curAmount = getAmount ( shoppingBasket ).get ( 0 );
        return (greater && curAmount > amount) || curAmount <= amount;
    }

    @Override
    public List<Double> getAmount(ShoppingBasket shoppingBasket) throws MarketException {
        List<Double> amount = new ArrayList<> (  );
        double curAmount = 0;
        for(Integer itemId : shoppingBasket.getItems().keySet ())
            if(Market.getInstance ().getItemByID ( itemId ).getCategory ().equals ( category ))
                curAmount += shoppingBasket.getItems ().get ( itemId );
        amount.add ( curAmount );
        return amount;
    }
}
