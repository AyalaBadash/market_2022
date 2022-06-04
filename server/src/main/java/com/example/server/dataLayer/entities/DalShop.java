package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shops")
public class DalShop {
    @Id
    private String name;
    private int marketID;
    private String shopFounder;
    private boolean closed;
    private int rnk;
    private int rnkrs;

    @OneToMany(targetEntity = DalItem.class,cascade =CascadeType.ALL )
    @JoinColumn(name = "shop",referencedColumnName = "name")
    private List<DalItem> items;

    public DalShop(){}

    public DalShop(String name, int marketID, String shopFounder, boolean closed, int rnk, int rnkrs,List<DalItem> items) {
        this.name = name;
        this.marketID = marketID;
        this.shopFounder = shopFounder;
        this.closed = closed;
        this.rnk = rnk;
        this.rnkrs = rnkrs;
        this.items = items;
    }

    public List<DalItem> getItems() {
        return items;
    }

    public void setItems(List<DalItem> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarketID() {
        return marketID;
    }

    public void setMarketID(int marketID) {
        this.marketID = marketID;
    }

    public String getShopFounder() {
        return shopFounder;
    }

    public void setShopFounder(String shopFounder) {
        this.shopFounder = shopFounder;
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
}
