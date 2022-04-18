package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class History {
    private List<Shop> closedShops;
    private String overallHistory;
    private static History instance;
//    private HashMap<String,String> purchase;

    private History(){
        this.closedShops = new ArrayList<>();
        this.overallHistory = "";
    }

    public synchronized static History getInstance(){
        if (instance == null){
            instance =  new History();
        }
        return instance;
    }

    public void addPurchaseHistory(String purchaseReview){


    }
}
