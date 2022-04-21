package test.UnitTests;

import main.businessLayer.Market;
import main.businessLayer.MarketException;
import main.businessLayer.Shop;
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

import static org.mockito.Mockito.*;

public class MarketUnitTest  extends mainTest{
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
        market = mock ( Market.class );
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
    @DisplayName ( "Open New Shop Use Case Test" )
    public void testOpenNewShop() throws MarketException {
        when(market.getShops ()).thenCallRealMethod();
//        when(market.openNewShop()).thenCallRealMethod();
        market = Market.getInstance ();
        ReflectionTestUtils.setField ( market, "userController", userController );
        Mockito.when ( member.getName ()).thenReturn ( memberName );
        Mockito.when ( userController.isMember ( memberName ) ).thenReturn ( true );
        Mockito.when ( userController.isMember ( visitorName ) ).thenReturn ( false );
        Mockito.when ( userController.getMember (memberName) ).thenReturn ( new Member ( memberName ) );
//        Mockito.when ( market.getShops ().get ( existingShopName ) ).thenReturn ( existingShop );
//        Mockito.when ( market.getShops ().get ( notExistingShopName ) ).thenReturn ( null );

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
}
