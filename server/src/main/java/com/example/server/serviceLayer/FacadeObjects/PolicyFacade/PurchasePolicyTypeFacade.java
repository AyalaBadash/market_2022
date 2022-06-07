package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyType;
import com.example.server.serviceLayer.FacadeObjects.FacadeObject;

public abstract class PurchasePolicyTypeFacade implements FacadeObject<PurchasePolicyType> {

    protected PurchasePolicyLevelStateFacade purchasePolicyLevelStateFacade;

    public PurchasePolicyTypeFacade(PurchasePolicyLevelStateFacade purchasePolicyLevelStateFacade) {
        this.purchasePolicyLevelStateFacade = purchasePolicyLevelStateFacade;
    }

    public PurchasePolicyTypeFacade(){}

    public PurchasePolicyLevelStateFacade getPurchasePolicyLevelStateFacade() {
        return purchasePolicyLevelStateFacade;
    }


    public void setPurchasePolicyLevelStateFacade(PurchasePolicyLevelStateFacade purchasePolicyLevelStateFacade) {
        this.purchasePolicyLevelStateFacade = purchasePolicyLevelStateFacade;
    }
}
