package com.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ServerApplication.class)
@AutoConfigureMockMvc
class ServerApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private  ContorllerT contorller;


    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    protected HttpMessageConverter mappingJakson2HTTPMessageConverter;
    protected String tokenStr;
//    protected JaksonSerializer serializer;


    @Test
    void contextLoads() {
    }


    @Test
    public void test_hello() throws Exception {
        String name = "shaked";
        String email = "shak@gmail.com";
        String methodCall = "/getStudent";
        List<Student> studentList = new ArrayList<>();
        Student ido = new Student("ido", "1");

        studentList.add(ido);
        studentList.add(new Student("raz", "2"));
        Gson gson = new Gson();

        Student ido2 = gson.fromJson(gson.toJson(ido),Student.class);
        String json = gson.toJson(studentList);
        int x = 3;
        Type listType = new TypeToken<ArrayList<Student>>(){}.getType();
        List<Student> result = gson.fromJson(json,listType);

//        String req = String.format("?name=%s&email=%s",name,email);
        String req = String.format("?name=shaked&email=ido");
        MvcResult res = mvc.perform(post("/getStudent"+req)).andReturn();
//        MvcResult res = mvc.perform(post("/getStudent?name=shaked&email=ido")).andReturn();
        String resS = res.getResponse().getContentAsString();
        Student student = new ObjectMapper().readValue(resS,Student.class);
        Assertions.assertEquals("hello shaked", res.getResponse().getContentAsString());
    }

}
