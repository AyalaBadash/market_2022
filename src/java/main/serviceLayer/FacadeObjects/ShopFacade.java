package main.serviceLayer.FacadeObjects;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Item;
import main.businessLayer.Shop;

import java.util.Map;

public class ShopFacade implements FacadeObject<Shop> {

    private String shopName;
    private Map<Integer, Item> itemMap;             //<ItemID,main.businessLayer.Item>
    private Map<String, Appointment> employees;     //<name, appointment>
    private Map<Item, Integer> itemsCurrentAmount;
    private boolean closed;

    public ShopFacade(String shopName, Map<Integer, Item> itemMap, Map<String,
            Appointment> employees, Map<Item, Integer> itemsCurrentAmount, boolean closed) {
        this.shopName = shopName;
        this.itemMap = itemMap;
        this.employees = employees;
        this.itemsCurrentAmount = itemsCurrentAmount;
        this.closed = closed;
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

    public Map<Item, Integer> getItemsCurrentAmount() {
        return itemsCurrentAmount;
    }

    public void setItemsCurrentAmount(Map<Item, Integer> itemsCurrentAmount) {
        this.itemsCurrentAmount = itemsCurrentAmount;
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
