package com.example.server.serviceLayer.Requests;

import com.example.server.serviceLayer.FacadeObjects.ItemFacade;

public class AddItemToShoppingCartRequest {
    private ItemFacade itemToInsert;
    private double amount;
    private String shopName;
    private String visitorName;

    public AddItemToShoppingCartRequest() {
    }

    public AddItemToShoppingCartRequest(ItemFacade itemToInsert, double amount, String shopName, String visitorName) {
        this.itemToInsert = itemToInsert;
        this.amount = amount;
        this.shopName = shopName;
        this.visitorName = visitorName;
    }

    public ItemFacade getItemToInsert() {
        return itemToInsert;
    }

    public void setItemToInsert(ItemFacade itemToInsert) {
        this.itemToInsert = itemToInsert;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }
}
