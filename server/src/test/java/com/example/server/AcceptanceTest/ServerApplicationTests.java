package com.example.server.AcceptanceTest;
import java.io.*;

import com.example.server.ServerApplication;
import com.example.server.Student;
import com.example.server.businessLayer.ExternalServices.PaymentMock;
import com.example.server.businessLayer.ExternalServices.SupplyMock;
import com.example.server.businessLayer.Item;
import com.example.server.serviceLayer.FacadeObjects.ItemFacade;
import com.example.server.serviceLayer.FacadeObjects.MemberFacade;
import com.example.server.serviceLayer.FacadeObjects.VisitorFacade;
import com.example.server.serviceLayer.Requests.*;
import com.example.server.serviceLayer.Response;
import com.example.server.serviceLayer.ResponseT;
import com.example.server.serviceLayer.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ServerApplication.class)
@AutoConfigureMockMvc
class ServerApplicationTests {

    private String shopName1 = "shop1";
    private String memberName1 = "member1";
    private String password1 = "password1";
    private String itemName1 = "item1";
    private double price1 = 10;
    private Item.Category category = Item.Category.cellular;
    private double amount1 = 5;

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    protected HttpMessageConverter mappingJakson2HttpMessageConverter ;
    protected String tokenStr;
//    protected JaksonSerializer serializer;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Service service;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters){
        this.mappingJakson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
        assertNotNull("the Json message converter must not be null",this.mappingJakson2HttpMessageConverter);
    }



    @Test
    void contextLoads() {
    }

    PaymentMock paymentMock = new PaymentMock();
    SupplyMock supplyMock = new SupplyMock();
    String systemManagerName = "Raz";
    String systemManagerPassword = "1234Manager";
    String shopOwnerName = "Ayala";
    Gson gson = new Gson();
;



    @BeforeEach
    public void initAcceptanceTest() throws Exception {

    }

    @Test
    @DisplayName("first init market - valid")
    public void validFirstInitMarketTwice() throws Exception {
        InitMarketRequest request =  new InitMarketRequest("ido", "1234Ido");
        String methodCall = "/firstInitMarket";
        try{
            MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                            content(toHttpRequest(request)).contentType(contentType))
                    .andExpect(status().isOk())
                    .andReturn();
            Response result =  deserialize(res, Response.class);
            assert !result.isErrorOccurred();

        }catch (Exception e){
            assert false;
        }
    }

    @Test
    @DisplayName("first init market - twice ")
    public void validFirstInitMarket() throws Exception {
        InitMarketRequest request =  new InitMarketRequest("ido", "1234Ido");
        InitMarketRequest request2 =  new InitMarketRequest("raz", "1234Raz");
        String methodCall = "/firstInitMarket";
        try{
            MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                            content(toHttpRequest(request)).contentType(contentType))
                    .andExpect(status().isOk())
                    .andReturn();
            Response result =  deserialize(res, Response.class);
            MvcResult res2 = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                            content(toHttpRequest(request2)).contentType(contentType))
                    .andExpect(status().isOk())
                    .andReturn();
            Response result2 =  deserialize(res2, Response.class);
            Assertions.assertFalse(result.isErrorOccurred());
            Assertions.assertTrue(result2.isErrorOccurred());
        }catch (Exception e){
            assert false;
        }
    }

    @Test
    @DisplayName("valid guest login")
    public void guestLoginValid() throws Exception {
        initMarket();
        String methodCall = "/guestLogin";
        try{
            MvcResult res = mvc.perform(post(methodCall)).andReturn();
            ResponseT<VisitorFacade> result =  deserialize(res, ResponseT.class);
            assert !result.isErrorOccurred();
            VisitorFacade visitor = result.getValue();

        }catch (Exception e){
            assert false;
        }

    }

    @Test
    @DisplayName("guest leaves the market")
    public void guestExitMarket() throws Exception {

        initMarket();
        VisitorFacade visitor = getVisitor();
        String memberName = "raz";
        String password = "1234Raz";
        NamePasswordRequest request = new NamePasswordRequest(memberName, password);
        String methodCall = "/register";
        try{
            MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                            content(toHttpRequest(request)).contentType(contentType))
                    .andExpect(status().isOk())
                    .andReturn();
            ResponseT<Boolean> result =  deserialize(res, ResponseT.class);
            assertTrue(result.getValue());
            assert !result.isErrorOccurred();

        }catch (Exception e){
            assert false;
        }
    }

    @Test
    @DisplayName("member login valid")
    public void memberLoginValid() throws Exception {

        initMarket();
        VisitorFacade visitor = getVisitor();
        String memberName = "raz";
        String password = "Raz123";
        try {
            register(memberName, password);
            List<String> questions = getMemberQuestions(memberName, password);
            Assertions.assertTrue(questions.isEmpty());
            MemberFacade member = validateSecurityQuestions(memberName,new ArrayList<>() , password);
        }catch (Exception e){assert false;}


    }




//
//    @Test
//    public void requestExample() throws Exception{
//
//        ItemFacade item = new ItemFacade(1,"milk", 10, Item.Category.general , new ArrayList<>() , "sad" );
//        EditItemFromShoppingCartRequest request =  new EditItemFromShoppingCartRequest(10.4, item, "shop" , "visitor" );
//        String methodCall = "/editItemFromShoppingCart";
////        CloseShopRequest request = new CloseShopRequest("ido", "1");
//        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
//                        content(toHttpRequest(request)).contentType(contentType))
//                .andExpect(status().isOk())
//                .andReturn();
//
//    }

//    @Test
//    public void testReq() throws Exception {
//        String methodCall = "/closeShop";
//        CloseShopRequest request = new CloseShopRequest("ido", "1");
//        String t = this.toHttpRequest(request);
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(methodCall).
//                        content(toHttpRequest(request)).contentType(contentType))
//                .andExpect(status().isOk())
//                .andReturn();
//
//    }

//    @Test
//    public void test_hello() throws Exception {
//        String name = "shaked";
//        String email = "shak@gmail.com";
//        String methodCall = "/getStudent";
//        List<Student> studentList = new ArrayList<>();
//        Student ido = new Student("ido", "1");
//
//        studentList.add(ido);
//        studentList.add(new Student("raz", "2"));
//        Gson gson = new Gson();
//
//        Student ido2 = gson.fromJson(gson.toJson(ido),Student.class);
//        String json = gson.toJson(studentList);
//        int x = 3;
//        Type listType = new TypeToken<ArrayList<Student>>(){}.getType();
//        List<Student> result = gson.fromJson(json,listType);
//
////        String req = String.format("?name=%s&email=%s",name,email);
//        String req = String.format("?name=shaked&email=ido");
//        MvcResult res = mvc.perform(post("/getStudent"+req)).andReturn();
////        MvcResult res = mvc.perform(post("/getStudent?name=shaked&email=ido")).andReturn();
//        String resS = res.getResponse().getContentAsString();
//        Student student = new ObjectMapper().readValue(resS,Student.class);
//        Assertions.assertEquals("hello shaked", res.getResponse().getContentAsString());
//    }

    protected String toHttpRequest(Object obj) throws IOException {
        MockHttpOutputMessage  mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJakson2HttpMessageConverter.write(
                obj,MediaType.APPLICATION_JSON,
                mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
    protected <T> T deserialize(MvcResult res, Class<T> classType) throws UnsupportedEncodingException {
        return gson.fromJson(res.getResponse().getContentAsString(), classType);
    }
    public void initMarket() throws Exception {
        InitMarketRequest request =  new InitMarketRequest("ido", "1234Ido");
        String methodCall = "/firstInitMarket";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();

    }


    public void login(String name , String password){
        NamePasswordRequest request =  new NamePasswordRequest (name, password);
        String methodCall = "/register";
        try{

            MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                            content(toHttpRequest(request)).contentType(contentType))
                    .andExpect(status().isOk())
                    .andReturn();
        }catch (Exception e){return ;}
    }
    public MemberFacade validateSecurityQuestions(String userName, List<String> answers, String visitorName){
        ValidateSecurityRequest request =  new ValidateSecurityRequest(userName, answers,visitorName);
        String methodCall = "/validateSecurityQuestions";
        try{
            MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                            content(toHttpRequest(request)).contentType(contentType))
                    .andExpect(status().isOk())
                    .andReturn();
            ResponseT<MemberFacade> result =  deserialize(res, ResponseT.class);
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
            ResponseT<List<String>> result =  deserialize(res, ResponseT.class);
            return result.getValue();


        }catch (Exception e){
            return null;
        }

    }
    public void register(String name, String password) throws Exception {
        NamePasswordRequest request =  new NamePasswordRequest (name, password);
        String methodCall = "/register";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
    }

    public void addShop(String memberName, String shopName) throws Exception {
        TwoStringRequest request =  new TwoStringRequest (memberName, shopName);
        String methodCall = "/openNewShop";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
    }

    public void addItemToShop(String shopOwnerName, String itemName, double price, Item.Category category, String info, List<String> keywords, double amount, String shopName) throws Exception {
        AddItemToShopRequest request =  new AddItemToShopRequest (memberName1, itemName1, price1, category, info, new ArrayList<> (  ), amount1, shopName);
        String methodCall = "/addItemToShop";
        MvcResult res = mvc.perform(MockMvcRequestBuilders.post(methodCall).
                        content(toHttpRequest(request)).contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
    }
    public VisitorFacade getVisitor(){
        String methodCall = "/guestLogin";
        try{
            MvcResult res = mvc.perform(post(methodCall)).andReturn();
            ResponseT<VisitorFacade> result =  deserialize(res, ResponseT.class);
            VisitorFacade visitor = result.getValue();
            return  visitor;

        }catch (Exception e){
            return null;
        }

    }

}
