package com.example.server.businessLayer;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClosedShopHistory {
    private List<Shop> closedShops;
    private StringBuilder overallHistory;
    private static ClosedShopHistory instance;

    private ClosedShopHistory(){
        this.closedShops = new CopyOnWriteArrayList<>();
        this.overallHistory = new StringBuilder();
    }

    public synchronized static ClosedShopHistory getInstance(){
        if (instance == null){
            instance =  new ClosedShopHistory ();
        }
        return instance;
    }
    // TODO need to implement here
    public void closeShop(Shop closedShop) throws MarketException {
        if (closedShop == null){
            throw new MarketException("tried to close a null shop!");
        }
        closedShops.add(closedShop);
    }

    public void addPurchaseHistory(String purchaseReview, Shop shop){
        if (purchaseReview != null){
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