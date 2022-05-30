package com.example.server.businessLayer.Supply;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;

import java.util.List;

public interface SupplyService {

    boolean testRequest = false;
    String TypeSupply = "supply";
    String TypeCancel_supply = "cancel_supply";
    RequestConfig requestConfig = RequestConfig.custom().build();

    int supply(Address address);

    int cancelSupply(int supplyID);

    List<NameValuePair> addressToString(Address address);

    List<NameValuePair> transactionToString(int transactionId);
}