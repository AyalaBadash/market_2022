package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.ItemLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.DiscountLevelStateFacade;

public class ItemLevelStateFacade extends DiscountLevelStateFacade {
    int itemID;

    public ItemLevelStateFacade(int itemID) {
        this.itemID = itemID;
    }

    public ItemLevelStateFacade(){}

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public DiscountLevelState toBusinessObject() throws MarketException {
        return new ItemLevelState ( itemID );
    }
}
