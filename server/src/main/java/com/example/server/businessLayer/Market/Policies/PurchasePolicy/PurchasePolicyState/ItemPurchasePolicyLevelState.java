package com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.dataLayer.repositories.ItemPolicyRep;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
@Entity
@DiscriminatorValue(value = "Item_ls")
public class ItemPurchasePolicyLevelState extends PurchasePolicyLevelState {
    Integer itemId;

    private static ItemPolicyRep itemPolicyRep;

    public ItemPurchasePolicyLevelState(){}
    public ItemPurchasePolicyLevelState(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean isPolicyHeld(ShoppingBasket shoppingBasket, double amount, boolean greater) throws MarketException {
        double curAmount = getAmount ( shoppingBasket ).get ( 0 );
        return curAmount == 0 || ((greater && curAmount > amount) || (!greater && curAmount <= amount));
    }

    @Override
    public List<Double> getAmount(ShoppingBasket shoppingBasket) {
        List<Double> amount = new ArrayList<> (  );
        Double curAmount;
        curAmount = shoppingBasket.getItems ().get ( itemId );
        if(curAmount == null)
            curAmount = ( Double.valueOf ( 0 ) );
        amount.add ( curAmount );
        return amount;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof ItemPurchasePolicyLevelState){
            ItemPurchasePolicyLevelState itemPurchasePolicyLevelState = (ItemPurchasePolicyLevelState) object;
            if(itemPurchasePolicyLevelState.itemId == itemId)
                return true;
        }
        return false;
    }

    @Override
    public boolean isItemLevel() {
        return true;
    }

    @Override
    public boolean isCategoryLevel() {
        return false;
    }

    @Override
    public boolean isShopLevel() {
        return false;
    }

    @Override
    public boolean isOrLevel() {
        return false;
    }

    @Override
    public boolean isXorLevel() {
        return false;
    }

    @Override
    public boolean isAndLevel() {
        return false;
    }

    @Override
    public PurchasePolicyLevelStateFacade visitToFacade(ItemPurchasePolicyLevelStateFacade levelStateFacade) {
        return levelStateFacade.toFacade ( this );
    }

    @Override
    public PurchasePolicyLevelStateFacade visitToFacade(CategoryPurchasePolicyLevelStateFacade levelStateFacade) {
        return null;
    }

    @Override
    public PurchasePolicyLevelStateFacade visitToFacade(ShopPurchasePolicyLevelStateFacade levelStateFacade) {
        return null;
    }

    @Override
    public PurchasePolicyLevelStateFacade visitToFacade(AndCompositePurchasePolicyLevelStateFacade levelStateFacade) {
        return null;
    }

    @Override
    public PurchasePolicyLevelStateFacade visitToFacade(XorCompositePurchasePolicyLevelStateFacade levelStateFacade) {
        return null;
    }

    @Override
    public PurchasePolicyLevelStateFacade visitToFacade(OrCompositePurchasePolicyLevelStateFacade levelStateFacade) {
        return null;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public static ItemPolicyRep getItemPolicyRep() {
        return itemPolicyRep;
    }

    public static void setItemPolicyRep(ItemPolicyRep itemPolicyRep) {
        ItemPurchasePolicyLevelState.itemPolicyRep = itemPolicyRep;
    }
}
