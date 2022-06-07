package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.OrCompositePurchasePolicyType;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

public class OrCompositePurchasePolicyTypeFacade extends CompositePurchasePolicyTypeFacade{

    @Override
    public PurchasePolicyType toBusinessObject() throws MarketException {
        List<PurchasePolicyType> purchasePolicyTypes = new ArrayList<> (  );
        for(PurchasePolicyTypeFacade purchasePolicyTypeFacade: purchasePolicyTypeFacades)
            purchasePolicyTypes.add ( purchasePolicyTypeFacade.toBusinessObject () );
        return new OrCompositePurchasePolicyType ( purchasePolicyTypes );
    }
}
