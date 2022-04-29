package com.example.server.AcceptanceTest;

import com.example.server.ServerApplication;
import com.example.server.serviceLayer.Requests.InitMarketRequest;
import com.example.server.serviceLayer.Response;
import com.example.server.serviceLayer.Service;
import com.google.gson.Gson;
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
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

//@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ServerApplication.class)
@AutoConfigureMockMvc
public class SystemAcceptanceTest {

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
    public void setup(){
        try{
            initMarket();
        } catch (Exception Ignored) {
        }
    }

    @BeforeEach
    public void reset() throws Exception {
    }


    // ################################ TESTS ######################################


    @Test
    @DisplayName("init market twice test - fail")
    public void initTwice(){
        try{
            Response result = initMarket();
            assert result.isErrorOccurred();
        }catch (Exception e){
            // should return as result fail and not as exception
            assert false;};
    }












    // ################################ Service ######################################


    protected String toHttpRequest(Object obj) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJakson2HttpMessageConverter.write(
                obj,MediaType.APPLICATION_JSON,
                mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
    protected <T> T deserialize(MvcResult res, Class<T> classType) throws UnsupportedEncodingException {
        return gson.fromJson(res.getResponse().getContentAsString(), classType);
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
}
