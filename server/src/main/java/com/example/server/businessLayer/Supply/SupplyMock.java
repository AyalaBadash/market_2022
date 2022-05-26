package com.example.server.businessLayer.Supply;

import org.apache.http.NameValuePair;

import java.util.List;

public class SupplyMock implements SupplyService {

    public SupplyMock() {
    }

    @Override
    public int supply(List<NameValuePair> request) {
        return 1;
    }

    @Override
    public int cancelSupply(List<NameValuePair> supplyID) {
        return 1;
    }
}
