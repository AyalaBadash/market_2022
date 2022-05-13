package com.example.server.AcceptanceTest;

import com.example.server.serviceLayer.FacadeObjects.MemberFacade;
import com.example.server.serviceLayer.FacadeObjects.ShoppingBasketFacade;
import com.example.server.serviceLayer.FacadeObjects.ShoppingCartFacade;
import com.example.server.serviceLayer.FacadeObjects.VisitorFacade;
import com.example.server.serviceLayer.Response;
import com.example.server.serviceLayer.ResponseT;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class MemberAcceptanceTests extends AcceptanceTests {
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Member {
        MemberFacade testMember;
        static String testMemberPassword;
        static String testMemberName;

        @BeforeAll
        public static void setUpMember() {
            try {
                testMemberName = "managerTest";
                testMemberPassword = "1234";

            } catch (Exception ignored) {

            }
        }

        @BeforeEach
        public void resetMember() {
            try {
                VisitorFacade visitor = guestLogin();
                register(testMemberName, testMemberPassword);
                List<String> questions = memberLogin(testMemberName, testMemberPassword);
                testMember = validateSecurityQuestions(testMemberName, new ArrayList<>(), visitor.getName());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        @AfterEach
        public void endOfMemberTest() throws Exception {
            logout(testMember.getName());
        }

        @Test
        @Order(1)
        @DisplayName("open a new shop")
        public void openNewShop() {
            try {
                String shopTest = "shopTest";
                Response result = openShop(testMember.getName(), shopTest);
                assert !result.isErrorOccurred();
            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("open shop with visitor - fails")
        public void visitorOpenShop() {
            // open with visitor
            try {
                VisitorFacade visitor = guestLogin();
                Response result = openShop(visitor.getName(), "testHere");
                assert result.isErrorOccurred();
            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("member opens multiple shops")
        public void openMultipleShops() {
            try {
                String shopTest = "shopTest2";
                Response result = openShop(testMember.getName(), shopTest);
                assert !result.isErrorOccurred();
            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("open shop with no name  - fails")
        public void openShopWithNoName() {
            try {
                String shopTest = "";
                Response result = openShop(testMember.getName(), shopTest);
                assert result.isErrorOccurred();
            } catch (Exception e) {
                assert false;
            }

        }


        @Test
        @Order(2)
        @DisplayName("open shop with used name")
        public void usedNameOpenShop() {
            try {
                String shopTest = "shopTest";
                Response result = openShop(testMember.getName(), shopTest);
                assert result.isErrorOccurred();
            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("logout - check member saved")
        public void checkMemberSaved() {
            try {
                addItemToCart(milk, productAmount - 1, shopName, testMemberName);
                ShoppingCartFacade prevCart = testMember.getMyCart();
                VisitorFacade visitor = logout(testMember.getName());
                assert visitor.getCart().getCart().isEmpty();
                List<String> questions = memberLogin(testMember.getName(), testMemberPassword);
                MemberFacade returnedMember = validateSecurityQuestions(testMember.getName(), new ArrayList<>(),
                        visitor.getName());
                Assertions.assertEquals(returnedMember.getAppointedByMe(), testMember.getAppointedByMe());
                Assertions.assertEquals(returnedMember.getName(), testMember.getName());
                Assertions.assertEquals(returnedMember.getMyAppointments(), testMember.getMyAppointments());
                if (!(testMember.getMyCart() == returnedMember.getMyCart())) {
                    assert testMember.getMyCart().getCart().size() == returnedMember.getMyCart().getCart().size();
                    // for each shop - check equals
                    prevCart.getCart().forEach((shop, prevBasket) -> {
                        assert returnedMember.getMyCart().getCart().containsKey(shop);
                        ShoppingBasketFacade newBasket = returnedMember.getMyCart().getCart().get(shop);
                        // for each item in shopping basket
                        prevBasket.getItems().forEach((item, amount) -> {
                            assert newBasket.getItems().size() == prevBasket.getItems().size();
                            assert newBasket.getItems().containsKey(item);
                            assert newBasket.getItems().get(item).equals(amount);
                        });
                    });
                }
                // for each shopping basket - checks equals
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        @Test
        @DisplayName("login twice")
        public void loginTwice() {
            try {
                VisitorFacade visitor = guestLogin();
                List<String> questions = memberLogin(testMemberName, testMemberPassword);
                MemberFacade newMember = validateSecurityQuestions(testMemberName, new ArrayList<>(), visitor.getName());
                Assertions.assertNull(newMember);
                assert false;
            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("member exit system logs out")
        public void memberExitSystem() {
            try {
                Response response = exitMarket(testMember.getName());
                assert !response.isErrorOccurred();
                VisitorFacade visitor = guestLogin();
                List<String> questions = memberLogin(testMemberName, testMemberPassword);
                testMember = validateSecurityQuestions(testMemberName, new ArrayList<>(), visitor.getName());
                assert testMember != null;
            } catch (Exception e) {
                assert false;
            }

        }

        @Test
        @DisplayName("add question")
        public void addQuestion() {
            try {
                VisitorFacade visitor = guestLogin();
                String currName = "questionsName";
                String password = "1234";
                ResponseT<Boolean> response = register(currName, password);
                assert !response.isErrorOccurred();
                List<String> questions = memberLogin(currName, password);
                assert questions.size() == 0;
                MemberFacade member = validateSecurityQuestions(currName, new ArrayList<>(), visitor.getName());
                Response queryResponse = addPersonalQuery("whats your mother's name?", "idk", member.getName());
                assert !queryResponse.isErrorOccurred();
                visitor = logout(member.getName());
                questions = memberLogin(currName, password);
                assert questions.size() == 1;
                assert questions.contains("whats your mother's name?");
                ArrayList<String> answers = new ArrayList<>();
                answers.add("idk");
                member = validateSecurityQuestions(currName, answers, member.getName());
                assert member != null;

            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("invalid authentication")
        public void invalidAuthentication() {
            try {
                VisitorFacade visitor = guestLogin();
                String currName = "questionsName2";
                String password = "1234";
                ResponseT<Boolean> response = register(currName, password);
                List<String> questions = memberLogin(currName, password);
                MemberFacade member = validateSecurityQuestions(currName, new ArrayList<>(), visitor.getName());
                Response queryResponse = addPersonalQuery("whats your mother's name?", "idk", member.getName());
                visitor = logout(member.getName());
                questions = memberLogin(currName, "123");
                try {
                    questions = memberLogin(currName, "123");
                    assert false;
                } catch (Exception ignored) {
                }
                questions = memberLogin(currName, password);
                ArrayList<String> answers = new ArrayList<>();
                answers.add("idk1");
                try {
                    member = validateSecurityQuestions(currName, answers, member.getName());
                    assert member == null;
                } catch (Exception ignored) {
                }
                answers.clear();
                answers.add("idk");
                assert member != null;
                member = validateSecurityQuestions(currName, answers, member.getName());
            } catch (Exception e) {
                assert false;
            }
        }
    }
}
