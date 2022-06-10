package com.example.server.serviceLayer.FacadeObjects.PolicyFacade.Wrappers;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.*;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.serviceLayer.FacadeObjects.FacadeObject;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerTemplateAvailabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class PurchasePolicyLevelStateWrapper implements FacadeObject<PurchasePolicyLevelState> {


    public enum PurchasePolicyLevelStateWrapperType {
        AndCompositePurchasePolicyLevelStateFacade,
        XorCompositePurchasePolicyLevelStateFacade,
        OrCompositePurchasePolicyLevelStateFacade,
        CategoryPurchasePolicyLevelStateFacade,
        ItemPurchasePolicyLevelStateFacade,
        ShopPurchasePolicyLevelStateFacade;
    }
    private PurchasePolicyLevelStateWrapperType purchasePolicyLevelStateWrapperType;
    private int itemID;
    private Item.Category category;
    private List<PurchasePolicyLevelStateWrapper> purchasePolicyLevelStateWrappers;
    public PurchasePolicyLevelStateWrapper(PurchasePolicyLevelStateWrapperType purchasePolicyLevelStateWrapperType, int itemID, Item.Category category, List<PurchasePolicyLevelStateWrapper> purchasePolicyLevelStateWrappers) {
        this.purchasePolicyLevelStateWrapperType = purchasePolicyLevelStateWrapperType;
        this.itemID = itemID;
        this.category = category;
        this.purchasePolicyLevelStateWrappers = purchasePolicyLevelStateWrappers;
    }

    public PurchasePolicyLevelStateWrapper() {
    }

    @Override
    public PurchasePolicyLevelState toBusinessObject() throws MarketException {
        switch (purchasePolicyLevelStateWrapperType){
            case ItemPurchasePolicyLevelStateFacade -> {
                return new ItemPurchasePolicyLevelState ( itemID );
            }
            case CategoryPurchasePolicyLevelStateFacade -> {
                return new CategoryPurchasePolicyLevelState ( category );
            }
            case ShopPurchasePolicyLevelStateFacade -> {
                return new ShopPurchasePolicyLevelState ();
            }
            case OrCompositePurchasePolicyLevelStateFacade -> {
                List<PurchasePolicyLevelState> purchasePolicyLevelStates = new ArrayList<> (  );
                for(PurchasePolicyLevelStateWrapper purchasePolicyLevelStateWrapper : purchasePolicyLevelStateWrappers){
                    purchasePolicyLevelStates.add ( purchasePolicyLevelStateWrapper.toBusinessObject () );
                }
                return new OrCompositePurchasePolicyLevelState ( purchasePolicyLevelStates );
            }
            case XorCompositePurchasePolicyLevelStateFacade -> {
                List<PurchasePolicyLevelState> purchasePolicyLevelStates = new ArrayList<> (  );
                for(PurchasePolicyLevelStateWrapper purchasePolicyLevelStateWrapper : purchasePolicyLevelStateWrappers){
                    purchasePolicyLevelStates.add ( purchasePolicyLevelStateWrapper.toBusinessObject () );
                }
                return new XorCompositePurchasePolicyLevelState ( purchasePolicyLevelStates );
            }
            case AndCompositePurchasePolicyLevelStateFacade -> {
                List<PurchasePolicyLevelState> purchasePolicyLevelStates = new ArrayList<> (  );
                for(PurchasePolicyLevelStateWrapper purchasePolicyLevelStateWrapper : purchasePolicyLevelStateWrappers){
                    purchasePolicyLevelStates.add ( purchasePolicyLevelStateWrapper.toBusinessObject () );
                }
                return new AndCompositePurchasePolicyLevelState ( purchasePolicyLevelStates );
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

    public List<PurchasePolicyLevelStateWrapper> getPurchasePolicyLevelStateWrappers() {
        return purchasePolicyLevelStateWrappers;
    }

    public void setPurchasePolicyLevelStateWrappers(List<PurchasePolicyLevelStateWrapper> purchasePolicyLevelStateWrappers) {
        this.purchasePolicyLevelStateWrappers = purchasePolicyLevelStateWrappers;
    }

    public PurchasePolicyLevelStateWrapperType getCompositeDiscountLevelStateWrapperType() {
        return purchasePolicyLevelStateWrapperType;
    }

    public void setCompositeDiscountLevelStateWrapperType(PurchasePolicyLevelStateWrapperType purchasePolicyLevelStateWrapperType) {
        this.purchasePolicyLevelStateWrapperType = purchasePolicyLevelStateWrapperType;
    }
}
