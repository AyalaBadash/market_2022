package com.example.server.businessLayer.ExternalComponents.Payment;

import com.example.server.businessLayer.ExternalComponents.Supply.Address;

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
    public int pay(String request) {
        return 10000;
    }

    @Override
    public int cancelPayment(String transactionId) {
        return 0;
    }

    @Override
    public String handShake(String request) {
        return "OK";
    }

}
