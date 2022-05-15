package com.example.server.businessLayer;

import com.example.server.ResourcesObjects.ErrorLog;
import com.example.server.ResourcesObjects.EventLog;
import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Appointment.ShopManagerAppointment;
import com.example.server.businessLayer.Appointment.ShopOwnerAppointment;
import com.example.server.businessLayer.Policies.Discount.DiscountPolicy;
import com.example.server.businessLayer.Users.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Shop implements IHistory {
    private String shopName;
    private Map<Integer, Item> itemMap;             //<ItemID,main.businessLayer.Item>
    private Map<String, Appointment> shopManagers;     //<name, appointment>
    private Map<String, Appointment> shopOwners;     //<name, appointment>
    private Map<Integer, Double> itemsCurrentAmount;
    private boolean closed;

    Member shopFounder;//todo
    private int rank;
    private int rankers;
    private List<StringBuilder> purchaseHistory;
    //TODO getter,setter,constructor
    private DiscountPolicy discountPolicy;

    public Shop(String name,Member founder) {
        this.shopName = name;
        itemMap = new HashMap<> ( );
        shopManagers = new ConcurrentHashMap<> ( );
        shopOwners = new ConcurrentHashMap<> ( );
        itemsCurrentAmount = new ConcurrentHashMap<>( );
        this.closed = false;
        purchaseHistory = new ArrayList<> ( );
        rank = 1;
        rankers = 0;
        this.shopFounder = founder;
        ShopOwnerAppointment shopOwnerAppointment = new ShopOwnerAppointment(founder, null, this, true);
        shopOwners.put(founder.getName(), shopOwnerAppointment);
        founder.addAppointmentToMe(shopOwnerAppointment);
    }

    public void editManagerPermission(String superVisorName, String managerName, Appointment appointment) throws MarketException {


        Appointment ownerAppointment = shopOwners.get ( superVisorName );
        if (ownerAppointment == null) {
            ErrorLog.getInstance ( ).Log ( String.format ( "%s: cannot find an owner '%s'", shopName, superVisorName ) );
            throw new MarketException ( String.format ( "%s: cannot find an owner '%s'", shopName, superVisorName ) );
        }
        Appointment oldAppointment = shopManagers.get ( managerName );
        if (oldAppointment == null) {
            ErrorLog.getInstance ( ).Log ( String.format ( "%s: cannot find an appointment of %s", this.getShopName ( ), managerName ) );
            throw new MarketException ( String.format ( "%s: cannot find an appointment of %s", this.getShopName ( ), managerName ) );
        }
        if (!oldAppointment.getSuperVisor ( ).equals ( superVisorName )) {
            ErrorLog.getInstance ( ).Log ( String.format ( "%s is not the supervisor of %s so is not authorized to change the permissions", superVisorName, managerName ) );
            throw new MarketException ( String.format ( "%s is not the supervisor of %s so is not authorized to change the permissions", superVisorName, managerName ) );
        }
        //TODO: check the ability to remove permission(not update).
        this.shopManagers.put ( managerName, appointment );
    }


    //use case - Stock management
    public void editItem(Item newItem, String id) throws MarketException {
        int newItemId = newItem.getID();
        int oldItemId = Integer.parseInt(id);
        if (newItemId != oldItemId)
            throw new MarketException ( "must not change the item id" );
        itemMap.put ( newItem.getID ( ), newItem );
    }


    public void deleteItem(Item item) {
        itemMap.remove ( item.getID() );
    }
    /*
    private void addItem(Item item) throws MarketException {
        if (!itemMap.containsKey ( item.getID ( ) ))
            itemMap.put ( item.getID ( ), item );
        else throw new MarketException ( "Item name already exist" );
    }*/

    public double getItemCurrentAmount(Item item) {
        return itemsCurrentAmount.get(itemMap.get(item.getID()));
    }

    public Map<Integer, Double> getItemsCurrentAmountMap() {
        return itemsCurrentAmount;
    }


    public void setItemAmount(String shopOwnerName, Integer item, double amount) throws MarketException {
        if (!isShopOwner ( shopOwnerName )) {
            throw new MarketException ( "member is not the shop owner and is not authorized to effect the inventory." );
        }
        if (amount < 0)
            throw new MarketException ( "amount cannot be negative" );
        if (itemMap.get ( item ) == null) {
            throw new MarketException("item does not exist in the shop");
        }
        itemsCurrentAmount.replace ( item, amount );
    }
    /*
    public Item receiveInfoAboutItem(String itemId, String userName) throws Exception {
        if (isClosed ( )) {
            boolean hasPermission = false;
            for ( Map.Entry<String, Appointment> appointment : shopManagers.entrySet ( ) ) {
                if (appointment.getValue ( ).getAppointed ( ).getName ( ).equals ( userName )) {
                    hasPermission = true;
                    break;
                }
            }
            if (!hasPermission) {
                for ( Map.Entry<String, Appointment> appointment : shopOwners.entrySet ( ) ) {
                    if (appointment.getValue ( ).getAppointed ( ).getName ( ).equals ( userName )) {
                        hasPermission = true;
                        break;
                    }
                }
            }
            if (!hasPermission)
                throw new Exception ( "only owners and managers of the shop can view it's information" );
        }
        if (!itemMap.containsKey ( itemId )) {
            throw new Exception ( "no such item in shop" );
        } else {
            return itemMap.get ( itemId );
        }

    }
    */


    public void releaseItems(ShoppingBasket shoppingBasket) throws MarketException {
        Map<Integer, Double> items = shoppingBasket.getItems ( );
        for ( Map.Entry<Integer, Double> itemAmount : items.entrySet ( ) ) {
//            Item currItem = itemAmount.getKey ( );
            if(itemsCurrentAmount.get(itemAmount.getKey()) == null)
                throw new MarketException("shopping basket holds an item which does not exist in market");
            Double newAmount =this.itemsCurrentAmount.get ( itemAmount.getKey() )+  itemAmount.getValue ( );//
            this.itemsCurrentAmount.put ( itemAmount.getKey(), newAmount );
        }
    }

    //Bar: adding the parameter buyer name for the notification send.
    public synchronized double buyBasket(ShoppingBasket shoppingBasket,String buyer) throws MarketException {
        //the notification to the shop owners publisher.
        Publisher publisher= Publisher.getInstance();
        ArrayList<String> names = new ArrayList<>(getShopOwners().values().stream().collect(Collectors.toList()).stream()
                .map(appointment -> appointment.getAppointed().getName()).collect(Collectors.toList()));
        String shopName = getShopName();
        ArrayList<String> itemsNames =new ArrayList<>();
        ArrayList<Double> prices =new ArrayList<>();
        //
        Map<Integer, Double> items = shoppingBasket.getItems ( );
        StringBuilder missingMessage = new StringBuilder ( );
        boolean failed = false;
        missingMessage.append ( String.format ( "%s: cannot complete your purchase because some items are missing:\n", this.shopName ) );
        for ( Map.Entry<Integer, Double> itemAmount : items.entrySet ( ) ) {
            Item currItem = shoppingBasket.getItemMap().get(itemAmount.getKey());
            //for notifications:
            itemsNames.add(currItem.getName());
            prices.add(currItem.getPrice());
            //
            double currAmount = itemAmount.getValue ( );
            if (this.itemsCurrentAmount.get ( currItem ) < currAmount) {
                failed = true;
                missingMessage.append ( String.format ( "%s X %f", currItem.getName ( ), currAmount ) );
            }
        }
        if (failed) {
            throw new MarketException ( missingMessage );
        }
        for ( Map.Entry<Integer, Double> itemAmount : items.entrySet ( ) ) {
            Item currItem = shoppingBasket.getItemMap().get(itemAmount.getKey());
            double newAmount = this.itemsCurrentAmount.get ( currItem ) - itemAmount.getValue ( );
            this.itemsCurrentAmount.put ( itemAmount.getKey(), newAmount );
        }
        purchaseHistory.add ( shoppingBasket.getReview ( ) );
        //send notifications to shop owners:
        //TODO cancel comment
//        publisher.sendItemBaughtNotificationsBatch(names,shopName,itemsNames,prices);
        //
        return shoppingBasket.getPrice ( );
    }


    public List<Item> getAllItemsByPrice(double minPrice, double maxPrice) {//TODO - do we need this?
        throw new UnsupportedOperationException ( );
    }

    public synchronized Map<Integer, Item> getItemMap() {
        return itemMap;
    }

    public boolean isShopOwner(String memberName) {
        return shopOwners.containsKey ( memberName );
    }

    public boolean isClosed() {
        return closed;
    }

    public String getShopName() {
        return shopName;
    }

    //TODO need to check if works
    public Map<String, Appointment> getEmployees() {
        Map<String,Appointment> employees = new HashMap<>( );
        employees.putAll ( shopOwners );
        employees.putAll ( shopManagers );
        return employees;
    }

    public Member getShopFounder() {
        return this.shopFounder;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Shop && ((Shop) obj).getShopName ( ).equals ( this.shopName );
    }

    public Appointment getManagerAppointment(String shopOwnerName, String managerName) throws MarketException {

        Appointment ownerAppointment = shopOwners.get ( shopOwnerName);
        if (ownerAppointment == null) {
            throw new MarketException ( String.format ( "%s: cannot find an owner '%s'", shopName, shopOwnerName ) );
        }
        Appointment appointment = shopManagers.get ( managerName );
        if (appointment == null) {
            throw new MarketException ( String.format ( "%s: cannot find an appointment of %s", this.getShopName ( ), managerName ) );
        }
        if (!appointment.getSuperVisor ( ).getName ( ).equals ( shopOwnerName )) {
            throw new MarketException ( String.format ( "%s: you must be %s superVisor to change his permissions", this.getShopName ( ), managerName ) );
        }
        return appointment;
    }

    //TODO shop owner wants to get info about what? need to insert another name?
    public Map<String, Appointment> getShopEmployeesInfo(String shopManagerName) throws MarketException {
        Appointment employee = shopManagers.get ( shopManagerName );
        if (employee == null)
            employee = shopOwners.get ( shopManagerName );
        if (employee == null) {
            ErrorLog.getInstance ( ).Log ( "Tried to get information on someone who doesnt work in the shop." );
            throw new MarketException ( shopManagerName + " is not working at this shop" );
        }
        if (!employee.isOwner ( )) {
            ErrorLog.getInstance ( ).Log ( "Non shop owner tried to access employees info. " );
            throw new MarketException ( "only owners can view employees info" );
        }
        return employee.getShopEmployeesInfo ( );
    }

    public Shop getShopInfo(String member) throws MarketException {
        if (isClosed ( ) && !isShopOwner ( member )) {
            ErrorLog.getInstance ( ).Log ( "Non shop owner or system manager tried to access shop info. " );
            throw new MarketException ( "member must be shop owner or system manager in order to get a close shop info" );
        }
        return this;
    }

    private boolean isEmployee(String memberName) {
        for ( Map.Entry<String, Appointment> appointment : shopManagers.entrySet ( ) ) {
            if (appointment.getValue ( ).getAppointed ( ).getName ( ).equals ( memberName )) {
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

    private Appointment getEmployee(String member) {
        Appointment employee = shopManagers.get ( member );
        if (employee != null)
            return employee;
        return shopOwners.get ( member );
    }

    public ShoppingBasket validateBasket(ShoppingBasket basket) {
        Map<Integer, Double> items = basket.getItems ( );
        for ( Map.Entry<Integer, Double> currentItem : items.entrySet ( ) ) {
            Item curItem = basket.getItemMap().get(currentItem.getKey());;
            Double curAmount = itemsCurrentAmount.get(curItem);
            if (currentItem.getValue ( ) > curAmount) {
                if(curAmount == 0)
                    basket.removeItem ( curItem );
                currentItem.setValue ( itemsCurrentAmount.get ( currentItem.getKey ( ) ) );
            }
        }
        return basket;
    }

    public void addEmployee(Appointment newAppointment) throws MarketException {
        String employeeName = newAppointment.getAppointed ( ).getName ( );
        Appointment oldAppointment = shopOwners.get ( employeeName );
        if (oldAppointment != null) {
            if (newAppointment.isOwner ( ))
                throw new MarketException ( "this member is already a shop owner" );
            shopManagers.put ( employeeName, newAppointment );
        } else {
            oldAppointment = shopManagers.get ( employeeName );
            if (oldAppointment != null) {
                if (newAppointment.isManager ( ))
                    throw new MarketException ( "this member is already a shop manager" );
                shopOwners.put ( employeeName, newAppointment );
            } else if (newAppointment.isOwner ( )) {
                shopOwners.put(employeeName, newAppointment);
            }
            else
                shopManagers.put ( employeeName, newAppointment );
        }
    }

    public List<Item> getItemsByCategory(Item.Category category) {
        List<Item> toReturn = new ArrayList<> ( );
        for ( Item item : itemMap.values ( ) ) {
            if (item.getCategory ( ).equals ( category ))
                toReturn.add ( item );
        }
        return toReturn;
    }

    public List<Item> getItemsByKeyword(String keyword) {
        List<Item> toReturn = new ArrayList<> ( );
        for ( Item item : itemMap.values ( ) ) {
            if (item.getKeywords ( ).contains ( keyword ))
                toReturn.add ( item );
        }
        return toReturn;
    }

    public boolean isManager(String shopManagerName) {
        return shopManagers.get ( shopManagerName ) != null;
    }

    public boolean hasPermission(String shopManagerName, String permission) {
        if (shopOwners.get ( shopManagerName ) != null)
            return true;
        if (shopManagers.get ( shopManagerName ) == null)
            return false;
        return shopManagers.get ( shopManagerName ).hasPermission ( permission );
    }

    public StringBuilder getPurchaseHistory(String shopManagerName) throws MarketException {
        if (!hasPermission ( shopManagerName, "get_purchase_history" )) {
            ErrorLog.getInstance ( ).Log ( "Non authorized user tried to access shop's purchase history." );
            throw new MarketException ( shopManagerName + " is not authorized to see shop purchase history" );
        }
        return getReview ( );
    }

    @Override
    public StringBuilder getReview() {
        StringBuilder review = new StringBuilder ( "Shop name: " + shopName + "\n" );
        int i = 1;
        for ( StringBuilder acquisition : purchaseHistory ) {
            review.append ( String.format ( "acquisition %d:\n %s", i, acquisition.toString ( ) ) );
            i++;
        }
        EventLog eventLog = EventLog.getInstance ( );
        eventLog.Log ( "A user received the shop: " + this.shopName + " history." );
        return review;
    }

    //TODO why do we need this.
    public Item addItem(String shopOwnerName, String itemName, double price, Item.Category category, String info, List<String> keywords, double amount, int id) throws MarketException {

        if (!isShopOwner ( shopOwnerName ))
            throw new MarketException ( "member is not the shop owner so not authorized to add an item to the shop" );
        if (amount < 0)
            throw new MarketException ( "amount has to be positive" );
        if (itemMap.containsKey(id))
            throw new MarketException("ID is taken by other item");
        if (category == null)
            category = Item.Category.general;
        Item addedItem = new Item ( id, itemName, price, info, category, keywords );
        itemMap.put ( id, addedItem );
        itemsCurrentAmount.put ( id, amount );
        return addedItem;
    }

    public void addRank(int rankN) {
        rank = ((rank * rankers) + rankN) / (rankers + 1);
        rankers++;
    }

    public int getRank() {
        return rank;
    }

    public void changeShopItemInfo(String shopOwnerName, String info, Integer oldItemID) throws MarketException {
        if (!isShopOwner ( shopOwnerName ))
            throw new MarketException ( "member is not shop owner so is not authorized to change item info" );
        Item item = itemMap.get(oldItemID);
        if (item == null)
            throw new MarketException ( "item does not exist in shop" );
        item.setInfo(info);
    }

//    public void removeItemMissing(ShoppingBasket shoppingBasket) throws MarketException {
//        Map<Item, Double> items = shoppingBasket.getItems ( );
//        for ( Map.Entry<Item, Double> itemAmount : items.entrySet ( ) ) {
//            Item currItem = itemAmount.getKey ( );
//            double currAmount = itemAmount.getValue ( );
//            double amount = this.itemsCurrentAmount.get ( currItem );
//            if (amount < currAmount) {
//                if (amount == 0)
//                    shoppingBasket.removeItem ( currItem );
//                else
//                    shoppingBasket.updateQuantity ( amount, currItem );
//            }
//        }
//    }

    public void appointShopOwner(Member shopOwner, Member appointedShopOwner) throws MarketException {
        if (isShopOwner ( appointedShopOwner.getName ( ) )){
            ErrorLog.getInstance ().Log ( "appointed shop owner is already a shop owner of the shop." );
            throw new MarketException ( "appointed shop owner is already a shop owner of the shop." );
        }
        //TODO : check the is shop member check . makes some problem with instances. change it to ==
        if (shopOwner == null || !isShopOwner ( shopOwner.getName ( ) )) {
            ErrorLog.getInstance ().Log ( "member is not a shop owner so is not authorized to appoint shop owner" );
            throw new MarketException ( "member is not a shop owner so is not authorized to appoint shop owner" );
        }
        ShopOwnerAppointment appointment = new ShopOwnerAppointment ( appointedShopOwner, shopOwner, this, false );
        addEmployee ( appointment );
    }

    public void appointShopManager(Member shopOwner, Member appointed) throws MarketException {
        if (appointed == null || isShopManager ( appointed.getName ( ) )) {
            ErrorLog.getInstance ().Log ( "appointed shop manager is already a shop manager of the shop." );
            throw new MarketException ( "appointed shop manager is already a shop manager of the shop." );
        }
        if (shopOwner == null || !isShopOwner ( shopOwner.getName ( ) ))
            throw new MarketException ( "member is not a shop owner so is not authorized to appoint shop owner" );
        ShopManagerAppointment appointment = new ShopManagerAppointment ( appointed, shopOwner, this );
        addEmployee ( appointment );
    }

    private boolean isShopManager(String name) {

        Appointment app= shopManagers.get ( name );

        return app !=null;
    }

    public Map<String, Appointment> getShopOwners() {
        return shopOwners;
    }

    public Map<String, Appointment> getShopManagers() {
        return shopManagers;
    }

    public void removeShopOwnerAppointment(String boss, String firedAppointed) throws MarketException {
        if (!this.shopOwners.containsKey(boss))
        {
            ErrorLog.getInstance().Log("You are not a shop owner in the shop:"+this.shopName+" so you cant remove a shop owner");
            throw new MarketException("You are not a shop owner in the shop:"+this.shopName+" so you cant remove a shop owner");
        }
        if (!this.shopOwners.containsKey(firedAppointed))
        {
            ErrorLog.getInstance().Log(firedAppointed+" is not a shop owner in the shop:"+this.shopName+" so there no need to remove his appointment.");
            throw new MarketException(firedAppointed+" is not a shop owner in the shop:"+this.shopName+" so there no need to remove his appointment.");
        }
        Appointment appointment = shopOwners.get(firedAppointed);
        if (!appointment.getSuperVisor().getName().equals(boss))
        {
            ErrorLog.getInstance().Log(boss + " didnt appoint "+firedAppointed+" so he cant remove his appointment.");
            throw new MarketException(boss + " didnt appoint "+firedAppointed+" so he cant remove his appointment.");
        }

        for (Map.Entry<String,Appointment> entry:shopOwners.entrySet())
        {
            Appointment toCheck = entry.getValue();
            Member appMember = toCheck.getSuperVisor();
            if (appMember!=null &&toCheck.getSuperVisor().getName().equals(firedAppointed))
                removeShopOwnerAppointment(firedAppointed,toCheck.getAppointed().getName());
        }
        for (Map.Entry<String,Appointment> entry:shopManagers.entrySet())
        {
            Appointment toCheck = entry.getValue();
            if (toCheck.getSuperVisor().getName().equals(firedAppointed))
                shopManagers.remove(entry.getKey());
        }
        shopOwners.remove(firedAppointed);


    }
}
