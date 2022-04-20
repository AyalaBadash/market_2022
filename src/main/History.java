package main;

import main.businessLayer.MarketException;
import main.businessLayer.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class History {
    private List<Shop> closedShops;
    private StringBuilder overallHistory;
    private static History instance;

    private History() {
        this.closedShops = new ArrayList<>();
        this.overallHistory = new StringBuilder();
    }

    public synchronized static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }

    public synchronized void closeShop(Shop closedShop) throws MarketException {
        for (Shop shop : closedShops) {
            if (shop.getShopName().equals(closedShop.getShopName())) {
                throw new MarketException("Shop: " + closedShop.getShopName() + " is already closed and cannot be closed again!");
            }
        }
        this.closedShops.add(closedShop);
    }

    public void addPurchaseHistory(String purchaseReview, Shop shop) {
        if (purchaseReview != null || purchaseReview.equals("")) {
            overallHistory.append("Purchase in: "+shop.getShopName());
            overallHistory.append("\n").append(purchaseReview);
        }

    }

    public List<Shop> getClosedShops() {
        return closedShops;
    }

    private void setClosedShops(List<Shop> closedShops) {
        this.closedShops = closedShops;
    }

    public StringBuilder getOverallHistory() {
        return overallHistory;
    }

    private void setOverallHistory(StringBuilder overallHistory) {
        this.overallHistory = overallHistory;
    }
}
