package com.example.server.ScenarioTests;

import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.ExternalServices.PaymentMock;
import com.example.server.businessLayer.ExternalServices.SupplyMock;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Users.Visitor;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShopOwnerTests {



    Market market;
    String userName = "userTest";
    String password = "passTest";
    PaymentMock paymentService = new PaymentMock();
    SupplyMock supplyService = new SupplyMock();
    String shopOwnerName = "bar";
    String shopOwnerPassword = "pass";
    String memberName = "bar1";
    String memberPassword = "pass1";
    String shopName = "store";
    String ItemName= "item1";
    int productAmount;
    Double productPrice;
    double newAmount;


    @BeforeAll
    public void setUp() {
        try {
            market = Market.getInstance();
            productAmount = 3;
            productPrice = 1.2;
            newAmount=10;
            market.firstInitMarket(paymentService, supplyService, userName, password);
            // shop manager register
            registerVisitor(shopOwnerName,shopOwnerPassword);
            // open shop
            openShop();
            registerVisitor(memberName,memberPassword);

        } catch (Exception Ignored) {
        }
    }

    private void openShop() throws MarketException {

        loginMember(shopOwnerName,shopOwnerPassword);
        market.openNewShop(shopOwnerName, shopName);

    }

    @Test
    @DisplayName("shop owner add new item")
    public void addNewItem() {
        try {
            //login owner and add product
            loginMember(shopOwnerName, shopOwnerPassword);
            market.addItemToShop(shopOwnerName, ItemName, productPrice, Item.Category.general,"some info"
                    ,new ArrayList<>() , productAmount,shopName);
            Assertions.assertTrue(market.getItemByName(ItemName).size()>=1);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("shop owner add new item bad case - not an owner")
    public void addNewItemFail() {
        try {
            //login owner and add product
            loginMember(shopOwnerName, shopOwnerPassword);
            market.addItemToShop("non existing owner", ItemName, productPrice, Item.Category.general,
                    "some info",new ArrayList<>() , productAmount,shopName);
             assert  false;
        } catch (Exception e) {
            assert true;
        }
    }
    @Test
    @DisplayName("shop owner add new item bad case - not a real shop")
    public void addNewItemFail2() {
        try {
            //login owner and add product

            loginMember(shopOwnerName, shopOwnerPassword);
            market.addItemToShop(shopOwnerName, ItemName, productPrice, Item.Category.general,
                    "some info",new ArrayList<>() , productAmount,"non existing shop name");
            assert  false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("set item new amount")
    public void setItemAmount() {
        try {
            //login owner and add product

            loginMember(shopOwnerName,shopOwnerPassword);
            addItem(shopOwnerName,ItemName,productPrice,Item.Category.general,"info", new ArrayList(),productAmount,shopName);
            //get the item from the market for args.
            Item it= market.getItemByName(ItemName).get(0);
            market.setItemCurrentAmount(shopOwnerName,it,newAmount,shopName);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("set item new amount bad case - not an existing item")
    public void setItemAmountFail() {
        try {
            //login owner and add product

            loginMember(shopOwnerName,shopOwnerPassword);
            //set amount for item that is not exists.
            market.setItemCurrentAmount(shopOwnerName,new Item(111,ItemName,10,"inf", Item.Category.fruit,new ArrayList<>()),newAmount,shopName);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }


    @Test
    @DisplayName("set item new amount bad case - unauthorized member to update")
    public void setItemAmountFail2() {
        try {
            //login owner and add product

            loginMember(shopOwnerName,shopOwnerPassword);
            loginMember(memberName,memberPassword);
            addItem(shopOwnerName,ItemName,productPrice,Item.Category.general,"info", new ArrayList(),productAmount,shopName);
            //get the item from the market for args.
            Item it= market.getItemByName(ItemName).get(0);
            market.setItemCurrentAmount(memberName,it,newAmount,shopName);
            assert false;
        } catch (Exception e) {
            Assertions.assertEquals("member is not the shop owner and is not authorized to effect the inventory.",e.getMessage());
        }
    }

    @Test
    @DisplayName("edit item info")
    public void editItemInfo()   {
        try {
            //login owner and add product
            loginMember(shopOwnerName,shopOwnerPassword);
            addItem(shopOwnerName,ItemName,productPrice,Item.Category.general,"info", new ArrayList(),productAmount,shopName);
            //get the item from the market for args.
            Item it= market.getItemByName(ItemName).get(0);
            Item newIt= new Item(it.getID(),it.getName(),it.getPrice(),"another info",it.getCategory(), it.getKeywords());
            market.changeShopItemInfo(shopOwnerName,it,newIt,shopName);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("edit item info bad case - item to update does not exists")
    public void editItemInfoFail()  {
        try {
            //login owner and add product
            loginMember(shopOwnerName,shopOwnerPassword);
            addItem(shopOwnerName,ItemName,productPrice,Item.Category.general,"info", new ArrayList(),productAmount,shopName);
            //get the item from the market for args.
            Item it= market.getItemByName(ItemName).get(0);
            Item newIt= new Item(it.getID(),it.getName(),it.getPrice(),"another info",it.getCategory(), it.getKeywords());
            market.changeShopItemInfo(shopOwnerName,newIt,it,shopName);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("edit item info bad case - not a real shop name")
    public void editItemFail1()  {
        try {
            //login owner and add product
            loginMember(shopOwnerName,shopOwnerPassword);
            addItem(shopOwnerName,ItemName,productPrice,Item.Category.general,"info", new ArrayList(),productAmount,shopName);
            //get the item from the market for args.
            Item it= market.getItemByName(ItemName).get(0);
            Item newIt= new Item(it.getID(),it.getName(),it.getPrice(),"another info",it.getCategory(), it.getKeywords());
            market.changeShopItemInfo(shopOwnerName,it,newIt,"not real shop name");
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("appoint new shop owner")
    public void appointNewOwner() {
        try {

            loginMember(shopOwnerName,shopOwnerPassword);
            market.appointShopOwner(shopOwnerName,memberName,shopName);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("appoint new shop owner bad case - appoint for not a real shop")
    public void appointNewOwnerFail() {
        try {

            loginMember(shopOwnerName,shopOwnerPassword);
            market.appointShopOwner(shopOwnerName,"not real member",shopName);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
    @Test
    @DisplayName("appoint new shop owner bad case - appoint twice a member")
    public void appointNewOwnerFail2() {
        try {
            loginMember(shopOwnerName,shopOwnerPassword);
            market.appointShopOwner(shopOwnerName,memberName,shopName);
            try {
                market.appointShopOwner(shopOwnerName, memberName, shopName);
            }
            catch(Exception e){
                assert  true;
            }
            assert false;
        } catch (Exception e) {
            assert false;
        }
    }



    @Test
    @DisplayName("appoint new shop manager")
    public void appointNewManager() {
        try {
            loginMember(shopOwnerName,shopOwnerPassword);
            market.appointShopManager(shopOwnerName,memberName,shopName);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("appoint new shop manager bad case - appoint not a real member")
    public void appointNewManagerFail() {
        try {

            loginMember(shopOwnerName,shopOwnerPassword);
            market.appointShopManager(shopOwnerName,"not real member",shopName);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
    @Test
    @DisplayName("appoint new shop manager bad case - appoint twice a member")
    public void appointNewManagerFail2()  {
        try {
            loginMember(shopOwnerName,shopOwnerPassword);
            market.appointShopManager(shopOwnerName,memberName,shopName);
            try {
                market.appointShopManager(shopOwnerName, memberName, shopName);
            }
            catch(Exception e){
                assert  true;
            }
            assert false;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("close shop")
    public void closeShop() {
        try {

            loginMember(shopOwnerName,shopOwnerPassword);
            market.closeShop(shopOwnerName,shopName);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("close shop bad case- owner not login")
    public void closeShopFail() {
        try {
            try{
                logoutMember(shopOwnerName);
            }
            catch (Exception e){}
            market.closeShop(shopOwnerName,shopName);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("close shop bad case- shop not exists")
    public void closeShopFail2() {
        try {
            loginMember(shopOwnerName,shopOwnerPassword);
            market.closeShop(shopOwnerName,"not a shop name");
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("get employees info")
    public void empInfo() {
        try {
            loginMember(shopOwnerName,shopOwnerPassword);
            Map<String , Appointment> emps=  market.getShopEmployeesInfo(shopOwnerName,shopName);
            Assertions.assertNotNull(emps);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("get employees info bad case - not authorized member")
    public void empInfoFail() {
        try {
            loginMember(shopOwnerName,shopOwnerPassword);
            Map<String , Appointment> emps=  market.getShopEmployeesInfo(memberName,shopName);
            Assertions.assertNotNull(emps);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("get employees info bad case - member not logged in")
    public void empInfoFail1() {
        try {
            try{
                logoutMember(shopOwnerName);
            }
            catch (Exception e){}
            market.getShopEmployeesInfo(memberName,shopName);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
    @Test
    @DisplayName("get shop purchase history")
    public void purchaseHistory() {
        try {
            loginMember(shopOwnerName,shopOwnerPassword);
            String  str = new String( market.getShopPurchaseHistory(shopOwnerName,shopName));
            Assertions.assertNotNull(str);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("get shop purchase history bad case - not authorized member")
    public void purchaseHistoryFail() {
        try {
            loginMember(shopOwnerName,shopOwnerPassword);
          market.getShopPurchaseHistory(memberName,shopName);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("get shop purchase history bad case -not real shop")
    public void purchaseHistoryFail1() {
        try {
            loginMember(shopOwnerName,shopOwnerPassword);
            market.getShopPurchaseHistory(memberName,"not a real shop name");
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    public void loginMember(String shopOwnerN, String shopOwnerP) throws MarketException {
        Visitor visitor = market.guestLogin();
        market.memberLogin(shopOwnerN, shopOwnerP);
        market.validateSecurityQuestions(shopOwnerN, new ArrayList<>(), visitor.getName());
        market.memberLogin(shopOwnerN,shopOwnerP);
    }
    public void logoutMember(String name) throws MarketException {
        market.memberLogout(name);
    }
    public void registerVisitor(String name, String pass) throws MarketException {
        // shop manager register
        Visitor visitor = market.guestLogin();
        market.register(name, pass);
      //  market.memberLogin(name, pass);
      //  market.validateSecurityQuestions(name, new ArrayList<>(), visitor.getName());
    }
    public void addItem(String son, String in, double pp ,Item.Category cat, String inf, List<String> lis, int am, String sn) throws MarketException {
        market.addItemToShop(son, in, pp, cat,
                inf,lis , am,sn);
    }
}