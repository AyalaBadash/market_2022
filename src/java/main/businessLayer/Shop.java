package main.businessLayer;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.users.Member;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Shop {
    // TODO must be unique
    private String shopName;
    private Map<Integer, Item> itemMap;             //<ItemID,main.businessLayer.Item>
    private Map<String, Appointment> employees;     //<name, appointment>
    private Map<Item, Double> itemsCurrentAmount;
    private boolean closed;


    public Shop(String name) {
        this.shopName = name;
        itemMap = new ConcurrentHashMap<>();
        employees = new ConcurrentHashMap<>();
        itemsCurrentAmount = new ConcurrentHashMap<Item, Double>();
        this.closed = false;

    }


    //use case - receive info of a shop
    public String receiveInfo(String userName) throws Exception {
        if (isClosed()) {
            for (Map.Entry<String, Appointment> appointment : employees.entrySet()) {
                if (appointment.getValue().getAppointed().getName().equals(userName)) {
                    break;
                }
                throw new Exception("only owners and managers of the shop can view it's information");
            }
        }
        return "shop: " + shopName;
    }

    public void editManagerPermission(String superVisorName, String managerName, Appointment appointment) throws MarketException {
        Appointment ownerAppointment = employees.get(superVisorName);
        if (ownerAppointment == null ){
            throw new MarketException(String.format("%s: cannot find an owner '%s'",shopName , superVisorName));
        }
        Appointment oldAppointment = employees.get(managerName);
        if (oldAppointment ==  null ){
            throw new MarketException(String.format("%s: cannot find an appointment of %s" , this.getShopName(), managerName));
        }
        this.employees.put(managerName, appointment);
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

    public void addItem(Item item) throws MarketException {
        if (!itemMap.containsKey(item.getName()))
            itemMap.put(item.getID(), item);
        else throw new MarketException("Item name already exist");
    }

    public int getItemCurrentAmount(Item item) {
        throw new UnsupportedOperationException();
    }

    public Map<Item, Double> getItemsCurrentAmountMap() {
        return itemsCurrentAmount;
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

    public void releaseItems(ShoppingBasket shoppingBasket) {
        Map<Item, Double> items = shoppingBasket.getItems();
        for (Map.Entry<Item,Double> itemAmount: items.entrySet()){
            Item currItem = itemAmount.getKey();
            Double newAmount = this.itemsCurrentAmount.get(currItem) + itemAmount.getValue();
            this.itemsCurrentAmount.put(currItem, newAmount);
        }
    }

    public synchronized double buyBasket(ShoppingBasket shoppingBasket) throws MarketException {
        Map<Item, Double> items = shoppingBasket.getItems();
        StringBuilder missingMessage = new StringBuilder();
        boolean failed = false;
        missingMessage.append(String.format("%s: cannot complete your purchase because some items are missing:\n", this.shopName));
        for (Map.Entry<Item,Double> itemAmount: items.entrySet()){
            Item currItem = itemAmount.getKey();
            Double currAmount = itemAmount.getValue();
            if (this.itemsCurrentAmount.get(currItem) < currAmount){
                failed = true;
                missingMessage.append(String.format("%s X %f",currItem.getName(), currAmount));
            };
        }
        if (failed){
            throw new MarketException(missingMessage);
        }
        for (Map.Entry<Item,Double> itemAmount: items.entrySet()){
            Item currItem = itemAmount.getKey();
            Double newAmount = this.itemsCurrentAmount.get(currItem) - itemAmount.getValue();
            this.itemsCurrentAmount.put(currItem, newAmount);
        }
        return calculateBasket(shoppingBasket);
    }


    public List<Item> getAllItemsByPrice(int minPrice, int maxPrice) {
        throw new UnsupportedOperationException();
    }

    public Map<Integer, Item> getItemMap() {
        return itemMap;
    }

    public int calculateBasket(ShoppingBasket basket) {
        throw new UnsupportedOperationException();
    }

    // TODO need to calculate again - if doesn't match - exception

    public boolean isShopOwner(String memberName) {
        throw new UnsupportedOperationException();
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
        return employees;
    }

    public List<Appointment> getEmployeesList(){
        return new ArrayList<>(getEmployees().values());
    }

    public Member getShopFounder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Shop && ((Shop) obj).getShopName().equals(this.shopName);
    }

    public Appointment getManagerAppointment(String shopOwnerName, String managerName) throws MarketException {

        Appointment ownerAppointment = employees.get(shopOwnerName);
        if (ownerAppointment == null ){
            throw new MarketException(String.format("%s: cannot find an owner '%s'",shopName , shopOwnerName));
        }
        Appointment appointment = employees.get(managerName);
        if (appointment ==  null ){
            throw new MarketException(String.format("%s: cannot find an appointment of %s" , this.getShopName(), managerName));
        }
        // TODO can it be the superVisor of the superVisor?
        if (!appointment.getSuperVisor().getName().equals(shopOwnerName)){
            throw new MarketException(String.format("%s: you must be %s superVisor to change his permissions" , this.getShopName(), managerName));
        }
        return appointment;

    }


    public List<Appointment> getShopEmployeesInfo(String shopManagerName) throws MarketException {
        if(!employees.containsKey(shopManagerName))
            throw new MarketException(shopManagerName+" is not working at this shop");
        Appointment employee = employees.get(shopManagerName);
        if (!employee.isOwner())
            throw new MarketException("only owners can view employees info");
        return employee.getShopEmployeesInfo();
    }

    public Shop getShopInfo(String member) throws MarketException {
        if (isClosed()){
            if (!employees.containsKey(member))
                throw new MarketException("member must be shop owner in order to get close shop info");
            return employees.get(member).getShopInfo();
        }
        return this;
    }
}
