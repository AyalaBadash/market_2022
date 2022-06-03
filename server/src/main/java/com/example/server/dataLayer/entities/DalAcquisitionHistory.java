package com.example.server.dataLayer.entities;



import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Acquisitions History")
public class DalAcquisitionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String buyerName;
    private double totalPriceBeforeDiscount;
    private double discount;
    private double totalPriceAfterDiscount;
    @OneToMany
    private List<DalItemAcquisitionHistory> itemAcquisitionHistories;

    public DalAcquisitionHistory(){}

    public DalAcquisitionHistory(String buyerName, double totalPriceBeforeDiscount, double discount, double totalPriceAfterDiscount, List<DalItemAcquisitionHistory> itemAcquisitionHistories) {
        this.buyerName = buyerName;
        this.totalPriceBeforeDiscount = totalPriceBeforeDiscount;
        this.discount = discount;
        this.totalPriceAfterDiscount = totalPriceAfterDiscount;
        this.itemAcquisitionHistories = itemAcquisitionHistories;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public double getTotalPriceBeforeDiscount() {
        return totalPriceBeforeDiscount;
    }

    public void setTotalPriceBeforeDiscount(double totalPriceBeforeDiscount) {
        this.totalPriceBeforeDiscount = totalPriceBeforeDiscount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalPriceAfterDiscount() {
        return totalPriceAfterDiscount;
    }

    public void setTotalPriceAfterDiscount(double totalPriceAfterDiscount) {
        this.totalPriceAfterDiscount = totalPriceAfterDiscount;
    }

    public List<DalItemAcquisitionHistory> getItemAcquisitionHistories() {
        return itemAcquisitionHistories;
    }

    public void setItemAcquisitionHistories(List<DalItemAcquisitionHistory> itemAcquisitionHistories) {
        this.itemAcquisitionHistories = itemAcquisitionHistories;
    }
}
