package main;

import java.util.List;
import java.util.Map;

public class Market {
    //TODO :need to change Map's names
    SystemManager manager;
    Map<String,String> items1; // <itemID,ShopID>
    Map<String, List<String>> items2; // <name ,List<itemID>>
    //public main.Market(){}
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

    public void addSecurity(){

    }



}