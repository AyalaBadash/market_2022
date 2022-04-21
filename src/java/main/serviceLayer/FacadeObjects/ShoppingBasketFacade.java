package main.serviceLayer.FacadeObjects;

import java.util.Map;

public class ShoppingBasketFacade {
    Map<ItemFacade,Double> items;//<Item,quantity>

    public ShoppingBasketFacade(Map<ItemFacade, Double> items) {
        this.items = items;
    }

    public Map<ItemFacade, Double> getItems() {
        return items;
    }

    public void setItems(Map<ItemFacade, Double> items) {
        this.items = items;
    }
}
