package com.example.server.businessLayer.ExternalServices;

import com.example.server.ResourcesObjects.Address;
import com.example.server.businessLayer.ExternalServices.ProductsSupplyService;

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
