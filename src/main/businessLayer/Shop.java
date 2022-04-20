package main.businessLayer;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.users.ShopFounder;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    // TODO must be unique
    private String shopName;
    private Map<Integer, Item> itemMap;             //<ItemID,main.businessLayer.Item>
    private Map<String, Appointment> employees;     //<name, appointment>
    private Map<Item, Integer> itemsCurrentAmount;
    private ShopFounder shopFounder;
    private boolean closed;


    public Shop(String name , ShopFounder shopFounder) {
        this.shopName = name;
        itemMap = new HashMap<>();
        employees = new HashMap<>();
        itemsCurrentAmount = new HashMap<>();
        this.shopFounder = shopFounder;
        this.closed = false;

    }


    //use case - receive info of a shop
    public String receiveInfo(String userName) throws Exception {
        if (isClosed()) {
            for (Map.Entry<String,Appointment> appointment : employees.entrySet()) {
                if (appointment.getValue().getAppointed().getName().equals(userName)) {
                    break;
                }
                throw new Exception("only owners and managers of the shop can view it's information");
            }
        }
        return "shop: " + shopName;
    }

    public void editManagerPermission(String superVisorName, String managerName, Appointment appointment){

        throw new UnsupportedOperationException();
    }


    //use case - Stock management
    // TODO if key value changed, need to inform all relevant like users
    public void editItem(Item item) {
        itemMap.put(item.getID(), item);
    }

    ;

    public void deleteItem(Item item) {
        itemMap.remove(item.getName());
    }

    public void addItem(Item item) throws Exception {
        if (!itemMap.containsKey(item.getName()))
            itemMap.put(item.getID(), item);
        else throw new Exception();
    }

    public int getItemCurrentAmount(Item item){
        throw new UnsupportedOperationException();
    }
    public void setItemAmount(Item item, int amount){
        throw new UnsupportedOperationException();
    }


    public Item receiveInfoAboutItem(String itemId, String userName) throws Exception {
        if (isClosed()) {
            for (Map.Entry<String,Appointment> appointment : employees.entrySet()) {
                if (appointment.getValue().getAppointed().getName().equals(userName)) {
                    break;
                }
                throw new Exception("only owners and managers of the shop can view it's information");
            }
        }
        if (!itemMap.containsKey(itemId)) {
            throw new Exception("no such item in shop");
        } else {
            return itemMap.get(itemId);
        }

    }

    public List<Item> getAllItemsByPrice(int minPrice, int maxPrice){
        throw new UnsupportedOperationException();
    }
    public int calculateBasket(ShoppingBasket basket){
        throw new UnsupportedOperationException();
    }
    // TODO need to calculate again - if doesn't match - exception
    public int buyBasket(int expectedAmount){
        throw new UnsupportedOperationException();
    }

    public boolean isShopOwner(String memberName){
        throw new UnsupportedOperationException();
    }

    public boolean isClosed() {
        return closed;
    }

    public List<Item> getAllItems(){
        throw new UnsupportedOperationException();
    }

    public String getShopName() {
        return shopName;
    }

    public ShopFounder getShopFounder() {
        return shopFounder;
    }

    public Map<String, Appointment> getEmployees() {
        return employees;
    }
}
