package main.businessLayer;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Appointment.ShopOwnerAppointment;
import main.businessLayer.users.Member;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    // TODO must be unique
    private String shopName;
    private Map<Integer, Item> itemMap;             //<ItemID,main.businessLayer.Item>

    private Map<String, Appointment> shopManagers;     //<name, appointment>

    private Map<String, Appointment> shopOwners;     //<name, appointment>
    private Map<Item, Integer> itemsCurrentAmount;
    private boolean closed;

    public Shop(String name) {
        this.shopName = name;
        itemMap = new HashMap<>();
        shopManagers = new HashMap<>();
        shopOwners = new HashMap<> (  );
        itemsCurrentAmount = new HashMap<>();
        this.closed = false;

    }


    //use case - receive info of a shop

    public String receiveInfo(String userName) throws Exception {
        Boolean hasPermission = false;
        if (isClosed()) {
            for (Map.Entry<String, Appointment> appointment : shopOwners.entrySet()) {
                if (appointment.getValue().getAppointed().getName().equals(userName)) {
                    hasPermission = true;
                }
            }
            for (Map.Entry<String, Appointment> appointment : shopManagers.entrySet()) {
                if (appointment.getValue().getAppointed().getName().equals(userName)) {
                    hasPermission = true;
                }
            }
            throw new Exception("only owners and managers of the shop can view it's information");
        }
        return "shop: " + shopName;
    }
    public void editManagerPermission(String superVisorName, String managerName, Appointment appointment) {

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

    public int getItemCurrentAmount(Item item) {
        throw new UnsupportedOperationException();
    }

    public void setItemAmount(Item item, int amount) {
        throw new UnsupportedOperationException();
    }


    public Item receiveInfoAboutItem(String itemId, String userName) throws Exception {
        if (isClosed()) {
            for (Map.Entry<String, Appointment> appointment : employees.entrySet()) {
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

    public List<Item> getAllItemsByPrice(int minPrice, int maxPrice) {
        throw new UnsupportedOperationException();
    }

    public int calculateBasket(ShoppingBasket basket) {
        throw new UnsupportedOperationException();
    }

    // TODO need to calculate again - if doesn't match - exception

    public int buyBasket(int expectedAmount) {
        throw new UnsupportedOperationException();
    }
    public boolean isShopOwner(String memberName) {
        return shopOwners.get ( memberName ) != null;
    }

    public boolean isClosed() {
        return closed;
    }

    //TODO returns all items, doesn't matter amount

    public List<Item> getAllItems() {
        throw new UnsupportedOperationException();
    }
    public String getShopName() {
        return shopName;
    }


    public Map<String, Appointment> getEmployees() {

        Map<String, Appointment> employees = new HashMap<> (  );
        employees.putAll ( shopOwners );
        employees.putAll ( shopManagers );
        return employees;
    }

    public Member getShopFounder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Shop && ((Shop) obj).getShopName().equals(this.shopName);
    }

    public void addEmployee(ShopOwnerAppointment newAppointment) throws MarketException {
        String employeeName = newAppointment.getAppointed ().getName ();
        Appointment oldAppointment = shopOwners.get ( employeeName );
        if(oldAppointment != null) {
            if (newAppointment.isOwner ())
                throw new MarketException ( "this member is already a shop owner" );
            shopManagers.put ( employeeName, newAppointment );
        } else {
            oldAppointment = shopManagers.get ( employeeName );
            if(oldAppointment != null){
                if (newAppointment.isManager ())
                    throw new MarketException ( "this member is already a shop manager" );
                shopOwners.put ( employeeName, newAppointment );
            }
            else if(newAppointment.isOwner () )
                shopOwners.put ( employeeName, newAppointment );
            else
                shopManagers.put ( employeeName, newAppointment );
        }
    }

    public Map<String, Appointment> getShopManagers() {
        return shopManagers;
    }

    public void setShopManagers(Map<String, Appointment> shopManagers) {
        this.shopManagers = shopManagers;
    }

    public Map<String, Appointment> getShopOwners() {
        return shopOwners;
    }

    public void setShopOwners(Map<String, Appointment> shopOwners) {
        this.shopOwners = shopOwners;
    }
}
