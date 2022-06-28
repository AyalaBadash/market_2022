package com.example.server.businessLayer.Market;

import com.example.server.businessLayer.Market.ResourcesObjects.DebugLog;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.dataLayer.repositories.ClosedShopsHistoryRep;
import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Entity
public class ClosedShopsHistory {
    @Id
    @GeneratedValue
    private long id;
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "shop_to_closed_shop_history",
            joinColumns = {@JoinColumn(name = "ClosedShopsHistoryId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "shop_name", referencedColumnName = "shop_name")})
    @MapKeyColumn(name = "shop")
    private Map<String, Shop> closedShops;
    private StringBuilder overallHistory;
    private static ClosedShopsHistory instance;
    private static ClosedShopsHistoryRep closedShopsHistoryRep;

    private ClosedShopsHistory(int i){
        this.closedShops = new ConcurrentHashMap<> ();
        this.overallHistory = new StringBuilder();
    }

    public synchronized static ClosedShopsHistory getInstance(){
        if (instance == null){
            instance =  new ClosedShopsHistory(1);
        }
        return instance;
    }

    public ClosedShopsHistory(){}

    public void closeShop(Shop closedShop) throws MarketException {
        if (closedShop == null){
            DebugLog.getInstance ().Log ( "tried to close a null shop!" );
            throw new MarketException("tried to close a null shop!");
        }
        if(closedShop.isClosed ())
        {
            DebugLog.getInstance ().Log ( String.format ( "shop %s is already closed", closedShop.getShopName () ) );
            throw new MarketException ( String.format ( "shop %s is already closed", closedShop.getShopName () ));
        }
        closedShops.put (closedShop.getShopName (), closedShop);
        if (!MarketConfig.IS_TEST_MODE) {
            closedShopsHistoryRep.save(this);
        }
    }

    public void addPurchaseHistory(String purchaseReview, Shop shop) throws MarketException {
        if(shop == null)
            throw new MarketException("cannot add a purchase history of non defined shop (null)");
        if (purchaseReview != null && purchaseReview != ""){
            overallHistory.append("\n").append(purchaseReview);
        }
        if (!MarketConfig.IS_TEST_MODE) {
            closedShopsHistoryRep.save(this);
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

    public void reset() {
        closedShops = new HashMap<>();
        overallHistory = new StringBuilder();
    }

    public void reopenShop(String shopName) {
        this.closedShops.remove(shopName);
    }

    public static void setClosedShopsHistoryRep(ClosedShopsHistoryRep closedShopsHistoryRep) {
        ClosedShopsHistory.closedShopsHistoryRep = closedShopsHistoryRep;
    }
}
