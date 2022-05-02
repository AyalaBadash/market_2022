package com.example.server.ScenarioTests;

import com.example.server.businessLayer.ExternalServices.PaymentMock;
import com.example.server.businessLayer.ExternalServices.SupplyMock;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Users.Visitor;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShopManagerTests {


    Market market;
    String userName = "userTest";
    String password = "passTest";
    PaymentMock paymentService = new PaymentMock();
    SupplyMock supplyService = new SupplyMock();
    String shopOwnerName = "bar";
    String shopOwnerPassword = "pass";
    String managerName = "bar1";
    String managerPassword = "pass1";
    String shopName = "store";
    int productAmount;
    Double productPrice;
    double newAmount;


    @BeforeAll
    public void setUp() {
        try {
            market = Market.getInstance();
            productAmount = 3;
            productPrice = 1.2;
            newAmount=10;
            market.firstInitMarket(paymentService, supplyService, userName, password);
            // shop manager register
            registerVisitor(shopOwnerName,shopOwnerPassword);
            // open shop
            openShop();
            registerVisitor(managerName,managerPassword);
            loginMember(shopOwnerName,shopOwnerPassword);

        } catch (Exception Ignored) {
        }
    }
    @Test
    @DisplayName("get shop purchase history")
    public void purchaseHistory() {
        try {
            market.appointShopManager(shopOwnerName,managerName,shopName);
            loginMember(managerName,managerPassword);
            String  str = new String( market.getShopPurchaseHistory(managerName,shopName));
            Assertions.assertNotNull(str);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("get shop purchase history bad case - not authorized member")
    public void purchaseHistoryFail() {
        try {
            loginMember(managerName,managerPassword);
            market.getShopPurchaseHistory(managerName,shopName);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("get shop purchase history bad case -not real shop")
    public void purchaseHistoryFail1() {
        try {
            loginMember(managerName,managerPassword);
            market.getShopPurchaseHistory(managerName,"not a real shop name");
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }


    public void loginMember(String shopOwnerN, String shopOwnerP) throws MarketException {
        market.memberLogin(shopOwnerN,shopOwnerP);
    }
    public void registerVisitor(String name, String pass) throws MarketException {
        // shop manager register
        Visitor visitor = market.guestLogin();
        market.register(name, pass);
        List<String> questions = market.memberLogin(name, pass);
        market.validateSecurityQuestions(name, new ArrayList<>(), visitor.getName());
    }

    private void openShop() throws MarketException {

        loginMember(shopOwnerName,shopOwnerPassword);
        market.openNewShop(shopOwnerName, shopName);

    }
}