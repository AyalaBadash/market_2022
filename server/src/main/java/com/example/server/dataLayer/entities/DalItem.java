package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "items")
public class DalItem {
    @Id
    @Column(name = "item_id")
    private int item_id;
    private String name;
    private int price;
    private String info;
    private int rnk;
    private int rnkrs;
    private String category;
//    @OneToMany
    @ElementCollection
    @Column (name = "keyword")
    @CollectionTable (name = "item_keywords", joinColumns = {@JoinColumn(name = "item_id")})
    private List<String> keywords;
//    @OneToMany (mappedBy = "item")
//    private Set<DalItemsForBasket> baskets;

    public DalItem(int id, String name, int price, String info, int rnk, int rnkrs, String category) {
        this.item_id = id;
        this.name = name;
        this.price = price;
        this.info = info;
        this.rnk = rnk;
        this.rnkrs = rnkrs;
        this.category = category;
//        keywords = new ArrayList<>();
    }

    public DalItem(){}


    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int id) {
        this.item_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

//    public List<DalKeyword> getKeywords() {
//        return keywords;
//    }
//
//    public void setKeywords(List<DalKeyword> keywords) {
//        this.keywords = keywords;
//    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


//    public List<String> getKeywords() {
//        return keywords;
//    }
//
//    public void setKeywords(List<String> keywords) {
//        this.keywords = keywords;
//    }
}
