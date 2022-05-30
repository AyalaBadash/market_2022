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
    PaymentServiceProxy paymentService = new PaymentServiceProxy();
    SupplyServiceProxy supplyService = new SupplyServiceProxy();

    static TextDispatcher textDispatcher= TextDispatcher.getInstance();
    @BeforeEach
    public void setUp(){
        try {
            market = Market.getInstance();
            if (market.getPaymentService()==null)
                market.firstInitMarket (paymentService, supplyService, textDispatcher,userName, password );
        }
        catch (Exception e){}
    }
    @Test
    public void initTwice() {
        try {
            market.firstInitMarket (paymentService,supplyService, textDispatcher,userName, password );
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

}
