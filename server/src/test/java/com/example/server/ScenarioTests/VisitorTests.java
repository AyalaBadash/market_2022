package com.example.server.ScenarioTests;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.CreditCard;
import com.example.server.businessLayer.ExternalServices.PaymentMock;
import com.example.server.businessLayer.ExternalServices.SupplyMock;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.Users.Visitor;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VisitorTests {
    Market market;
    String userName = "userTest";
    String password = "passTest";
    PaymentMock paymentService = new PaymentMock();
    SupplyMock supplyService = new SupplyMock();
    String shopManagerName = "shaked";
    String shopManagerPassword = "shaked1234";
    String shopName = "kolbo";
    Double productAmount;
    Double productPrice;
    CreditCard creditCard;
    Address address;

    @BeforeAll
    public void setUp() {
        try {
            market = Market.getInstance();
            if (market.getPaymentService() == null)
                market.firstInitMarket(paymentService, supplyService, userName, password);
            // shop manager register
            Visitor visitor = market.guestLogin();
            market.register(shopManagerName, shopManagerPassword);
            List<String> questions = market.memberLogin(shopManagerName, shopManagerPassword);
            market.validateSecurityQuestions(shopManagerName, new ArrayList<>(), visitor.getName());
            // open shop
            market.openNewShop(shopManagerName, shopName);
            productAmount = 3.0;
            productPrice = 1.2;
            List<String> keywords = new ArrayList<>();
            keywords.add("in sale");
            market.addItemToShop(shopManagerName, "milk", productPrice, Item.Category.general,
                    "soy",keywords , productAmount,shopName);
            market.addItemToShop(shopManagerName, "chocolate", productPrice, Item.Category.general,
                    "soy",keywords , productAmount,shopName);
            creditCard = new CreditCard("124","13/5" , "555");
            address = new Address("Tel Aviv", "Super" , "1");

        } catch (Exception Ignored) {
        }
    }

    @Test
    @DisplayName("valid guest login")
    public void guestLoginValid() throws Exception {
//        initMarket();
        try {
            Visitor visitor = market.guestLogin();
            assert !(visitor == null);
            Assertions.assertNotNull(visitor.getCart());
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("guest leaves the market")
    public void guestExitMarket() throws Exception {
        Visitor visitor = market.guestLogin();
        try {
            market.visitorExitSystem(visitor.getName());
            Visitor visitor2 = market.guestLogin();
            assert !(visitor2.getName().equals(visitor.getName()));

        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("register taken name")
    public void registerTest() {
        try {
            Visitor visitor = market.guestLogin();
            market.register("registerTest", "1234R");
        } catch (Exception e) {
            assert false;
            try {
                market.register("registerTest", "1234R");
            } catch (Exception e2) {
                assert true;
            }
        }
    }

    @Test
    @DisplayName("get shop info")
    public void shopInfoTest() {
//        String shopName = "shopTest";
        try {
            Visitor visitor = market.guestLogin();
            Shop res = market.getShopInfo(visitor.getName(), shopName);
            assert res.getShopName().equals(shopName);
            market.visitorExitSystem(visitor.getName());
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("search item by name")
    public void searchItemName() {
        try {
            List<Item> res = market.getItemByName("milk");
            assert res.size() > 0;
            assert res.get(0).getName().equals("milk");
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("search item by keyWord")
    public void searchItemByKeyword(){
        try {
            List<Item> res = market.getItemsByKeyword("in sale");
            assert res.size() > 0;
            boolean milkFound = false;
            for (Item item: res){
                assert item.getKeywords().contains("in sale");
                if (item.getName().equals("milk")) {
                    milkFound = true;
                    break;
                }
            }
            assert milkFound;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("search item by Category")
    public void searchItemByCategory(){
        try {
            List<Item> res = market.getItemByCategory(Item.Category.general);
            assert res.size() > 0;
            for (Item item: res){
                assert item.getCategory() == Item.Category.general;
            }
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("add item to cart")
    public void addItemCart() {
        try {
            Visitor visitor = market.guestLogin();
            List<Item> res = market.getItemByName("milk");
            Item milk = res.get(0);
            market.addItemToShoppingCart(milk, 3, shopName, visitor.getName());
            assert true;
            //check shopping basket includes only the milk
            assert visitor.getCart().getCart().size() == 1;
            visitor.getCart().getCart().forEach((shop, basket) -> {
                assert basket.getItems().size() == 1;
                assert shop.getShopName().equals(shopName);
                // check right amount added
                assert basket.getItems().get(milk.getID()).equals(3.0);
            });

        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("add item to cart, exit - empty cart")
    public void emptyCartWhenExit() {
        try {
            Visitor visitor = market.guestLogin();
            List<Item> res = market.getItemByName("milk");
            Item milk = res.get(0);
            market.addItemToShoppingCart(milk, 3, shopName, visitor.getName());
            assert !visitor.getCart().getCart().isEmpty();
            market.visitorExitSystem(visitor.getName());
            visitor = market.guestLogin();
            assert visitor.getCart().getCart().isEmpty();
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("add item to cart, 0 amount")
    public void addZeroAmount() {
        try {
            Visitor visitor = market.guestLogin();
            List<Item> res = market.getItemByName("milk");
            Item milk = res.get(0);
            market.addItemToShoppingCart(milk, 0, shopName, visitor.getName());
            assert false;
        } catch (MarketException e) {
            assert true;
        } catch (Exception e){
            assert false;
        }
    }

    @Test
    @DisplayName("buy item, valid amount")
    public void buyItemValid() {
        try {
            Visitor visitor = market.guestLogin();
            Shop shop = market.getShopInfo(shopManagerName, shopName);
            List<Item> res = market.getItemByName("chocolate");
            Item chocolate = res.get(0);
            Double itemAmount = shop.getItemCurrentAmount(chocolate);
            double buyingAmount = itemAmount - 1;
            market.addItemToShoppingCart(chocolate, buyingAmount, shopName, visitor.getName());
            market.buyShoppingCart(visitor.getName(), productPrice * buyingAmount, creditCard, address);
            shop = market.getShopInfo(shopManagerName, shopName);
            Double newAMount = shop.getItemCurrentAmount(chocolate);
            assert newAMount == 1;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("buy not existing item")
    public void buyWithUnexpectedPrice() {
        try {
            Visitor visitor = market.guestLogin();
            Shop shop = market.getShopInfo(shopManagerName, shopName);
            List<Item> res = market.getItemByName("milk");
            Item milk = res.get(0);
            Double itemAmount = shop.getItemCurrentAmount(milk);
            double buyingAmount = itemAmount + 1;
            market.addItemToShoppingCart(milk, buyingAmount, shopName, visitor.getName());
            market.buyShoppingCart(visitor.getName(), productPrice * buyingAmount, creditCard, address);

        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("buy cart with unexpected price")
    public void buyNotExistingItem() {
        try {
            Visitor visitor = market.guestLogin();
            Shop shop = market.getShopInfo(shopManagerName, shopName);
            List<Item> res = market.getItemByName("milk");
            Item milk = res.get(0);
            Double itemAmount = shop.getItemCurrentAmount(milk);
            double buyingAmount = itemAmount;
            market.addItemToShoppingCart(milk, buyingAmount, shopName, visitor.getName());
            // add not existing item shouldn't fail
            market.buyShoppingCart(visitor.getName(), productPrice * buyingAmount + 1, creditCard, address);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }


}
