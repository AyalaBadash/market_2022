package main.businessLayer;

import main.businessLayer.ExternalServices.PaymentService;
import main.businessLayer.ExternalServices.ProductsSupplyService;
import main.businessLayer.users.UserController;
import main.serviceLayer.FacadeObjects.ShoppingCartFacade;
import main.resources.Address;
import main.resources.Pair;
import main.resources.PaymentMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Market {
    private UserController userController;
    private String systemManagerName;
    private Map<String, Shop> shops;                                 // <shopName, shop>
    private Map<Integer, String> allItemsInMarketToShop;             // <itemID,ShopName>
    private Map<String, List<Integer>> itemByName;                   // <itemName ,List<itemID>>
    private int nextItemID;
    private PaymentService paymentService;
    private ProductsSupplyService supplyService;

    private static Market instance;


    private Market() {
        this.shops = new HashMap();
        this.allItemsInMarketToShop = new HashMap<>();
        this.itemByName = new HashMap<>();
        this.userController = UserController.getInstance();
        nextItemID = 1;
    }


    public synchronized static Market getInstance() {
        if (instance == null) {
            instance = new Market();
        }
        return instance;
    }

    //TODO must check permission
    public String getAllSystemPurchaseHistory() {
        return null;
    }

    //TODO must check permission
    public String getHistoryByShop(String shopName) {
        return null;
    }

    //TODO must check permission
    public String getHistoryByMember(String memberName) {
        return null;
    }

    public void register(String name, String password, String validatedPassword, List<Pair<String, String>> securityQuestions) throws Exception {
//        try {
//            Security security = Security.getInstance();
//            security.validateRegister(name, password, validatedPassword, securityQuestions);
//            MemberController mc = MemberController.getInstance();
//            mc.createMember(name);
//            security.addNewMember(name, password, securityQuestions);
//
//        } catch (Exception e) {
//            throw e;
//        }
    }

    public Shop getShopByName(String shopName){
        throw new UnsupportedOperationException();
    }



    public boolean login(String name, String pass,
                                            List<String> questions, List<String> answers) throws Exception {
        Security security = Security.getInstance();
        return security.validateLogin(name, pass, questions, answers);

    }

    //TODO need to check - store update is synchronized
    public void buyShoppingCart(String visitorName, int expectedPrice,
                                PaymentMethod paymentMethod, Address address) {
        throw new UnsupportedOperationException();
    }

    public void addSecurity() {

    }

    public ShoppingCartFacade calculateShoppingCart(){
        return null;
    }
    private int calculateShoppingCartPrice(){
        throw new UnsupportedOperationException();
    }

    public Map<String, Shop> getShops() {
        return shops;
    }

    private String ReceiveInformationAboutShop(String user, String shop) throws Exception {
//        MemberController mc = MemberController.getInstance();
//        if (mc.getVisitorsInMarket().getName() != user) {
//            throw new Exception("user is not currently logged in");
//        } else if (!shops.containsKey(shop)) {
//            throw new Exception("shop does not exist");
//        } else {
//            return shops.get(shop).receiveInfo(user);
//        }
        throw new UnsupportedOperationException();
    }

    private String ReceiveInformationAboutItemInShop(String user, String shop, String itemId) throws Exception {
//        MemberController mc = MemberController.getInstance();
//        if (mc.getVisitorsInMarket().getName() != user) {
//            throw new Exception("user is not currently logged in");
//        } else if (!shops.containsKey(shop)) {
//            throw new Exception("shop does not exist");
//        } else {
//            return shops.get(shop).receiveInfoAboutItem(itemId, user);
//        }
        throw new UnsupportedOperationException();

    }

    public String getSystemManagerName() {
        return systemManagerName;
    }

    public void setSystemManagerName(String systemManagerName) {
        this.systemManagerName = systemManagerName;
    }

    public void setShops(Map<String, Shop> shops) {
        this.shops = shops;
    }

    public Map<Integer, String> getAllItemsInMarketToShop() {
        return allItemsInMarketToShop;
    }


    public Map<String, List<Integer>> getItemByName() {
        return itemByName;
    }


    public static void setInstance(Market instance) {
        Market.instance = instance;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public ProductsSupplyService getSupplyService() {
        return supplyService;
    }

    public void setSupplyService(ProductsSupplyService supplyService) {
        this.supplyService = supplyService;
    }

    public synchronized int getNextItemID() {
        int temp =  nextItemID;
        nextItemID ++;
        return temp;
    }
}
