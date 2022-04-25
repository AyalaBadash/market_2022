package main.businessLayer;

import main.resources.ErrorLog;
import main.resources.EventLog;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClosedShopsHistory {
    private List<Shop> closedShops;
    private StringBuilder overallHistory;
    private static ClosedShopsHistory instance;

    private ClosedShopsHistory(){
        this.closedShops = new CopyOnWriteArrayList<>();
        this.overallHistory = new StringBuilder();
    }

    public synchronized static ClosedShopsHistory getInstance(){
        if (instance == null){
            instance =  new ClosedShopsHistory ();
        }
        return instance;
    }
    // TODO need to implement here
    public void closeShop(Shop closedShop) throws MarketException {
        if (closedShop == null){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Tried to close a shop but shop is null");
            throw new MarketException("tried to close a null shop!");
        }
        EventLog.getInstance().Log("Shop " + closedShop.getShopName() + " closed successfully");
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
