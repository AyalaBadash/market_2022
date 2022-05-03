package com.example.server.ScenarioTests;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.CreditCard;
import com.example.server.businessLayer.ExternalServices.PaymentMock;
import com.example.server.businessLayer.ExternalServices.SupplyMock;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.Users.Visitor;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServicesTests {


    Market market;
    String userName = "userTest";
    String password = "passTest";
    PaymentMock paymentService = new PaymentMock();
    SupplyMock supplyService = new SupplyMock();

    String memberName = "bar1";

    String memberPassword = "pass1";
    String shopName = "store";

    String ItemName= "item1";
    int productAmount;
    Double productPrice;
    Visitor visitor;

    @BeforeEach
    public void setUp(){
        productAmount = 10;
        productPrice = 1.2;
        try {

            market = Market.getInstance();
            market.firstInitMarket (paymentService, supplyService, userName, password );
        }
        catch (Exception e){}
    }

    @Test
    @DisplayName("guest login")
    public void questLogin() {
        try {
            visitor= market.guestLogin();
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("exit market")
    public void exitMarket() {
        try {
            visitor= market.guestLogin();
            market.visitorExitSystem(visitor.getName());
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("exit market bad case- visitor not exists")
    public void exitMarketFail() {
        try {
            market.visitorExitSystem("not a visitor");
            assert false;
        } catch (Exception e) {
            Assertions.assertEquals("you must be a visitor in the market in order to make actions", e.getMessage());
        }
    }


    @Test
    @DisplayName("register")
    public void register() {
        try {
            String name = "name";
            String pass = "Pass";
            visitor= market.guestLogin();
            market.register(name,pass);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }


    @Test
    @DisplayName("exit market bad case- member exists")
    public void registerFail2() {
        try {
            String name = "new name";
            String pass = "new Pass";
            visitor= market.guestLogin();
            market.register(name,pass);
            market.register(name,pass);
            assert false;
        } catch (Exception e) {
            Assertions.assertEquals("Name is already taken ,try to be a little more creative and choose another name. ", e.getMessage());
        }
    }

    @Test
    @DisplayName("validate Questions")
    public void validateQuestions() {
        try {
            market.memberLogin(memberName,memberPassword);
            market.validateSecurityQuestions(memberName,new ArrayList<String>(),visitor.getName());
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }


    @Test
    @DisplayName("validate Questions bad case- wrong answers")
    public void validateQuestionsFail() {
        try {
            visitor = register(memberName,memberPassword);

            market.memberLogin(memberName,memberPassword);
            market.validateSecurityQuestions(memberName,new ArrayList<String>() {
                {
                    add("not ans1");
                    add("not ans2");
                    add("not ans3");
                }
            },visitor.getName());
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    private Visitor register(String memberName, String memberPassword) throws MarketException {

        Visitor visit= market.guestLogin();
        market.register(memberName,memberPassword);
        market.validateSecurityQuestions(memberName,new ArrayList<>(), visit.getName());
        return visit;
    }

    @Test
    @DisplayName("member login")
    public void loginMember() throws MarketException {
        try {
            Visitor visitor = market.guestLogin();
            market.memberLogin(memberName, memberPassword);
            market.validateSecurityQuestions(memberName, new ArrayList<>(), visitor.getName());
            assert true;
        }
        catch (Exception e){
            assert false;
        }
    }
    @Test
    @DisplayName("open shop")
    public void openShopTest() throws MarketException {
       try {
           loginMember(memberName, memberPassword);
           String sname= "name shop";
           market.openNewShop(memberName, sname);
        assert true;
       }
       catch (Exception e){
           assert false;
       }
    }

    @Test
    @DisplayName("open shop bad case - not a member")
    public void openShopTestFail() throws MarketException {
        try {
            Visitor visit= market.guestLogin();
            market.openNewShop(visit.getName(), shopName);
            assert false;
        }
        catch (Exception e){
            assert true;
        }
    }
    @Test
    @DisplayName("search product by name")
    public void searchProductByName() throws MarketException {
        try {
            loginMember(memberName,memberPassword);
            String sName= "shop name";
            market.openNewShop(memberName, sName);
            addItem(memberName, ItemName, productPrice, Item.Category.general, "some info", new ArrayList<>(), productAmount, sName);
            List<Item> lis = market.getItemByName(ItemName);
            assert lis!=null;
        }
        catch (Exception e){
            assert false;
        }
    }
    @Test
    @DisplayName("search product by name bad case - not an item")
    public void searchProductByNameFail() throws MarketException {
        try {
            loginMember(memberName,memberPassword);
            String sName= "shop name";
            market.openNewShop(memberName, sName);
            addItem(memberName, ItemName, productPrice, Item.Category.general, "some info", new ArrayList<>(), productAmount, sName);
            List<Item> lis = market.getItemByName("ItemName false");
            assert lis==null;
        }
        catch (Exception e){
            assert true;
        }
    }

    @Test
    @DisplayName("add item to shop")
    public void addItemToShop() throws MarketException {
        try {
            loginMember(memberName, memberPassword);
            market.openNewShop(memberName, shopName);
            market.addItemToShop(memberName, ItemName, productPrice, Item.Category.general, "some info", new ArrayList<>(), productAmount, shopName);
            assert true;
        }
        catch (Exception e){
            assert false;
        }
    }

    @Test
    @DisplayName("add item to shop bad case - shop not exists")
    public void addItemToShopFail() throws MarketException {
        try {
            loginMember(memberName, memberPassword);
            market.addItemToShop(memberName, ItemName, productPrice, Item.Category.general, "some info", new ArrayList<>(), productAmount, shopName);
            assert false;
        }
        catch (Exception e){
            assert true;
        }
    }

    @Test
    @DisplayName("add item to cart")
    public void AddItemToCart() throws MarketException {
        try {
            visitor= market.guestLogin();
            market.register(memberName,memberPassword);
            loginMember(memberName,memberPassword);
            String sName= "the new shop name";
            market.openNewShop(memberName, sName);
            addItem(memberName, ItemName, productPrice, Item.Category.general, "some info", new ArrayList<>(), productAmount, sName);
            List<Item> lis = market.getItemByName(ItemName);
            market.addItemToShoppingCart(lis.get(1),1,sName,visitor.getName());
            assert true;
        }
        catch (Exception e){
            String str =e.getMessage();
            assert false;
        }
    }

    @Test
    @DisplayName("add item to cart bad case - not an item")
    public void AddItemToCartFail() throws MarketException {
        try {
            String name = "new name11";
            String pass = "new Pass11";
            visitor= market.guestLogin();
            market.register(name,pass);
            loginMember(name,pass);
            String sName= "Sname";
            market.openNewShop(name, sName);
            market.addItemToShoppingCart(new Item(11, ItemName, productPrice, "some info", Item.Category.general, new ArrayList<>()),1,sName,visitor.getName());
            assert false;
        }
        catch (Exception e){
            assert true;
        }
    }

    @Test
    @DisplayName("logout member")
    public void logoutMember() throws MarketException {
      try {
          visitor = market.guestLogin();
          loginMember(memberName, memberPassword);
          market.memberLogout(memberName);
          assert true;
      }
      catch (Exception e){
          assert false;
      }
    }

    @Test
    @DisplayName("logout member bad casse - member not logged in")
    public void logoutMemberFail() throws MarketException {
        try {
            market.memberLogout(memberName);
            assert false;
        }
        catch (Exception e){
            Assertions.assertEquals("you must be a visitor in the market in order to make actions", e.getMessage());
        }
    }

    @Test
    @DisplayName("get shop info")
    public void getShopInfo() throws MarketException {
        try {
            loginMember(memberName, memberPassword);
            String nam= "name for shop";
            market.openNewShop(memberName, nam);
            market.addItemToShop(memberName, ItemName, productPrice, Item.Category.general, "some info", new ArrayList<>(), productAmount, nam);
            Shop shop= market.getShopInfo(memberName,nam);
            Assertions.assertNotNull(shop);
        }
        catch (Exception e){
            assert false;
        }
    }

    @Test
    @DisplayName("get shop info bad case - not a shop")
    public void getShopInfoFail() throws MarketException {
        try {
            loginMember(memberName, memberPassword);
            Shop shop= market.getShopInfo(memberName,"not a real shop");
            assert false;
        }
        catch (Exception e){
            Assertions.assertEquals("shop does not exist in the market",e.getMessage());
        }
    }

    @Test
    @DisplayName("buy shopping cart")
    public void PurchaseCart() throws MarketException {
        try {
            visitor=market.guestLogin();
            String nname= "nname";
            String ppas= "ppas";
            market.register(nname,ppas);
            market.memberLogin(nname, ppas);
            market.validateSecurityQuestions(nname, new ArrayList<>(), visitor.getName());
            String sName= "new shop name";
            market.openNewShop(nname, sName);
            addItem(nname, ItemName, productPrice, Item.Category.general, "some info", new ArrayList<>(), productAmount, sName);
            List<Item> lis = market.getItemByName(ItemName);
            market.addItemToShoppingCart(lis.get(0),1,sName,nname);
            market.buyShoppingCart(nname, 1.2,new CreditCard("2345","345","456"),new Address("beer sheba","atad","30"));
            assert true;
        }
        catch (Exception e){
            String str= e.getMessage();
            assert false;
        }
    }

    @Test
    @DisplayName("buy shopping cart bad case - not a shopper")
    public void PurchaseCartFail() throws MarketException {
        try {
            market.buyShoppingCart("not a name", 100,new CreditCard("2345","345","456"),new Address("beer sheba","atad","30"));
            assert false;
        }
        catch (Exception e){
            Assertions.assertEquals("you must be a visitor in the market in order to make actions",e.getMessage());
        }
    }

    public void addItem(String son, String in, double pp , Item.Category cat, String inf, List<String> lis, int am, String sn) throws MarketException {
        try {
            market.addItemToShop(son, in, pp, cat, inf,lis , am,sn);
        }
        catch (Exception e){
            throw e;
        }
    }
    public void loginMember(String shopOwnerN, String shopOwnerP) throws MarketException {
        Visitor visitor = market.guestLogin();
        market.memberLogin(shopOwnerN, shopOwnerP);
        market.validateSecurityQuestions(shopOwnerN, new ArrayList<>(), visitor.getName());
    }




    //buyShoppingCart(String visitorName, double expectedPrice, PaymentMethod paymentMethod, Address address)
}
