package test.UnitTests;

import main.businessLayer.Market;
import main.businessLayer.MarketException;
import main.businessLayer.users.Member;
import main.businessLayer.users.UserController;
import main.businessLayer.users.Visitor;
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

public class MarketUnitTest extends mainTest{
//    @Mock
//    Market market = Mockito.mock(Market.class);
//    UserController userController = Mockito.mock(UserController.class);
//
//    @BeforeEach
//    public void marketInit(){
//        ReflectionTestUtils.setField(market, "userController", userController);
//    }
//    @Test
//    @DisplayName("Market Unit Test - logout")
//    public void testLogout() throws IllegalAccessException, MarketException {
//        Market market = Mockito.mock(Market.class, CALLS_REAL_METHODS);
//        UserController userController = Mockito.mock(UserController.class);
//        ReflectionTestUtils.setField(market, "userController", userController);
//        Member member = Mockito.mock(Member.class);
//        Visitor visitor = Mockito.mock(Visitor.class);
//        String memberName = "memberName_test";
//        Map visitorsInMarket = new HashMap();
//        visitorsInMarket.put(memberName, visitor);
//        Map members = new HashMap();
//        members.put(memberName, member);
//        ReflectionTestUtils.setField(userController, "members", members);
//        ReflectionTestUtils.setField(userController, "visitorsInMarket", visitorsInMarket);
//        String newVisitor = market.memberLogout(memberName);
//        Assertions.assertFalse(userController.getVisitorsInMarket().containsKey(memberName));
////        Assertions.assertFalse(userController.getVisitorsInMarket().isEmpty());
//    }
}
