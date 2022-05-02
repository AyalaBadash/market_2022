package com.example.server.AcceptanceTest;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.CreditCard;
import com.example.server.ResourcesObjects.PaymentMethod;
import com.example.server.ServerApplication;
import com.example.server.businessLayer.Item;
import com.example.server.serviceLayer.AppointmentShopManagerRequest;
import com.example.server.serviceLayer.FacadeObjects.*;
import com.example.server.serviceLayer.Requests.*;
import com.example.server.serviceLayer.Response;
import com.example.server.serviceLayer.ResponseT;
import com.example.server.serviceLayer.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.*;

import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

//@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ServerApplication.class)
@AutoConfigureMockMvc
public class AcceptanceTestAll {


    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    protected HttpMessageConverter mappingJakson2HttpMessageConverter;
    protected String tokenStr;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Service service;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    // common fields
    Gson gson = new Gson();
    VisitorFacade curVisitor;
    String shopOwnerName = "shaked";
    String shopOwnerPassword = "shaked1234";
    String shopName = "kolbo";
    AcceptanceTestService config = new AcceptanceTestService();
    Double productAmount;
    Double productPrice;
    CreditCard creditCard;
    Address address;
    ItemFacade milk;


    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJakson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
        assertNotNull("the Json message converter must not be null", this.mappingJakson2HttpMessageConverter);
    }

    // ############################### SETUP #######################################

    @BeforeAll
    public void setup() {
        try {
            initMarket();
            // shop manager register
            VisitorFacade visitor = guestLogin();
            register(shopOwnerName, shopOwnerPassword);
            List<String> questions = memberLogin(shopOwnerName, shopOwnerPassword);
            validateSecurityQuestions(shopOwnerName, new ArrayList<>(), visitor.getName());
            // open shop
            openShop(shopOwnerName, shopName);
            productAmount = 3.0;
            productPrice = 1.2;
            addItemToShop(shopOwnerName, "milk", productPrice, Item.Category.general,
                    "soy", new ArrayList<>(), productAmount, shopName);
            List<ItemFacade> res = searchProductByName("milk");
            milk = res.get(0);
            creditCard = new CreditCard("124", "13/5", "555");
            address = new Address("Tel Aviv", "Super", "1");
        } catch (Exception Ignored) {
        }
    }

    @BeforeEach
    public void reset() throws Exception {
        setItemCurrentAmount(shopOwnerName, milk, productAmount, shopName);
    }
    // ############################### TEST CLASSES #######################################

    // ############################### SYSTEM #######################################

    @Nested
    class System {
        @Test
        @DisplayName("init market twice test - fail")
        public void initTwice() {
            try {
                Response result = initMarket();
                assert result.isErrorOccurred();
            } catch (Exception e) {
                // should return as result fail and not as exception
                assert false;
            }
            ;
        }
    }

    // ############################### VISITOR #######################################

    @Nested
    class VisitorTest {

        @Test
        @DisplayName("valid guest login")
        public void guestLoginValid() throws Exception {
//        initMarket();
            String methodCall = "/guestLogin";
            try {
                MvcResult res = mvc.perform(post(methodCall)).andReturn();
                ResponseT<VisitorFacade> result = deserialize(res, new TypeToken<ResponseT<VisitorFacade>>() {
                }.getType());
                assert !result.isErrorOccurred();
                VisitorFacade visitor = result.getValue();
                Assertions.assertNotNull(visitor.getCart());
            } catch (Exception e) {
                assert false;
            }

        }

        @Test
        @DisplayName("guest leaves the market")
        public void guestExitMarket() throws Exception {

            VisitorFacade visitor = guestLogin();
            try {
                Response result = exitMarket(visitor.getName());
                assert !result.isErrorOccurred();
                VisitorFacade visitor2 = guestLogin();
                assert !(visitor2.getName().equals(visitor.getName()));

            } catch (Exception e) {
                assert false;
            }
        }


        @Test
        @DisplayName("register taken name")
        public void registerTest() {
            try {
                VisitorFacade visitor = guestLogin();
                ResponseT<Boolean> res = register("registerTest", "1234R");
                assert !res.isErrorOccurred();
                ResponseT<Boolean> res2 = register("registerTest", "1234R");
                assert res2.isErrorOccurred();

            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("get shop info")
        public void shopInfoTest() {
            try {
                VisitorFacade visitor = guestLogin();
                ShopFacade res = getShopInfo(visitor.getName(), shopName);
                assert res.getShopName().equals(shopName);
                exitMarket(visitor.getName());
            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("get item info")
        public void itemInfoTest() {
            // not implemented in service while requirement number 2.1 demands it
            assert false;
        }

        @Test
        @DisplayName("search item by name")
        public void searchItemName() {
            try {
                VisitorFacade visitor = guestLogin();
                List<ItemFacade> res = searchProductByName("milk");
                assert res.size() > 0;
                assert res.get(0).getName().equals("milk");
            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("add item to cart")
        public void addItemCart() {
            try {
                VisitorFacade visitor = guestLogin();
                List<ItemFacade> res = searchProductByName("milk");
                ItemFacade milk = res.get(0);
                Response response = addItemToCart(milk, 3, shopName, visitor.getName());
                assert !response.isErrorOccurred();
                // TODO need to think how to refresh visitor's cart
                //  idea - return the new cart/visitor

                //check shopping basket includes only the milk
                assert visitor.getCart().getCart().size() == 1;
                visitor.getCart().getCart().forEach((shop, basket) -> {
                    assert basket.getItems().size() == 1;
                    assert shop.getShopName().equals(shopName);
                    // check shop amount didn't change
                    assert shop.getItemsCurrentAmount().get(milk).equals(productAmount);
                    // check right amount added
                    assert basket.getItems().get(milk).equals(3.0);
                });
                // checks adding item
                response = addItemToCart(milk, 6, shopName, visitor.getName());
                assert !response.isErrorOccurred();
                visitor.getCart().getCart().forEach((shop, basket) -> {
                    assert basket.getItems().get(milk).equals(9.0);
                });
            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("add item to cart, exit - empty cart")
        public void emptyCartWhenExit() {
            try {
                VisitorFacade visitor = guestLogin();
                List<ItemFacade> res = searchProductByName("milk");
                ItemFacade milk = res.get(0);
                Response response = addItemToCart(milk, 3, shopName, visitor.getName());
                assert !response.isErrorOccurred();
                assert !visitor.getCart().getCart().isEmpty();
                exitMarket(visitor.getName());
                visitor = guestLogin();
                assert visitor.getCart().getCart().isEmpty();
            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("add item to cart, 0 amount")
        public void addZeroAmount() {
            try {
                VisitorFacade visitor = guestLogin();
                List<ItemFacade> res = searchProductByName("milk");
                ItemFacade milk = res.get(0);
                Response response = addItemToCart(milk, 0, shopName, visitor.getName());
                assert !response.isErrorOccurred();
                assert visitor.getCart().getCart().isEmpty();
            } catch (Exception e) {
                assert false;
            }
        }


        @Test
        @DisplayName("buy item, valid amount")
        public void buyItemValid() {
            try {
                VisitorFacade visitor = guestLogin();
                ShopFacade shop = getShopInfo(shopOwnerName, shopName);
                List<ItemFacade> res = searchProductByName("milk");
                ItemFacade milk = res.get(0);
                Double itemAmount = shop.getItemsCurrentAmount().get(milk);
                double buyingAmount = itemAmount - 1;
                Response response = addItemToCart(milk, buyingAmount, shopName, visitor.getName());
                Response result = buyShoppingCart(visitor.getName(), productPrice * buyingAmount, creditCard, address);
                assert !result.isErrorOccurred();
                shop = getShopInfo(shopOwnerName, shopName);
                Double newAMount = shop.getItemsCurrentAmount().get(milk);
                assert newAMount == 1;
                itemAmount = newAMount;

            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("buy not existing item")
        public void buyWithUnexpectedPrice() {
            try {
                VisitorFacade visitor = guestLogin();
                ShopFacade shop = getShopInfo(shopOwnerName, shopName);
                List<ItemFacade> res = searchProductByName("milk");
                ItemFacade milk = res.get(0);
                Double itemAmount = shop.getItemsCurrentAmount().get(milk);
                double buyingAmount = itemAmount + 1;
                Response response = addItemToCart(milk, buyingAmount, shopName, visitor.getName());
                // add not existing item shouldn't fail
                assert !response.isErrorOccurred();
                Response result = buyShoppingCart(visitor.getName(), productPrice * buyingAmount, creditCard, address);
                assert result.isErrorOccurred();
                shop = getShopInfo(shopOwnerName, shopName);
                Double newAMount = shop.getItemsCurrentAmount().get(milk);
                Assertions.assertEquals(newAMount, itemAmount);

            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("buy cart with unexpected price")
        public void buyNotExistingItem() {
            try {
                VisitorFacade visitor = guestLogin();
                ShopFacade shop = getShopInfo(shopOwnerName, shopName);
                List<ItemFacade> res = searchProductByName("milk");
                ItemFacade milk = res.get(0);
                Double itemAmount = shop.getItemsCurrentAmount().get(milk);
                double buyingAmount = itemAmount;
                Response response = addItemToCart(milk, buyingAmount, shopName, visitor.getName());
                // add not existing item shouldn't fail
                assert !response.isErrorOccurred();
                Response result = buyShoppingCart(visitor.getName(), productPrice * buyingAmount + 1, creditCard, address);
                assert result.isErrorOccurred();
                shop = getShopInfo(shopOwnerName, shopName);
                Double newAMount = shop.getItemsCurrentAmount().get(milk);
                Assertions.assertEquals(newAMount, itemAmount);

            } catch (Exception e) {
                assert false;
            }
        }

    }

    // ############################### MEMBER #######################################


    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Member {
        MemberFacade testMember;
        String testMemberPassword;
        String testMemberName;

        @BeforeAll
        public void setUpMember() {
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
                // TODO need to add item to cart if setup first
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
                member = validateSecurityQuestions(currName, answers, member.getName());
                assert member != null;
            } catch (Exception e) {
                assert false;
            }
        }

    }

    // ############################### SHOP OWNER #######################################

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ShopOwner {


        @Test
        @Order(1)
        @DisplayName("add new item")
        public void addNewItem() {
            try {
                Response response = addItemToShop(shopOwnerName, "steak", 10, Item.Category.meat,
                        " best in town", new ArrayList<>(), 5.0, shopName);
                assert !response.isErrorOccurred();
                // TODO maybe add field to item - shop name. search would return a list of item where we
                //  wouldn't know how to take it
                ShopFacade shop = getShopInfo(shopOwnerName, shopName);
                boolean found = false;
                for (Map.Entry<ItemFacade, Double> itemAmount : shop.getItemsCurrentAmount().entrySet()) {
                    ItemFacade item = itemAmount.getKey();
                    Double amount = itemAmount.getValue();
                    if (item.getName().equals("steak")) {
                        found = amount.equals(5.0);
                        break;
                    }
                }
                assert found;

            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @Order(2)
        @DisplayName("add existing item")
        public void addExistingItem() {
            try {
                Response response = addItemToShop(shopOwnerName, "milk", productPrice, Item.Category.general,
                        "soy", new ArrayList<>(), 10, shopName);
                assert response.isErrorOccurred();
                ShopFacade shop = getShopInfo(shopOwnerName, shopName);
                boolean found = false;
                for (Map.Entry<ItemFacade, Double> itemAmount : shop.getItemsCurrentAmount().entrySet()) {
                    ItemFacade item = itemAmount.getKey();
                    Double amount = itemAmount.getValue();
                    if (item.getName().equals("steak")) {
                        found = amount.equals(productAmount);
                        break;
                    }
                }
                assert found;

            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @Order(3)
        @DisplayName("set item amount - valid")
        public void setValidItemAmount() {
            try {

                Response response = setItemCurrentAmount(shopOwnerName, milk, 8.0, shopName);
                assert !response.isErrorOccurred();
                ShopFacade shop = getShopInfo(shopOwnerName, shopName);
                boolean found = false;
                for (Map.Entry<ItemFacade, Double> itemToAmount : shop.getItemsCurrentAmount().entrySet()) {
                    ItemFacade item = itemToAmount.getKey();
                    Double amount = itemToAmount.getValue();
                    if (found) break;
                    found = item.getName().equals("milk") && amount.equals(8.0);
                }
                assert found;

            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("set item amount 0")
        public void setValidItemAmountZero() {
            try {

                Response response = setItemCurrentAmount(shopOwnerName, milk, 0, shopName);
                assert !response.isErrorOccurred();
                ShopFacade shop = getShopInfo(shopOwnerName, shopName);
                boolean found = false;
                for (Map.Entry<ItemFacade, Double> itemToAmount : shop.getItemsCurrentAmount().entrySet()) {
                    ItemFacade item = itemToAmount.getKey();
                    Double amount = itemToAmount.getValue();
                    if (found) break;
                    found = item.getName().equals("milk") && amount.equals(0.0);
                }
                assert found;

            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("set item amount negative")
        public void setValidItemAmountNegative() {
            try {
                Response response = setItemCurrentAmount(shopOwnerName, milk, -1, shopName);
                assert response.isErrorOccurred();
            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("change item info")
        public void setItemInfo() {
            try {
                String newInfo = "out of Date!";
                ItemFacade sourMilk = new ItemFacade(-1, milk.getName(), milk.getPrice(), milk.getCategory(), milk.getKeywords(), newInfo);
                Response response = changeShopItemInfo(shopOwnerName, sourMilk, milk, shopName);
                assert !response.isErrorOccurred();
                ShopFacade newShop = getShopInfo(shopOwnerName, shopName);
                boolean found = false;
                for (Map.Entry<Integer, ItemFacade> idToItem : newShop.getItemMap().entrySet()) {
                    ItemFacade item = idToItem.getValue();
                    if (item.getName().equals(milk.getName())) {
                        found = item.getInfo().equals(newInfo);
                        break;
                    }
                }
                assert found;

            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("change item info to empty")
        public void changeToEmptyInfo() {
            try {
                String newInfo = "";
                ItemFacade sourMilk = new ItemFacade(-1, milk.getName(), milk.getPrice(), milk.getCategory(), milk.getKeywords(), newInfo);
                Response response = changeShopItemInfo(shopOwnerName, sourMilk, milk, shopName);
                assert !response.isErrorOccurred();
                ShopFacade newShop = getShopInfo(shopOwnerName, shopName);
                boolean found = false;
                for (Map.Entry<Integer, ItemFacade> idToItem : newShop.getItemMap().entrySet()) {
                    ItemFacade item = idToItem.getValue();
                    if (item.getName().equals(milk.getName())) {
                        found = item.getInfo().equals(newInfo);
                        break;
                    }
                }
                assert found;

            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("appoint new shop owner")
        public void appointOwner() {
            try {
                VisitorFacade nextOwner = guestLogin();
                String nextOwnerName = "raz1";
                String nextOwnerPass = "123";
                Response response = register(nextOwnerName, nextOwnerPass);
                ShopFacade shop = getShopInfo(shopOwnerName, shopName);
                int prevAppoints = shop.getEmployees().size();
                response = appointShopOwner(shopOwnerName, nextOwnerName, shopName);
                assert !response.isErrorOccurred();
                shop = getShopInfo(shopOwnerName, shopName);
                assert shop.getEmployees().size() + 1 == prevAppoints;
            } catch (Exception e) {
                assert false;
            }
        }

        @Test
        @DisplayName("appoint new shop manager")
        public void appointShopManagerTest() {
            try {
                VisitorFacade nextOwner = guestLogin();
                String nextOwnerName = "raz2";
                String nextOwnerPass = "123";
                Response response = register(nextOwnerName, nextOwnerPass);
                ShopFacade shop = getShopInfo(shopOwnerName, shopName);
                int prevAppoints = shop.getEmployees().size();
                response = appointShopManager(shopOwnerName, nextOwnerName, shopName);
                assert !response.isErrorOccurred();
                shop = getShopInfo(shopOwnerName, shopName);
                assert shop.getEmployees().size() + 1 == prevAppoints;
            } catch (Exception e) {
                assert false;
            }
        }


        @Test
        @DisplayName("close shop")
        public void closeShopTest() {
            try{
                VisitorFacade visitor = guestLogin();
                String password = "9may";
                String name = "razBam";
                register(name, password);
                List<String> questions = memberLogin(name, password);
                validateSecurityQuestions(name, new ArrayList<>(), visitor.getName());
                // open shop
                String shopName = "RealMadrid_Fuckers";
                openShop(name, shopName);
                ShopFacade shop = getShopInfo(name,shopName);
                Response response = closeShop(name, shopName);
                assert !response.isErrorOccurred();
                try{
                    shop = getShopInfo(name,shopName);
                    assert false;
                }catch (Exception e){assert true;};

            } catch (Exception e) {
                assert false;
            }
        }

    }


    // ############################### SHOP MANAGER #######################################

    // TODO to add test
    //      check permissions
    //
    //

    // ############################### SYSTEM MANAGER #######################################
    // TODO to add test
    //      get all purchase history
    //
    //
    //########################################### SERVICE METHODS ##########################################3

    // TODO go through all service methods - check everything is used
    public VisitorFacade guestLogin() throws Exception {
        String methodCall = "/guestLogin";
        MvcResult res = mvc.perform(post(methodCall)).andReturn();
        Type type = new TypeToken<ResponseT<VisitorFacade>>() {
        }.getType();
        ResponseT<VisitorFacade> result = deserialize(res, type);
        VisitorFacade visitor = result.getValue();
        return visitor;
    }

    private Response exitMarket(String name) throws Exception {
        String methodCall = "/exitSystem";
        ExitSystemRequest request = new ExitSystemRequest(name);
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Response result = deserialize(res, Response.class);
        return result;
    }

    public ResponseT<Boolean> register(String name, String password) throws Exception {
        NamePasswordRequest request = new NamePasswordRequest(name, password);
        String methodCall = "/register";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type = new TypeToken<ResponseT<Boolean>>() {
        }.getType();
        ResponseT<Boolean> result = deserialize(res, type);
        return result;
    }

    public MemberFacade validateSecurityQuestions(String userName, List<String> answers, String visitorName) {
        ValidateSecurityRequest request = new ValidateSecurityRequest(userName, answers, visitorName);
        String methodCall = "/validateSecurityQuestions";
        try {
            MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                            content(toHttpRequest(request)).contentType(contentType))
                    .andExpect(status().isOk())
                    .andReturn();
            Type type = new TypeToken<ResponseT<MemberFacade>>() {
            }.getType();
            ResponseT<MemberFacade> result = deserialize(res, type);
            return result.getValue();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> memberLogin(String name, String password) throws Exception {
        NamePasswordRequest request = new NamePasswordRequest(name, password);
        String methodCall = "/memberLogin";

        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type = new TypeToken<ResponseT<List<String>>>() {
        }.getType();
        ResponseT<List<String>> result = deserialize(res, type);
        return result.getValue();

    }

    public Response openShop(String managerName, String shopName) throws Exception {
        OpenNewShopRequest request = new OpenNewShopRequest(managerName, shopName);
        String methodCall = "/openNewShop";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type = new TypeToken<Response>() {
        }.getType();
        Response result = deserialize(res, type);
        return result;


    }

    public Response closeShop(String shopOwnerName, String shopName) throws Exception {
        CloseShopRequest request = new CloseShopRequest(shopOwnerName, shopName);
        String methodCall = "/closeShop";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type = new TypeToken<Response>() {
        }.getType();
        Response result = deserialize(res, type);
        return result;
    }

    public List<ItemFacade> searchProductByName(String productName) throws Exception {
        SearchProductByNameRequest request = new SearchProductByNameRequest(productName);
        String methodCall = "/searchProductByName";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type = new TypeToken<ResponseT<List<ItemFacade>>>() {
        }.getType();
        ResponseT<List<ItemFacade>> result = deserialize(res, type);
        return result.getValue();
    }

    public Response addItemToShop(String shopOwnerName, String name, double price,
                                  Item.Category category, String info,
                                  List<String> keywords, double amount, String shopName) throws Exception {
        AddItemToShopRequest request = new AddItemToShopRequest(shopOwnerName, name, price,
                category, info, keywords, amount, shopName);
        String methodCall = "/addItemToShop";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type = new TypeToken<Response>() {
        }.getType();
        Response result = deserialize(res, type);
        return result;

    }

    public Response addItemToCart(ItemFacade itemToInsert, double amount, String shopName, String visitorName) throws Exception {
        AddItemToShoppingCartRequest request = new AddItemToShoppingCartRequest(itemToInsert, amount, shopName, visitorName);
        String methodCall = "/addItemToShoppingCart";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type = new TypeToken<Response>() {
        }.getType();
        Response result = deserialize(res, type);
        return result;
    }

    public VisitorFacade logout(String name) throws Exception {
        RequestVisitorName request = new RequestVisitorName(name);
        String methodCall = "/logout";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type = new TypeToken<ResponseT<VisitorFacade>>() {
        }.getType();
        ResponseT<VisitorFacade> result = deserialize(res, type);
        return result.getValue();
    }

    public ShopFacade getShopInfo(String name, String shopName) throws Exception {
        TwoStringRequest request = new TwoStringRequest(name, shopName);
        String methodCall = "/getShopInfo";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type = new TypeToken<ResponseT<ShopFacade>>() {
        }.getType();
        ResponseT<ShopFacade> result = deserialize(res, type);
        return result.getValue();
    }

    public Response buyShoppingCart(String visitorName, double expectedPrice, PaymentMethod paymentMethod, Address address) throws Exception {
        BuyShoppingCartRequest request = new BuyShoppingCartRequest(visitorName, expectedPrice, paymentMethod, address);
        String methodCall = "/buyShoppingCart";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        return deserialize(res, Response.class);
    }

    public Response addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, String member) throws Exception {
        AddPersonalQueryRequest request = new AddPersonalQueryRequest(userAdditionalQueries, userAdditionalAnswers, member);
        String methodCall = "/addPersonalQuery";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Response result = deserialize(res, Response.class);
        return result;
    }

    public Response setItemCurrentAmount(String shopOwnerName, ItemFacade item, double amount,
                                         String shopName) throws Exception {
        SetItemCurrentAmountRequest request = new SetItemCurrentAmountRequest(shopOwnerName, item,
                amount, shopName);
        String methodCall = "/setItemCurrentAmount";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Response result = deserialize(res, Response.class);
        return result;

    }

    public Response changeShopItemInfo(String shopOwnerName, ItemFacade updatedItem, ItemFacade oldItem, String shopName) throws Exception {
        ChangeShopItemInfoRequest request = new ChangeShopItemInfoRequest(shopOwnerName, updatedItem,
                oldItem, shopName);
        String methodCall = "/changeShopItemInfo";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Response result = deserialize(res, Response.class);
        return result;
    }


    public Response appointShopOwner(String shopOwnerName, String appointedShopOwner, String shopName) throws Exception {
        AppointmentShopOwnerRequest request = new AppointmentShopOwnerRequest(shopOwnerName, appointedShopOwner, shopName);
        String methodCall = "/appointShopOwner";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Response result = deserialize(res, Response.class);
        return result;
    }

    public Response appointShopManager(String shopOwnerName, String appointedShopManager, String shopName) throws Exception {
        AppointmentShopManagerRequest request = new AppointmentShopManagerRequest(shopOwnerName, appointedShopManager, shopName);
        String methodCall = "/appointShopManager";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Response result = deserialize(res, Response.class);
        return result;

    }

    public List<AppointmentFacade> getShopEmployeesInfo(String shopManagerName, String shopName) throws Exception {
        GetShopEmployeesRequest request = new GetShopEmployeesRequest(shopManagerName, shopName);
        String methodCall = "/getShopEmployeesInfo";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type = new TypeToken<ResponseT<List<AppointmentFacade>>>() {
        }.getType();
        ResponseT<List<AppointmentFacade>> appointmentFacadeList = deserialize(res, type);
        return appointmentFacadeList.getValue();
    }
    public Response initMarket() throws Exception {
        InitMarketRequest request = new InitMarketRequest(config.systemManagerName, config.systemManagerPassword);
        String methodCall = "/firstInitMarket";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        return deserialize(res, Response.class);

    }

    protected String toHttpRequest(Object obj) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJakson2HttpMessageConverter.write(
                obj, MediaType.APPLICATION_JSON,
                mockHttpOutputMessage);
        String request = mockHttpOutputMessage.getBodyAsString();
        return request;
    }

    protected <T> T deserialize(MvcResult res, Class<T> classType) throws UnsupportedEncodingException, JsonProcessingException {
        String json = res.getResponse().getContentAsString();
        T result = gson.fromJson(json, classType);
        return result;
    }

    protected <T> T deserialize(MvcResult res, Type classType) throws UnsupportedEncodingException, JsonProcessingException {
        String json = res.getResponse().getContentAsString();
        T result = gson.fromJson(json, classType);
        return result;
    }

}
