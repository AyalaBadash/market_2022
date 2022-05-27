package com.example.server.businessLayer.ExternalComponents;

import com.example.server.ResourcesObjects.PaymentMethod;

public class PaymentMock implements PaymentService {
    int num;
    public PaymentMock() {
    }

    public PaymentMock(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String pay(PaymentMethod paymentMethod) {
        if(paymentMethod == null)
            return "-1";
        else return "1";
    }

    @Override
    public boolean cancelPayment(PaymentMethod paymentMethod) {
        return true;
    }
}
