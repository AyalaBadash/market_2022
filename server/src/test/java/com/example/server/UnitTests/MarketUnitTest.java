package com.example.server.UnitTests;

import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Shop;
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
    @Mock
    Member member;
    Visitor notMemberVisitor;
    Visitor memberVisitor;
    UserController userController;
    Shop shop;
    Market market;
    String memberName;
    String visitorName;
    String shopName;

    @BeforeEach
    public void marketUnitTestInit(){
        //TODO change
        market = Mockito.mock ( Market.class );
        member = Mockito.mock ( Member.class, CALLS_REAL_METHODS );
        notMemberVisitor = Mockito.mock ( Visitor.class, CALLS_REAL_METHODS );
        memberVisitor = Mockito.mock ( Visitor.class, CALLS_REAL_METHODS );
        userController = Mockito.mock ( UserController.class);
        shop = Mockito.mock ( Shop.class, CALLS_REAL_METHODS );
        memberName = "member_name";
        visitorName = "visitor_name";
        shopName = "not_Existing_Shop_Name";
    }

    @Test
    @DisplayName( "Open New Shop Use Case Test" )
    public void testOpenNewShop() throws MarketException {
        when(market.getShops ()).thenCallRealMethod();
        market = Market.getInstance ();
        ReflectionTestUtils.setField (market, "userController", userController );
        when ( member.getName ()).thenReturn ( memberName );
        when ( userController.isMember ( memberName ) ).thenReturn ( true );
        when ( userController.isMember ( visitorName ) ).thenReturn ( false );
        when ( userController.getMember (memberName) ).thenReturn ( new Member ( memberName ) );

        market.openNewShop ( memberName, shopName );
        Assertions.assertTrue ( market.getShops ().size () == 1 );
        try{
            market.openNewShop ( memberName, shopName );
        } catch (MarketException marketException){
            Assertions.assertEquals ( marketException.getMessage (), "Shop with the same shop name is already exists" );
        }
        Assertions.assertTrue ( market.getShops ().size () == 1 );
        try{
            market.openNewShop ( visitorName, shopName );
        } catch (MarketException marketException){
            Assertions.assertEquals ( marketException.getMessage (), "You are not a member. Only members can open a new shop in the market" );
        }
        Assertions.assertTrue ( market.getShops ().size () == 1 );
        shop = market.getShopByName ( shopName );
        Assertions.assertTrue ( shop.isShopOwner ( memberName ) );
        Assertions.assertTrue ( shop.getEmployees ().containsKey ( memberName ) );
    }


    @Test
    @DisplayName("Market Unit Test - logout")
    public void testLogout() throws IllegalAccessException, MarketException {
        Market market = Mockito.mock(Market.class, CALLS_REAL_METHODS);
        UserController userController = Mockito.mock(UserController.class);
        ReflectionTestUtils.setField(market, "userController", userController);
        Member member = Mockito.mock(Member.class);
        Visitor visitor = Mockito.mock(Visitor.class);
        String memberName = "memberName_test";
        Map visitorsInMarket = new HashMap();
        visitorsInMarket.put(memberName, visitor);
        Map members = new HashMap();
        members.put(memberName, member);
        ReflectionTestUtils.setField(userController, "members", members);
        ReflectionTestUtils.setField(userController, "visitorsInMarket", visitorsInMarket);
        String newVisitor = market.memberLogout(memberName);
        Assertions.assertFalse(userController.getVisitorsInMarket().containsKey(memberName));
    }
}
