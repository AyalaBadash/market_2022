package com.example.server.businessLayer.Market.Policies.PurchasePolicy;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.businessLayer.Market.Users.Visitor;

import java.util.List;

public class PurchasePolicy {
    private List<PurchasePolicyType> validPurchasePolicies;

    public PurchasePolicy(List<PurchasePolicyType> validPurchasePolicies) {
        this.validPurchasePolicies = validPurchasePolicies;
    }

    public List<PurchasePolicyType> getValidPurchasePolicies() {
        return validPurchasePolicies;
    }

    public void setValidPurchasePolicies(List<PurchasePolicyType> validPurchasePolicies) {
        this.validPurchasePolicies = validPurchasePolicies;
    }

    public void addPurchasePolicy(PurchasePolicyType purchasePolicyType){
        throw new UnsupportedOperationException (  );
    }

    public boolean isPoliciesHeld(Visitor visitor, ShoppingBasket shoppingBasket) throws MarketException {
        for(PurchasePolicyType purchasePolicyType : validPurchasePolicies){
            if (!purchasePolicyType.isPolicyHeld ( visitor, shoppingBasket ))
                return false;
        }
        return true;
    }
}
