package com.example.server.ScenarioTests;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.Users.UserController;
import com.example.server.businessLayer.Market.Users.Visitor;
import com.example.server.businessLayer.Payment.CreditCard;
import com.example.server.businessLayer.Payment.PaymentServiceProxy;
import com.example.server.businessLayer.Payment.WSEPPaymentServiceAdapter;
import com.example.server.businessLayer.Publisher.TextDispatcher;
import com.example.server.businessLayer.Supply.Address;
import com.example.server.businessLayer.Supply.SupplyServiceProxy;
import com.example.server.businessLayer.Supply.WSEPSupplyServiceAdapter;
import com.example.server.serviceLayer.MarketService;
import com.example.server.serviceLayer.Notifications.Notification;
import com.example.server.serviceLayer.Notifications.RealTimeNotifications;
import com.example.server.serviceLayer.PurchaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ServicesTests {
    PaymentServiceProxy paymentServiceProxy;
    SupplyServiceProxy supplyServiceProxy;
    String userName = "userTest";
    String password = "passTest";
    String ItemName= "item1";
    Item itemAdded;
    int productAmount=20;
    Double productPrice=30.0;
    String shopOwnerName = "bar";
    String shopOwnerPassword = "pass";
    String memberName = "bar1";
    String memberPassword = "pass1";
    String loggedInmemberName = "bar2";

    String loggedInmemberPassword = "pass2";
    String shopName = "store";
    TextDispatcher textDispatcher = TextDispatcher.getInstance();
    CreditCard creditCard;
    Address address;
    Market market ;
    Visitor visitor;

    @BeforeEach
    public void init() {
        paymentServiceProxy = new PaymentServiceProxy(new WSEPPaymentServiceAdapter(), true);
        supplyServiceProxy = new SupplyServiceProxy(new WSEPSupplyServiceAdapter(), true);
        creditCard = new CreditCard("1234567890", "07", "2026", "205", "Bar Damri", "208915751");
        address = new Address("Bar Damri", "Atad 3", "Beer Shaba", "Israel", "8484403");
        market = Market.getInstance();
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

    @Test
    @DisplayName("System init from file")
    public void initFromFile(){
        try{
            MarketService marketService= MarketService.getInstance();
            marketService.firstInitMarket(true);
            PurchaseService purchaseService= PurchaseService.getInstance();
            UserController userController= UserController.getInstance();
            List<String> list= new ArrayList<>();
            list.add("u2");
            list.add("u3");
            list.add("u4");
            Assertions.assertTrue(userController.allInMarket(list));
        }
        catch(Exception e){
            assert false;
        }
    }
    @Test
    @DisplayName("System init from no file")
    public void initFromNoFile(){
        try{

            market.firstInitMarket("AdminName","AdminPassword","noName.txt",true);
            market.setPublishService(TextDispatcher.getInstance(), market.getSystemManagerName());
            market.memberLogout("AdminName");
            assert false;
        }
        catch(MarketException ex){
            assert true;
        }
        catch(Exception e){
            assert false;
        }
    }


   @Test
    @DisplayName("notification test- close shop")
    public void closeShop() {
       try {
           try {
               market.firstInitMarket(true);
           } catch (Exception e) {
           }
           // shop manager register
           registerVisitor(shopOwnerName, shopOwnerPassword);
           loginMember(shopOwnerName, shopOwnerPassword);
           openShop();
           market.closeShop(shopOwnerName, shopName);
           logoutMember(shopOwnerName);
           market.removeMember( market.getSystemManagerName(),shopOwnerName);
           assert true;
       } catch (Exception e) {
           assert false;
       }
       try {
           logoutMember(shopOwnerName);
       } catch (MarketException ex) {
           System.out.println(ex.getMessage());
       }
   }


    public void loginMember(String name, String password) throws MarketException {
        if(UserController.getInstance().isLoggedIn(name))
            return;
        visitor = market.guestLogin();
        market.memberLogin(name, password);
        market.validateSecurityQuestions(name, new ArrayList<>(), visitor.getName());
    }
    public void logoutMember(String name) throws MarketException {
        market.memberLogout(name);
    }
    public void registerVisitor(String name, String pass) throws MarketException {
        // shop manager register
        Visitor visitor = market.guestLogin();
        market.register(name, pass);
    }

    private void openShop() throws MarketException {
        market.openNewShop(shopOwnerName, shopName);
        itemAdded = market.addItemToShopItem(shopOwnerName, ItemName, productPrice, Item.Category.electricity, "", new ArrayList<>(), productAmount, shopName);

    }
}
