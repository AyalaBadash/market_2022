package test.AcceptanceTest;

import main.MarketController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import resources.ConfigReader;
import test.Bridge.Proxy;
import test.Bridge.SystemBridge;


public class AcceptanceTest {
    static SystemBridge bridge;
    static ConfigReader configReader = ConfigReader.getInstance();

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
}
