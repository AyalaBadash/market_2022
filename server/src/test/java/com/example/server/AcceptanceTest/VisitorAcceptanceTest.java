package com.example.server.AcceptanceTest;

import com.example.server.ServerApplication;
import com.example.server.businessLayer.ShoppingCart;
import com.example.server.serviceLayer.FacadeObjects.ItemFacade;
import com.example.server.serviceLayer.FacadeObjects.MemberFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopFacade;
import com.example.server.serviceLayer.FacadeObjects.VisitorFacade;
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
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

//@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ServerApplication.class)
@AutoConfigureMockMvc
public class VisitorAcceptanceTest {
    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    protected HttpMessageConverter mappingJakson2HttpMessageConverter ;
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
    MemberFacade curMember;
    ItemFacade milk;
    ShopFacade shop;
    ShoppingCart curShoppingCart;
    String shopManagerName = "shaked";
    String shopManagerPassword = "shaked1234";
    String shopName = "kolbo";
    AcceptanceTestService config = new AcceptanceTestService();

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters){
        this.mappingJakson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
        assertNotNull("the Json message converter must not be null",this.mappingJakson2HttpMessageConverter);
    }


    // ############################### SETUP #######################################

    @BeforeAll
    public void setup() {
        try{
            initMarket();
            SystemAcceptanceTest t = new SystemAcceptanceTest();
            // shop manager register
            register(shopManagerName , shopManagerPassword );
            // open shop
            openShop(shopManagerName, shopName);

        }
        catch (Exception Ignored){}
    }
    @BeforeEach
    public void reset() throws Exception {
    }
    // ############################### TEST #######################################



    @Test
    @DisplayName("valid guest login")
    public void guestLoginValid() throws Exception {
//        initMarket();
        String methodCall = "/guestLogin";
        try{
            MvcResult res = mvc.perform(post(methodCall)).andReturn();
            ResponseT<VisitorFacade> result =  deserialize(res, new TypeToken<ResponseT<VisitorFacade>>(){}.getType());
            assert !result.isErrorOccurred();
            VisitorFacade visitor = result.getValue();
            Assertions.assertNotNull(visitor.getCart());
        }catch (Exception e){
            assert false;
        }

    }

    @Test
    @DisplayName("guest leaves the market")
    public void guestExitMarket() throws Exception {

        VisitorFacade visitor =  guestLogin();
        try{
            Response result =  exitMarket(visitor);
            assert !result.isErrorOccurred();
            VisitorFacade visitor2 = guestLogin();
            assert !(visitor2.getName().equals(visitor.getName()));

        }catch (Exception e){assert false;}
    }

    // TODO need to think how to make guest exit automatic when reloging

    @Test
    @DisplayName("guest relogin - empty cart")
    public void newCartEachGuestLogin(){
        VisitorFacade firstVisitor = guestLogin();
//        exitMarket(firstVisitor);
        VisitorFacade secondVisitor = guestLogin();
        assert false;
    }




    @Test
    @DisplayName("member login valid")
    public void memberLoginValid() throws Exception {

        assert false;
//        initMarket();
//        VisitorFacade visitor = guestLogin();
//        String memberName = "raz";
//        String password = "Raz123";
//        try {
//            register();
//            List<String> questions = getMemberQuestions(memberName, password);
//            Assertions.assertTrue(questions.isEmpty());
//            MemberFacade member = validateSecurityQuestions(memberName,new ArrayList<>() , password);
//        }catch (Exception e){assert false;}


    }


    // ############################### SERVICE METHODS #######################################
    public VisitorFacade guestLogin(){
        String methodCall = "/guestLogin";
        try{
            MvcResult res = mvc.perform(post(methodCall)).andReturn();
            Type type = new TypeToken<ResponseT<VisitorFacade>>(){}.getType();
            ResponseT<VisitorFacade> result =  deserialize(res,type);
            VisitorFacade visitor = result.getValue();
            return  visitor;

        }catch (Exception e){
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
        Response result =  deserialize(res, Response.class);
        return result;
    }

    public ResponseT<Boolean> register(String name , String password) throws Exception {
        guestLogin();
        NamePasswordRequest request =  new NamePasswordRequest (name,password);
        String methodCall = "/register";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type  = new TypeToken<ResponseT<Boolean>>(){}.getType();
        ResponseT<Boolean> result = deserialize(res,type);
        return result;
    }

    public MemberFacade validateSecurityQuestions(String userName, List<String> answers, String visitorName){
        ValidateSecurityRequest request =  new ValidateSecurityRequest(userName, answers,visitorName);
        String methodCall = "/validateSecurityQuestions";
        try{
            MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                            content(toHttpRequest(request)).contentType(contentType))
                    .andExpect(status().isOk())
                    .andReturn();
            Type type  = new TypeToken<ResponseT<MemberFacade>>(){}.getType();
            ResponseT<MemberFacade> result =  deserialize(res, type);
            return result.getValue();
        }catch (Exception e){
            return null;
        }
    }

    public List<String> getMemberQuestions(String name, String password){
        NamePasswordRequest request = new NamePasswordRequest(name, password);
        String methodCall = "/memberLogin";
        try{
            MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                            content(toHttpRequest(request)).contentType(contentType))
                    .andExpect(status().isOk())
                    .andReturn();
            Type type  = new TypeToken<ResponseT<List<String>>>(){}.getType();
            ResponseT<List<String>> result =  deserialize(res, type);
            return result.getValue();


        }catch (Exception e){
            return null;
        }

    }

    public Response openShop(String managerName, String shopName) throws Exception {
        OpenNewShopRequest request =  new OpenNewShopRequest (managerName, shopName);
        String methodCall = "/openNewShop";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        Type type  = new TypeToken<Response>(){}.getType();
        Response result = deserialize(res,type);
        return result;


    }

    public void addItemToCart( ){
        curVisitor = guestLogin();
        AddItemToShoppingCartRequest request = new AddItemToShoppingCartRequest();
    }

    public Response initMarket() throws Exception {
        InitMarketRequest request =  new InitMarketRequest(config.systemManagerName, config.systemManagerPassword);
        String methodCall = "/firstInitMarket";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
        return deserialize(res,Response.class);

    }
    protected String toHttpRequest(Object obj) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJakson2HttpMessageConverter.write(
                obj,MediaType.APPLICATION_JSON,
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
