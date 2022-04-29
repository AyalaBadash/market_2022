package com.example.server.AcceptanceTest;

import com.example.server.ServerApplication;
import com.example.server.serviceLayer.Service;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

//@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ServerApplication.class)
@AutoConfigureMockMvc
public class MemberAcceptanceTest {

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
    AcceptanceTestService config = new AcceptanceTestService();


    // common fields
    Gson gson = new Gson();

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters){
        this.mappingJakson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
        assertNotNull("the Json message converter must not be null",this.mappingJakson2HttpMessageConverter);
    }

    // ############################### SETUP #######################################

    // ####################################### TEST  #########################################
    @BeforeAll
    public void setup() {
//        try{
//            initMarket();
//            // shop manager register
//            register(shopManagerName , shopManagerPassword );
//            // open shop
//            openShop(shopManagerName, shopName);
//
//        }
//        catch (Exception Ignored){}
    }
    @BeforeEach
    public void reset() throws Exception {
    }
    // ####################################### TEST  #########################################


}
