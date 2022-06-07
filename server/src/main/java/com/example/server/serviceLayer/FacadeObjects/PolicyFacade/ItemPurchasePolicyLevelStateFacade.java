package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.ItemPurchasePolicyLevelState;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.PurchasePolicyLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

public class ItemPurchasePolicyLevelStateFacade implements PurchasePolicyLevelStateFacade{
    int itemID;

    public ItemPurchasePolicyLevelStateFacade(int itemID) {
        this.itemID = itemID;
    }

    public ItemPurchasePolicyLevelStateFacade(){}

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public PurchasePolicyLevelState toBusinessObject() throws MarketException {
        return new ItemPurchasePolicyLevelState ( itemID );
    }
}
