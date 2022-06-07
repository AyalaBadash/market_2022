package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.PurchasePolicyLevelState;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.XorCompositePurchasePolicyLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

public class XorCompositePurchasePolicyLevelStateFacade extends CompositePurchasePolicyLevelStateFacade{
    @Override
    public PurchasePolicyLevelState toBusinessObject() throws MarketException {
        List<PurchasePolicyLevelState> purchasePolicyLevelStates = new ArrayList<> (  );
        for(PurchasePolicyLevelStateFacade purchasePolicyLevelStateFacade : purchasePolicyLevelStateFacades)
            purchasePolicyLevelStates.add ( purchasePolicyLevelStateFacade.toBusinessObject () );
        return new XorCompositePurchasePolicyLevelState ( purchasePolicyLevelStates);
    }
}
