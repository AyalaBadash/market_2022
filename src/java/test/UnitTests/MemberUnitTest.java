package test.UnitTests;

import main.businessLayer.Market;
import main.businessLayer.Security;
import main.businessLayer.users.Member;
import main.businessLayer.users.UserController;
import main.businessLayer.users.Visitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.CALLS_REAL_METHODS;

public class MemberUnitTest {
    //TODO IMPLEMENT LOGOUT AND THE IDEA THAT THE MEMBER RETURNS TO BE VISITOR.
    //TODO IMPLEMENT OPEN SHOP

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

    @BeforeEach
    public void marketUnitTestInit(){
        visitor = Mockito.mock(Visitor.class,CALLS_REAL_METHODS );
        userController= Mockito.mock(UserController.class,CALLS_REAL_METHODS);
        market = Mockito.mock ( Market.class,CALLS_REAL_METHODS );
        memberName = "member1";
        memberPass= "123";
        visitorsInMarket= new ConcurrentHashMap<>();
        security= Mockito.mock(Security.class, CALLS_REAL_METHODS);

    }

    @Test
    @DisplayName("Member Unit Test - logout user")
    public void Logout() throws Exception {

        ReflectionTestUtils.setField ( market, "userController", userController );
        ReflectionTestUtils.setField ( market, "userController", userController );
        ReflectionTestUtils.setField ( userController, "members", members );
        ReflectionTestUtils.setField ( userController, "visitorsInMarket", visitorsInMarket );



        ReflectionTestUtils.setField ( visitor, "name", name );
        ReflectionTestUtils.setField ( visitor, "member", member );
        Mockito.when(visitor.getMember()).thenReturn(member);
        Security sec= Security.getInstance();
        market.register(memberName,memberPass);
        visitor=market.guestLogin();
        Assertions.assertEquals(1, visitorsInMarket.size());
        market.memberLogin(memberName,memberPass,name);
        Assertions.assertNotNull(visitor.getMember());
        market.memberLogout(memberName);
        Assertions.assertNull(visitor.getMember());
    }



}
