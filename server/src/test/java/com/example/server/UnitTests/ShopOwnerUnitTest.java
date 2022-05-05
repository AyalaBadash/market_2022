package com.example.server.UnitTests;

import com.example.server.businessLayer.*;
import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.UserController;
import com.example.server.businessLayer.Users.Visitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.CALLS_REAL_METHODS;

public class ShopOwnerUnitTest {
    Visitor visitor;
    UserController userController;
    Map<String, Visitor> visitorsInMarket;
    Map<String, Member> members;
    Security security;
    Member member;
    String memberName;
    String memberPass;
    String name;
    Market market2;
    List<Appointment> apps;

    Shop shop;

    Map<Integer, Item> itemMap;
    Map<String, Shop> shops;
    String shopName;


    @BeforeEach
    public void marketUnitTestInit() throws MarketException {
        assert false;
//        visitor = Mockito.mock(Visitor.class, CALLS_REAL_METHODS);
//        userController = Mockito.mock(UserController.class, CALLS_REAL_METHODS);
//        market2 = Mockito.mock(Market.class, CALLS_REAL_METHODS);
//        memberName = "member1";
//        memberPass = "123";
//        shopName = "ebay";
//        shops = new ConcurrentHashMap<>();
//        visitorsInMarket = new ConcurrentHashMap<>();
//        members = new ConcurrentHashMap<>();
//        security = Mockito.mock(Security.class, CALLS_REAL_METHODS);
//        apps = new ArrayList<>();
//        ReflectionTestUtils.setField(market2, "userController", userController);
//        ReflectionTestUtils.setField(market2, "userController", userController);
//        ReflectionTestUtils.setField(userController, "members", members);
//        ReflectionTestUtils.setField(shop, "itemMap", itemMap);
//        ReflectionTestUtils.setField(userController, "visitorsInMarket", visitorsInMarket);
//        ReflectionTestUtils.setField(visitor, "name", name);
//        ReflectionTestUtils.setField(visitor, "member", member);
//        Mockito.when(visitor.getMember()).thenCallRealMethod();
//        Mockito.when(visitor.getName()).thenCallRealMethod();
//        ReflectionTestUtils.setField(market2, "shops", shops);
//        ReflectionTestUtils.setField(member, "myAppointments", apps);
//        market2.register(memberName, memberPass);
//        visitor = market2.guestLogin(false);
//        member = userController.memberLogin(memberName, memberPass, visitor.getName());
//        market2.openNewShop(memberName, shopName);
    }


    @Test
    @DisplayName("Shop owner Unit Test - add item good case")
    public void AddItem() throws Exception {
        Item item = Mockito.mock(Item.class, CALLS_REAL_METHODS);
        String itemName = "item name";
        double amount = 15;
        int price = 10;
        Item.Category cat = Item.Category.fruit;
        ReflectionTestUtils.setField(item, "name", itemName);
        ReflectionTestUtils.setField(item, "price", price);
        ReflectionTestUtils.setField(item, "category", cat);
        ReflectionTestUtils.setField(item, "amount", amount);
        market2.addItemToShop(memberName, item.getName(), item.getPrice(), cat, " ", new ArrayList<>(), amount, shopName);
        Assertions.assertEquals(1, itemMap.size());

    }

    @Test
    @DisplayName("Shop owner Unit Test - add item good case")
    public void EditItem() throws Exception {


        Item item = Mockito.mock(Item.class, CALLS_REAL_METHODS);
        String itemName = "item name";
        Integer amount = 15;
        Integer price = 10;
        Item.Category cat = Item.Category.fruit;
        ReflectionTestUtils.setField(item, "name", itemName);
        ReflectionTestUtils.setField(item, "price", price);
        ReflectionTestUtils.setField(item, "category", cat);
        ReflectionTestUtils.setField(item, "amount", amount);
        market2.addItemToShop(memberName, item.getName(), item.getPrice(), cat, " ", new ArrayList<>(), amount, shopName);
//        market.editItemStock(memberName, item.getName(), shopName, 20);
        //TODO check here
        Assertions.assertEquals(20, amount.intValue());


    }

    @Test
    @DisplayName("Shop owner Unit Test - add item good case")
    public void RemoveItem() throws Exception {


        Item item = Mockito.mock(Item.class, CALLS_REAL_METHODS);
        String itemName = "item name";
        double amount = 15;
        double price = 10;
        Item.Category cat = Item.Category.fruit;
        ReflectionTestUtils.setField(item, "name", itemName);
        ReflectionTestUtils.setField(item, "price", price);
        ReflectionTestUtils.setField(item, "category", cat);
        ReflectionTestUtils.setField(item, "amount", amount);
        market2.addItemToShop(memberName, item.getName(), item.getPrice(), cat, " ", new ArrayList<>(), amount, shopName);
        Assertions.assertEquals(1, itemMap.size());
        market2.removeItemFromShop(memberName, item.getID(), shopName);
        Assertions.assertEquals(0, itemMap.size());
    }

    @Test
    @DisplayName("Shop owner Unit Test - add item bad case.")
    public void AddItemB() throws Exception {

        Item item = Mockito.mock(Item.class, CALLS_REAL_METHODS);
        String itemName = "item name";
        double amount = 15;
        double price = 10;
        Item.Category cat = Item.Category.fruit;
        ReflectionTestUtils.setField(item, "name", itemName);
        ReflectionTestUtils.setField(item, "price", price);
        ReflectionTestUtils.setField(item, "category", cat);
        ReflectionTestUtils.setField(item, "amount", amount);
        // TODO check here
//        Assertions.fail(market.addItemToShop(memberName, item.getName(), item.getPrice(), cat, " ", new ArrayList<>(), amount, "Shop who is not exists"));
    }

    @Test
    @DisplayName("Shop owner Unit Test - remove item bad case")
    public void RemoveItemB() throws Exception {


        Item item = Mockito.mock(Item.class, CALLS_REAL_METHODS);
        String itemName = "item name";
        double amount = 15;
        double price = 10;
        Item.Category cat = Item.Category.fruit;
        ReflectionTestUtils.setField(item, "name", itemName);
        ReflectionTestUtils.setField(item, "price", price);
        ReflectionTestUtils.setField(item, "category", cat);
        ReflectionTestUtils.setField(item, "amount", amount);
//        Assertions.fail(market.addItemToShop(memberName, "name that not exists", item.getPrice(), cat, " ", new ArrayList<>(), amount, shopName));

    }

    @Test
    @DisplayName("Shop owner Unit Test -remove shop good case")
    public void CloseShop() throws Exception {


        market2.closeShop(memberName,shopName);
        Assertions.assertEquals(0, shops.size());

    }

    @Test
    @DisplayName("Shop owner Unit Test -remove shop bad case")
    public void CloseShopB() throws Exception {

        market2.closeShop(memberName,"not existing shop name");
        Assertions.assertEquals(0, shops.size());
    }

}
