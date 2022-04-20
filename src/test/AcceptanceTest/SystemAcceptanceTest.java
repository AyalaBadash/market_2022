package test.AcceptanceTest;

import main.businessLayer.Market;
import main.businessLayer.services.PaymentService;
import main.businessLayer.services.ProductsSupplyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SystemAcceptanceTest extends AcceptanceTest {
    PaymentService paymentService ;
    ProductsSupplyService supplyService ;


    @BeforeEach
    public void beforeRun(){
        paymentService =  new PaymentService();
        supplyService =  new ProductsSupplyService();
    }

//    @Test
//    @DisplayName("Initialize Market System - Valid Values")
//    public void marketInitialize(){
//        Assertions.assertNotNull(bridge.initMarket(paymentService, supplyService,"tester","1234" ));
//    }
//    @Test
//    @DisplayName("Initialize Market System - Null values")
//    public void marketInitializeNullValues(){
//        Market market1 = bridge.initMarket(null, supplyService, "tester", "1234");
//        Market market2 = bridge.initMarket(paymentService, supplyService, null, "1234");
//        Market market3 = bridge.initMarket(paymentService, null, "tester", "1234");
//        Market market4 = bridge.initMarket(paymentService, supplyService, "tester", null);
//        Assertions.assertNull(market1);
//        Assertions.assertNull(market2);
//        Assertions.assertNull(market3);
//        Assertions.assertNull(market4);
//    }
//
//    @Test
//    @DisplayName("Initialize Market System - invalid user values")
//    public void marketInitializeInvalidValues(){
//        Market market1 = bridge.initMarket(paymentService, supplyService, "tes  ter", "1234");
//        Market market2 = bridge.initMarket(paymentService, supplyService, "!2dsa#", "1234");
//        Market market3 = bridge.initMarket(paymentService, supplyService, "tester", "");
//        Assertions.assertNull(market1);
//        Assertions.assertNull(market2);
//        Assertions.assertNull(market3);
//    }
//
//    @Test
//    @DisplayName("Change payment Service")
//    public void changePaymentService(){
//        PaymentService newPaymentService = new PaymentService();
//        Assertions.assertTrue(bridge.changePaymentService(newPaymentService));
//        // TODO need to check whether adding not-working service is a possible test
//        Assertions.assertFalse(bridge.changePaymentService(null));
//    }
//
//    @Test
//    @DisplayName("Change Supply service")
//    public void changeSupplyService(){
//        ProductsSupplyService supplyService = new ProductsSupplyService();
//        Assertions.assertTrue(bridge.changeSupplyService(supplyService));
//        // TODO need to check whether adding not-working service is a possible test
//        Assertions.assertFalse(null);
//    }

//    @Test
//    @DisplayName("Payment test")
//    public void paymentTest(){
//        Assertions.fail();
//    }

}
