package test.UnitTests;

import main.businessLayer.*;
import main.businessLayer.Appointment.Appointment;
import main.businessLayer.users.Member;
import main.businessLayer.users.UserController;
import main.businessLayer.users.Visitor;
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
    Market market;
    List<Appointment> apps;

    Shop shop;
    Map<String, Shop> shops;
    String shopName;


    @BeforeEach
    public void marketUnitTestInit() throws MarketException {
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
        ReflectionTestUtils.setField ( market, "userController", userController );
        ReflectionTestUtils.setField ( market, "userController", userController );
        ReflectionTestUtils.setField ( userController, "members", members );
        ReflectionTestUtils.setField ( userController, "visitorsInMarket", visitorsInMarket );
        ReflectionTestUtils.setField ( visitor, "name", name );
        ReflectionTestUtils.setField ( visitor, "member", member );
        Mockito.when(visitor.getMember()).thenCallRealMethod();
        Mockito.when(visitor.getName()).thenCallRealMethod();
        ReflectionTestUtils.setField ( market, "shops", shops );
        ReflectionTestUtils.setField ( member, "myAppointments", apps );
        market.register(memberName,memberPass);
        visitor=market.guestLogin(false);
        member = userController.memberLogin(memberName,memberPass,visitor.getName());
        market.openNewShop(memberName,shopName);
    }

    //TODO IMPLEMENT SHOP OWNER: ADD AND REMOVE PRODUCTS AND THEIR DETAILS.
    //TODO IMPLEMENT CLOSE SHOP(SHOP OWNER)
    //TODO IMPLEMENT ASK FOR DATA ON SHOP EMPLOYEES(FOR OWNER ONLY)
    //TODO PURCHASE HISTORY OF THE SHOP(SHOP OWNER)

    @Test
    @DisplayName("Shop owner Unit Test - edit stock good cases")
    public void EditStock() throws Exception {


        Item item= Mockito.mock(Item.class);
      // market updateMarketOnAddedItem(Item toAdd,String shopName)

     // market   removeItemFromShop(String shopOwnerName, String itemName, String shopName)
    }

    @Test
    @DisplayName("Shop owner Unit Test - edit stock bad cases")
    public void EditStockB() throws Exception {

    }
    }
