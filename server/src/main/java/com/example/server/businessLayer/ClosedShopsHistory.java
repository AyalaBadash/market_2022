package com.example.server.businessLayer;


import com.example.server.ResourcesObjects.ErrorLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClosedShopsHistory {
    private Map<String, Shop> closedShops;
    private StringBuilder overallHistory;
    private static ClosedShopsHistory instance;

    private ClosedShopsHistory(){
        this.closedShops = new ConcurrentHashMap<> ();
        this.overallHistory = new StringBuilder();
    }

    public synchronized static ClosedShopsHistory getInstance(){
        if (instance == null){
            instance =  new ClosedShopsHistory();
        }
        return instance;
    }

    public void closeShop(Shop closedShop) throws MarketException {
        if (closedShop == null){
            ErrorLog.getInstance ().Log ( "tried to close a null shop!" );
            throw new MarketException("tried to close a null shop!");
        }
        if(closedShop.isClosed ())
        {
            ErrorLog.getInstance ().Log ( String.format ( "shop %s is already closed", closedShop.getShopName () ) );
            throw new MarketException ( String.format ( "shop %s is already closed", closedShop.getShopName () ));
        }
        closedShops.put (closedShop.getShopName (), closedShop);
    }

    public void addPurchaseHistory(String purchaseReview, Shop shop){
        if (purchaseReview != null && purchaseReview != ""){
            overallHistory.append("\n").append(purchaseReview);
        }
    }

    public Map<String, Shop> getClosedShops() {
        return closedShops;
    }

    public void setClosedShops(Map<String, Shop> closedShops) {
        this.closedShops = closedShops;
    }

    public StringBuilder getOverallHistory() {
        return overallHistory;
    }

    public void setOverallHistory(StringBuilder overallHistory) {
        this.overallHistory = overallHistory;
    }

    public boolean isClosed(String shopName) {
        return closedShops.containsKey ( shopName );
    }
}
