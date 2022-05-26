package com.example.server.businessLayer.ExternalComponents.Supply;

import java.time.LocalDateTime;

public class SupplyMock implements SupplyService {

    public SupplyMock() {
    }

    @Override
    public int supply(String request) {
        return 1;
    }

    @Override
    public int cancelSupply(String supplyID) {
        return 1;
    }
}
