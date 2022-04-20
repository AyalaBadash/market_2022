import main.users.SystemManager;
import java.util.List;
import java.util.Map;

public class Market {
    //TODO :need to change Map's names
    Map<String,String> items1; // <itemID,ShopID>
    Map<String, List<String>> items2; // <name ,List<itemID>>
    //public Market(){}
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


}
