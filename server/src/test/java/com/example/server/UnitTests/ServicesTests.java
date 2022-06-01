package com.example.server.UnitTests;

import com.example.server.businessLayer.Payment.CreditCard;
import com.example.server.businessLayer.Payment.PaymentHandler;
import com.example.server.businessLayer.Payment.WSEPPaymentService;
import com.example.server.businessLayer.Supply.Address;
import com.example.server.businessLayer.Supply.SupplyHandler;
import com.example.server.businessLayer.Supply.WSEPSupplyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ServicesTests {
    PaymentHandler paymentHandler;
    SupplyHandler supplyHandler;
    CreditCard creditCard;
    Address address;

    @BeforeEach
    public void init(){
        paymentHandler=new PaymentHandler(new WSEPPaymentService());
        supplyHandler=new SupplyHandler(new WSEPSupplyService());
        creditCard=new CreditCard("1234567890","07","2026","205","Bar Damri","208915751");
        address= new Address("Bar Damri","Atad 3","Beer Shaba","Israel","8484403");
    }

    @Test
    @DisplayName("Payment service- pay")
    public void PaymentHandler(){
        try {
            int result=paymentHandler.pay(creditCard);
            Assertions.assertNotEquals(result,-1);
        }
        catch (Exception e){
            assert false;
        }
    }
    @Test
    @DisplayName("Supply service-supply")
    public void SupplyHandler(){
        try {
            int result=supplyHandler.supply(address);
            Assertions.assertNotEquals(result,-1);
        }
        catch (Exception e){
            assert false;
        }
    }
    @Test
    @DisplayName("Payment service- cancel pay")
    public void PaymentHandlerCancel(){
        try {
            int result = paymentHandler.pay(creditCard);
            if (result != -1) {
                result = paymentHandler.cancelPay(result);
                Assertions.assertNotEquals(result, -1);
            } else {
                assert false;
            }
        }
        catch (Exception e){
            assert false;
        }
    }
    @Test
    @DisplayName("Supply service- cancel supply")
    public void SupplyHandlerCancel(){
        try {
            int result=supplyHandler.supply(address);
            if(result!=-1) {
                result = supplyHandler.cancelSupply(result);
                Assertions.assertNotEquals(result, -1);
            }
            else{
                assert false;
            }
        }
        catch (Exception e){
            assert false;
        }
    }
}
