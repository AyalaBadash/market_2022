package com.example.server.businessLayer;

import java.util.List;
import java.util.Map;

class ItemAcquisition {
    String shopName;
    String itemName;
    double amount;
    double totalPriceForItem;

    public ItemAcquisition(String shopName, String itemName, double amount, double totalPriceForItem) {
        this.shopName = shopName;
        this.itemName = itemName;
        this.amount = amount;
        this.totalPriceForItem = totalPriceForItem;
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
        return "You bought : "+amount + " "+ itemName+" in the shop : "+ shopName+". Total price for this item:"+totalPriceForItem+"\n";
    }
}
//--------------------------------------------------------------------------------------------------------------------

public class Acquisition {
    private String name;
    private List<ItemAcquisition> ItemAcquisitions;
    private Boolean paymentDone;
    private Boolean supplied;

    public Acquisition(ShoppingCart cart , String name)
    {
        this.name = name;
        paymentDone = false;
        supplied = false;
        for (Map.Entry<Shop,ShoppingBasket> entry:cart.getCart().entrySet())
        {
            String curShop = entry.getKey().getShopName();
            for (Map.Entry<Item,Double> bask: entry.getValue().getItems().entrySet())
            {
                double curPrice = bask.getKey().getPrice()*bask.getValue();
                ItemAcquisition acq = new ItemAcquisition(curShop,bask.getKey().getName(), bask.getValue(),curPrice);
                ItemAcquisitions.add(acq);
            }
        }

    }

    public List<ItemAcquisition> getItemAcquisitions() {
        return ItemAcquisitions;
    }

    public void setItemAcquisitions(List<ItemAcquisition> ItemAcquisitions) {
        this.ItemAcquisitions = ItemAcquisitions;
    }

    public Boolean getPaymentDone() {
        return paymentDone;
    }

    public void setPaymentDone(Boolean paymentDone) {
        this.paymentDone = paymentDone;
    }

    public Boolean getSupplied() {
        return supplied;
    }

    public void setSupplied(Boolean supplied) {
        this.supplied = supplied;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (ItemAcquisition acq : ItemAcquisitions)
            str.append(acq.toString());
        str.append("Payment status:").append(paymentDone);
        str.append("Supply status:").append(supplied);
        return str.toString();

    }
}
