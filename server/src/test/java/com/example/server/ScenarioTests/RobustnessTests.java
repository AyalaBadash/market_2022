package com.example.server.ScenarioTests;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Payment.CreditCard;
import com.example.server.businessLayer.Publisher.TextDispatcher;
import com.example.server.businessLayer.Supply.Address;
import com.example.server.serviceLayer.FacadeObjects.ItemFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopFacade;
import com.example.server.serviceLayer.FacadeObjects.VisitorFacade;
import com.example.server.serviceLayer.MarketService;
import com.example.server.serviceLayer.PurchaseService;
import com.example.server.serviceLayer.ResponseT;
import com.example.server.serviceLayer.UserService;
import org.junit.jupiter.api.*;
import org.yaml.snakeyaml.error.Mark;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RobustnessTests {

    CreditCard creditCard;
    Address address;
    String managerName="";
    String managerPassword="";
    String userName = "userTest";
    String password = "passTest";
    String shopManagerName = "shaked";
    String shopManagerPassword = "shaked1234";
    String shopName = "kolbo";
    Double productAmount;
    Double productPrice;
    MarketService marketService;
    UserService userService;
    PurchaseService purchaseService;


    @BeforeEach
    public void init() throws MarketException {
        productAmount = 3.0;
        productPrice = 1.2;
        creditCard = new CreditCard("1234567890", "07", "2026", "205", "Bar Damri", "208915751");
        address = new Address("Bar Damri", "Atad 3", "Beer Shaba", "Israel", "8484403");
        loadAdminName();

    }

    private void loadAdminName() {
        try {

            File myObj = new File(System.getProperty("user.dir") + "/config/" + "config.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] vals = data.split("::");
                if (!vals[0].equals("PaymentService") & !vals[0].equals("SupplyService") & !vals[0].equals("Publisher")) {

                    managerName = vals[0];
                    managerPassword = vals[1];
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        marketService= MarketService.getInstance();
        marketService.firstInitMarket(true);
        Market market=Market.getInstance();
        purchaseService=PurchaseService.getInstance();
        userService= UserService.getInstance();

        // shop manager register
        ResponseT<VisitorFacade> visitor= userService.guestLogin();
        userService.register(shopManagerName, shopManagerPassword);
        userService.memberLogin(shopManagerName, shopManagerPassword);
        userService.validateSecurityQuestions(shopManagerName, new ArrayList<>(), visitor.getValue().getName());
        // open shop
        marketService.openNewShop(shopManagerName, shopName);
        productAmount = 3.0;
        productPrice = 1.2;
        List<String> keywords = new ArrayList<>();
        keywords.add("in sale");
        marketService.addItemToShop(shopManagerName, "milk", productPrice, Item.Category.general,
                "soy",keywords , productAmount,shopName);
        marketService.addItemToShop(shopManagerName, "chocolate", productPrice, Item.Category.general,
                "soy",keywords , productAmount,shopName);
        creditCard = new CreditCard("1234567890", "5","2024", "555","Ido livne","204534839");
    }

        @Test
    @DisplayName("Payment service check")
    public void PaymentService() {
        try {
            marketService.setPaymentService(null,managerName);
            ResponseT<VisitorFacade> visitor = userService.guestLogin();
            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
            ItemFacade chocolate = res.getValue().get(0);
            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
            double buyingAmount = itemAmount;
            purchaseService.addItemToShoppingCart(chocolate, buyingAmount,shop.getValue().getShopName(), visitor.getValue().getName());
            purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }
    @Test
    @DisplayName("Supply service check")
    public void SupplyService() {
        try {
            marketService.setSupplyService(null,managerName);
            ResponseT<VisitorFacade> visitor = userService.guestLogin();
            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
            ItemFacade chocolate = res.getValue().get(0);
            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
            double buyingAmount = itemAmount;
            purchaseService.addItemToShoppingCart(chocolate, buyingAmount,shop.getValue().getShopName(), visitor.getValue().getName());
            purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }
    @Test
    @DisplayName("Publish service check")
    public void PublishService() {
        try {
            marketService.setPublishService(null,managerName);
            ResponseT<VisitorFacade> visitor = userService.guestLogin();
            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
            ItemFacade chocolate = res.getValue().get(0);
            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
            double buyingAmount = itemAmount;
            purchaseService.addItemToShoppingCart(chocolate, buyingAmount,shop.getValue().getShopName(), visitor.getValue().getName());
            purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }
    @Test
    @DisplayName("Market init file")
    public void initTest() {
        try {
            marketService.firstInitMarket(managerName,managerPassword,"config.txt", "noName.txt");
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }
}