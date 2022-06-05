package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "acquisitions")
public class DalAcquisition implements Serializable {
    @Id
    private int cartID;
    private String buyerName;
    private boolean paymentDone;
    private boolean supplyConfirmed;
    private int supplyID;
    private int paymentID;

    public DalAcquisition(){}

    public DalAcquisition(int cartID, String buyerName, boolean paymentDone, boolean supplyConfirmed, int supplyID, int paymentID) {
        this.cartID = cartID;
        this.buyerName = buyerName;
        this.paymentDone = paymentDone;
        this.supplyConfirmed = supplyConfirmed;
        this.supplyID = supplyID;
        this.paymentID = paymentID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public boolean isPaymentDone() {
        return paymentDone;
    }

    public void setPaymentDone(boolean paymentDone) {
        this.paymentDone = paymentDone;
    }

    public boolean isSupplyConfirmed() {
        return supplyConfirmed;
    }

    public void setSupplyConfirmed(boolean supplyConfirmed) {
        this.supplyConfirmed = supplyConfirmed;
    }

    public int getSupplyID() {
        return supplyID;
    }

    public void setSupplyID(int supplyID) {
        this.supplyID = supplyID;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
}
