package com.example.server.serviceLayer.Requests;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyType;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.DiscountTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PurchasePolicyTypeFacade;

public class AddPurchasePolicyToShopRequest {

    private PurchasePolicyTypeFacade purchasePolicyTypeFacade;
    private String shopName;
    private String visitorName;

    public AddPurchasePolicyToShopRequest(PurchasePolicyTypeFacade purchasePolicyTypeFacade, String shopName, String visitorName) {
        this.purchasePolicyTypeFacade = purchasePolicyTypeFacade;
        this.shopName = shopName;
        this.visitorName = visitorName;
    }

    AddPurchasePolicyToShopRequest(){}

    public PurchasePolicyTypeFacade getPolicy() {
        return purchasePolicyTypeFacade;
    }

    public void setPolicy(PurchasePolicyTypeFacade purchasePolicyTypeFacade) {
        this.purchasePolicyTypeFacade = purchasePolicyTypeFacade;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }
}
