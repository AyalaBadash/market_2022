package com.example.server.dataLayer.entities;

import javax.persistence.*;

@Entity
@Table
public class DalShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String memberName;
    private double price;
    public DalShoppingCart(){}

    public DalShoppingCart(int ID, String memberName, double price) {
        this.ID = ID;
        this.memberName = memberName;
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
