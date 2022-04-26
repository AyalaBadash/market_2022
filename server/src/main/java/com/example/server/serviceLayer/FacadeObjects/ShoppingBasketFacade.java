package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingBasket;

import java.util.HashMap;
import java.util.Map;

public class ShoppingBasketFacade implements FacadeObject<ShoppingBasket> {
    Map<ItemFacade,Double> items;//<Item,quantity>
    double price;

    public ShoppingBasketFacade(Map<ItemFacade, Double> items,double price) {
        this.items = items;
    }

    public ShoppingBasketFacade(ShoppingBasket shoppingBasket) {
        this.items = new HashMap<>();
        for (Map.Entry<Item,Double> itemsAmount: shoppingBasket.getItems().entrySet()){
            items.put(new ItemFacade(itemsAmount.getKey()), itemsAmount.getValue());
        }
    }

    public Map<ItemFacade, Double> getItems() {
        return items;
    }

    public void setItems(Map<ItemFacade, Double> items) {
        this.items = items;
    }

    @Override
    public ShoppingBasket toBusinessObject() throws MarketException {
        Map<ItemFacade,Double> facadeItems = this.items;
        Map<Item,Double> BLItems = new HashMap<>();
        for (Map.Entry<ItemFacade,Double> entry: facadeItems.entrySet()){
            BLItems.put(entry.getKey().toBusinessObject(),entry.getValue());
        }
        ShoppingBasket basket = new ShoppingBasket(BLItems,this.price);
        return basket;

    }
}
