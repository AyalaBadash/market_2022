package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.AtMostPurchasePolicyType;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

public class AtMostPurchasePolicyTypeFacade extends PurchasePolicyTypeFacade {

    protected double amount;

    public AtMostPurchasePolicyTypeFacade(PurchasePolicyLevelStateFacade purchasePolicyLevelStateFacade, double amount) {
        super ( purchasePolicyLevelStateFacade );
        this.amount = amount;
    }

    public AtMostPurchasePolicyTypeFacade() {
    }

    @Override
    public PurchasePolicyType toBusinessObject() throws MarketException {
        return new AtMostPurchasePolicyType ( purchasePolicyLevelStateFacade.toBusinessObject (), amount );
    }
}
