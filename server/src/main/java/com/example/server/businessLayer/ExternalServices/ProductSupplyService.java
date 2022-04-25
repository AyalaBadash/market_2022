package com.example.server.businessLayer.ExternalServices;

import com.example.server.ResourcesObjects.Address;

import java.time.LocalDateTime;

public interface ProductSupplyService {
    //    public LocalDateTime checkAvailability(Address address);
    public String supply(Address address, LocalDateTime date);
    public boolean cancelSupply(String supplyID);
}