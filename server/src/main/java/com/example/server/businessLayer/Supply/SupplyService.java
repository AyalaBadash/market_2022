package com.example.server.businessLayer.Supply;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;

import java.util.List;

public interface SupplyService {

    public static RequestConfig requestConfig = RequestConfig.custom().build();
    public int supply(List<NameValuePair> requestBody);
    public int cancelSupply(List<NameValuePair> supplyID);
}