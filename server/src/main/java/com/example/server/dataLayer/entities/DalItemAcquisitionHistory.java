package com.example.server.dataLayer.entities;


import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="Item_Acquisitions_History")
public class DalItemAcquisitionHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String shopName;
    private String itemName;
    private double amount;
    private double totalPriceForItem;


    public DalItemAcquisitionHistory(){}

    public DalItemAcquisitionHistory(String shopName, String itemName, double amount, double totalPriceForItem) {
        this.shopName = shopName;
        this.itemName = itemName;
        this.amount = amount;
        this.totalPriceForItem = totalPriceForItem;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalPriceForItem() {
        return totalPriceForItem;
    }

    public void setTotalPriceForItem(double totalPriceForItem) {
        this.totalPriceForItem = totalPriceForItem;
    }
}

