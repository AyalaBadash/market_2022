package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.AtLeastPurchasePolicyType;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

public class AtLeastPurchasePolicyTypeFacade extends PurchasePolicyTypeFacade {
    protected double amount;
    public AtLeastPurchasePolicyTypeFacade(PurchasePolicyLevelStateFacade purchasePolicyLevelStateFacade, double amount) {
        super ( purchasePolicyLevelStateFacade );
        this.amount = amount;
    }

    public AtLeastPurchasePolicyTypeFacade() {
    }

    @Override
    public PurchasePolicyType toBusinessObject() throws MarketException {
        return new AtLeastPurchasePolicyType ( purchasePolicyLevelStateFacade.toBusinessObject (), amount );
    }
}
