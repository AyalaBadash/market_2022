package com.example.server.businessLayer.Market;

import com.example.server.businessLayer.Market.Appointment.Permissions.PurchaseHistoryPermission;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicy;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyType;
import com.example.server.businessLayer.Market.ResourcesObjects.DebugLog;
import com.example.server.businessLayer.Market.ResourcesObjects.EventLog;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.Appointment.Appointment;
import com.example.server.businessLayer.Market.Appointment.ShopManagerAppointment;
import com.example.server.businessLayer.Market.Appointment.ShopOwnerAppointment;
import com.example.server.businessLayer.Market.Users.UserController;
import com.example.server.businessLayer.Publisher.NotificationHandler;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountPolicy;
import com.example.server.businessLayer.Market.Users.Member;
import com.example.server.dataLayer.repositories.ShopRep;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Entity
public class Shop implements IHistory {
    @Id
    @Column (name = "shop_name")
    private String shopName;
    @Transient
    private Map<java.lang.Integer, Item> itemMap;             //<ItemID,main.businessLayer.Item>
    @Transient //TODO
    private Map<String, Appointment> shopManagers;     //<name, appointment>
    @Transient //TODO
    private Map<String, Appointment> shopOwners;     //<name, appointment>
    @ElementCollection
    @CollectionTable(name = "items_in_shop")
    @MapKeyColumn(name="item_id")
    @Column(name="amount")
    private Map<java.lang.Integer, Double> itemsCurrentAmount;
    private boolean closed;


    @Transient
    private static ShopRep shopRep;
    @Transient
    Member shopFounder;//todo
    private int rnk;
    private int rnkers;
    @ElementCollection
    @Column (name = "purchase_history")
    @CollectionTable (name = "purchase_histories")
    private List<StringBuilder> purchaseHistory;
    //TODO getter,setter,constructor
    @Transient
    private DiscountPolicy discountPolicy;

    private PurchasePolicy purchasePolicy;

    public Shop(){}

    public Shop(String name,Member founder) {
        this.shopName = name;
        itemMap = new HashMap<> ( );
        shopManagers = new ConcurrentHashMap<> ( );
        shopOwners = new ConcurrentHashMap<> ( );
        itemsCurrentAmount = new ConcurrentHashMap<>( );
        this.closed = false;
        purchaseHistory = new ArrayList<> ( );
        rnk = 1;
        rnkers = 0;
        this.shopFounder = founder;
        ShopOwnerAppointment shopOwnerAppointment = new ShopOwnerAppointment(founder, null, this, true);
        shopOwners.put(founder.getName(), shopOwnerAppointment);
        founder.addAppointmentToMe(shopOwnerAppointment);
        discountPolicy = new DiscountPolicy ();
        purchasePolicy = new PurchasePolicy ();
        shopRep.save(this);
    }

    public void editManagerPermission(String superVisorName, String managerName, Appointment appointment) throws MarketException {
        Appointment ownerAppointment = shopOwners.get ( superVisorName );
        if (ownerAppointment == null) {
            DebugLog.getInstance ( ).Log ( String.format ( "%s: cannot find an owner '%s'", shopName, superVisorName ) );
            throw new MarketException ( String.format ( "%s: cannot find an owner '%s'", shopName, superVisorName ) );
        }
        Appointment oldAppointment = shopManagers.get ( managerName );
        if (oldAppointment == null) {
            DebugLog.getInstance ( ).Log ( String.format ( "%s: cannot find an appointment of %s", this.getShopName ( ), managerName ) );
            throw new MarketException ( String.format ( "%s: cannot find an appointment of %s", this.getShopName ( ), managerName ) );
        }
        if (!oldAppointment.getSuperVisor( ).getName().equals ( superVisorName )) {
            DebugLog.getInstance ( ).Log ( String.format ( "%s is not the supervisor of %s so is not authorized to change the permissions", superVisorName, managerName ) );
            throw new MarketException ( String.format ( "%s is not the supervisor of %s so is not authorized to change the permissions", superVisorName, managerName ) );
        }
        this.shopManagers.put ( managerName, appointment );
        shopRep.save(this);
    }


    //use case - Stock management
    public void editItem(Item newItem, String id) throws MarketException {
        int newItemId = newItem.getID();
        int oldItemId = java.lang.Integer.parseInt(id);
        if (newItemId != oldItemId)
            throw new MarketException ( "must not change the item id" );
        itemMap.put( newItem.getID ( ), newItem );
        //todo save
    }


    public void deleteItem(Item item) {
        itemMap.remove( item.getID() );
        itemsCurrentAmount.remove(item.getID());
        shopRep.save(this);
//        getDalObject().removeItemFromShop(item.toDalObject()); //todo
    }
    /*
    private void addItem(Item item) throws MarketException {
        if (!itemMap.containsKey ( item.getID ( ) ))
            itemMap.put ( item.getID ( ), item );
        else throw new MarketException ( "Item name already exist" );
    }*/

    public double getItemCurrentAmount(Item item) throws MarketException {
        Double amount = itemsCurrentAmount.get(item.getID());
        if(amount == null)
            throw new MarketException("no such item in this shop");
        return amount;
    }

    public Map<java.lang.Integer, Double> getItemsCurrentAmountMap() {
        return itemsCurrentAmount;
    }


    public void setItemAmount(String shopOwnerName, java.lang.Integer item, double amount) throws MarketException {
        if (!isShopOwner ( shopOwnerName )) {
            throw new MarketException ( "member is not the shop owner and is not authorized to effect the inventory." );
        }
        if (amount < 0)
            throw new MarketException ( "amount cannot be negative" );
        if (itemMap.get ( item ) == null) {
            throw new MarketException("item does not exist in the shop");
        }
        itemsCurrentAmount.replace ( item, amount );
        shopRep.save(this);
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
        Map<java.lang.Integer, Double> items = shoppingBasket.getItems ( );
        for ( Map.Entry<java.lang.Integer, Double> itemAmount : items.entrySet ( ) ) {
//            Item currItem = itemAmount.getKey ( );
            if(itemsCurrentAmount.get(itemAmount.getKey()) == null)
                throw new MarketException("shopping basket holds an item which does not exist in market");
            Double newAmount =this.itemsCurrentAmount.get ( itemAmount.getKey() )+  itemAmount.getValue ( );//
            this.itemsCurrentAmount.put ( itemAmount.getKey(), newAmount );
        }
        shopRep.save(this);
    }

    //Bar: adding the parameter buyer name for the notification send.
    public synchronized double buyBasket(NotificationHandler publisher, ShoppingBasket shoppingBasket, String buyer,boolean test) throws MarketException {
        //the notification to the shop owners publisher.
        ArrayList<String> names = new ArrayList<>(getShopOwners().values().stream().collect(Collectors.toList()).stream()
                .map(appointment -> appointment.getAppointed().getName()).collect(Collectors.toList()));
        String shopName = getShopName();
        ArrayList<String> itemsNames =new ArrayList<>();
        ArrayList<Double> prices =new ArrayList<>();
        //
        Map<java.lang.Integer, Double> items = shoppingBasket.getItems ( );
        StringBuilder missingMessage = new StringBuilder ( );
        boolean failed = false;
        missingMessage.append ( String.format ( "%s: cannot complete your purchase because some items are missing:\n", this.shopName ) );
        for ( Map.Entry<java.lang.Integer, Double> itemAmount : items.entrySet ( ) ) {
            java.lang.Integer id = itemAmount.getKey();
            Item currItem = shoppingBasket.getItemMap().get(id);
            //for notifications:
            itemsNames.add(currItem.getName());
            prices.add(currItem.getPrice());
            //
            double currAmount = itemAmount.getValue ( );
            if (this.itemsCurrentAmount.get ( currItem.getID() ) < currAmount) {
                failed = true;
                missingMessage.append ( String.format ( "%s X %f", currItem.getName ( ), currAmount ) );
            }
        }
        if (failed) {
            throw new MarketException ( missingMessage );
        }
        for ( Map.Entry<java.lang.Integer, Double> itemAmount : items.entrySet ( ) ) {
            Item currItem = shoppingBasket.getItemMap().get(itemAmount.getKey());
            double newAmount = this.itemsCurrentAmount.get ( currItem.getID() ) - itemAmount.getValue ( );
            this.itemsCurrentAmount.put ( itemAmount.getKey(), newAmount );
        }
        purchaseHistory.add ( shoppingBasket.getReview ( ) );
        //send notifications to shop owners:
        try{
            publisher.sendItemBaughtNotificationsBatch(buyer,names,shopName,itemsNames,prices,test);
        }
        //todo - need to be handled
        catch (Exception e){}
        if(purchasePolicy.isPoliciesHeld (shoppingBasket ))
            return discountPolicy.calculateDiscount ( shoppingBasket );
        throw new MarketException ( "shopping basket does not held the purchase policy" );
        shopRep.save(this);
        return shoppingBasket.getPrice ( );
    }


    public List<Item> getAllItemsByPrice(double minPrice, double maxPrice) {//TODO - do we need this?
        throw new UnsupportedOperationException ( );
    }

    public synchronized Map<java.lang.Integer, Item> getItemMap() {
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
            DebugLog.getInstance ( ).Log ( "Tried to get information on someone who doesnt work in the shop." );
            throw new MarketException ( shopManagerName + " is not working at this shop" );
        }
        if (!employee.isOwner ( )) {
            DebugLog.getInstance ( ).Log ( "Non shop owner tried to access employees info. " );
            throw new MarketException ( "only owners can view employees info" );
        }
        return employee.getShopEmployeesInfo ( );
    }

    public Shop getShopInfo(String member) throws MarketException {
        if (isClosed ( ) && !isShopOwner ( member )) {
            DebugLog.getInstance ( ).Log ( "Non shop owner or system manager tried to access shop info. " );
            throw new MarketException ( "member must be shop owner or system manager in order to get a close shop info" );
        }
        return this;
    }

    public boolean isEmployee(String memberName) {
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
        Map<java.lang.Integer, Double> items = basket.getItems ( );
        for ( Map.Entry<java.lang.Integer, Double> currentItem : items.entrySet ( ) ) {
            Item curItem = basket.getItemMap().get(currentItem.getKey());;
            Double curAmount = itemsCurrentAmount.get(currentItem.getKey());
            if (currentItem.getValue ( ) > curAmount) {
                if(curAmount == 0)
                    basket.removeItem ( curItem );
                currentItem.setValue ( itemsCurrentAmount.get ( currentItem.getKey ( ) ) );
            }
        }
        shopRep.save(this);
        return basket;
    }

    public double getPriceOfShoppingBasket(ShoppingBasket shoppingBasket) throws MarketException {
        return discountPolicy.calculateDiscount ( shoppingBasket );
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
        shopRep.save(this);
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
        if (!hasPermission ( shopManagerName, "PurchaseHistoryPermission" )) {
            DebugLog.getInstance ( ).Log ( "Non authorized user tried to access shop's purchase history." );
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
        Item addedItem;
        synchronized (this) {
            if (itemNameExists(itemName))
                throw new MarketException("different item with the same name already exists in this shop");
            addedItem = new Item(id, itemName, price, info, category, keywords);
            itemMap.put( id, addedItem );
        }
        itemsCurrentAmount.put ( id, amount );
//        itemRepository.save(addedItem.toDalObject()); //todo
        shopRep.save(this);
        return addedItem;
    }

    private boolean itemNameExists(String itemName) {
        for(Item item: itemMap.values()){
            if(item.getName().equals(itemName))
                return true;
        }
        return false;
    }

    public void addRank(int rankN) {
        rnk = ((rnk * rnkers) + rankN) / (rnkers + 1);
        rnkers++;
    }

    public int getRnk() {
        return rnk;
    }

    public void changeShopItemInfo(String shopOwnerName, String info, java.lang.Integer oldItemID) throws MarketException {
        if (!isShopOwner ( shopOwnerName ))
            throw new MarketException ( "member is not shop owner so is not authorized to change item info" );
        Item item = itemMap.get(oldItemID);
        if (item == null)
            throw new MarketException ( "item does not exist in shop" );
        item.setInfo(info);
        shopRep.save(this);
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
            DebugLog.getInstance ().Log ( "appointed shop owner is already a shop owner of the shop." );
            throw new MarketException ( "appointed shop owner is already a shop owner of the shop." );
        }
        //TODO : check the is shop member check . makes some problem with instances. change it to ==
        if (shopOwner == null || !isShopOwner ( shopOwner.getName ( ) )) {
            DebugLog.getInstance ().Log ( "member is not a shop owner so is not authorized to appoint shop owner" );
            throw new MarketException ( "member is not a shop owner so is not authorized to appoint shop owner" );
        }
        ShopOwnerAppointment appointment = new ShopOwnerAppointment ( appointedShopOwner, shopOwner, this, false );
        addEmployee ( appointment );
        shopRep.save(this);
    }

    public void appointShopManager(Member shopOwner, Member appointed) throws MarketException {
        if (appointed == null || isShopManager ( appointed.getName ( ) )) {
            DebugLog.getInstance ().Log ( "appointed shop manager is already a shop manager of the shop." );
            throw new MarketException ( "appointed shop manager is already a shop manager of the shop." );
        }
        if (shopOwner == null || !isShopOwner ( shopOwner.getName ( ) ))
            throw new MarketException ( "member is not a shop owner so is not authorized to appoint shop owner" );
        ShopManagerAppointment appointment = new ShopManagerAppointment ( appointed, shopOwner, this ,new ArrayList<>(){{ add(new PurchaseHistoryPermission());}});
        addEmployee ( appointment );
        shopRep.save(this);
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
            DebugLog.getInstance().Log("You are not a shop owner in the shop:"+this.shopName+" so you cant remove a shop owner");
            throw new MarketException("You are not a shop owner in the shop:"+this.shopName+" so you cant remove a shop owner");
        }
        if (!this.shopOwners.containsKey(firedAppointed))
        {
            DebugLog.getInstance().Log(firedAppointed+" is not a shop owner in the shop:"+this.shopName+" so there no need to remove his appointment.");
            throw new MarketException(firedAppointed+" is not a shop owner in the shop:"+this.shopName+" so there no need to remove his appointment.");
        }
        Appointment appointment = shopOwners.get(firedAppointed);
        if (!appointment.getSuperVisor().getName().equals(boss))
        {
            DebugLog.getInstance().Log(boss + " didnt appoint "+firedAppointed+" so he cant remove his appointment.");
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
        shopRep.save(this);
    }



    public void addDiscountToShop(String visitorName, DiscountType discountType) throws MarketException {
        if (!isShopOwner ( visitorName ))
            throw new MarketException ( "member is not the shop owner so not authorized to add a discount to the shop" );
        discountPolicy.addNewDiscount ( discountType );
        shopRep.save(this);
    }

    public boolean hasItem(Item item) {
        return itemMap.get(item.getID()) != null;
    }

        public static void setShopRep(ShopRep shopRepToSet){
            shopRep = shopRepToSet;
        }
    public void removeDiscountFromShop(String visitorName, DiscountType discountType) throws MarketException {
        if (!isShopOwner ( visitorName ))
            throw new MarketException ( "member is not the shop owner so not authorized to add a discount to the shop" );
        discountPolicy.removeDiscount ( discountType );
    }

    public void addPurchasePolicyToShop(String visitorName, PurchasePolicyType purchasePolicyType) throws MarketException {
        if (!isShopOwner ( visitorName ))
            throw new MarketException ( "member is not the shop owner so not authorized to add a purchase policy to the shop" );
        purchasePolicy.addNewPurchasePolicy( purchasePolicyType );
    }

    public void removePurchasePolicyFromShop(String visitorName, PurchasePolicyType purchasePolicyType) throws MarketException {
        if (!isShopOwner ( visitorName ))
            throw new MarketException ( "member is not the shop owner so not authorized to add a discount to the shop" );
        purchasePolicy.removePurchasePolicy ( purchasePolicyType );
    }


    public List<PurchasePolicyType> getPurchasePolicies() {
        return purchasePolicy.getValidPurchasePolicies ();
    }

    public List<DiscountType> getDiscountTypes() {
        return discountPolicy.getValidDiscounts ();
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public PurchasePolicy getPurchasePolicy() {
        return purchasePolicy;
    }
}


