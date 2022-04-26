package com.example.server.serviceLayer.Requests;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.PaymentMethod;

public class BuyShoppingCartRequest {
    private String visitorName;
    private double expectedPrice;
    private PaymentMethod paymentMethod;
    private Address address;

    public BuyShoppingCartRequest() {
    }

    public BuyShoppingCartRequest(String visitorName, double expectedPrice, PaymentMethod paymentMethod, Address address) {
        this.visitorName = visitorName;
        this.expectedPrice = expectedPrice;
        this.paymentMethod = paymentMethod;
        this.address = address;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public double getExpectedPrice() {
        return expectedPrice;
    }

    public void setExpectedPrice(double expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
