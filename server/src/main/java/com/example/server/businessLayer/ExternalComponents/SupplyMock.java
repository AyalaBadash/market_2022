package com.example.server.businessLayer.ExternalComponents;

import com.example.server.ResourcesObjects.Address;

import java.time.LocalDateTime;

public class SupplyMock implements ProductsSupplyService {

    public SupplyMock() {
    }

    @Override
    public String supply(Address address, LocalDateTime date) {
        return "1";
    }

    @Override
    public boolean cancelSupply(String supplyID) {
        return true;
    }
}
