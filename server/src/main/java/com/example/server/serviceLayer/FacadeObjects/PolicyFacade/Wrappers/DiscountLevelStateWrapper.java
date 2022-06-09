package com.example.server.serviceLayer.FacadeObjects.PolicyFacade.Wrappers;

import com.example.server.businessLayer.Market.Item;

import java.util.List;

public class DiscountLevelStateWrapper {
    enum CompositeDiscountLevelStateWrapperType {
        MaxXorCompositeDiscountLevelStateFacade,
        AndCompositeDiscountLevelStateFacade,
        ItemLevelStateFacade,
        ShopLevelStateFacade,
        CategoryLevelStateFacade
    }
    private int itemID;
    private Item.Category category;
    private List<DiscountLevelStateWrapper> discountLevelStateWrappers;

    public DiscountLevelStateWrapper(int itemID, Item.Category category, List<DiscountLevelStateWrapper> discountLevelStateWrappers) {
        this.itemID = itemID;
        this.category = category;
        this.discountLevelStateWrappers = discountLevelStateWrappers;
    }

    public DiscountLevelStateWrapper() {
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

    public List<DiscountLevelStateWrapper> getDiscountLevelStateWrappers() {
        return discountLevelStateWrappers;
    }

    public void setDiscountLevelStateWrappers(List<DiscountLevelStateWrapper> discountLevelStateWrappers) {
        this.discountLevelStateWrappers = discountLevelStateWrappers;
    }
}
