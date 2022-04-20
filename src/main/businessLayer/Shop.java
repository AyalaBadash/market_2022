package main.businessLayer;

import main.businessLayer.discountPolicy.DiscountPolicy;
import main.businessLayer.services.PaymentService;
import main.businessLayer.services.ProductsSupplyService;
import main.businessLayer.users.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    private String shopName;
    private Map<String,Item> itemMap; //<ItemID,main.businessLayer.Item>

    private List<String> employees;
    //TODO - check which way is preferred
    private HashMap<String , Member> shopOwners;
    private HashMap<String , Member> shopManagers;

    private boolean closed;

    private List<PurchasePolicy> purchasePolicyList;
    private List<DiscountPolicy> discountPolicyList;
    private ProductsSupplyService supplyService;
    private PaymentService paymentService;

    public Shop(String name){
        this.shopName = name;
        itemMap = new HashMap<>();
        employees = new ArrayList<>();
        purchasePolicyList = new ArrayList<>();
        discountPolicyList = new ArrayList<>();
        closed = false;
    }

    public boolean isClosed() {
        return closed;
    }

    //use case - receive info of a shop
    public String receiveInfo(String userId) throws Exception {
        if (isClosed() && (!shopManagers.containsKey(userId) & !shopOwners.containsKey(userId)))
            throw new Exception("only owners and managers of the shop can view it's information");
        return "shop: "+shopName;
    } // TODO - check if returned value is indeed String and complete

    //use case - Stock management
    public void editItem(Item item){
        itemMap.put(item.getName(),item);
    };
    public void deleteItem(Item item){
        itemMap.remove(item.getName());
    }
    public void addItem(Item item) throws Exception{
        if (!itemMap.containsKey(item.getName()))
        itemMap.put(item.getName(),item);
        else throw new Exception();
    }
    public Map<String , Item> getItems(){
        return itemMap;
    }

    public HashMap<String, Member> getShopOwners() {
        return shopOwners;
    }

    public HashMap<String, Member> getShopManagers() {
        return shopManagers;
    }

    //use case - Change shop's policy
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }
//
//    public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
//        this.purchasePolicy = purchasePolicy;
//    }

    public String receiveInfoAboutItem(String itemId, String userId) throws Exception {
        if (isClosed() && (!shopManagers.containsKey(userId) & !shopOwners.containsKey(userId)))
            throw new Exception("only owners and managers of the shop can view it's information");
        String toReturn;
        if (!itemMap.containsKey(itemId))
            throw new Exception("no such item in shop");

        else{
            toReturn = itemMap.get(itemId).toString();
            for (PurchasePolicy purPolicy : purchasePolicyList){
                if (purPolicy.itemExistInPolicy(itemId)){
                    toReturn+=purPolicy.getInfo();
                }
            }
            for (DiscountPolicy disPolicy : discountPolicyList){
                if (disPolicy.itemExistInPolicy(itemId)){
                    toReturn+=disPolicy.getInfo();
                }
            }
        }
        return toReturn;
    }
}
