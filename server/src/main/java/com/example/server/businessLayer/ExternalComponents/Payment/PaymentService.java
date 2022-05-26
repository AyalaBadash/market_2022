package com.example.server.businessLayer.ExternalComponents.Payment;

import com.example.server.businessLayer.ExternalComponents.Supply.Address;

public interface PaymentService {

    public int pay(String request);
    public int cancelPayment(String transactionId);
    public String handShake(String request);
}