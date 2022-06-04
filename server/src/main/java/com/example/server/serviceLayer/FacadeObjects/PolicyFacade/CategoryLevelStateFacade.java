package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.CategoryLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

public class CategoryLevelStateFacade extends DiscountLevelStateFacade {
    Item.Category category;

    public CategoryLevelStateFacade(Item.Category category) {
        this.category = category;
        this.type = "CategoryLevelStateFacade";
    }

    public Item.Category getCategory() {
        return category;
    }

    public void setCategory(Item.Category category) {
        this.category = category;
    }

    @Override
    public DiscountLevelState toBusinessObject() throws MarketException {
        return new CategoryLevelState ( category );
    }
}
