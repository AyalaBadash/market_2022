package com.example.server.ScenarioTests;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.CreditCard;
import com.example.server.businessLayer.ExternalServices.PaymentMock;
import com.example.server.businessLayer.ExternalServices.SupplyMock;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.Users.Visitor;
import com.example.server.serviceLayer.FacadeObjects.ShopFacade;
import com.example.server.serviceLayer.FacadeObjects.VisitorFacade;
import com.example.server.serviceLayer.Response;
import com.example.server.serviceLayer.ResponseT;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;
import org.springframework.test.web.servlet.MvcResult;

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
            market.firstInitMarket(paymentService, supplyService, userName, password);
//            // shop manager register
//            Visitor visitor = market.guestLogin();
//            market.register(shopManagerName, shopManagerPassword);
//            List<String> questions = market.memberLogin(shopManagerName, shopManagerPassword);
//            market.validateSecurityQuestions(shopManagerName, new ArrayList<>(), visitor.getName());
//            // open shop
//            market.openNewShop(shopManagerName, shopName);
//            productAmount = 3.0;
//            productPrice = 1.2;
//            market.addItemToShop(shopManagerName, "milk", productPrice, Item.Category.general,
//                    "soy",new ArrayList<>() , productAmount,shopName);
//
//            creditCard = new CreditCard("124","13/5" , "555");
//            address = new Address("Tel Aviv", "Super" , "1");
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

//    @Test
//    @DisplayName("get shop info")
//    public void shopInfoTest() {
//        String shopName = "shopTest";
//        try {
//            Visitor visitor = market.guestLogin();
//            Shop res = market.getShopInfo(visitor.getName(), shopName);
//            assert res.getShopName().equals(shopName);
//            market.visitorExitSystem(visitor.getName());
//        } catch (Exception e) {
//            assert false;
//        }
//    }
}
