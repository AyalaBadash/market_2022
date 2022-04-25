package com.example.server.UnitTests;

import com.example.server.Bridge.Proxy;
import com.example.server.ResourcesObjects.ConfigReader;
import com.example.server.serviceLayer.IService;
import com.example.server.serviceLayer.Service;
import org.junit.jupiter.api.BeforeAll;

public class mainTest {
    static IService app;
//    static ConfigReader configReader = ConfigReader.getInstance();

    @BeforeAll
    public static void init() {
        System.out.println("Init Unit Test");
        //TODO
//        if (Boolean.parseBoolean(configReader.getProperty("use_system_for_tests"))) {
        if (true) {
            System.out.println("using system for tests");
            app = Service.getInstance();
        } else {
            System.out.println("using proxy for tests");
            app = new Proxy();
        }


    }

}
