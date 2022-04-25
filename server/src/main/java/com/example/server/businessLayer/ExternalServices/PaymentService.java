package com.example.server.businessLayer.ExternalServices;

import com.example.server.ResourcesObjects.PaymentMethod;

public interface PaymentService {

    public String pay(PaymentMethod paymentMethod);
    public boolean cancelPayment(PaymentMethod paymentMethod);
}