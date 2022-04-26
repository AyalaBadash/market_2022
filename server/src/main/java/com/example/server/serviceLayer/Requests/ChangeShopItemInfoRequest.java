package com.example.server.serviceLayer.Requests;

import com.example.server.serviceLayer.FacadeObjects.ItemFacade;

public class ChangeShopItemInfoRequest {

    private String shopOwnerName;
    private ItemFacade updatedItem;
    private ItemFacade oldItem;
    private String shopName;

    public ChangeShopItemInfoRequest() {
    }

    public ChangeShopItemInfoRequest(String shopOwnerName, ItemFacade updatedItem, ItemFacade oldItem, String shopName) {
        this.shopOwnerName = shopOwnerName;
        this.updatedItem = updatedItem;
        this.oldItem = oldItem;
        this.shopName = shopName;
    }

    public String getShopOwnerName() {
        return shopOwnerName;
    }

    public void setShopOwnerName(String shopOwnerName) {
        this.shopOwnerName = shopOwnerName;
    }

    public ItemFacade getUpdatedItem() {
        return updatedItem;
    }

    public void setUpdatedItem(ItemFacade updatedItem) {
        this.updatedItem = updatedItem;
    }

    public ItemFacade getOldItem() {
        return oldItem;
    }

    public void setOldItem(ItemFacade oldItem) {
        this.oldItem = oldItem;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
