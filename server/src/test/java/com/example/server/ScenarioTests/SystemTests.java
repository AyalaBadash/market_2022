package com.example.server.ScenarioTests;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Payment.PaymentServiceProxy;
import com.example.server.businessLayer.Publisher.TextDispatcher;
import com.example.server.businessLayer.Supply.SupplyServiceProxy;
import com.example.server.businessLayer.Market.Market;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SystemTests {
    Market market;
    String userName = "u1";
    String password = "password";
    @BeforeEach
    public void setUp(){
        try {
            market = Market.getInstance();
        if (market.getPaymentService()==null)
                market.firstInitMarket (userName, password );

        }
        catch (Exception e){}
    }
    @Test
    public void initTwice() {
        try {
            MarketConfig.FIRST_INIT=true;
            market.isInit();
            market.firstInitMarket (userName, password );
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

}
