package StatisticsTests;

import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.Statistics;
import com.example.server.businessLayer.Market.Users.UserController;
import com.example.server.businessLayer.Market.Users.Visitor;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatisticsTests {

    static Market market;
    static String systemManager = "u1";
    static Visitor systemManagerVisitor;
    static String ownerName="2";
    static Visitor ownerVisitor;
    static String manager="3";
    static Visitor managerVisitor;
    static String password = "password";
    static String shopName = "rami levi";
    static Statistics statistics=Statistics.getInstance();
    static boolean useData;
    static boolean loadedData;

    @BeforeAll
    public static void setUpMember() {

        try {
            useData= MarketConfig.USING_DATA;
            MarketConfig.USING_DATA=false;
            MarketConfig.FIRST_INIT=true;
            market = Market.getInstance();
            loadedData=market.getLoadedData();
            market.setLoadedData(true);
            if (market.getPaymentService() == null) {
                market.firstInitMarket(systemManager, password);
                market.isInit();
            }
        }
        catch(Exception ignored){
                System.out.printf(ignored.getMessage());
        }
    }
    @AfterAll
    public void setUseData(){
        MarketConfig.USING_DATA=useData;
        market.setLoadedData(loadedData);
    }
    @Test
    @Order(1)
    @DisplayName("check init market statistics visitors")
    public void initMarketVisitors() {
        int visitors= statistics.getData().numOfVisitors;
        int val=0;
        Assertions.assertEquals(val,visitors);
    }
    @Test
    @Order(2)
    @DisplayName("check init market statistics system manager")
    public void initMarketSystemManager() {
        try {
            systemManagerVisitor=market.guestLogin();
            loginMember(systemManagerVisitor,systemManager, password);
            int systemManagers = statistics.getData().numOfSystemManager;
            Assertions.assertEquals(1, systemManagers);
        }
        catch (Exception e){
            assert false;
        }
    }
    @Test
    @Order(3)
    @DisplayName("check num of visitors after guest login")
    public void newVisitor() {
        ownerVisitor=market.guestLogin();
        int visitors= statistics.getData().numOfVisitors;
        Assertions.assertEquals(2,visitors);
    }
    @Test
    @Order(4)
    @DisplayName("check num of visitors after several guest login")
    public void newVisitors() {
        managerVisitor=market.guestLogin();
        int visitors= statistics.getData().numOfVisitors;
        Assertions.assertEquals(3,visitors);
    }
    @Test
    @Order(5)
    @DisplayName("check num of members after member register and log in")
    public void numOfMembers() {
        try {
            market.register(ownerName,password);
            loginMember(ownerVisitor,ownerName,password);
            int visitors= statistics.getData().numOfVisitors;
            int members=statistics.getData().getNumOfRegularMembers();
            Assertions.assertEquals(3,visitors);
            Assertions.assertEquals(2,members);
        } catch (MarketException e) {
            assert false;
        }
    }
    @Test
    @Order(6)
    @DisplayName("check num of members after members register and login")
    public void numOfMembersNewLogin() {
        try {
            market.register(manager,password);
            loginMember(managerVisitor,manager,password);
            int visitors= statistics.getData().numOfVisitors;
            int members=statistics.getData().getNumOfRegularMembers();
            Assertions.assertEquals(3,visitors);
            Assertions.assertEquals(3,members);
        } catch (MarketException e) {
            assert false;
        }
    }
    @Test
    @Order(7)
    @DisplayName("check num of owners after members register and login")
    public void openNewShop() {
        try {
            market.openNewShop(ownerName,shopName);
            int owners= statistics.getData().numOfOwners;
            Assertions.assertEquals(1,owners);
        } catch (MarketException e) {
            assert false;
        }
    }
    @Test
    @Order(8)
    @DisplayName("check num of managers after manager appointment")
    public void managerAppointment() {

        try {
            market.appointShopManager(ownerName,manager,shopName);
            int owners= statistics.getData().numOfShopsManagers;
            Assertions.assertEquals(1,owners);
        } catch (MarketException e) {
            assert false;
        }
    }
    public void loginMember(Visitor visitor,String name, String password) throws MarketException {
        if(UserController.getInstance().isLoggedIn(name))
            return;
        market.memberLogin(name, password);
        market.validateSecurityQuestions(name, new ArrayList<>(), visitor.getName());
    }
}
