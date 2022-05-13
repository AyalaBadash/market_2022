package com.example.server.businessLayer;

import com.example.server.ResourcesObjects.ErrorLog;

import java.text.DecimalFormat;
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

    //TODO - needs to stay without checking the discount
    public double getPrice() {
        return calculatePrice();
    }

    //TODO - add calculationOfDiscount
    public double getPriceWithDiscount() {
        throw new UnsupportedOperationException();
    }

    private double calculatePrice() {
        double price = 0;
        for (Map.Entry<Item,Double> currItem:items.entrySet())
        {
            price = price + currItem.getValue()*currItem.getKey().getPrice();
        }
        DecimalFormat format = new DecimalFormat("#.###");
        price = Double.parseDouble(format.format(price));
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

    public void addItem(Item item, double amount) throws MarketException {
        if (amount<0)
            throw new MarketException("Cant add negative amount of item to basket.");
        items.put(item,amount);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }


    public void updateQuantity(double amount, Item item) throws MarketException {
        if(!items.containsKey(item)){
            throw new MarketException("Item is not in cart. cannot update amount");
        }
        if (amount<0)
        {
            ErrorLog.getInstance().Log("Visitor tried to update negative amount for item.");
            throw new MarketException("Cant put negative amount for item");
        }
        items.remove(item);
        items.put(item, amount);
    }
}
