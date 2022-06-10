package com.example.server.serviceLayer.FacadeObjects.PolicyFacade.Wrappers;

import java.util.List;

public class PurchasePolicyTypeWrapper {
    enum CompositePurchasePolicyTypeWrapperType {
        OrCompositePurchasePolicyTypeFacade,
        AtLeastPurchasePolicyTypeFacade,
        AtMostPurchasePolicyTypeFacade
    }
    private CompositePurchasePolicyTypeWrapperType compositePurchasePolicyTypeWrapperType;
    private double amount;
    private List<PurchasePolicyTypeWrapper> purchasePolicyTypeWrappers;

    public PurchasePolicyTypeWrapper(CompositePurchasePolicyTypeWrapperType compositePurchasePolicyTypeWrapperType, double amount, List<PurchasePolicyTypeWrapper> purchasePolicyTypeWrappers) {
        this.compositePurchasePolicyTypeWrapperType = compositePurchasePolicyTypeWrapperType;
        this.amount = amount;
        this.purchasePolicyTypeWrappers = purchasePolicyTypeWrappers;
    }

    public PurchasePolicyTypeWrapper() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<PurchasePolicyTypeWrapper> getPurchasePolicyTypeWrappers() {
        return purchasePolicyTypeWrappers;
    }

    public void setPurchasePolicyTypeWrappers(List<PurchasePolicyTypeWrapper> purchasePolicyTypeWrappers) {
        this.purchasePolicyTypeWrappers = purchasePolicyTypeWrappers;
    }

    public CompositePurchasePolicyTypeWrapperType getCompositePurchasePolicyTypeWrapperType() {
        return compositePurchasePolicyTypeWrapperType;
    }

    public void setCompositePurchasePolicyTypeWrapperType(CompositePurchasePolicyTypeWrapperType compositePurchasePolicyTypeWrapperType) {
        this.compositePurchasePolicyTypeWrapperType = compositePurchasePolicyTypeWrapperType;
    }
}
