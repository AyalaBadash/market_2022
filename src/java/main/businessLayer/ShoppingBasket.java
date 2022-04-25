package main.businessLayer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingBasket implements IHistory {
    private Map<Item, Double> items;//<Item,quantity>
    private double price;

    public ShoppingBasket() {
        items = new ConcurrentHashMap<>();
        price = 0;
    }


    @Override
    public StringBuilder getReview() {
        StringBuilder review = new StringBuilder();
        for (Map.Entry<Item, Double> itemToAmount : items.entrySet()) {
            Item item = itemToAmount.getKey();
            Double amount = itemToAmount.getValue();
            if (amount > 0) {
                review.append(String.format("%s, amount: %f\n", item.getReview(), amount));
            }
        }
        review.append ( String.format ( "total price: %f", price ));
        return review;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
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

    public Map<Item, Double> getItems() {
        return items;
    }

    public void addItem(Item item, double amount) {
        items.put(item,amount);
    }

    public void removeItem(Item item, double amount) throws MarketException {
        if (!items.containsKey(item))
            throw new MarketException("No such item on basket");
        if (items.get(item)>amount)
            items.put(item,items.get(item)-amount);
        else items.remove(item);
    }


    public ShoppingBasket calculate() {
        return null;
        // TODO check if needed.
    }

    public void updateQuantity(int amount, Item itemFacade) throws MarketException {
        if(!items.containsKey(itemFacade)){
            throw new MarketException("Item is not in cart. cannot update amount");
        }
        items.remove(itemFacade);
        items.put(itemFacade, Double.valueOf(amount));
    }
}
