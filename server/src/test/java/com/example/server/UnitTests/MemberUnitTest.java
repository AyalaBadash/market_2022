package com.example.server.UnitTests;

import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.Security;
import com.example.server.businessLayer.Shop;
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

public class MemberUnitTest {
    Visitor visitor;
    UserController userController;
    Map<String, Visitor> visitorsInMarket;
    Map<String, Member> members;
    Security security;
    Member member;
    String memberName;
    String memberPass;
    String name;
    Market market;
    List<Appointment> apps;

    Map<String, Shop> shops;
    String shopName;

    @BeforeEach
    public void marketUnitTestInit(){
        visitor = Mockito.mock(Visitor.class,CALLS_REAL_METHODS );
        userController= Mockito.mock(UserController.class,CALLS_REAL_METHODS);
        market = Mockito.mock ( Market.class,CALLS_REAL_METHODS );
        memberName = "member1";
        memberPass= "123";
        shopName="ebay";
        shops= new ConcurrentHashMap<>();
        visitorsInMarket= new ConcurrentHashMap<>();
        members= new ConcurrentHashMap<>();
        security= Mockito.mock(Security.class, CALLS_REAL_METHODS);
        apps= new ArrayList<>();
        ReflectionTestUtils.setField (market, "userController", userController );
        ReflectionTestUtils.setField (market, "userController", userController );
        ReflectionTestUtils.setField ( userController, "members", members );
        ReflectionTestUtils.setField ( userController, "visitorsInMarket", visitorsInMarket );
        ReflectionTestUtils.setField ( visitor, "name", name );
        ReflectionTestUtils.setField ( visitor, "member", member );
        Mockito.when(visitor.getMember()).thenCallRealMethod();
        Mockito.when(visitor.getName()).thenCallRealMethod();
        ReflectionTestUtils.setField (market, "shops", shops );
        ReflectionTestUtils.setField ( member, "myAppointments", apps );
    }

    //TODO
    @Test
    @DisplayName("Member Unit Test - logout user good case")
    public void Logout() throws Exception {
        assert false;
        //good case
//        market.register(memberName,memberPass);
//        visitor= market.guestLogin(false);
//        Assertions.assertEquals(1, visitorsInMarket.size());
//        userController.memberLogin(memberName,memberPass,visitor.getName());
//        Assertions.assertNotNull(visitor.getMember());
//        userController.memberLogout(memberName);
//        Assertions.assertNull(visitor.getMember());

    }

    @Test
    @DisplayName("Member Unit Test - logout user bad case")
    public void LogoutB() throws Exception {

        //bad case - not logged in
        assert false;
//        market.register(memberName,memberPass);
//        visitor= market.guestLogin(false);
//        Assertions.assertEquals(1, visitorsInMarket.size());
//        Assertions.fail(userController.memberLogout(memberName));

    }

    @Test
    @DisplayName("Member Unit Test - open shop good case")
    public void OpenShop() throws Exception {


        //good case
        assert false;
//        market.register(memberName,memberPass);
//        visitor= market.guestLogin();
//        Assertions.assertEquals(1, visitorsInMarket.size());
//        member = userController.memberLogin(memberName,memberPass,visitor.getName());
//        market.openNewShop(memberName,shopName);
//        Assertions.assertEquals(1, shops.size());
//        Assertions.assertEquals(1, apps.size());



    }

    @Test
    @DisplayName("Member Unit Test - open shop bad case")
    public void OpenShopB() throws Exception {

        //bad case
        //TODO check
        assert false;
//        visitor = market.guestLogin();
//        Assertions.assertEquals(1, visitorsInMarket.size());
//        member = userController.memberLogin(memberName, memberPass, visitor.getName());
//        Assertions.fail(String.valueOf(market.openNewShop(memberName, shopName)));
    }

}
