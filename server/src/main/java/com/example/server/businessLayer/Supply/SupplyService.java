package com.example.server.businessLayer.Supply;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;

import java.util.List;

public interface SupplyService {

    boolean testRequest = false;
    RequestConfig requestConfig = RequestConfig.custom().build();
    int supply(List<NameValuePair> requestBody);
    int cancelSupply(List<NameValuePair> supplyID);
}