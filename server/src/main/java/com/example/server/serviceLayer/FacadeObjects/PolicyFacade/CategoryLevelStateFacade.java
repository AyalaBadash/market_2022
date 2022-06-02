package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.Market.Item;

public class CategoryLevelStateFacade {
    Item.Category category;

    public CategoryLevelStateFacade(Item.Category category) {
        this.category = category;
    }

    public Item.Category getCategory() {
        return category;
    }

    public void setCategory(Item.Category category) {
        this.category = category;
    }
}
