package com.example.server.ScenarioTests;

import com.example.server.businessLayer.Payment.CreditCard;
import com.example.server.businessLayer.Payment.PaymentServiceProxy;
import com.example.server.businessLayer.Payment.WSEPPaymentServiceAdapter;
import com.example.server.businessLayer.Publisher.TextDispatcher;
import com.example.server.businessLayer.Supply.Address;
import com.example.server.businessLayer.Supply.SupplyServiceProxy;
import com.example.server.businessLayer.Supply.WSEPSupplyServiceAdapter;
import com.example.server.serviceLayer.Notifications.Notification;
import com.example.server.serviceLayer.Notifications.RealTimeNotifications;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ServicesTests {
    PaymentServiceProxy paymentServiceProxy;
    SupplyServiceProxy supplyServiceProxy;

    TextDispatcher textDispatcher = TextDispatcher.getInstance();
    CreditCard creditCard;
    Address address;

    @BeforeEach
    public void init() {
        paymentServiceProxy = new PaymentServiceProxy(new WSEPPaymentServiceAdapter(), false);
        supplyServiceProxy = new SupplyServiceProxy(new WSEPSupplyServiceAdapter(), false);
        creditCard = new CreditCard("1234567890", "07", "2026", "205", "Bar Damri", "208915751");
        address = new Address("Bar Damri", "Atad 3", "Beer Shaba", "Israel", "8484403");
    }

    @Test
    @DisplayName("Payment service- pay")
    public void PaymentHandler() {
        try {
            int result = paymentServiceProxy.pay(creditCard);
            Assertions.assertNotEquals(result, -1);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("Supply service-supply")
    public void SupplyHandler() {
        try {
            int result = supplyServiceProxy.supply(address);
            Assertions.assertNotEquals(result, -1);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("Payment service- cancel pay")
    public void PaymentHandlerCancel() {
        try {
            int result = paymentServiceProxy.pay(creditCard);
            if (result != -1) {
                result = paymentServiceProxy.cancelPay(result);
                Assertions.assertNotEquals(result, -1);
            } else {
                assert false;
            }
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("Supply service- cancel supply")
    public void SupplyHandlerCancel() {
        try {
            int result = supplyServiceProxy.supply(address);
            if (result != -1) {
                result = supplyServiceProxy.cancelSupply(result);
                Assertions.assertNotEquals(result, -1);
            } else {
                assert false;
            }
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("text dispatcher service- add")
    public void textDispatcherAdd() {
        textDispatcher.clean();
        String name = "Bar";
        RealTimeNotifications not = new RealTimeNotifications();
        not.createMembershipDeniedMessage();
        Assertions.assertEquals(0, textDispatcher.getSessionNum());
        textDispatcher.add(name);
        Assertions.assertEquals(1, textDispatcher.getSessionNum());
    }

    @Test
    @DisplayName("text dispatcher service- add twice to user")
    public void textDispatcherAdd2() {
        textDispatcher.clean();
        String name = "Bar";
        RealTimeNotifications not = new RealTimeNotifications();
        not.createMembershipDeniedMessage();
        Assertions.assertEquals(0, textDispatcher.getSessionNum());
        textDispatcher.add(name);
        Assertions.assertEquals(1, textDispatcher.getSessionNum());
        try{
            textDispatcher.add(name);
            Assertions.assertEquals(1, textDispatcher.getSessionNum());
        }
        catch (Exception e){
            assert true;
        }
    }

    @Test
    @DisplayName("text dispatcher service- remove")
    public void textDispatcherRemove(){
        textDispatcher.clean();
        String name = "Bar";
        RealTimeNotifications not = new RealTimeNotifications();
        not.createMembershipDeniedMessage();
        textDispatcher.add(name);
        Assertions.assertEquals(1, textDispatcher.getSessionNum());
        textDispatcher.remove(name);
        Assertions.assertEquals(0, textDispatcher.getSessionNum());
    }
    @Test
    @DisplayName("text dispatcher service- remove user without add")
    public void textDispatcherRemove2(){
        textDispatcher.clean();
        String name = "Bar";
       RealTimeNotifications not = new RealTimeNotifications();
        not.createMembershipDeniedMessage();
        Assertions.assertEquals(0, textDispatcher.getSessionNum());
        List<Notification> notifs=textDispatcher.remove(name);
        Assertions.assertEquals(0, notifs.size());
    }
    @Test
    @DisplayName("text dispatcher service- add new message")
    public void textDispatcherAddMessage(){
        textDispatcher.clean();
        String name = "Bar";
        RealTimeNotifications not = new RealTimeNotifications();
        not.createMembershipDeniedMessage();
        textDispatcher.add(name);
        Assertions.assertTrue(textDispatcher.addMessgae(name,not));
        not.createShopPermissionDeniedMessage("some shop", "some permission");
        Assertions.assertTrue(textDispatcher.addMessgae(name,not));
    }
}
