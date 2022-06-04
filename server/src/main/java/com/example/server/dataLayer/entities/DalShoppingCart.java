package com.example.server.dataLayer.entities;

import javax.persistence.*;

@Entity
@Table
public class DalShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private double currentPrice;


    public DalShoppingCart(){}

    public DalShoppingCart(int ID, double price) {
        this.ID = ID;
        this.currentPrice = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public double getPrice() {
        return currentPrice;
    }

    public void setPrice(double price) {
        this.currentPrice = price;
    }
}
