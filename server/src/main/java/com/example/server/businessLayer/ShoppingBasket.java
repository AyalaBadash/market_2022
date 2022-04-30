package com.example.server.businessLayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingBasket implements IHistory {
    private Map<Item, Double> items;//<Item,quantity>
    private double price;

    public ShoppingBasket() {
        items = new ConcurrentHashMap<>();
        price = 0;
    }
    public ShoppingBasket(Map<Item,Double> items , double price){
        this.items = items;
        this.price = price;
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
        return calculatePrice();
    }
    //TODO - price should be up to 3 digits . Example : 3.14159265 -> 3.141

    private double calculatePrice() {
        double price = 0;
        for (Map.Entry<Item,Double> currItem:items.entrySet())
        {
            price = price + currItem.getValue()*currItem.getKey().getPrice();
        }
        setPrice(price);
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

    public void removeItem(Item item) throws MarketException {//TODO delete throws exception
        if (!items.containsKey(item))
            return;
        else items.remove(item);
    }


    public void updateQuantity(double amount, Item itemFacade) throws MarketException {
        if(!items.containsKey(itemFacade)){
            throw new MarketException("Item is not in cart. cannot update amount");
        }
        items.remove(itemFacade);
        items.put(itemFacade, Double.valueOf(amount));
    }
}
