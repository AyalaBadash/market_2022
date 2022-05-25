package com.example.server.businessLayer.ExternalComponents.Supply;

import com.example.server.businessLayer.ExternalComponents.Address;

import java.time.LocalDateTime;

public interface ProductsSupplyService {
    //    public LocalDateTime checkAvailability(Address address);
    public String supply(Address address, LocalDateTime date);
    public boolean cancelSupply(String supplyID);
}