package main.businessLayer;

import java.util.HashMap;
import java.util.Map;

public class PurchasePolicy {
    private Map<String, Item> items;

    public PurchasePolicy(){
        items = new HashMap<>();
    }
    public boolean itemExistInPolicy(String itemId) {
        return items.containsKey(itemId);
    }

    //TODO: complete
    public String getInfo() {
        return "";
    }
}
