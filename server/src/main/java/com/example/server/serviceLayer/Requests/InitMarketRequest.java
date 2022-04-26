package com.example.server.serviceLayer.Requests;

import com.example.server.businessLayer.ExternalServices.PaymentService;
import com.example.server.businessLayer.ExternalServices.ProductsSupplyService;

public class InitMarketRequest {
    PaymentService paymentService;
    ProductsSupplyService supplyService;
    String userName;
    String password;

    public InitMarketRequest() {
    }

    public InitMarketRequest(PaymentService paymentService, ProductsSupplyService supplyService, String userName, String password) {
        this.paymentService = paymentService;
        this.supplyService = supplyService;
        this.userName = userName;
        this.password = password;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public ProductsSupplyService getSupplyService() {
        return supplyService;
    }

    public void setSupplyService(ProductsSupplyService supplyService) {
        this.supplyService = supplyService;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
