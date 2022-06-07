package com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.ArrayList;
import java.util.List;

public class OrCompositePurchasePolicyLevelState extends CompositePurchasePolicyLevelState{

    public OrCompositePurchasePolicyLevelState(List<PurchasePolicyLevelState> purchasePolicyLevelStates) {
        super ( purchasePolicyLevelStates );
    }

    @Override
    public boolean isPolicyHeld(ShoppingBasket shoppingBasket, double amount, boolean greater) throws MarketException {
        double curAmount = 0;
        for ( PurchasePolicyLevelState purchasePolicyLevelState : purchasePolicyLevelStates ) {
            List<Double> amounts = purchasePolicyLevelState.getAmount ( shoppingBasket );
            for(Double cur : amounts)
                curAmount += cur;
        }
        return ((greater && curAmount > amount) || curAmount <= amount);
    }
}
