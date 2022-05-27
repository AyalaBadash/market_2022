package com.example.server.businessLayer.Payment;

import org.apache.http.NameValuePair;

import java.util.List;

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
    public int pay(List<NameValuePair> request ) {
        return 10000;
    }

    @Override
    public int cancelPayment(List<NameValuePair> request) {
        return 0;
    }

    @Override
    public String handShake(List<NameValuePair> request) {
        return "OK";
    }

}
