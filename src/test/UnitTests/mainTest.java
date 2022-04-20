package test.UnitTests;

import main.serviceLayer.IService;
import main.serviceLayer.Service;
import org.junit.jupiter.api.BeforeAll;
import resources.ConfigReader;
import test.Bridge.Proxy;

public class mainTest {
    static IService app;
    static ConfigReader configReader = ConfigReader.getInstance();

    @BeforeAll
    public static void init() {
        System.out.println("Init Unit Test");
        if (Boolean.parseBoolean(configReader.getProperty("use_system_for_tests"))) {
            System.out.println("using system for tests");
            app = Service.getInstance();
        } else {
            System.out.println("using proxy for tests");
            app = new Proxy();
        }

    }


}
