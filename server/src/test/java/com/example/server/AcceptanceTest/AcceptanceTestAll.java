package com.example.server.AcceptanceTest;

import com.example.server.ServerApplication;
import com.example.server.serviceLayer.FacadeObjects.*;
import com.example.server.serviceLayer.Requests.*;
import com.example.server.serviceLayer.Response;
import com.example.server.serviceLayer.ResponseT;
import com.example.server.serviceLayer.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    String shopManagerName = "shaked";
    String shopManagerPassword = "shaked1234";
    String shopName = "kolbo";
    AcceptanceTestService config = new AcceptanceTestService();


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
            VisitorFacade visitor =  guestLogin();
            register(shopManagerName, shopManagerPassword);
            List<String> questions = memberLogin(shopManagerName, shopManagerPassword);
            validateSecurityQuestions(shopManagerName,new ArrayList<>(),visitor.getName());
            // open shop

            openShop(shopManagerName, shopName);

        } catch (Exception Ignored) {
        }
    }

    @BeforeEach
    public void reset() throws Exception {
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
                Response result = exitMarket(visitor);
                assert !result.isErrorOccurred();
                VisitorFacade visitor2 = guestLogin();
                assert !(visitor2.getName().equals(visitor.getName()));

            } catch (Exception e) {
                assert false;
            }
        }

        // TODO to add tests:
        //      register
        //      get shop info
        //      get item info
        //      add item to cart -> check it is inside
        //      add item -> log out -> log in  -> empty cart
        //      buy shopping cart
        //      buy shopping cart with no items
        //      buy shopping cart with no items in shop
    }

    // ############################### MEMBER #######################################


    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Member {
        MemberFacade testMember;
        String testMemberPassword;
        @BeforeAll
        public void setUpMember(){
            try {
                String managerName = "managerTest";
                VisitorFacade visitor = guestLogin();
                testMemberPassword = "1234";
                register(managerName, testMemberPassword);
                List<String> questions = memberLogin(managerName, testMemberPassword);
                testMember = validateSecurityQuestions(managerName, new ArrayList<>(), visitor.getName());

            } catch (Exception ignored) {

            }
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
        public void visitorOpenShop(){
            // open with visitor
            try {
                VisitorFacade visitor = guestLogin();
                Response result = openShop(visitor.getName(), "testHere");
                assert result.isErrorOccurred();
            }catch (Exception e){assert false;}
        }

        @Test
        @DisplayName("member opens multiple shops")
        public void openMultipleShops(){
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
        public void openShopWithNoName(){
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
        public void usedNameOpenShop(){
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
        public void checkMemberSaved(){
            try{
                // TODO need to add item to cart if setup first
                ShoppingCartFacade prevCart = testMember.getMyCart();
                VisitorFacade visitor = logout(testMember.getName());
                assert visitor.getCart().getCart().isEmpty();
                List<String> questions =  memberLogin(testMember.getName(),testMemberPassword);
                MemberFacade returnedMember = validateSecurityQuestions(testMember.getName(),new ArrayList<>() ,
                        visitor.getName());
                Assertions.assertEquals(returnedMember.getAppointedByMe(),testMember.getAppointedByMe());
                Assertions.assertEquals(returnedMember.getName(),testMember.getName());
                Assertions.assertEquals(returnedMember.getMyAppointments(),testMember.getMyAppointments());
                if (!(testMember.getMyCart() == returnedMember.getMyCart())){
                    assert testMember.getMyCart().getCart().size() == returnedMember.getMyCart().getCart().size();
                    // for each shop - check equals
                    prevCart.getCart().forEach((shop, prevBasket) -> {
                        assert returnedMember.getMyCart().getCart().containsKey(shop);
                        ShoppingBasketFacade newBasket = returnedMember.getMyCart().getCart().get(shop);
                        // for each item in shopping basket
                        prevBasket.getItems().forEach((item,amount) -> {
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
    }
    //TODO to add tests:
    //      double login
    //      check member exit system logs out
    //      add questions
    //      wrong password/questions
    //      open a shop
    //
    //

    // ############################### SHOP OWNER #######################################


    // TODO to add tests
    //      add new item
    //      set item amount
    //      set item info
    //      appoint new shop owner
    //      appoint shop manager
    //      close a shop
    //      employees info
    //      get purchase history


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
    public VisitorFacade guestLogin() {
        String methodCall = "/guestLogin";
        try {
            MvcResult res = mvc.perform(post(methodCall)).andReturn();
            Type type = new TypeToken<ResponseT<VisitorFacade>>() {
            }.getType();
            ResponseT<VisitorFacade> result = deserialize(res, type);
            VisitorFacade visitor = result.getValue();
            return visitor;

        } catch (Exception e) {
            return null;
        }

    }

    private Response exitMarket(VisitorFacade visitor) throws Exception {
        String methodCall = "/exitSystem";
        ExitSystemRequest request = new ExitSystemRequest(visitor.getName());
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


    public VisitorFacade logout(String name) throws Exception {
        RequestVisitorName request = new RequestVisitorName(name);
        String methodCall = "/logout";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type = new TypeToken<ResponseT<VisitorFacade>>() {}.getType();
        ResponseT<VisitorFacade> result = deserialize(res, type);
        return result.getValue();
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
