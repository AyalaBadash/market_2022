package com.example.server.businessLayer.Payment;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;

import java.util.List;

public interface PaymentService {

    public static RequestConfig requestConfig = RequestConfig.custom().build();
    public int pay(List<NameValuePair> request);
    public int cancelPayment(List<NameValuePair> request);
    public String handShake(List<NameValuePair> request);

}