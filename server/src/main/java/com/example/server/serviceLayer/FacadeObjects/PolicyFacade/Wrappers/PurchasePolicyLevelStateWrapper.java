package com.example.server.serviceLayer.FacadeObjects.PolicyFacade.Wrappers;

import com.example.server.businessLayer.Market.Item;

import java.util.List;

public class PurchasePolicyLevelStateWrapper {
    enum CompositeDiscountLevelStateWrapperType{
        AndCompositePurchasePolicyLevelStateFacade,
        XorCompositePurchasePolicyLevelStateFacade,
        OrCompositePurchasePolicyLevelStateFacade,
        CategoryPurchasePolicyLevelStateFacade,
        ItemPurchasePolicyLevelStateFacade,
        ShopPurchasePolicyLevelStateFacade
    }
    private CompositeDiscountLevelStateWrapperType compositeDiscountLevelStateWrapperType;
    private int itemID;
    private Item.Category category;
    private List<PurchasePolicyLevelStateWrapper> purchasePolicyLevelStateWrappers;

    public PurchasePolicyLevelStateWrapper(CompositeDiscountLevelStateWrapperType compositeDiscountLevelStateWrapperType, int itemID, Item.Category category, List<PurchasePolicyLevelStateWrapper> purchasePolicyLevelStateWrappers) {
        this.compositeDiscountLevelStateWrapperType = compositeDiscountLevelStateWrapperType;
        this.itemID = itemID;
        this.category = category;
        this.purchasePolicyLevelStateWrappers = purchasePolicyLevelStateWrappers;
    }

    public PurchasePolicyLevelStateWrapper() {
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public Item.Category getCategory() {
        return category;
    }

    public void setCategory(Item.Category category) {
        this.category = category;
    }

    public List<PurchasePolicyLevelStateWrapper> getPurchasePolicyLevelStateWrappers() {
        return purchasePolicyLevelStateWrappers;
    }

    public void setPurchasePolicyLevelStateWrappers(List<PurchasePolicyLevelStateWrapper> purchasePolicyLevelStateWrappers) {
        this.purchasePolicyLevelStateWrappers = purchasePolicyLevelStateWrappers;
    }

    public CompositeDiscountLevelStateWrapperType getCompositeDiscountLevelStateWrapperType() {
        return compositeDiscountLevelStateWrapperType;
    }

    public void setCompositeDiscountLevelStateWrapperType(CompositeDiscountLevelStateWrapperType compositeDiscountLevelStateWrapperType) {
        this.compositeDiscountLevelStateWrapperType = compositeDiscountLevelStateWrapperType;
    }
}
