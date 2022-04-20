package main.businessLayer;

import java.util.HashMap;
import java.util.Map;

public class ShoppingBasket {
    Map<String,Double> items;//<itemID,quantity>
    public ShoppingBasket()
    {
        items= new HashMap<>();
    }


}
