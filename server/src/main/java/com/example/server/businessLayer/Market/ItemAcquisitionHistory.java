package com.example.server.businessLayer.Market;

import com.example.server.dataLayer.entities.DalItemAcquisitionHistory;
import com.example.server.dataLayer.repositories.ItemAckHistRep;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ItemAcquisitionHistory {
    @Id
    @GeneratedValue
    private long id;
    String shopName;
    String itemName;
    double amount;
    double totalPriceForItem;

    private static ItemAckHistRep itemAckHistRep;

    public ItemAcquisitionHistory(String shopName, String itemName, double amount, double totalPriceForItem) {
        this.shopName = shopName;
        this.itemName = itemName;
        this.amount = amount;
        this.totalPriceForItem = totalPriceForItem;
        itemAckHistRep.save(this);
    }

    public ItemAcquisitionHistory() {
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTotalPriceForItem(double totalPriceForItem) {
        this.totalPriceForItem = totalPriceForItem;
    }

    public String getShopName() {
        return shopName;
    }

    public String getItemName() {
        return itemName;
    }

    public double getAmount() {
        return amount;
    }

    public double getTotalPriceForItem() {
        return totalPriceForItem;
    }

    @Override
    public String toString() {
        return "You bought : " + amount + " " + itemName + " in the shop : " + shopName + ". Total price for this item:" + totalPriceForItem + "\n";
    }

    public DalItemAcquisitionHistory ToDalObject() {
        return new DalItemAcquisitionHistory(this.shopName, this.itemName, this.amount, this.totalPriceForItem);
    }

    public static void setItemAckHistRep(ItemAckHistRep itemAckHistRep) {
        ItemAcquisitionHistory.itemAckHistRep = itemAckHistRep;
    }
}
