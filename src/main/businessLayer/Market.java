package main.businessLayer;

import main.businessLayer.users.MemberController;
import main.businessLayer.users.SystemManager;

import java.util.List;
import java.util.Map;

public class Market {
    //TODO :need to change Map's names
    SystemManager manager;
    Map<String,String> items1; // <itemID,ShopID>
    Map<String, List<String>> items2; // <name ,List<itemID>>
    Map<String,Shop> shops;
    Map<String,Shop> closedShops;
    public boolean collectDebt(){
        throw new UnsupportedOperationException();
    }
    public boolean supplyProducts(){throw new UnsupportedOperationException();}
    private static Market instance;
    private Market(SystemManager manager){} // TODO - add market details
    public static Market getInstance() {
        if (instance == null)
            return new Market(null);
        else return instance;
    }
    //TODO - add exception class
    // Bar: implemented exception class named MarketException
    public void register(String name , String password , String validatedPassword,List<Pair<String,String>> securityQuestions) throws Exception
    {
        try {
            Security security = Security.getInstance();
            security.validateRegister(name, password, validatedPassword, securityQuestions);
            MemberController mc = MemberController.getInstance();
            mc.createMember(name);
            security.addNewMember(name,password,securityQuestions);

        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public List<Pair<String, String>> login(String name, String pass) throws Exception
    {
        Security security = Security.getInstance();
        return security.validateLogin(name, pass);

    }

    public void addSecurity(){

    }

    public Map<String, Shop> getShops() {
        return shops;
    }

    private String ReceiveInformationAboutShop(String user, String shop) throws Exception {
        MemberController mc = MemberController.getInstance();
        if (mc.getCurrentLoggedIn().getName() != user){
            throw new Exception("user is not currently logged in");
        }
        else if (!shops.containsKey(shop)){
            throw new Exception("shop does not exist");
        }
        else{
            return shops.get(shop).receiveInfo(user);
        }
    }

    private String ReceiveInformationAboutItemInShop(String user, String shop, String itemId) throws Exception {
        MemberController mc = MemberController.getInstance();
        if (mc.getCurrentLoggedIn().getName() != user){
            throw new Exception("user is not currently logged in");
        }
        else if (!shops.containsKey(shop)){
            throw new Exception("shop does not exist");
        }
        else{
            return shops.get(shop).receiveInfoAboutItem(itemId, user);
        }
    }
}