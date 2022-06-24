package com.example.server.ScenarioTests;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.DataSourceConfigReader;
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
import com.example.server.serviceLayer.Notifications.Notification;
import com.example.server.serviceLayer.Notifications.RealTimeNotifications;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServicesTests {
    static PaymentServiceProxy paymentServiceProxy;
    static SupplyServiceProxy supplyServiceProxy;
    String userName = "u1";
    String password = "p1";
    String ItemName= "item1";
    Item itemAdded;
    int productAmount=20;
    Double productPrice=30.0;
    String shopOwnerName = "bar";
    String shopOwnerPassword = "password";
    String memberName = "bar1";
    String memberPassword = "password";
    String loggedInmemberName = "bar2";

    String loggedInmemberPassword = "password";
    String shopName = "store";
    TextDispatcher textDispatcher = TextDispatcher.getInstance();
    static CreditCard creditCard;
    static Address address;
    static Market market ;
    Visitor visitor;
    static boolean useData;
    static String ManName;
    static String ManPass;


    @BeforeAll
    public static void initBefore(){
        paymentServiceProxy = new PaymentServiceProxy(WSEPPaymentServiceAdapter.getinstance(), true);
        supplyServiceProxy = new SupplyServiceProxy(WSEPSupplyServiceAdapter.getInstance(), true);
        creditCard = new CreditCard("1234567890", "07", "2026", "205", "Bar Damri", "208915751");
        address = new Address("Bar Damri", "Atad 3", "Beer Shaba", "Israel", "8484403");
        market = Market.getInstance();
        useData=MarketConfig.USING_DATA;
        MarketConfig.USING_DATA=true;
        MarketConfig.IS_TEST_MODE=true;
        Visitor visitor= market.guestLogin();
        try {
            String[] dets = market.resetSystemManager().split(":");
            ManName = dets[0];
            ManPass = dets[1];
        }catch(Exception e){}
        try{
            market.isInit ( );
        }catch(MarketException e){
            System.out.println (e.getMessage () );
        }
    }
    @AfterAll
    public static void setUseData(){

        MarketConfig.USING_DATA=useData;
        MarketConfig.IS_TEST_MODE=false;
        market.restoreSytemManager(ManName,ManPass);
    }
    @BeforeEach
    public void init() {
//        paymentServiceProxy = new PaymentServiceProxy(WSEPPaymentServiceAdapter.getinstance(), true);
//        supplyServiceProxy = new SupplyServiceProxy(WSEPSupplyServiceAdapter.getInstance(), true);
//        creditCard = new CreditCard("1234567890", "07", "2026", "205", "Bar Damri", "208915751");
//        address = new Address("Bar Damri", "Atad 3", "Beer Shaba", "Israel", "8484403");
//        market = Market.getInstance();
//
//        Visitor visitor= market.guestLogin();
//        try {
//            market.isInit ( );
//        }catch(MarketException e){}
        try{
            market.memberLogin(userName, password);
            market.validateSecurityQuestions(userName,new ArrayList<>(), visitor.getName());
        }catch (Exception e){}
    }


    @Test
    @Order(0)
    @DisplayName("Payment service- successful payment action")
    public void PaymentHandler() {
        try {
            int result = paymentServiceProxy.pay(creditCard);
            Assertions.assertNotEquals(result, -1);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @Order(1)
    @DisplayName("Supply service- successful supply action")
    public void SupplyHandler() {
        try {
            int result = supplyServiceProxy.supply(address);
            Assertions.assertNotEquals(result, -1);
        } catch (Exception e) {
            assert false;
        }
    }


    @Test
    @Order(2)
    @DisplayName("Payment service- successful cancel pay action")
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
    @Order(3)
    @DisplayName("Supply service- successful cancel supply action")
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
    @Order(4)
    @DisplayName("Payment service- check error message without crash when service falls")
    public void PaymentServiceFalls() throws MarketException {
        try {
            try {

                Visitor visitor = market.guestLogin();
                market.memberLogin(userName, password);
                market.restoreSytemManager(userName,password);
                market.validateSecurityQuestions(userName, new ArrayList<>(), visitor.getName());
            }catch (Exception e){
                String str= e.getMessage();
            }
            market.setPaymentServiceAddress("", userName);
            paymentServiceProxy.pay(creditCard);
            market.setPaymentServiceAddress(MarketConfig.WSEP_ADDRESS, userName);
            assert false;
        } catch (Exception e) {
            market.setPaymentServiceAddress(MarketConfig.WSEP_ADDRESS, userName);
            Assertions.assertEquals("Error2",e.getMessage());

        }
    }

    @Test
    @Order(5)
    @DisplayName("Supply service- check error message without crash when service falls")
    public void SupplyServiceFalls() throws MarketException {
        try {
            market.setSupplyServiceAddress("", userName);
            supplyServiceProxy.supply(address);
            market.setSupplyServiceAddress(MarketConfig.WSEP_ADDRESS, market.getSystemManagerName());
            assert false;
         } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),"Error1");
            market.setSupplyServiceAddress(MarketConfig.WSEP_ADDRESS, market.getSystemManagerName());

        }
    }
    @Test
    @Order(6)
    @DisplayName("Dispatcher service- successful add action")
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
    @Order(7)
    @DisplayName("Dispatcher service- try add twice a user should not allow.")
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
    @Order(8)
    @DisplayName("Dispatcher service- successful remove action")
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
    @Order(9)
    @DisplayName("Dispatcher service- try to remove user without adding him before should not allow.")
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
    @Order(10)
    @DisplayName("Dispatcher service- successful send new message action")
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
    @Order(11)
    @DisplayName("System init from file, check the file is loaded to the system.")
    public void initFromFile(){
        try{
            UserController userController= UserController.getInstance();
            List<String> list= new ArrayList<>();
            try {
                market.loadDataFromFile();
            }
            catch(Exception e){

            }
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
    @Order(12)
    @DisplayName("System init from no existing file. should not continue the market init.")
    public void initFromNoFile(){
        try{
            MarketConfig.SERVICES_FILE_NAME="noName.txt";
            market.isInit();
            market.setPublishService(TextDispatcher.getInstance(), market.getSystemManagerName());
            market.memberLogout(userName);
            MarketConfig.SERVICES_FILE_NAME="config.txt";
            assert false;
        }
        catch(Exception e){
            MarketConfig.SERVICES_FILE_NAME="config.txt";
            assert true;
        }
    }
    @Test
    @Order(13)
    @DisplayName("System init from no existing data source file. should not continue the market init.")
    public void initFromDataSourceNoFile() throws MarketException, FileNotFoundException {
        String name=MarketConfig.DATA_SOURCE_FILE_NAME;
        try{

            MarketConfig.DATA_SOURCE_FILE_NAME ="noName.txt";
            DataSourceConfigReader.resetInstance();
            market.isInit();
            MarketConfig.SERVICES_FILE_NAME=name;
            assert false;
        }
        catch(Exception e){
            MarketConfig.SERVICES_FILE_NAME=name;
            Assertions.assertEquals("Data source config file not found.",e.getMessage());
        }
    }

    @Test
    @Order(14)
    @DisplayName("System init from data source file with bad arguments. should not continue the market init.")
    public void initFromDataSourceBadArgs() {
        String name=MarketConfig.DATA_SOURCE_FILE_NAME;
        try{

            MarketConfig.DATA_SOURCE_FILE_NAME =MarketConfig.MISS_DATA_SOURCE_FILE_NAME;
            DataSourceConfigReader.resetInstance();
            market.isInit();
            MarketConfig.SERVICES_FILE_NAME=name;
            assert false;
        }
        catch(Exception e){
            MarketConfig.SERVICES_FILE_NAME=name;
            Assertions.assertEquals("Missing username in data source config file.",e.getMessage());
        }
    }

   @Test
   @Order(15)
    @DisplayName("Notification test- successful close shop action")
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
    @Order(16)
    @DisplayName("Notification test- appoint owner with delayed notification, check message exists.")
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
    @Order(17)
    @DisplayName("Notification test- close shop with delayed notification, check message exists.")
    public void closeShopDelayed() {
        try {

            String appointedName = "appointedNameTest2";
            String testShopName = "ShopName2";
            String owner = "ownerNameTest2";
            List<String> nots= new ArrayList<>();
            RealTimeNotifications not= new RealTimeNotifications();
            setUpCloseShop(owner,appointedName,not,testShopName);
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
    @Order(18)
    @DisplayName("Notification test- close shop with real time notification, check message exists.")
    public void closeShopRealTime() {
        try {

            String appointedName = "appointedNameTest3";
            String testShopName = "ShopName3";
            String owner = "ownerNameTest3";
            List<String> nots= new ArrayList<>();
            RealTimeNotifications not= new RealTimeNotifications();
            setUpCloseShop(owner,appointedName,not,testShopName);
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

    @Test
    @Order(19)
    @DisplayName("Notification test- close shop with real time notification, check message exists.")
    public void shopManagerStatistics() {
        try {

            String appointedName = "appointedNameTest4";
            String testShopName = "ShopName4";
            String owner = "ownerNameTest4";
            List<String> nots= new ArrayList<>();
            RealTimeNotifications not= new RealTimeNotifications();
            setUpCloseShop(owner,appointedName,not,testShopName);
            nots.addAll(readRealTimeMessages(userName));
            boolean found = false;
            for(String message : nots){
                if(message.contains("Statistics")){
                    found=true;
                }
            }
            Assertions.assertTrue(found);
        } catch (Exception e) {
            assert false;
        }
    }
    @Test
    @Order(20)
    @DisplayName("Notification test- close shop with real time notification, not system manager")
    public void shopManagerStatisticsNoManager() {
        try {

            String appointedName = "appointedNameTest5";
            String testShopName = "ShopName5";
            String owner = "ownerNameTest5";
            List<String> nots= new ArrayList<>();
            RealTimeNotifications not= new RealTimeNotifications();
            setUpCloseShop(owner,appointedName,not,testShopName);
            nots.addAll(readRealTimeMessages(userName));
            boolean found = false;
            for(String message : nots){
                if(message.contains("Statistics")){
                    found=true;
                }
            }
            Assertions.assertFalse(found);
        } catch (Exception e) {
            assert true;
        }
    }

    public void setUpCloseShop(String owner,String appointedName, RealTimeNotifications not,String testShopName) throws MarketException {

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
