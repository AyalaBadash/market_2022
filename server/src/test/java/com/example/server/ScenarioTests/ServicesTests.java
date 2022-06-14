package com.example.server.ScenarioTests;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
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
import com.example.server.serviceLayer.Notifications.DelayedNotifications;
import com.example.server.serviceLayer.Notifications.Notification;
import com.example.server.serviceLayer.Notifications.RealTimeNotifications;
import com.example.server.serviceLayer.PurchaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class ServicesTests {
    PaymentServiceProxy paymentServiceProxy;
    SupplyServiceProxy supplyServiceProxy;
    String userName = "u1";
    String password = "p1";
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
        paymentServiceProxy = new PaymentServiceProxy(WSEPPaymentServiceAdapter.getinstance(), true);
        supplyServiceProxy = new SupplyServiceProxy(WSEPSupplyServiceAdapter.getInstance(), true);
        creditCard = new CreditCard("1234567890", "07", "2026", "205", "Bar Damri", "208915751");
        address = new Address("Bar Damri", "Atad 3", "Beer Shaba", "Israel", "8484403");
        market = Market.getInstance();
        Visitor visitor= market.guestLogin();
        try {
            market.isInit();
            market.memberLogin(userName, password);
            market.validateSecurityQuestions(userName,new ArrayList<>(), visitor.getName());
        }catch (Exception e){}
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


    //TODO:BAR CHECK USE NOT EXIST PUBLISHER.
    @Test
    @DisplayName("Payment service- service falls")
    public void PaymentServiceFalls() throws MarketException {
        try {
            try {
                Visitor visitor = market.guestLogin();
                market.memberLogin(userName, password);
                market.validateSecurityQuestions(userName, new ArrayList<>(), visitor.getName());
            }catch (Exception e){
                String str= e.getMessage();
            }
            market.setPaymentServiceAddress("", userName);
            paymentServiceProxy.pay(creditCard);
            assert false;
            market.setPaymentServiceAddress("https://cs-bgu-wsep.herokuapp.com/", market.getSystemManagerName());
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),"Error2");
            market.setPaymentServiceAddress("https://cs-bgu-wsep.herokuapp.com/", market.getSystemManagerName());

        }
    }

    @Test
    @DisplayName("Supply service- service falls")
    public void SupplyServiceFalls() throws MarketException {
        try {
            market.setSupplyServiceAddress("", userName);
            supplyServiceProxy.supply(address);
            assert false;
            market.setSupplyServiceAddress("https://cs-bgu-wsep.herokuapp.com/", market.getSystemManagerName());
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),"Error1");
            market.setSupplyServiceAddress("https://cs-bgu-wsep.herokuapp.com/", market.getSystemManagerName());

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
            MarketConfig.SERVICES_FILE_NAME="noName.txt";
            market.isInit();
            market.setPublishService(TextDispatcher.getInstance(), market.getSystemManagerName());
            market.memberLogout(userName);
            assert false;
            MarketConfig.SERVICES_FILE_NAME="config.txt";
        }
        catch(Exception e){
            assert true;
            MarketConfig.SERVICES_FILE_NAME="config.txt";
        }
    }

    @Test
    @DisplayName("System init from file with wrong services")
    public void initFromWrongServicesFile(){
        try{
            MarketConfig.SERVICES_FILE_NAME="missWrittenConfigs.txt";
            market.isInit();
            market.setPublishService(TextDispatcher.getInstance(), market.getSystemManagerName());
            market.memberLogout(userName);
            assert false;
            MarketConfig.SERVICES_FILE_NAME="config.txt";
        }
        catch(Exception e){
            Assertions.assertEquals("Failed to init supply service",e.getMessage());
            MarketConfig.SERVICES_FILE_NAME="config.txt";
        }
    }


   @Test
    @DisplayName("notification test- close shop")
    public void closeShop() {
       try {
           // shop manager register
           registerVisitor("notificationTestUser", shopOwnerPassword);
           loginMember("notificationTestUser", shopOwnerPassword);
           market.openNewShop("notificationTestUser", "notificationShop");
           itemAdded = market.addItemToShopItem("notificationTestUser", ItemName, productPrice, Item.Category.electricity, "", new ArrayList<>(), productAmount, "notificationShop");
           market.closeShop("notificationTestUser", "notificationShop");
           logoutMember("notificationTestUser");
           assert true;
       } catch (Exception e) {
           assert false;
       }
       try {
           logoutMember("notificationTestUser");
       } catch (MarketException ex) {
           System.out.println(ex.getMessage());
       }
   }


    @Test
    @DisplayName("notification test- appoint owner with delayed notification")
    public void AppointOwnerNotificationTest() {
        try {

            String appointedName = "appointedNameTest1";
            List<String> nots= new ArrayList<>();
            RealTimeNotifications not= new RealTimeNotifications();
            setUpAppointOwner(appointedName,not);
            nots.addAll(readDelayedMessages(appointedName));
            boolean found = false;
            for(String message : nots){
                if(message.equals(not.getMessage().split("\n")[0])){
                    found=true;
                }
            }
            Assertions.assertTrue(found);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("notification test- close shop with delayed notification")
    public void closeShopDelayed() {
        try {

            String appointedName = "appointedNameTest2";
            String owner = "ownerNameTest2";
            List<String> nots= new ArrayList<>();
            RealTimeNotifications not= new RealTimeNotifications();
            setUpCloseShop(owner,appointedName,not);
            nots.addAll(readDelayedMessages(appointedName));
            boolean found = false;
            for(String message : nots){
                if(message.equals(not.getMessage().split("\n")[0])){
                    found=true;
                }
            }
            Assertions.assertTrue(found);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("notification test- close shop with real time notification")
    public void closeShopRealTime() {
        try {

            String appointedName = "appointedNameTest3";
            String owner = "ownerNameTest3";
            List<String> nots= new ArrayList<>();
            RealTimeNotifications not= new RealTimeNotifications();
            setUpCloseShop(owner,appointedName,not);
            nots.addAll(readRealTimeMessages(owner));
            boolean found = false;
            for(String message : nots){
                if(message.equals(not.getMessage().split("\n")[0])){
                    found=true;
                }
            }
            Assertions.assertTrue(found);
        } catch (Exception e) {
            assert false;
        }
    }

    public void setUpCloseShop(String owner,String appointedName, RealTimeNotifications not) throws MarketException {

        String testShopName = "ShopName2";
        List<String> nots = new ArrayList<>();
        not.createShopClosedMessage(testShopName);
        // shop manager register
        registerVisitor(owner, shopOwnerPassword);
        registerVisitor(appointedName, shopOwnerPassword);
        loginMember(owner, shopOwnerPassword);
        market.openNewShop(owner, testShopName);
        market.appointShopOwner(owner,appointedName,testShopName);
        market.closeShop(owner,testShopName);
        market.memberLogout(owner);
    }


    public void setUpAppointOwner(String appointedName, RealTimeNotifications not) throws MarketException {
        String owner="notificationTestUser3";
        String testShopName="testShopName3";
        not.createNewOwnerMessage(owner,appointedName,testShopName);
        registerVisitor(owner, shopOwnerPassword);
        registerVisitor(appointedName, shopOwnerPassword);
        loginMember(owner, shopOwnerPassword);
        market.openNewShop(owner, testShopName);
        market.appointShopOwner(owner,appointedName,testShopName);
        market.memberLogout(owner);
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

    private List<String> readDelayedMessages(String name) {

        try {
            List<String> nots= new ArrayList<>();
            File myObj = new File(getConfigDir() + name+".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.isEmpty())
                    continue;
                nots.add(data);
            }
            return nots;

        } catch (Exception e) {
            return new ArrayList<>();
        }

    }
    private List<String> readRealTimeMessages(String name) {

        try {
            List<String> nots= new ArrayList<>();
            File myObj = new File(getConfigRealTimeDir() + name+".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.isEmpty())
                    continue;
                nots.add(data);
            }
            return nots;

        } catch (Exception e) {
            return new ArrayList<>();
        }

    }
    private String getConfigDir() {
        String dir = System.getProperty("user.dir");
        dir += "/notifications/Delayed/";
        return dir;
    }
    private String getConfigRealTimeDir() {
        String dir = System.getProperty("user.dir");
        dir += "/notifications/Real_Time/";
        return dir;
    }
}
