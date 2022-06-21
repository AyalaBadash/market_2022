package com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Map;
@Entity
@DiscriminatorValue(value = "ItemLevelState")
public class ItemLevelState extends DiscountLevelState{
    private Integer itemID;

    public ItemLevelState(Integer itemID) {
        this.itemID = itemID;
    }

    public ItemLevelState(){}
    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, double percentageOfDiscount) throws MarketException {
        double price = shoppingBasket.getPrice();
        Item item=Market.getInstance().getItemByID(itemID);
        Map<java.lang.Integer,Double> items= shoppingBasket.getItems();
        double itemDiscount = 0;
        if (items.containsKey(item.getID()))
        {
            Double amount = items.get(item.getID());
            itemDiscount = amount * (percentageOfDiscount/100);

        }
        return price - itemDiscount;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof ItemLevelState){
            ItemLevelState toCompare = (ItemLevelState) object;
            return this.itemID == toCompare.itemID;
        }
        return false;
    }

    @Override
    public boolean isItem(){
        return true;
    }

    @Override
    public DiscountLevelStateFacade visitToFacade(CategoryLevelStateFacade levelStateFacade) {
        return null;
    }

    @Override
    public DiscountLevelStateFacade visitToFacade(ItemLevelStateFacade levelStateFacade) {
        return levelStateFacade.toFacade ( this );
    }

    @Override
    public DiscountLevelStateFacade visitToFacade(ShopLevelStateFacade levelStateFacade) {
        return null;
    }

    @Override
    public DiscountLevelStateFacade visitToFacade(AndCompositeDiscountLevelStateFacade levelStateFacade) {
        return null;
    }

    @Override
    public DiscountLevelStateFacade visitToFacade(MaxXorCompositeDiscountLevelStateFacade levelStateFacade) {
        return null;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }
}
