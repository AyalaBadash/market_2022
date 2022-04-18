package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    private String ShopName;
    private Map<String,Item> itemMap; //<ItemID,main.Item>

    private List<String> employees;
    //TODO - check which way is preferred
    private HashMap<String , Member> shopOwners;
    private HashMap<String , Member> shopManagers;

    private PurchasePolicy purchasePolicy;
    private DiscountPolicy discountPolicy;
    private ProductsSupplyService supplyService;
    private PaymentService paymentService;

    public Shop(String name){
        this.ShopName = name;
        itemMap = new HashMap<>();
        employees = new ArrayList<>();
    }

    //use case - receive info of a shop
    public String receiveInfo(){throw new UnsupportedOperationException();} // TODO - check if returned value is indeed String

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
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
        this.purchasePolicy = purchasePolicy;
    }
}