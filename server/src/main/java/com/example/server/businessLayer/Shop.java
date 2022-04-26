package com.example.server.businessLayer;

import com.example.server.ResourcesObjects.ErrorLog;
import com.example.server.ResourcesObjects.EventLog;
import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Users.Member;
import com.example.server.serviceLayer.FacadeObjects.ItemFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop implements IHistory{
    private String shopName;
    private Map<Integer, Item> itemMap;             //<ItemID,main.businessLayer.Item>
    private Map<String, Appointment> shopManagers;     //<name, appointment>
    private Map<String, Appointment> shopOwners;     //<name, appointment>
    private Map<Item, Double> itemsCurrentAmount;
    private boolean closed;

    private int rank;
    private int rankers;
    private List<StringBuilder> purchaseHistory;



    public Shop(String name) {
        this.shopName = name;
        itemMap = new HashMap<> ();
        shopManagers = new HashMap<>();
        shopOwners = new HashMap<> (  );
        itemsCurrentAmount = new HashMap<>();
        this.closed = false;
        purchaseHistory = new ArrayList<> ();
        rank= 1;
        rankers=0;
    }

    public void editManagerPermission(String superVisorName, String managerName, Appointment appointment) throws MarketException {
        Appointment ownerAppointment = shopOwners.get(superVisorName);
        if (ownerAppointment == null ){
            ErrorLog.getInstance ().Log (String.format("%s: cannot find an owner '%s'",shopName , superVisorName));
            throw new MarketException(String.format("%s: cannot find an owner '%s'",shopName , superVisorName));
        }
        Appointment oldAppointment = shopManagers.get(managerName);
        if (oldAppointment ==  null ){
            ErrorLog.getInstance ().Log (String.format("%s: cannot find an appointment of %s" , this.getShopName(), managerName));
            throw new MarketException(String.format("%s: cannot find an appointment of %s" , this.getShopName(), managerName));
        }
        if(!oldAppointment.getSuperVisor ().equals ( superVisorName )){
            ErrorLog.getInstance ().Log ( String.format ( "%s is not the supervisor of %s so is not authorized to change the permissions", superVisorName, managerName ));
            throw new MarketException ( String.format ( "%s is not the supervisor of %s so is not authorized to change the permissions", superVisorName, managerName ) );
        }
        this.shopManagers.put(managerName, appointment);
    }


    //use case - Stock management
    public void editItem(Item newItem, String id) throws MarketException {
        if(newItem.getID () != Integer.getInteger ( id ))
            throw new MarketException ( "must not chance the item id" );
        itemMap.put(newItem.getID (), newItem);
    }


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


    public void setItemAmount(String shopOwnerName, Item item, double amount) throws MarketException {
        if(!isShopOwner ( shopOwnerName )) {
            throw new MarketException ( "member is not the shop owner and is not authorized to effect the inventory." );
        }
        if(amount < 0)
            throw new MarketException ( "amount cannot be negative" );
        if(itemMap.get ( item.getID ()) == null){
            itemMap.put ( item.getID (), item );
            itemsCurrentAmount.put ( item, amount );
        } else {
            itemsCurrentAmount.replace ( item, amount );
        }
    }

    public Item receiveInfoAboutItem(String itemId, String userName) throws Exception {
        if (isClosed()) {
            boolean hasPermission = false;
            for (Map.Entry<String, Appointment> appointment : shopManagers.entrySet()) {
                if (appointment.getValue().getAppointed().getName().equals(userName)) {
                    hasPermission = true;
                    break;
                }
            }
            if(!hasPermission) {
                for ( Map.Entry<String, Appointment> appointment : shopOwners.entrySet ( ) ) {
                    if (appointment.getValue ( ).getAppointed ( ).getName ( ).equals ( userName )) {
                        hasPermission = true;
                        break;
                    }
                }
            }
            if(!hasPermission)
                throw new Exception("only owners and managers of the shop can view it's information");
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
            double currAmount = itemAmount.getValue();
            if (this.itemsCurrentAmount.get(currItem) < currAmount){
                failed = true;
                missingMessage.append(String.format("%s X %f",currItem.getName(), currAmount));
            }
        }
        if (failed){
            throw new MarketException(missingMessage);
        }
        for (Map.Entry<Item,Double> itemAmount: items.entrySet()){
            Item currItem = itemAmount.getKey();
            double newAmount = this.itemsCurrentAmount.get(currItem) - itemAmount.getValue();
            this.itemsCurrentAmount.put(currItem, newAmount);
        }
        purchaseHistory.add ( shoppingBasket.getReview () );
        return shoppingBasket.getPrice ();
    }


    public List<Item> getAllItemsByPrice(double minPrice, double maxPrice) {
        throw new UnsupportedOperationException();
    }

    public synchronized Map<Integer, Item> getItemMap() {
        return itemMap;
    }

    public boolean isShopOwner(String memberName) {
        return shopOwners.containsKey(memberName);
    }

    public boolean isClosed() {
        return closed;
    }

    public String getShopName() {
        return shopName;
    }

    //TODO need to check if works
    public Map<String, Appointment> getEmployees(){
        Map employees = new HashMap (  );
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

    public Appointment getManagerAppointment(String shopOwnerName, String managerName) throws MarketException {

        Appointment ownerAppointment = shopOwners.get(shopOwnerName);
        if (ownerAppointment == null ){
            throw new MarketException(String.format("%s: cannot find an owner '%s'",shopName , shopOwnerName));
        }
        Appointment appointment = shopManagers.get(managerName);
        if (appointment ==  null ){
            throw new MarketException(String.format("%s: cannot find an appointment of %s" , this.getShopName(), managerName));
        }
        if (!appointment.getSuperVisor().getName().equals(shopOwnerName)){
            throw new MarketException(String.format("%s: you must be %s superVisor to change his permissions" , this.getShopName(), managerName));
        }
        return appointment;
    }

    //TODO shop owner wants to get info about what? need to insert another name?
    public Map<String, Appointment> getShopEmployeesInfo(String shopManagerName) throws MarketException {
        Appointment employee = shopManagers.get ( shopManagerName );
        if(employee == null)
            employee = shopOwners.get ( shopManagerName );
        if(employee == null) {
            ErrorLog.getInstance().Log("Tried to get information on someone who doesnt work in the shop.");
            throw new MarketException(shopManagerName + " is not working at this shop");
        }
        if (!employee.isOwner()) {
            ErrorLog.getInstance().Log("Non shop owner tried to access employees info. ");
            throw new MarketException("only owners can view employees info");
        }
        return employee.getShopEmployeesInfo();
    }

    public Shop getShopInfo(String member) throws MarketException {
        if (isClosed() && !isShopOwner ( member )) {
            ErrorLog.getInstance ( ).Log ( "Non shop owner or system manager tried to access shop info. " );
            throw new MarketException ( "member must be shop owner or system manager in order to get a close shop info" );
        }
        return this;
    }

    private boolean isEmployee(String memberName){
        for (Map.Entry<String, Appointment> appointment : shopManagers.entrySet()) {
            if (appointment.getValue().getAppointed().getName().equals(memberName)) {
                return true;
            }
        }
        for ( Map.Entry<String, Appointment> appointment : shopOwners.entrySet ( ) ) {
            if (appointment.getValue ( ).getAppointed ( ).getName ( ).equals ( memberName )) {
                return true;
            }
        }
        return false;
    }

    private Appointment getEmployee(String member){
        Appointment employee = shopManagers.get ( member );
        if(employee != null)
            return employee;
        return shopOwners.get ( member );
    }

    public ShoppingBasket validateBasket(ShoppingBasket basket) {
        Map<Item, Double> items = basket.getItems();
        for (Map.Entry<Item, Double> currentItem : items.entrySet())
        {
            if (currentItem.getValue()>itemsCurrentAmount.get(currentItem.getKey()))
            {
                currentItem.setValue(itemsCurrentAmount.get(currentItem.getKey()));
            }
        }
        return basket;
    }

    public void addEmployee(Appointment newAppointment) throws MarketException {
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

    public List<Item> getItemsByCategory(Item.Category category) {
        List<Item> toReturn = new ArrayList<>();
        for (Item item : itemMap.values()){
            if (item.getCategory().equals(category))
                toReturn.add(item);
        }
        return toReturn;
    }

    public List<Item> getItemsByKeyword(String keyword) {
        List<Item> toReturn = new ArrayList<>();
        for (Item item : itemMap.values()){
            if (item.getKeywords().contains(keyword))
                toReturn.add(item);
        }
        return toReturn;
    }

    public Item getItem(ItemFacade item) {
        return itemMap.get ( item );
    }

    public boolean isManager(String shopManagerName) {
        return shopManagers.get ( shopManagerName ) != null;
    }

    public boolean hasPermission(String shopManagerName, String permission) {
        if(shopOwners.get ( shopManagerName ) != null)
            return true;
        if(shopManagers.get ( shopManagerName ) == null)
            return false;
        return shopManagers.get ( shopManagerName ).hasPermission(permission);
    }

    public StringBuilder getPurchaseHistory(String shopManagerName) throws MarketException {
        if(!hasPermission(shopManagerName, "get_purchase_history")) {
            ErrorLog.getInstance().Log("Non authorized user tried to access shop's purchase history.");
            throw new MarketException(shopManagerName + " is not authorized to see shop purchase history");
        }
        return getReview ();
    }

    @Override
    public StringBuilder getReview() {
        StringBuilder review = new StringBuilder ( "Shop name: " + shopName + "\n" );
        int i = 1;
        for ( StringBuilder acquisition : purchaseHistory ){
            review.append ( String.format ("acquisition %d:\n %s", i, acquisition.toString () ));
            i++;
        }
        EventLog eventLog = EventLog.getInstance();
        eventLog.Log("A user recived the shop: "+this.shopName + " history.");
        return review;
    }

    public Item addItem(String shopOwnerName, String itemName, double price, Item.Category category, String info, List<String> keywords, double amount, int id) throws MarketException {
        if(!isShopOwner ( shopOwnerName ))
            throw new MarketException ( "member is not the shop owner so not authorized to add an item to the shop" );
        if(amount < 0)
            throw new MarketException ( "amount has to be positive" );
        if(category == null)
            category = Item.Category.general;
        Item addedItem = new Item ( id, itemName, price, info, category, keywords);
        itemMap.put ( id, addedItem );
        itemsCurrentAmount.put ( addedItem, amount );
        return addedItem;
    }

    public void addRank(int rankN){
        rank=((rank*rankers)+rankN)/(rankers+1);
        rankers++;
    }

    public int getRank(){return rank;}

    public void changeShopItemInfo(String shopOwnerName, Item updatedItem, Item oldItem) throws MarketException {
        if(!isShopOwner ( shopOwnerName ))
            throw new MarketException ( "member is not shop owner so is not authorized to vhange item info" );
        if (!itemMap.containsValue ( oldItem ))
            throw new MarketException ( "item does not exist in shop" );
        deleteItem ( oldItem );
        addItem ( updatedItem );
    }

    public void removeItemMissing(ShoppingBasket shoppingBasket) throws MarketException {
        Map<Item, Double> items = shoppingBasket.getItems();
        for (Map.Entry<Item,Double> itemAmount: items.entrySet()){
            Item currItem = itemAmount.getKey();
            double currAmount = itemAmount.getValue();
            double amount = this.itemsCurrentAmount.get(currItem);
            if ( amount < currAmount){
                if(amount == 0)
                    shoppingBasket.removeItem ( currItem );
                else
                    shoppingBasket.updateQuantity ( amount, currItem );
            }
        }
    }
}
