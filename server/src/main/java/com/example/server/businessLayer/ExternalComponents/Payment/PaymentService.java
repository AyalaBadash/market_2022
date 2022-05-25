package com.example.server.businessLayer.ExternalComponents.Payment;

import com.example.server.ResourcesObjects.PaymentMethod;

public interface PaymentService {

    public String pay(PaymentMethod paymentMethod);
    public boolean cancelPayment(PaymentMethod paymentMethod);
}