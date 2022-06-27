package com.example.server.businessLayer.Market.Policies.PurchasePolicy;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.ShopPurchasePolicyLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.dataLayer.repositories.OrTypePolicyRep;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.AtLeastPurchasePolicyTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.AtMostPurchasePolicyTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.OrCompositePurchasePolicyTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.PurchasePolicyTypeFacade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;
@Entity
@DiscriminatorValue(value = "Or")
public class OrCompositePurchasePolicyType extends CompositePurchasePolicyType {
    private static OrTypePolicyRep orTypePolicyRep;
    public OrCompositePurchasePolicyType(List<PurchasePolicyType> policies) {
        super ( new ShopPurchasePolicyLevelState (), policies );
        if (!MarketConfig.IS_TEST_MODE) {
            orTypePolicyRep.save(this);
        }
    }

    public OrCompositePurchasePolicyType(){}

    @Override
    public boolean equals(Object object) {
        if(object instanceof OrCompositePurchasePolicyType){
            OrCompositePurchasePolicyType toCompare = (OrCompositePurchasePolicyType) object;
            for( PurchasePolicyType policyType: this.policies){
                if (!toCompare.policies.contains ( policyType ))
                    return false;
            }
            for( PurchasePolicyType policyType: toCompare.policies){
                if ( !this.policies.contains ( policyType ))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isPolicyHeld(ShoppingBasket shoppingBasket) throws MarketException {
        for(PurchasePolicyType purchasePolicyType : policies)
        {
            if (purchasePolicyType.isPolicyHeld ( shoppingBasket ))
                return true;
        }
        return false;
    }

    @Override
    public boolean isAtLeast() {
        return false;
    }

    @Override
    public boolean isAtMost() {
        return false;
    }

    @Override
    public boolean isOrComposite() {
        return true;
    }

    @Override
    public PurchasePolicyTypeFacade visitToFacade(AtLeastPurchasePolicyTypeFacade policyTypeFacade) {
        return policyTypeFacade.toFacade ( this );
    }

    @Override
    public PurchasePolicyTypeFacade visitToFacade(AtMostPurchasePolicyTypeFacade policyTypeFacade) {
        return null;
    }

    @Override
    public PurchasePolicyTypeFacade visitToFacade(OrCompositePurchasePolicyTypeFacade policyTypeFacade) {
        return null;
    }

    public static OrTypePolicyRep getOrTypePolicyRep() {
        return orTypePolicyRep;
    }

    public static void setOrTypePolicyRep(OrTypePolicyRep orTypePolicyRep) {
        OrCompositePurchasePolicyType.orTypePolicyRep = orTypePolicyRep;
    }
}
