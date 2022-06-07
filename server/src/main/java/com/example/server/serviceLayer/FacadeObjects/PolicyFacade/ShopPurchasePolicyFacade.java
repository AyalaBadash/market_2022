package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.PurchasePolicyLevelState;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.ShopPurchasePolicyLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

public class ShopPurchasePolicyFacade implements PurchasePolicyLevelStateFacade{
    @Override
    public PurchasePolicyLevelState toBusinessObject() throws MarketException {
        return new ShopPurchasePolicyLevelState ();
    }
}
