package com.example.server.serviceLayer.FacadeObjects;


import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Shop;

import java.util.HashMap;
import java.util.Map;

public class ShopFacade implements FacadeObject<Shop> {

    private String shopName;
    private Map<Integer, Item> itemMap;             //<ItemID,main.businessLayer.Item>
    private Map<String, Appointment> employees;     //<name, appointment>
    private Map<ItemFacade, Double> itemsCurrentAmount;
    private boolean closed;

    public ShopFacade(String shopName, Map<Integer, Item> itemMap, Map<String,
            Appointment> employees, Map<ItemFacade, Double> itemsCurrentAmount, boolean closed) {
        this.shopName = shopName;
        this.itemMap = itemMap;
        this.employees = employees;
        this.itemsCurrentAmount = itemsCurrentAmount;
        this.closed = closed;
    }

    public ShopFacade(Shop fromShop) {
        this.shopName = fromShop.getShopName();
        this.itemMap = fromShop.getItemMap();
        this.employees = fromShop.getEmployees();

        this.itemsCurrentAmount = new HashMap<>();
        for (Map.Entry<Item, Double> fromItems: fromShop.getItemsCurrentAmountMap().entrySet()){
            ItemFacade toItem = new ItemFacade(fromItems.getKey());
            itemsCurrentAmount.put(toItem, fromItems.getValue());
        }
        this.closed = fromShop.isClosed();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Map<Integer, Item> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Integer, Item> itemMap) {
        this.itemMap = itemMap;
    }

    public Map<String, Appointment> getEmployees() {
        return employees;
    }

    public void setEmployees(Map<String, Appointment> employees) {
        this.employees = employees;
    }

    public void setItemsCurrentAmount(Map<ItemFacade, Double> itemsCurrentAmount) {
        this.itemsCurrentAmount = itemsCurrentAmount;
    }

    public Map<ItemFacade, Double> getItemsCurrentAmount() {
        return itemsCurrentAmount;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Override
    public Shop toBusinessObject() {
        return null;
    }
}
