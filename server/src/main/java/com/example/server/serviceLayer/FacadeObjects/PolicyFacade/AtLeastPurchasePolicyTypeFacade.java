package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.PurchasePolicyTypeFacade;

public class AtLeastPurchasePolicyTypeFacade extends PurchasePolicyTypeFacade {
    @Override
    public PurchasePolicyType toBusinessObject() throws MarketException {
        return null;
    }
}
