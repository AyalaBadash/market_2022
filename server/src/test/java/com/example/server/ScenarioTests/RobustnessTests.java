//package com.example.server.ScenarioTests;
//
//import com.example.server.businessLayer.Market.Item;
//import com.example.server.businessLayer.Market.Market;
//import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
//import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
//import com.example.server.businessLayer.Payment.CreditCard;
//import com.example.server.businessLayer.Payment.WSEPPaymentServiceAdapter;
//import com.example.server.businessLayer.Publisher.TextDispatcher;
//import com.example.server.businessLayer.Supply.Address;
//import com.example.server.businessLayer.Supply.WSEPSupplyServiceAdapter;
//import com.example.server.serviceLayer.*;
//import com.example.server.serviceLayer.FacadeObjects.ItemFacade;
//import com.example.server.serviceLayer.FacadeObjects.ShopFacade;
//import com.example.server.serviceLayer.FacadeObjects.ShoppingCartFacade;
//import com.example.server.serviceLayer.FacadeObjects.VisitorFacade;
//import org.junit.jupiter.api.*;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class RobustnessTests {
//
//    CreditCard creditCard;
//    Address address;
//    String managerName="";
//    String managerPassword="";
//    String userName = "userTest";
//    String password = "password";
//    String shopManagerName = "shaked";
//    String shopManagerPassword = "password";
//    String shopName = "kolbo";
//    Double productAmount;
//    Double productPrice;
//    MarketService marketService;
//    UserService userService;
//    PurchaseService purchaseService;
//
//
//
//    @BeforeEach
//    public void init()  {
//
//        Market.restartMarket();
//        productAmount = 3.0;
//        productPrice = 1.2;
//        creditCard = new CreditCard("1234567890", "07", "2026", "205", "Bar Damri", "208915751");
//        address = new Address("Bar Damri", "Atad 3", "Beer Shaba", "Israel", "8484403");
//        loadAdminName();
//
//    }
//
//    private void loadAdminName() {
//        try {
//            String dir=MarketConfig.IS_MAC ? "/config/" : "\\config\\";
//            File myObj = new File(System.getProperty("user.dir") + dir + "config.txt");
//            Scanner myReader = new Scanner(myObj);
//            while (myReader.hasNextLine()) {
//                String data = myReader.nextLine();
//                String[] vals = data.split("::");
//                if (!vals[0].equals("PaymentService") & !vals[0].equals("SupplyService") & !vals[0].equals("Publisher")) {
//
//                    managerName = vals[0];
//                    managerPassword = vals[1];
//                }
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        marketService= MarketService.getInstance();
//        marketService.firstInitMarket(userName,password);
//        Market market=Market.getInstance();
//        purchaseService=PurchaseService.getInstance();
//        userService= UserService.getInstance();
//
//        // shop manager register
//        ResponseT<VisitorFacade> visitor= userService.guestLogin();
//        userService.register(shopManagerName, shopManagerPassword);
//        userService.memberLogin(shopManagerName, shopManagerPassword);
//        userService.validateSecurityQuestions(shopManagerName, new ArrayList<>(), visitor.getValue().getName());
//        // open shop
//        marketService.openNewShop(shopManagerName, shopName);
//        productAmount = 3.0;
//        productPrice = 1.2;
//        List<String> keywords = new ArrayList<>();
//        keywords.add("in sale");
//        marketService.addItemToShop(shopManagerName, "milk", productPrice, Item.Category.general,
//                "soy",keywords , productAmount,shopName);
//        marketService.addItemToShop(shopManagerName, "chocolate", productPrice, Item.Category.general,
//                "soy",keywords , productAmount,shopName);
//        creditCard = new CreditCard("1234567890", "5","2024", "555","Ido livne","204534839");
//        try {
//            VisitorFacade manag = userService.guestLogin().getValue();
//            userService.memberLogin(userName, password);
//            userService.validateSecurityQuestions(userName, new ArrayList<>(), manag.getName());
//        }catch (Exception e){
//            String str= e.getMessage();
//        }
//    }
//
//        @Test
//    @DisplayName("Payment service null check")
//    public void PaymentService() {
//        try {
//            marketService.setPaymentService(null,managerName);
//            ResponseT<VisitorFacade> visitor = userService.guestLogin();
//            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
//            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
//            ItemFacade chocolate = res.getValue().get(0);
//            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
//            double buyingAmount = itemAmount;
//            purchaseService.addItemToShoppingCart(chocolate, buyingAmount, visitor.getValue().getName());
//            purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
//            marketService.setPaymentService(WSEPPaymentServiceAdapter.getinstance(),userName);
//            assert true;
//        } catch (Exception e) {
//            marketService.setPaymentService(WSEPPaymentServiceAdapter.getinstance(),userName);
//            assert false;
//        }
//    }
//    @Test
//    @DisplayName("Supply service null check")
//    public void SupplyService() {
//        try {
//            marketService.setSupplyService(null,managerName);
//            ResponseT<VisitorFacade> visitor = userService.guestLogin();
//            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
//            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
//            ItemFacade chocolate = res.getValue().get(0);
//            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
//            double buyingAmount = itemAmount;
//            purchaseService.addItemToShoppingCart(chocolate, buyingAmount, visitor.getValue().getName());
//            purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
//            marketService.setSupplyService(WSEPSupplyServiceAdapter.getInstance(),userName);
//            assert true;
//        } catch (Exception e) {
//            marketService.setSupplyService(WSEPSupplyServiceAdapter.getInstance(),userName);
//            assert false;
//        }
//    }
//
//    @Test
//    @DisplayName("Check cart waved when acquisition is canceled.")
//    public void CheckCartAfterBuyingCanceling() {
//        try {
//            ResponseT<VisitorFacade> visitor = userService.guestLogin();
//            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
//            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
//            ItemFacade chocolate = res.getValue().get(0);
//            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
//            double buyingAmount = itemAmount;
//            purchaseService.addItemToShoppingCart(chocolate, buyingAmount+1, visitor.getValue().getName());
//            purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
//            ResponseT<ShoppingCartFacade> response= purchaseService.showShoppingCart(visitor.getValue().getName());
//            assert !(response.getValue().getCart().isEmpty());
//        } catch (Exception e) {
//            assert false;
//        }
//    }
//    @Test
//    @DisplayName("Publish service null check")
//    public void PublishService() {
//        try {
//            ResponseT<VisitorFacade> visitor = userService.guestLogin();
//            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
//            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
//            ItemFacade chocolate = res.getValue().get(0);
//            Response r = marketService.setItemCurrentAmount ( shopManagerName, chocolate, 3, shopName );
//            shop = marketService.getShopInfo(shopManagerName, shopName);
//            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
//            double buyingAmount = itemAmount;
//            purchaseService.addItemToShoppingCart(chocolate, buyingAmount, visitor.getValue().getName());
//            marketService.setPublishService(null,managerName);
//            purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
//            marketService.setPublishService(TextDispatcher.getInstance(),managerName);
//            assert true;
//        } catch (Exception e) {
//            marketService.setPublishService(TextDispatcher.getInstance(),managerName);
//            assert false;
//        }
//    }
//    @Test
//    @DisplayName("Market init file")
//    public void initTest() {
//        try {
//            MarketConfig.DATA_FILE_NAME="noFile.txt";
//            boolean res=marketService.isServerInit().isErrorOccurred();
//            MarketConfig.DATA_FILE_NAME="Data.txt";
//            assert res;
//        } catch (Exception e) {
//            MarketConfig.DATA_FILE_NAME="Data.txt";
//            assert false;
//        }
//    }
//}
