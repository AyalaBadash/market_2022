package com.example.server.ScenarioTests;

import com.example.server.businessLayer.Payment.PaymentServiceProxy;
import com.example.server.businessLayer.Publisher.TextDispatcher;
import com.example.server.businessLayer.Supply.SupplyServiceProxy;
import com.example.server.businessLayer.Market.Market;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SystemTests {
    Market market;
    String userName = "userTest";
    String password = "passTest";
    @BeforeEach
    public void setUp(){
        try {
            market = Market.getInstance();
        if (market.getPaymentService()==null)
                market.firstInitMarket (userName, password ,true);

        }
        catch (Exception e){}
    }
    @Test
    public void initTwice() {
        try {
            market.firstInitMarket (userName, password ,true);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

}
