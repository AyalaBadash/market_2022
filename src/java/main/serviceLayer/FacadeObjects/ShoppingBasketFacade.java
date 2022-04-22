package main.serviceLayer.FacadeObjects;

import main.businessLayer.Item;
import main.businessLayer.ShoppingBasket;

import java.util.HashMap;
import java.util.Map;

public class ShoppingBasketFacade implements FacadeObject<ShoppingBasket> {
    Map<ItemFacade,Double> items;//<Item,quantity>

    public ShoppingBasketFacade(Map<ItemFacade, Double> items) {
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
    public ShoppingBasket toBusinessObject() {
        return null;
    }
}
