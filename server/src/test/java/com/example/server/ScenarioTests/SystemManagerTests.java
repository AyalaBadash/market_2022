package com.example.server.ScenarioTests;

import com.example.server.ResourcesObjects.PaymentMethod;
import com.example.server.businessLayer.ExternalServices.PaymentMock;
import com.example.server.businessLayer.ExternalServices.PaymentService;
import com.example.server.businessLayer.ExternalServices.SupplyMock;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Users.Visitor;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SystemManagerTests {

    //CHANGE EXTERNAL SERVICES


    Market market;
    String managerName = "userTest";
    String managerpassword = "passTest";
    PaymentMock paymentService = new PaymentMock();
    SupplyMock supplyService = new SupplyMock();
    PaymentMock paymentService2 = new PaymentMock();
    SupplyMock supplyService2 = new SupplyMock();


    @BeforeAll
    public void setUp() {
        try {
            market = Market.getInstance();
            market.firstInitMarket(paymentService, supplyService, managerName, managerpassword);
        } catch (Exception Ignored) {
        }
    }


    @Test
    @DisplayName("system manager get purchases history")
    public void PurchaseHistory() {
        try {

            loginManager(managerName,managerpassword);
            String str= new String(market.getAllSystemPurchaseHistory(managerName));
            Assertions.assertNotNull(str);
            assert  true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("system manager get purchases history bad case- not logged in")
    public void PurchaseHistoryFail() {
        try {

            try{
                logoutMember(managerName);
            }
            catch (Exception e){}
            market.getAllSystemPurchaseHistory(managerName);
            assert  false;
        } catch (Exception e) {
            assert true;
        }
    }
    @Test
    @DisplayName("system manager get purchases history bad case- not system manager")
    public void PurchaseHistoryFail2() {
        try {

            String memberName = "bar1";
            String memberPassword = "pass1";
            registerVisitor(memberName,memberPassword);
            loginManager(managerName,managerpassword);
            market.getAllSystemPurchaseHistory(memberName);
            assert  false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("system manager change services")
    public void changeServices() {
        try {


            loginManager(managerName,managerpassword);
            market.setPaymentService(paymentService2 );
            assert  true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("system manager change services bad case- not logged in")
    public void changeServicesFail() {
        try {

            try{
                logoutMember(managerName);
            }
            catch (Exception e){

            }
            market.setPaymentService(paymentService2 );
            assert  false;
        } catch (Exception e) {
            assert true;
        }
    }
    @Test
    @DisplayName("system manager change services bad case- not system manager")
    public void changeServicesFail2() {
        try {

            String memberName = "bar1";
            String memberPassword = "pass1";
            loginManager(managerName,managerpassword);
            market.setPaymentService(paymentService2 );
            assert  false;
        } catch (Exception e) {
            assert true;
        }
    }
    public void loginManager(String managerName, String managerPass) throws MarketException {
        Visitor visitor = market.guestLogin();
        market.memberLogin(managerName,managerPass);
        market.validateSecurityQuestions(managerName, new ArrayList<>(), visitor.getName());
    }

    public void registerVisitor(String name, String pass) throws MarketException {
        // shop manager register
        Visitor visitor = market.guestLogin();
        market.register(name, pass);
        List<String> questions = market.memberLogin(name, pass);
        market.validateSecurityQuestions(name, new ArrayList<>(), visitor.getName());
    }
    public void logoutMember(String name) throws MarketException {
        market.memberLogout(name);
    }
}
