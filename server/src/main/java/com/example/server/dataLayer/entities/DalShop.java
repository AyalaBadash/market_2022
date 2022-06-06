package com.example.server.dataLayer.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "shops")
public class DalShop {
    @Id
    @Column(name = "shop_name")
    private String shop_name;
//    @OneToMany(targetEntity = DalItem.class,cascade =CascadeType.ALL )
//    @JoinColumn(name = "shop",referencedColumnName = "shop_name")
//    private List<DalItem> items;

    //TODO add list of managers and list of owners
//    @OneToMany(targetEntity = DalItem.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "shop", referencedColumnName = "shop_name")
//    @Column(name = "amount_in_shop")
//    @MapKey (name = "item_id")


    @ElementCollection
    @CollectionTable(name = "items_in_shop", joinColumns = {@JoinColumn(name = "shop_name")})
    @Column(name = "amount")
    @MapKeyJoinColumn(name = "item_id")
    private Map<DalItem, Double> itemsCurrentAmount;
    private boolean closed;
    private int rnk;
    private int rnkrs;

    @ElementCollection
    @Column (name = "purchase_history")
    @CollectionTable (name = "purchase_histories", joinColumns = {@JoinColumn(name = "shop_name")})
    private List<String> purchaseHistory;
    //todo add discount policy

    public DalShop(){}

    public DalShop(String name, Map<DalItem, Double> itemsCurrentAmount, boolean closed, int rnk, int rnkrs) {
        this.shop_name = name;
        this.itemsCurrentAmount = itemsCurrentAmount;
        this.closed = closed;
        this.rnk = rnk;
        this.rnkrs = rnkrs;
    }


    public String getName() {
        return shop_name;
    }

    public void setName(String name) {
        this.shop_name = name;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public int getRnk() {
        return rnk;
    }

    public void setRnk(int rnk) {
        this.rnk = rnk;
    }

    public int getRnkrs() {
        return rnkrs;
    }

    public void setRnkrs(int rnkrs) {
        this.rnkrs = rnkrs;
    }

//    public List<Double> getItemCurrentAmount() {
//        return itemCurrentAmount;
//    }
//
//    public void setItemCurrentAmount(List<Double> itemCurrentAmount) {
//        this.itemCurrentAmount = itemCurrentAmount;
//    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public Map<DalItem, Double> getItemsCurrentAmount() {
        return itemsCurrentAmount;
    }

    public void setItemsCurrentAmount(Map<DalItem, Double> itemsCurrentAmount) {
        this.itemsCurrentAmount = itemsCurrentAmount;
    }

    public List<String> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List<String> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }
}
