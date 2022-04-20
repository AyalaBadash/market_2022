package main.businessLayer.ExternalServices;

import main.businessLayer.ShoppingCart;
import main.businessLayer.users.Visitor;
import resources.Address;

import java.time.LocalDateTime;

public interface ProductsSupplyService {


//    public LocalDateTime checkAvailability(Address address);
    public boolean supply(Address address, LocalDateTime date);
    public boolean cancelSupply(Address address, LocalDateTime date);

}
