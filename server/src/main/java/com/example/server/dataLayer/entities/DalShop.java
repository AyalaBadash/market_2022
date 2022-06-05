package com.example.server.dataLayer.entities;

import com.example.server.businessLayer.Market.Item;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "shops")
public class DalShop {
    @Id
    @Column(name = "shop_name")
    private String shop_name;
    @OneToMany(targetEntity = DalItem.class,cascade =CascadeType.ALL )
    @JoinColumn(name = "shop",referencedColumnName = "shop_name")
    private List<DalItem> items;

    //TODO add list of managers and list of owners
//    @OneToMany(targetEntity = DalItem.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "shop_name", referencedColumnName = "id")
//    @MapKeyJoinColumn (name = "basket_id")
//    private Map<Item, Double> itemsCurrentAmount;
    private boolean closed;
    private int rnk;
    private int rnkrs;

    @ElementCollection
    @Column (name = "purchase_history")
    @CollectionTable (name = "purchase_histories", joinColumns = {@JoinColumn(name = "shop_name")})
    private List<String> purchaseHistory;
    //todo add discount policy


    public DalShop(){}

    public DalShop(String name, List<DalItem> items, List<Double> itemCurrentAmount, boolean closed, int rnk, int rnkrs) {
        this.shop_name = name;
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
}
