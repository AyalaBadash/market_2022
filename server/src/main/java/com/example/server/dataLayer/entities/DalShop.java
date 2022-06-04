package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shops")
public class DalShop {
    @Id
    private String name;
    @OneToMany(targetEntity = DalItem.class,cascade =CascadeType.ALL )
    @JoinColumn(name = "shop",referencedColumnName = "name")
    private List<DalItem> items;

    //TODO add list of managers and list of owners

//    @OneToMany(targetEntity = DalItem.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "amount", referencedColumnName = "itemCurrentAmount")
//    private List<Double> itemCurrentAmount;
    private boolean closed;
    private int rnk;
    private int rnkrs;

    //todo add purchase history
    //todo add discount policy


    public DalShop(){}

    public DalShop(String name, List<DalItem> items, List<Double> itemCurrentAmount, boolean closed, int rnk, int rnkrs) {
        this.name = name;
        this.items = items;
//        this.itemCurrentAmount = itemCurrentAmount;
        this.closed = closed;
        this.rnk = rnk;
        this.rnkrs = rnkrs;
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
}
