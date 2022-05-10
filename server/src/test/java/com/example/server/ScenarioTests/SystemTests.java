package com.example.server.ScenarioTests;

import com.example.server.businessLayer.ExternalServices.PaymentMock;
import com.example.server.businessLayer.ExternalServices.SupplyMock;
import com.example.server.businessLayer.Market;
import com.example.server.serviceLayer.Response;
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
                market.firstInitMarket (paymentService, supplyService, userName, password );
        }
        catch (Exception e){}
    }
    @Test
    public void initTwice() {
        try {
            market.firstInitMarket (paymentService, supplyService, userName, password );
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

}
