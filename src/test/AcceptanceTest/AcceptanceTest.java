package test.AcceptanceTest;

import org.junit.jupiter.api.BeforeAll;
import resources.ConfigReader;
import test.Bridge.Proxy;
import test.Bridge.SystemBridge;

import java.util.ArrayList;

// TODO need to check if really acceptance shouldn't know the system objects,
//  if so, should create new objects

public class AcceptanceTest {
    static SystemBridge bridge;
    static ConfigReader configReader = ConfigReader.getInstance();
    protected String userName;
    protected String userPassword;

    @BeforeAll
    public static void init() {
        System.out.println("Init Acceptance Test");
        if (Boolean.parseBoolean(configReader.getProperty("use_system_for_tests"))) {
            System.out.println("using system for tests");
            bridge = null;
        } else {
            System.out.println("using proxy for tests");
            bridge = new Proxy();
        }

    }

    public void initValues(){
        // TODO need to create mocks
        userName = "tester";
        userPassword = "1234";

    }

    public void createValidUser(){
        bridge.guestLogin();
//        bridge.register(userName, userPassword, new ArrayList<String>(), new ArrayList<String>());
    }
}
