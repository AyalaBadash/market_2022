package com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.List;

public class XorCompositePurchasePolicyLevelState extends CompositePurchasePolicyLevelState{

    public XorCompositePurchasePolicyLevelState(List<PurchasePolicyLevelState> purchasePolicyLevelStates) {
        super ( purchasePolicyLevelStates );
    }

    @Override
    public boolean isPolicyHeld(ShoppingBasket shoppingBasket, double amount, boolean greater) throws MarketException {
        for ( PurchasePolicyLevelState purchasePolicyLevelState : purchasePolicyLevelStates ) {
            List<Double> amounts = purchasePolicyLevelState.getAmount ( shoppingBasket );
            for(Double cur : amounts)
                if((greater && cur > amount) || cur <= amount)
                    return true;
        }
        return false;
    }
}
