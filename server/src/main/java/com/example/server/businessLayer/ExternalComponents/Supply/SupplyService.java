package com.example.server.businessLayer.ExternalComponents.Supply;

import java.time.LocalDateTime;

public interface SupplyService {
    //   public LocalDateTime checkAvailability(Address address);
    public int supply(String requestBody);
    public int cancelSupply(String supplyID);
}