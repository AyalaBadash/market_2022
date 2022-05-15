package com.example.server.serviceLayer.FacadeObjects;


import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Appointment.ShopManagerAppointment;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Shop;

import java.util.HashMap;
import java.util.Map;

public class ShopFacade implements FacadeObject<Shop> {

    private String shopName;
    private Map<Integer, ItemFacade> itemMap;             //<ItemID,main.businessLayer.Item>
    private Map<String, AppointmentFacade> employees;     //<name, appointment>
    private Map<Integer, Double> itemsCurrentAmount;
    private boolean closed;

    public ShopFacade(String shopName, Map<Integer, Item> itemMap, Map<String,
            Appointment> employees, Map<Integer, Double> itemsCurrentAmount, boolean closed) {
        this.shopName = shopName;
        updateItemMap ( itemMap );
        updateEmployees ( employees );
        updateItemsAmount ( itemsCurrentAmount );
        this.closed = closed;
    }

    public ShopFacade(Shop fromShop) {
        this.shopName = fromShop.getShopName();
        updateItemMap (fromShop.getItemMap());
        updateEmployees (fromShop.getEmployees());
        updateItemsAmount ( fromShop.getItemsCurrentAmountMap () );
        this.closed = fromShop.isClosed();
    }

    private void updateItemMap(Map<Integer, Item> items){
        this.itemMap = new HashMap<> ();
        for( Map.Entry<Integer, Item> entry : items.entrySet ()) {
            this.itemMap.put (entry.getKey (), new ItemFacade (entry.getValue ()) );
        }
    }

    private void updateEmployees(Map<String, Appointment> appointmentMap){
        this.employees = new HashMap<> ();
        for( Map.Entry<String, Appointment> entry : appointmentMap.entrySet ()) {
            this.employees.put (entry.getKey (), new ShopOwnerAppointmentFacade (  ).toFacade (entry.getValue ()) );
        }
    }

    private void updateItemsAmount(Map<Integer, Double> itemsAmount){
        itemsCurrentAmount = new HashMap<>();
        for (Map.Entry<Integer, Double> entry: itemsAmount.entrySet()){
            itemsCurrentAmount.put(entry.getKey(), entry.getValue());
        }
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Map<Integer, ItemFacade> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Integer, ItemFacade> itemMap) {
        this.itemMap = itemMap;
    }

    public Map<String, AppointmentFacade> getEmployees() {
        return employees;
    }

    public void setEmployees(Map<String, AppointmentFacade> employees) {
        this.employees = employees;
    }

    public void setItemsCurrentAmount(Map<Integer, Double> itemsCurrentAmount) {
        this.itemsCurrentAmount = itemsCurrentAmount;
    }

    public Map<Integer, Double> getItemsCurrentAmount() {
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
