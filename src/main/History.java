package main;

import main.businessLayer.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class History {
    private List<Shop> closedShops;
    private StringBuilder overallHistory;
    private static History instance;

    private History(){
        this.closedShops = new ArrayList<>();
        this.overallHistory = new StringBuilder();
    }

    public synchronized static History getInstance(){
        if (instance == null){
            instance =  new History();
        }
        return instance;
    }
    // TODO need to implement here
    public void closeShop(Shop closedShop){
        throw new UnsupportedOperationException("method has not been implemented");
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
