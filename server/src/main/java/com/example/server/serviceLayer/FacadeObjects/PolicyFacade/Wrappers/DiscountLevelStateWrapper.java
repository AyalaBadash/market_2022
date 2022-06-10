package com.example.server.serviceLayer.FacadeObjects.PolicyFacade.Wrappers;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.*;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.FacadeObject;

import java.util.ArrayList;
import java.util.List;

public class DiscountLevelStateWrapper implements FacadeObject<DiscountLevelState> {


    enum DiscountLevelStateWrapperType {
        MaxXorCompositeDiscountLevelStateFacade,
        AndCompositeDiscountLevelStateFacade,
        ItemLevelStateFacade,
        ShopLevelStateFacade,
        CategoryLevelStateFacade;
    }
    private DiscountLevelStateWrapperType discountLevelStateWrapperType;
    private int itemID;
    private Item.Category category;
    private List<DiscountLevelStateWrapper> discountLevelStateWrappers;
    public DiscountLevelStateWrapper(DiscountLevelStateWrapperType discountLevelStateWrapperType, int itemID, Item.Category category, List<DiscountLevelStateWrapper> discountLevelStateWrappers) {
        this.discountLevelStateWrapperType = discountLevelStateWrapperType;
        this.itemID = itemID;
        this.category = category;
        this.discountLevelStateWrappers = discountLevelStateWrappers;
    }

    public DiscountLevelStateWrapper() {
    }

    @Override
    public DiscountLevelState toBusinessObject() throws MarketException {
        switch (discountLevelStateWrapperType){
            case ItemLevelStateFacade -> {
                return new ItemLevelState ( itemID );
            }
            case CategoryLevelStateFacade -> {
                return new CategoryLevelState ( category );
            }
            case ShopLevelStateFacade -> {
                return new ShopLevelState ();
            }
            case AndCompositeDiscountLevelStateFacade -> {
                List<DiscountLevelState> discountLevelStates = new ArrayList<> (  );
                for(DiscountLevelStateWrapper discountLevelStateWrapper: discountLevelStateWrappers){
                    discountLevelStates.add ( discountLevelStateWrapper.toBusinessObject () );
                }
                return new AndCompositeDiscountLevelState ( discountLevelStates );
            }
            case MaxXorCompositeDiscountLevelStateFacade -> {
                List<DiscountLevelState> discountLevelStates = new ArrayList<> (  );
                for(DiscountLevelStateWrapper discountLevelStateWrapper: discountLevelStateWrappers){
                    discountLevelStates.add ( discountLevelStateWrapper.toBusinessObject () );
                }
                return new MaxXorCompositeDiscountLevelState ( discountLevelStates );
            }
            default -> {
                return null;
            }
        }
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

    public DiscountLevelStateWrapperType getCompositeDiscountLevelStateWrapperType() {
        return discountLevelStateWrapperType;
    }

    public void setCompositeDiscountLevelStateWrapperType(DiscountLevelStateWrapperType discountLevelStateWrapperType) {
        this.discountLevelStateWrapperType = discountLevelStateWrapperType;
    }
}
