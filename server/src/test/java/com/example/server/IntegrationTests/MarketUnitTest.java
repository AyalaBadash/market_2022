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
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            market.register("raz","password");
            market.register("ayala","password");
            market.memberLogin("raz","password");
            market.openNewShop("raz","razShop");
            shop = market.getShopByName("razShop");
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
        List<String> keywords = new ArrayList<>();
        keywords.add("dairy");
        try {
            item = new Item(1,"milk",5.0,"", Item.Category.general,
                    keywords);
            shop.addItem("raz", item.getName(), item.getPrice(),item.getCategory(),item.getInfo(),item.getKeywords(),10.0,item.getID());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assert false;
        }
        try{
            market.addItemToShoppingCart(item,1.0,"razShop","ayala");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            assert false;
        }
        try {
            ShoppingCart updatedCart = market.calculateShoppingCart("ayala");
            Assertions.assertEquals(5.0,updatedCart.getCurrentPrice());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }


    }
    @Test
    @DisplayName("Buy shopping cart test - good test")
    public void BuyShoppingCart(){
        assert false;
    }

    @Test
    @DisplayName("Get item by name test")
    public void GetItemByNameTest(){
        List<String> keywords = new ArrayList<>();
        keywords.add("fruit");
        try {
            Item item1 = new Item(2,"apple",2.5,"red", Item.Category.fruit,
            keywords);
            shop.addItem("raz",item1.getName(),item1.getPrice(),item1.getCategory(),item1.getInfo()
                    ,item1.getKeywords(),10.0,item1.getID());
            market.openNewShop("raz","razShop2");
            market.addItemToShop("raz",item.getName(),item.getPrice(),item.getCategory(),"On shop 2 we have info",item.getKeywords(),3.0,"razShop2");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
        List<Item> res = market.getItemByName("milk");
        Assertions.assertEquals(2,res.size());
        Assertions.assertEquals("",res.get(0).getInfo());
        Assertions.assertEquals("On shop 2 we have info",res.get(1).getInfo());


    }

    @Test
    @DisplayName("Get Item By Category")
    public void getItemByCategoryTest(){}
    @Test
    @DisplayName("Validate Security questions - good test")
    public void validateSecurityQuestionsTest(){}
    @Test
    @DisplayName("Validate Security Questions - fail test - wrong answers")
    public void validateSecurityQuestionsFailTest(){}
    @Test
    @DisplayName("Visitor Exit System - good test")
    public void visitorExitSystemTest(){}
    @Test
    @DisplayName("Visitor Exit System- fail test - not a visitor")
    public void visitorExitSystemFailTest(){}
    @Test
    @DisplayName("Close Shop test -good test")
    public void closeShopTest(){}
    @Test
    @DisplayName("Close Shop test - fail test - not logged in")
    public void closeShopFailTest(){}
    @Test
    @DisplayName("Remove Item From Shop")
    public void removeItemFromShopTest(){}
    @Test
    @DisplayName("Remove Item From Shop-fail test ")
    public void removeItemFromShopFailTest(){}

    @Test
    @DisplayName("Add Item To Shop -good test")
    public void addItemToShopTest(){

    }

    @Test
    @DisplayName("Add Item To Shop - fail test")
    public void addItemToShopFailTest(){

    }
    @Test
    @DisplayName("Set Item Current Amount - good test")
    public void setItemCurrentAmountTest(){}
    @Test
    @DisplayName("Set Item Current Amount - fail test")
    public void setItemCurrentAmountFailTest(){}
    @Test
    @DisplayName("Member Logout-good test")
    public void memberLogoutTest(){}
    @Test
    @DisplayName("Member Logout- fail test")
    public void memberLogoutFailTest(){}
    @Test
    @DisplayName("Add Personal Query - good test")
    public void addPersonalQueryTest(){}
    @Test
    @DisplayName("Add Personal Query - fail Test")
    public void addPersonalQueryFailTest(){}
    @Test
    @DisplayName("Get Shop Info Test")
    public void getShopInfoTest(){}
    @Test
    @DisplayName("Open new Shop - good test")
    public void openNewShopTest(){}
    @Test
    @DisplayName("Open new Shop - fail test")
    public void openNewShopFailTest(){}
    @Test
    @DisplayName("Add Item To Shopping Cart - good test")
    public void addItemToShoppingCartTest(){}
    @Test
    @DisplayName("Add Item To Shopping Cart - fail test")
    public void addItemToShoppingCartFailTest(){}
    @Test
    @DisplayName("Appoint Shop Owner - good test")
    public void appointShopOwnerTest(){}
    @Test
    @DisplayName("Appoint shop owner - fail test")
    public void appointShopOwnerFailTest(){}
    @Test
    @DisplayName("Appoint shop manager - good test")
    public void appointShopManagerTest(){}
    @Test
    @DisplayName("Appoint shop manager - fail test")
    public void appointShopManagerFailTest(){}
    @Test
    @DisplayName("Edit cart - good test")
    public void editCartTest(){}
    @Test
    @DisplayName("Edit cart - fail test")
    public void editCartFailTest(){}
    @Test
    @DisplayName("Change Shop Item Info - good test")
    public void changeShopItemInfoTest(){}
    @Test
    @DisplayName("Change Shop Item Info - fail test")
    public void changeShopItemInfoFailTest(){}
    @Test
    @DisplayName("Edit Item - good test")
    public void editItemTest(){}

    @Test
    @DisplayName("Edit Item - fail test")
    public void editItemFailTest(){}
    @Test
    @DisplayName("Buy Shopping Cart - good test")
    public void buyShoppingCartTest(){}
    @Test
    @DisplayName("Buy Shopping Cart - fail test")
    public void buyShoppingCartFailTest(){}
    @Test
    @DisplayName("Validate Cart - good test")
    public void validateCartTest(){}
    @Test
    @DisplayName("Validate Cart - fail test")
    public void validateCartFailTest(){}
    @Test
    @DisplayName("Update Market On Delete Item - good test")
    public void updateMarketOnDeleteItemTest(){}
    @Test
    @DisplayName("Update Market On Delete Item - fail test")
    public void updateMarketOnDeleteItemFailTest(){}
    @Test
    @DisplayName("Update Market On Added Item - good test")
    public void updateMarketOnAddedItem(){}

    @Test
    @DisplayName("Remove Closed Shop Items From Market - good test")
    public void removeClosedShopItemsFromMarketTest(){}

    @Test
    @DisplayName("Remove Closed Shop Items From Market - fail test")
    public void removeClosedShopItemsFromMarketFailTest(){}





}
