package com.example.server.serviceLayer.Requests;

import com.example.server.serviceLayer.FacadeObjects.ItemFacade;

public class UpdateShopItemAmountRequest {

    private String shopOwnerName;
    private ItemFacade item;
    private int amount;
    private String shopName;

    public UpdateShopItemAmountRequest() {
    }

    public UpdateShopItemAmountRequest(String shopOwnerName, ItemFacade item, int amount, String shopName) {
        this.shopOwnerName = shopOwnerName;
        this.item = item;
        this.amount = amount;
        this.shopName = shopName;
    }

    public String getShopOwnerName() {
        return shopOwnerName;
    }

    public void setShopOwnerName(String shopOwnerName) {
        this.shopOwnerName = shopOwnerName;
    }

    public ItemFacade getItem() {
        return item;
    }

    public void setItem(ItemFacade item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
