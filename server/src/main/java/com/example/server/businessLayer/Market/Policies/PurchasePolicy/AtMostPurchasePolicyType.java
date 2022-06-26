package com.example.server.businessLayer.Market.Policies.PurchasePolicy;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.PurchasePolicyLevelState;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.dataLayer.repositories.AtMostPolicyRep;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.AtLeastPurchasePolicyTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.AtMostPurchasePolicyTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.OrCompositePurchasePolicyTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.PurchasePolicyTypeFacade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "AtMost")
public class AtMostPurchasePolicyType extends PurchasePolicyType {
    private double amount;
    private static AtMostPolicyRep atMostPolicyRep;

    public AtMostPurchasePolicyType(PurchasePolicyLevelState purchasePolicyLevelState, double amount) {
        super ( purchasePolicyLevelState );
        this.amount = amount;
        atMostPolicyRep.save(this);
    }

    public AtMostPurchasePolicyType(){}

    @Override
    public boolean equals(Object object) {
        if(object instanceof AtMostPurchasePolicyType){
            AtMostPurchasePolicyType atMostPurchasePolicyType = (AtMostPurchasePolicyType) object;
            return atMostPurchasePolicyType.amount == this.amount;
        }
        return false;
    }
    @Override
    public boolean isPolicyHeld(ShoppingBasket shoppingBasket) {
        return false;
    }

    @Override
    public boolean isAtLeast() {
        return false;
    }

    @Override
    public boolean isAtMost() {
        return true;
    }

    @Override
    public boolean isOrComposite() {
        return false;
    }

    @Override
    public PurchasePolicyTypeFacade visitToFacade(AtLeastPurchasePolicyTypeFacade policyTypeFacade) {
        return null;
    }

    @Override
    public PurchasePolicyTypeFacade visitToFacade(AtMostPurchasePolicyTypeFacade policyTypeFacade) {
        return policyTypeFacade.toFacade ( this );
    }

    @Override
    public PurchasePolicyTypeFacade visitToFacade(OrCompositePurchasePolicyTypeFacade policyTypeFacade) {
        return null;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public static AtMostPolicyRep getAtMostPolicyRep() {
        return atMostPolicyRep;
    }

    public static void setAtMostPolicyRep(AtMostPolicyRep atMostPolicyRep) {
        AtMostPurchasePolicyType.atMostPolicyRep = atMostPolicyRep;
    }
}
