package com.example.server.serviceLayer.FacadeObjects;

public class ItemLevelStateFacade {
    int itemID;

    public ItemLevelStateFacade(int itemID) {
        this.itemID = itemID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
