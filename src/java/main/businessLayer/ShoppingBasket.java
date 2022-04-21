package main.businessLayer;

import java.util.HashMap;
import java.util.Map;

public class ShoppingBasket implements IHistory {
    Map<Item, Double> items;//<Item,quantity>

    public ShoppingBasket() {
        items = new HashMap<>();
    }


    @Override
    public StringBuilder getReview() {
        StringBuilder review = new StringBuilder();
        for (Map.Entry<Item, Double> itemToAmount : items.entrySet()) {
            Item item = itemToAmount.getKey();
            Double amount = itemToAmount.getValue();
            if (amount > 0) {
                review.append(String.format("%s X %f\n", item.getReview(), amount));
            }
        }
        return review;
    }

    public boolean isEmpty() {
        if (this.items == null || this.items.isEmpty()) {
            return true;
        } else {
            for (Map.Entry<Item, Double> itemDoubleEntry : items.entrySet()) {
                if (itemDoubleEntry.getValue() > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addItem(Item item, int amount) {
        // TODO implement
    }

    public void removeItem(Item item, int amount) {

    }


}
