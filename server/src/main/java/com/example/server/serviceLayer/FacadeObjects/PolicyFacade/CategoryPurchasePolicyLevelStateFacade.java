package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.CategoryPurchasePolicyLevelState;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.PurchasePolicyLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

public class CategoryPurchasePolicyLevelStateFacade implements PurchasePolicyLevelStateFacade{
    Item.Category category;

    public CategoryPurchasePolicyLevelStateFacade(Item.Category category) {
        this.category = category;
    }

    public CategoryPurchasePolicyLevelStateFacade(){}

    public Item.Category getCategory() {
        return category;
    }

    public void setCategory(Item.Category category) {
        this.category = category;
    }

    @Override
    public PurchasePolicyLevelState toBusinessObject() throws MarketException {
        return new CategoryPurchasePolicyLevelState ( category );
    }
}
