package com.example.server.ScenarioTests;

import com.example.server.businessLayer.ExternalComponents.Payment.PaymentHandler;
import com.example.server.businessLayer.ExternalComponents.Payment.PaymentMock;
import com.example.server.businessLayer.ExternalComponents.Supply.SupplyHandler;
import com.example.server.businessLayer.ExternalComponents.Supply.SupplyMock;
import com.example.server.businessLayer.Market;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SystemTests {
    Market market;
    String userName = "userTest";
    String password = "passTest";
    PaymentMock paymentService = new PaymentMock();
    SupplyMock supplyService = new SupplyMock();

    @BeforeEach
    public void setUp(){
        try {
            market = Market.getInstance();
            if (market.getPaymentService()==null)
                market.firstInitMarket (new PaymentHandler(paymentService), new SupplyHandler(supplyService), userName, password );
        }
        catch (Exception e){}
    }
    @Test
    public void initTwice() {
        try {
            market.firstInitMarket (new PaymentHandler(paymentService), new SupplyHandler(supplyService), userName, password );
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

}
