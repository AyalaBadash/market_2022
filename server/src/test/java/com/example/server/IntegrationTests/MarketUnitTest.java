package com.example.server.IntegrationTests;

import com.example.server.ResourcesObjects.PaymentMethod;
import com.example.server.businessLayer.*;
import com.example.server.businessLayer.ExternalServices.PaymentMock;
import com.example.server.businessLayer.ExternalServices.PaymentService;
import com.example.server.businessLayer.ExternalServices.ProductsSupplyService;
import com.example.server.businessLayer.ExternalServices.SupplyMock;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.UserController;
import com.example.server.businessLayer.Users.Visitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.when;

public class MarketUnitTest {
    Member member;
    Visitor notMemberVisitor;
    Visitor memberVisitor;
    UserController userController;
    Shop shop;
    Market market;
    String memberName;
    String visitorName;
    String shopName;
    Security security;
    Item item;

    @BeforeEach
    public void marketUnitTestInit(){
        market = Market.getInstance();
        security = Security.getInstance();
        userController = UserController.getInstance();
        try {
            market.register("ayala","password");
            market.memberLogin("ayala","password");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @DisplayName("First init market - good test")
    public void initTest(){
        PaymentService paymentService = new PaymentMock();
        ProductsSupplyService supplyService = new SupplyMock();
        try{
                    market.firstInitMarket(paymentService,supplyService,"raz","password");
                    assert true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assert false;
        }
    }
    @Test
    @DisplayName("First init market - fail test - one of the external service is null")
    public void initFailTest(){
        ProductsSupplyService supplyService = new SupplyMock();
        try{
            market.firstInitMarket(null,supplyService,"raz","password");
            assert false;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assert true;
        }
    }
    @Test
    @DisplayName("First init market - fail test - one service already exist")
    public void initFailTestOneServiceIsntNull(){
        market.setPaymentService(new PaymentMock());
        PaymentService paymentService = new PaymentMock();
        ProductsSupplyService supplyService = new SupplyMock();
        try{
            market.firstInitMarket(paymentService,supplyService,"raz","password");
            assert false;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assert true;
        }
    }

    @Test
    @DisplayName("Register test - good test")
    public void registerTest(){
        try {
            security.validateRegister("ido", "password");
            userController.register("ido");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assert false;
        }
    }
    @Test
    @DisplayName("Register test - fail test - valid")
    public void registerFailTest(){
        try {
            security.validateRegister("ido", "password");
            userController.register("ido");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assert false;
        }
    }
    @Test
    @DisplayName("Member login - good test")
    public void MemberLoginTest(){
        try {
            market.register("ido","password");
            market.memberLogin("ido","password");
            assert true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    @DisplayName("Guest login")
    public void guestLogin(){
        Visitor visitor = userController.guestLogin();
        Assertions.assertNotNull(visitor);
    }

    @Test
    @DisplayName("Calculate shopping cart - good test")
    public void CalculateCart(){

        //market.addItemToShoppingCart(item);

    }
    @Test
    @DisplayName("Buy shopping cart test - good test")
    public void BuyShoppingCart(){
        assert false;
    }

}
