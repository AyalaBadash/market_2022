package RobustnessTesting;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.DataSourceConfigReader;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Payment.CreditCard;
import com.example.server.businessLayer.Payment.WSEPPaymentServiceAdapter;
import com.example.server.businessLayer.Publisher.TextDispatcher;
import com.example.server.businessLayer.Supply.Address;
import com.example.server.businessLayer.Supply.WSEPSupplyServiceAdapter;
import com.example.server.serviceLayer.FacadeObjects.ItemFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopFacade;
import com.example.server.serviceLayer.FacadeObjects.ShoppingCartFacade;
import com.example.server.serviceLayer.FacadeObjects.VisitorFacade;
import com.example.server.serviceLayer.MarketService;
import com.example.server.serviceLayer.PurchaseService;
import com.example.server.serviceLayer.ResponseT;
import com.example.server.serviceLayer.UserService;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RobustnessTests {

    CreditCard creditCard;
    Address address;
    String managerName = "";
    String managerPassword = "";
    String userName = "userTest";
    String password = "password";
    String shopManagerName = "shaked";
    String shopManagerPassword = "shaked1234";
    String shopName = "kolbo";
    Double productAmount;
    Double productPrice;
    MarketService marketService;
    UserService userService;
    PurchaseService purchaseService;

    static boolean useData;

    @BeforeAll
    public void reset() {
        useData = MarketConfig.USING_DATA;
        MarketConfig.IS_TEST_MODE = true;
        MarketConfig.USING_DATA = true;
        Market.restartMarket();
        productAmount = 100.0;
        productPrice = 1.2;
        creditCard = new CreditCard("1234567890", "07", "2026", "205", "Bar Damri", "208915751");
        address = new Address("Bar Damri", "Atad 3", "Beer Shaba", "Israel", "8484403");
        loadAdminName();
    }

    @AfterAll
    public static void setUseData() {

        MarketConfig.IS_TEST_MODE = false;
    }

    private void loadAdminName() {
        try {
            String dir = MarketConfig.IS_MAC ? "/config/" : "\\config\\";
            File myObj = new File(System.getProperty("user.dir") + dir + "config.txt");
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
        marketService = MarketService.getInstance();
        marketService.firstInitMarket(userName, password);
        Market market = Market.getInstance();
        purchaseService = PurchaseService.getInstance();
        userService = UserService.getInstance();

        // shop manager register
        ResponseT<VisitorFacade> visitor = userService.guestLogin();
        userService.register(shopManagerName, shopManagerPassword);
        userService.memberLogin(shopManagerName, shopManagerPassword);
        userService.validateSecurityQuestions(shopManagerName, new ArrayList<>(), visitor.getValue().getName());
        // open shop
        marketService.openNewShop(shopManagerName, shopName);
        List<String> keywords = new ArrayList<>();
        keywords.add("in sale");
        marketService.addItemToShop(shopManagerName, "milk", productPrice, Item.Category.general,
                "soy", keywords, productAmount, shopName);
        marketService.addItemToShop(shopManagerName, "chocolate", productPrice, Item.Category.general,
                "soy", keywords, productAmount, shopName);
        creditCard = new CreditCard("1234567890", "5", "2024", "555", "Ido livne", "204534839");
        try {
            VisitorFacade manag = userService.guestLogin().getValue();
            userService.memberLogin(userName, password);
            userService.validateSecurityQuestions(userName, new ArrayList<>(), manag.getName());
        } catch (Exception e) {
            String str = e.getMessage();
        }
    }

    @Test
    @DisplayName("Payment service null check")
    public void PaymentService() {
        try {
            marketService.setPaymentService(null, managerName);
            ResponseT<VisitorFacade> visitor = userService.guestLogin();
            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
            ItemFacade chocolate = res.getValue().get(0);
            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
            double buyingAmount = 10;
            purchaseService.addItemToShoppingCart(chocolate, buyingAmount, visitor.getValue().getName());
            purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
            marketService.setPaymentService(WSEPPaymentServiceAdapter.getinstance(), managerName);
            assert true;
        } catch (Exception e) {
            marketService.setPaymentService(WSEPPaymentServiceAdapter.getinstance(), managerName);
            assert false;
        }
    }

    @Test
    @DisplayName("Payment service is good check")
    public void PaymentServiceGood() {
        try {
            ResponseT<VisitorFacade> visitor = userService.guestLogin();
            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
            ItemFacade chocolate = res.getValue().get(0);
            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
            double buyingAmount = 10;
            purchaseService.addItemToShoppingCart(chocolate, buyingAmount, visitor.getValue().getName());
            ResponseT<ShoppingCartFacade> ret= purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
            Assertions.assertTrue(ret.getValue().getCart().isEmpty());
        } catch (Exception e) {
            assert false;
        }
    }
    @Test
    @DisplayName("Supply service null check")
    public void SupplyService() {
        try {
            marketService.setSupplyService(null, managerName);
            ResponseT<VisitorFacade> visitor = userService.guestLogin();
            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
            ItemFacade chocolate = res.getValue().get(0);
            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
            double buyingAmount = 10;
            purchaseService.addItemToShoppingCart(chocolate, buyingAmount, visitor.getValue().getName());
            purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
            marketService.setSupplyService(WSEPSupplyServiceAdapter.getInstance(), managerName);
            assert true;
        } catch (Exception e) {
            marketService.setSupplyService(WSEPSupplyServiceAdapter.getInstance(), managerName);
            assert false;
        }
    }
    @Test
    @DisplayName("Supply service is good check")
    public void SupplyServiceGood() {
        try {
            ResponseT<VisitorFacade> visitor = userService.guestLogin();
            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
            ItemFacade chocolate = res.getValue().get(0);
            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
            double buyingAmount = itemAmount;
            purchaseService.addItemToShoppingCart(chocolate, buyingAmount, visitor.getValue().getName());
            ResponseT<ShoppingCartFacade> ret= purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
            marketService.updateShopItemAmount(shopManagerName,chocolate,itemAmount,shopName);
            Assertions.assertTrue(ret.getValue().getCart().isEmpty());
        } catch (Exception e) {
            assert false;
        }
    }
    @Test
    @DisplayName("Check cart saved when acquisition is canceled.")
    public void CheckCartAfterBuyingCanceling() {
        try {
            ResponseT<VisitorFacade> visitor = userService.guestLogin();
            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
            ItemFacade chocolate = res.getValue().get(0);
            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
            double buyingAmount = 10;
            purchaseService.addItemToShoppingCart(chocolate, buyingAmount + 1, visitor.getValue().getName());
            purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
            ResponseT<ShoppingCartFacade> response = purchaseService.showShoppingCart(visitor.getValue().getName());
            assert !(response.getValue().getCart().isEmpty());
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("Publish service null check")
    public void PublishService() {
        try {

            marketService.setPublishService(null, managerName);
            ResponseT<VisitorFacade> visitor = userService.guestLogin();
            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
            ItemFacade chocolate = res.getValue().get(0);
            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
            double buyingAmount = 10;
            purchaseService.addItemToShoppingCart(chocolate, buyingAmount, visitor.getValue().getName());
            purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
            marketService.setPublishService(TextDispatcher.getInstance(), managerName);
            assert true;
        } catch (Exception e) {
            marketService.setPublishService(TextDispatcher.getInstance(), managerName);
            assert false;
        }
    }

    @Test
    @DisplayName("Publish service is good check")
    public void PublishServiceGood() {
        try {
            ResponseT<VisitorFacade> visitor = userService.guestLogin();
            ResponseT<ShopFacade> shop = marketService.getShopInfo(shopManagerName, shopName);
            ResponseT<List<ItemFacade>> res = marketService.searchProductByName("chocolate");
            ItemFacade chocolate = res.getValue().get(0);
            Double itemAmount = shop.getValue().getItemsCurrentAmount().get(chocolate.getId());
            double buyingAmount = 10;
            purchaseService.addItemToShoppingCart(chocolate, buyingAmount, visitor.getValue().getName());
            ResponseT<ShoppingCartFacade> ret= purchaseService.buyShoppingCart(visitor.getValue().getName(), productPrice * buyingAmount, creditCard, address);
            Assertions.assertTrue(ret.getValue().getCart().isEmpty());
        } catch (Exception e) {
            assert false;
        }
    }
    @Test
    @DisplayName("Market init from wrong file name file")
    public void initTest() {
        String name=MarketConfig.DATA_FILE_NAME;
        try {

            MarketConfig.DATA_FILE_NAME = "noFile.txt";
            MarketConfig.USING_DATA=true;
            boolean res = marketService.isServerInit().isErrorOccurred();
            MarketConfig.DATA_FILE_NAME =name;
            assert res;
        } catch (Exception e) {
            MarketConfig.DATA_FILE_NAME = name;
            assert false;
        }
    }
    @Test
    @DisplayName("Market init from wrong file name file")
    public void initTestNewUserName() {
        try {
            MarketConfig.DATA_FILE_NAME = "noFile.txt";
            boolean res = marketService.isServerInit().isErrorOccurred();
            MarketConfig.DATA_FILE_NAME = "Data.txt";
            assert res;
        } catch (Exception e) {
            MarketConfig.DATA_FILE_NAME = "Data.txt";
            assert false;
        }
    }
    @Test
    @DisplayName("System init from no existing data source file. should not continue the market init.")
    public void initFromDataSourceNoFile() throws MarketException, FileNotFoundException {
        String name=MarketConfig.DATA_SOURCE_FILE_NAME;
        try{

            Market market=Market.getInstance();
            MarketConfig.DATA_SOURCE_FILE_NAME ="noName.txt";
            DataSourceConfigReader.resetInstance();
            market.isInit();
            MarketConfig.DATA_SOURCE_FILE_NAME=name;
            assert false;
        }
        catch(Exception e){
            MarketConfig.DATA_SOURCE_FILE_NAME=name;
            Assertions.assertEquals("Data source config file not found.",e.getMessage());
        }
    }
    @Test
    @DisplayName("System init from data source file with no password. should not continue the market init.")
    public void initFromDataSourceBadArgsPass() {
        String name=MarketConfig.DATA_SOURCE_FILE_NAME;
        try{
            Market market= Market.getInstance();
            MarketConfig.DATA_SOURCE_FILE_NAME =MarketConfig.MISS_PASSWORD_DATA_SOURCE_FILE_NAME;
            DataSourceConfigReader.resetInstance();
            market.isInit();
            MarketConfig.DATA_SOURCE_FILE_NAME=name;
            assert false;
        }
        catch(Exception e){
            MarketConfig.DATA_SOURCE_FILE_NAME=name;
            Assertions.assertEquals("Missing password in data source config file.",e.getMessage());
        }
    }
    @Test
    @DisplayName("System init from data source good scenario.")
    public void initFromDataSource() {
        try{
            Market market= Market.getInstance();
            DataSourceConfigReader.resetInstance();
            market.isInit();
            assert true;
        }
        catch(Exception e){
            assert false;
        }
    }
}
