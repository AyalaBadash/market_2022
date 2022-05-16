package com.example.server.ScenarioTests;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.CreditCard;
import com.example.server.businessLayer.ExternalServices.PaymentMock;
import com.example.server.businessLayer.ExternalServices.SupplyMock;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.UserController;
import com.example.server.businessLayer.Users.Visitor;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberTests {
    Market market;
    Member testMember;
    String testMemberPassword;
    String testMemberName;
    PaymentMock paymentService = new PaymentMock();
    SupplyMock supplyService = new SupplyMock();
    String userName = "userTest";
    String password = "passTest";
    String shopManagerName = "shakedMember";
    String shopManagerPassword = "shaked1234";
    String shopName = "kolbo2";
    Double productAmount;
    Double productPrice;
    CreditCard creditCard;
    Address address;
    Item milk;

    @BeforeAll
    public void setUpMember() {
        try {
            market = Market.getInstance();
            if (market.getPaymentService() == null)
                market.firstInitMarket(paymentService, supplyService, userName, password);
            // shop manager register
            Visitor visitor = market.guestLogin();
            market.register(shopManagerName, shopManagerPassword);
            List<String> questions = market.memberLogin(shopManagerName, shopManagerPassword);
            market.validateSecurityQuestions(shopManagerName, new ArrayList<>(), visitor.getName());
            // open shop
            market.openNewShop(shopManagerName, shopName);
            productAmount = 3.0;
            productPrice = 1.2;
            List<String> keywords = new ArrayList<>();
            keywords.add("in sale");
            market.addItemToShop(shopManagerName, "milk", productPrice, Item.Category.general,
                    "soy",keywords , productAmount,shopName);
            List<Item> res = market.getItemByName("milk");
            milk = res.get(0);
            testMemberName = "managerTest";
            testMemberPassword = "1234";
            Visitor visitor2 = market.guestLogin();
            market.register(testMemberName, testMemberPassword);
            market.memberLogin(testMemberName, testMemberPassword);
            market.validateSecurityQuestions(testMemberName, new ArrayList<>(), visitor2.getName());
            creditCard = new CreditCard("124","13/5" , "555");
            address = new Address("Tel Aviv", "Super" , "1");


        } catch (Exception ignored) {
            System.out.printf(ignored.getMessage());
        }
    }

    @BeforeEach
    public void resetMember() {
        try {
            if (!UserController.getInstance().isLoggedIn(testMemberName)) {
                Visitor visitor = market.guestLogin();
                List<String> questions = market.memberLogin(testMemberName, testMemberPassword);
                market.validateSecurityQuestions(testMemberName, new ArrayList<>(), visitor.getName());
            }
            testMember = UserController.getInstance().getMember(testMemberName);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @AfterEach
    public void endOfMemberTest() throws Exception {
        market.memberLogout(testMember.getName());
    }

    @Test
    @DisplayName("open new shop")
    public void openNewShop() {
        try {
            String shopTest = "shopNewName";
            market.openNewShop(testMember.getName(), shopTest);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("open shop with visitor - fails")
    public void visitorOpenShop() {
        // open with visitor
        try {
            Visitor visitor = market.guestLogin();
            market.openNewShop(visitor.getName(), "testHere");
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("member opens multiple shops")
    public void openMultipleShops() {
        try {
            String shopTest = "shopTest2";
            market.openNewShop(testMember.getName(), shopTest);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("open shop with no name  - fails")
    public void openShopWithNoName() {
        try {
            String shopTest = "";
            market.openNewShop(testMember.getName(), shopTest);
            assert false;
        } catch (Exception e) {
            assert true;
        }

    }

    @Test
    @DisplayName("open shop with used name")
    public void usedNameOpenShop() {
        try {
            String shopTest = "shopTest";
            market.openNewShop(testMember.getName(), shopTest);
            market.openNewShop(testMember.getName(), shopTest);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

//    @Test
//    @DisplayName("logout - check member saved")
//    public void checkMemberSaved() {
//        try {
//            market.addItemToShoppingCart(milk, productAmount-1,shopName,testMemberName);
//            ShoppingCart prevCart = testMember.getMyCart();
//            String visitorName = market.memberLogout(testMember.getName());
//            Visitor visitor = UserController.getInstance().getVisitor(visitorName);
//            List<String> questions = market.memberLogin(testMember.getName(), testMemberPassword);
//            market.validateSecurityQuestions(testMember.getName(), new ArrayList<>(),
//                    visitorName);
//            Member returnedMember = visitor.getMember();
//            Assertions.assertEquals(returnedMember.getAppointedByMe(), testMember.getAppointedByMe());
//            Assertions.assertEquals(returnedMember.getName(), testMember.getName());
//            Assertions.assertEquals(returnedMember.getMyAppointments(), testMember.getMyAppointments());
//            if (!(testMember.getMyCart() == returnedMember.getMyCart())) {
//                assert testMember.getMyCart().getCart().size() == returnedMember.getMyCart().getCart().size();
//                // for each shop - check equals
//                prevCart.getCart().forEach((shop, prevBasket) -> {
//                    assert returnedMember.getMyCart().getCart().containsKey(shop);
//                    ShoppingBasket newBasket = returnedMember.getMyCart().getCart().get(shop);
//                    // for each item in shopping basket
//                    prevBasket.getItems().forEach((item, amount) -> {
//                        assert newBasket.getItems().size() == prevBasket.getItems().size();
//                        assert newBasket.getItems().containsKey(item);
//                        assert newBasket.getItems().get(item).equals(amount);
//                    });
//                });
//            }
//            // for each shopping basket - checks equals
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }

//    @Test
//    @DisplayName("login twice")
//    public void loginTwice() {
//        try {
//            Visitor visitor = market.guestLogin();
//            List<String> questions = market.memberLogin(testMemberName, testMemberPassword);
//            Member newMember = market.validateSecurityQuestions(testMemberName, new ArrayList<>(), visitor.getName());
//            Assertions.assertNull(newMember);
//            assert false;
//        } catch (Exception e) {
//            assert false;
//        }
//    }
//
//    @Test
//    @DisplayName("member exit system logs out")
//    public void memberExitSystem() {
//        try {
//            market.visitorExitSystem(testMember.getName());
//            Visitor visitor = market.guestLogin();
//            List<String> questions = market.memberLogin(testMemberName, testMemberPassword);
//            testMember = market.validateSecurityQuestions(testMemberName, new ArrayList<>(), visitor.getName());
//            assert testMember != null;
//        } catch (Exception e) {
//            assert false;
//        }
//    }
//
//    @Test
//    @DisplayName("add question")
//    public void addQuestion() {
//        try {
//            Visitor visitor = market.guestLogin();
//            String currName = "questionsName";
//            String password = "1234";
//            market.register(currName, password);
//            List<String> questions = market.memberLogin(currName, password);
//            assert questions.size() == 0;
//            Member member = market.validateSecurityQuestions(currName, new ArrayList<>(), visitor.getName());
//            market.addPersonalQuery("whats your mother's name?", "idk", member.getName());
//            String visitorName = market.memberLogout(member.getName());
//            questions = market.memberLogin(currName, password);
//            assert questions.size() == 1;
//            assert questions.contains("whats your mother's name?");
//            ArrayList<String> answers = new ArrayList<>();
//            answers.add("idk");
//            member = market.validateSecurityQuestions(currName, answers, member.getName());
//            assert member != null;
//
//        } catch (Exception e) {
//            assert false;
//        }
//    }
//
//    @Test
//    @DisplayName("invalid authentication")
//    public void invalidAuthentication() {
//        try {
//            Visitor visitor = market.guestLogin();
//            String currName = "questionsName2";
//            String password = "1234";
//            market.register(currName, password);
//            List<String> questions = market.memberLogin(currName, password);
//            Member member = market.validateSecurityQuestions(currName, new ArrayList<>(), visitor.getName());
//            market.addPersonalQuery("whats your mother's name?", "idk", member.getName());
//            String visitorName = market.memberLogout(member.getName());
//            questions = market.memberLogin(currName, "123");
//            try {
//                questions = market.memberLogin(currName, "123");
//                assert false;
//            } catch (Exception ignored) {
//            }
//            questions = market.memberLogin(currName, password);
//            ArrayList<String> answers = new ArrayList<>();
//            answers.add("idk1");
//            try {
//                member = market.validateSecurityQuestions(currName, answers, member.getName());
//                assert member == null;
//            } catch (Exception ignored) {
//            }
//            answers.clear();
//            answers.add("idk");
//            assert member != null;
//            member = market.validateSecurityQuestions(currName, answers, member.getName());
//        } catch (Exception e) {
//            assert false;
//        }
//    }



}
