package main.businessLayer.ExternalServices;

import main.resources.Address;

import java.time.LocalDateTime;

public interface ProductsSupplyService {


//    public LocalDateTime checkAvailability(Address address);
    public String supply(Address address, LocalDateTime date);
    public boolean cancelSupply(String supplyID);

}
