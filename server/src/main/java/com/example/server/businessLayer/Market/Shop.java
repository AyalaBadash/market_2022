package com.example.server.businessLayer.Market;

import com.example.server.businessLayer.Market.Appointment.PendingAppointments;
import com.example.server.businessLayer.Market.Appointment.Permissions.PurchaseHistoryPermission;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount.CompositeDiscount;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.CompositeDiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.CompositePurchasePolicyType;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicy;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.CompositePurchasePolicyLevelState;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyType;
import com.example.server.businessLayer.Market.ResourcesObjects.*;
import com.example.server.businessLayer.Market.Appointment.Appointment;
import com.example.server.businessLayer.Market.Appointment.ShopManagerAppointment;
import com.example.server.businessLayer.Market.Appointment.ShopOwnerAppointment;
import com.example.server.businessLayer.Market.Users.UserController;
import com.example.server.businessLayer.Publisher.NotificationHandler;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountPolicy;
import com.example.server.businessLayer.Market.Users.Member;
import org.springframework.stereotype.Component;

import javax.swing.event.DocumentEvent;
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
    @ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinTable (name = "allItemsInShop",
            joinColumns = {@JoinColumn(name = "shop", referencedColumnName = "shop_name")},
            inverseJoinColumns = {@JoinColumn(name = "item", referencedColumnName = "ID")})
    @MapKeyColumn (name = "item_id")
    private Map<java.lang.Integer, Item> itemMap;             //<ItemID,main.businessLayer.Item>
    @ManyToMany(cascade = {CascadeType.REMOVE})
    @JoinTable (name = "shopManagers",
            joinColumns = {@JoinColumn(name = "shop", referencedColumnName = "shop_name")},
            inverseJoinColumns = {@JoinColumn(name = "appointment", referencedColumnName = "id")})
    @MapKeyColumn (name = "member_name")
    private Map<String, Appointment> shopManagers;     //<name, appointment>
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable (name = "shopOwners",
        joinColumns = {@JoinColumn(name = "shop", referencedColumnName = "shop_name")},
        inverseJoinColumns = {@JoinColumn(name = "appointment", referencedColumnName = "id")})
    @MapKeyColumn (name = "member_name")
    private Map<String, Appointment> shopOwners;     //<name, appointment>
    @ElementCollection
    @CollectionTable(name = "items_in_shop")
    @MapKeyColumn(name="item_id")
    @Column(name="amount")
    private Map<java.lang.Integer, Double> itemsCurrentAmount;
    private boolean closed;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private PendingAppointments pendingAppointments;

    private static ShopRep shopRep;
    @OneToOne
    Member shopFounder;//todo
    private int rnk;
    private int rnkers;
    @ElementCollection
    @Column (name = "purchase_history")
    @CollectionTable (name = "purchase_histories")
    private List<StringBuilder> purchaseHistory;
    //TODO getter,setter,constructor
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private DiscountPolicy discountPolicy;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private PurchasePolicy purchasePolicy;
    @ManyToMany (cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    List<Bid> bids;

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
        discountPolicy = new DiscountPolicy ();
        if (!MarketConfig.IS_TEST_MODE) {
            DiscountPolicy.getDiscountPolicyRep().save(discountPolicy);
        }
        purchasePolicy = new PurchasePolicy ();
        if (!MarketConfig.IS_TEST_MODE) {
            PurchasePolicy.getPurchasePolicyRep().save(purchasePolicy);
        }
        ShopOwnerAppointment shopOwnerAppointment = new ShopOwnerAppointment(founder, null, this, true);
        shopOwners.put(founder.getName(), shopOwnerAppointment);
        founder.addAppointmentToMe(shopOwnerAppointment);
        discountPolicy = new DiscountPolicy ();
        purchasePolicy = new PurchasePolicy ();
        this.pendingAppointments = new PendingAppointments();
        if (!MarketConfig.IS_TEST_MODE) {
            PendingAppointments.getPendingAptsRep().save(pendingAppointments);
        }
        bids = new ArrayList<> (  );
        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
        shopOwnerAppointment.setRelatedShop(this);
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
//        this.shopManagers.put ( managerName, appointment );
        shopManagers.get(managerName).setPermissions(appointment.getPermissions());
        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
        this.shopManagers.put ( managerName, appointment );
        if(appointment.hasPermission ( "ApproveBidPermission" )){
            for(Bid bid : bids){
                bid.addApproves(appointment.getAppointed ().getName ());
            }
        }
    }


    //use case - Stock management
    public void editItem(Item newItem, String id) throws MarketException {
        int newItemId = newItem.getID();
        int oldItemId = java.lang.Integer.parseInt(id);
        if (newItemId != oldItemId)
            throw new MarketException ( "must not change the item id" );
        itemMap.put( newItem.getID ( ), newItem );
    }



    public void deleteItem(Item item, String shopOwnerName) throws MarketException {
        //Check if user indeed is the shop owner
        if (!isShopOwner(shopOwnerName)) {
            DebugLog.getInstance().Log(shopOwnerName + " tried to remove item from the shop " + shopName + " but he is not a owner.");
            throw new MarketException(shopOwnerName + " is not " + shopName + " owner . Removing item from shop has failed.");
        }
        itemMap.remove ( item.getID() );
        itemsCurrentAmount.remove ( item.getID () );
        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
        for(Bid bid : bids){
            if(bid.getItemId () == item.getID ()) {
                bid.rejectBid ( shopOwnerName );
                UserController.getInstance ().getVisitor ( bid.getBuyerName () ).getCart ().rejectBid(bid.getItemId (), this);
            }
        }
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
        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
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
        Map<java.lang.Integer, Double> items = shoppingBasket.getItems( );
        for ( Map.Entry<java.lang.Integer, Double> itemAmount : items.entrySet ( ) ) {
//            Item currItem = itemAmount.getKey ( );
            if(itemsCurrentAmount.get(itemAmount.getKey()) == null)
                throw new MarketException("shopping basket holds an item which does not exist in market");
            Double newAmount =this.itemsCurrentAmount.get ( itemAmount.getKey() )+  itemAmount.getValue ( );//
            this.itemsCurrentAmount.put ( itemAmount.getKey(), newAmount );
        }
        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
    }

    //Bar: adding the parameter buyer name for the notification send.
    public synchronized double buyBasket(NotificationHandler publisher, ShoppingBasket shoppingBasket, String buyer) throws MarketException {
        //the notification to the shop owners publisher.
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
            Integer id = itemAmount.getKey();
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
            throw new MarketException ( "" + missingMessage );
        }
        for ( Map.Entry<java.lang.Integer, Double> itemAmount : items.entrySet ( ) ) {
            Item currItem = shoppingBasket.getItemMap().get(itemAmount.getKey());
            double newAmount = this.itemsCurrentAmount.get ( currItem.getID() ) - itemAmount.getValue ( );
            this.itemsCurrentAmount.put ( itemAmount.getKey(), newAmount );
        }
        for(Bid bid : shoppingBasket.getBids ().values ()) {
            double bidAmount = bid.getAmount ();
            int bidItemID = bid.getItemId ();
            if (bid.getApproved() && (itemsCurrentAmount.get (bidItemID) != null && itemsCurrentAmount.get (bidItemID) > bidAmount)) {
                itemsCurrentAmount.put (bidItemID, itemsCurrentAmount.get ( bidItemID ) - bidAmount  );
            }
        }
        purchaseHistory.add ( shoppingBasket.getReview ( ) );

        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
        //send notifications to shop owners:
        try{
            publisher.sendItemBaughtNotificationsBatch(buyer,names,shopName,itemsNames,prices);
        }
        //todo - need to be handled
        catch (Exception e){}
        if(purchasePolicy.isPoliciesHeld (shoppingBasket ))
            return discountPolicy.calculateDiscount ( shoppingBasket );
        throw new MarketException ( "shopping basket does not held the purchase policy" );
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
        for ( Map.Entry<java.lang.Integer, Double> entry : items.entrySet ( ) ) {
            Item curItem = basket.getItemMap().get(entry.getKey());;
            Double curAmount = itemsCurrentAmount.get(entry.getKey());
            if (entry.getValue ( ) > curAmount) {
                if(curAmount == 0)
                    basket.removeItem ( curItem );
                else
                    items.replace(entry.getKey(),curAmount);
            }
        }
        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
        return basket;
    }

    public double getPriceOfShoppingBasket(ShoppingBasket shoppingBasket) throws MarketException {
        return discountPolicy.calculateDiscount ( shoppingBasket );
    }

    public synchronized void OldaddEmployee(Appointment newAppointment) throws MarketException {
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
        if(newAppointment.hasPermission ( "ApproveBidPermission" ))
            for(Bid bid : bids){
                bid.addApproves(newAppointment.getAppointed ().getName ());
            }
    }


    public synchronized void addEmployee(Appointment newAppointment) throws MarketException {
        String employeeName = newAppointment.getAppointed ( ).getName ( );
        Appointment oldAppointment = shopOwners.get ( employeeName );
        if (oldAppointment != null) {
            if (newAppointment.isOwner ( ))
                throw new MarketException ( "this member is already a shop owner" );
            shopManagers.put ( employeeName, newAppointment );
            if(newAppointment.hasPermission ( "ApproveBidPermission" ))
                for(Bid bid : bids){
                    bid.addApproves(newAppointment.getAppointed ().getName ());
                }
        } else {
            oldAppointment = shopManagers.get ( employeeName );
            if (oldAppointment != null) {
                if (newAppointment.isManager ( ))
                    throw new MarketException ( "this member is already a shop manager" );
                ShopOwnerAppointment app = (ShopOwnerAppointment) newAppointment;
                List<String> owners = new ArrayList<>();
                for (Map.Entry<String, Appointment> entry: this.shopOwners.entrySet())
                {
                    owners.add(entry.getKey());
                }
                pendingAppointments.addAppointment(employeeName,app,owners);
                approveAppointment(app.getAppointed().getName(),app.getSuperVisor().getName());
                try {
                    String role=getRole(newAppointment);
                    NotificationHandler.getInstance().sendNewAppointmentBatch(owners, newAppointment.getAppointed(),newAppointment.getSuperVisor(), shopName,role);
                    EventLog.getInstance().Log("Message has been sent to shop workers about the new appointment.");
                } catch (Exception e) {
                    ErrorLog.getInstance().Log("Could not send notification to  shop workers about the new appointment.");
                }
            } else if (newAppointment.isOwner ( )) {
                ShopOwnerAppointment app = (ShopOwnerAppointment) newAppointment;
                List<String> owners = new ArrayList<>();
                for (Map.Entry<String, Appointment> entry: this.shopOwners.entrySet())
                {
                    owners.add(entry.getKey());
                }
                pendingAppointments.addAppointment(employeeName,app,owners);
                approveAppointment(app.getAppointed().getName(),app.getSuperVisor().getName());
                try {
                    String role=getRole(newAppointment);
                    NotificationHandler.getInstance().sendNewAppointmentBatch(owners, newAppointment.getAppointed(),newAppointment.getSuperVisor(), shopName,role);
                    EventLog.getInstance().Log("Message has been sent to shop workers about the new appointment.");
                } catch (Exception e) {
                    ErrorLog.getInstance().Log("Could not send notification to  shop workers about the new appointment.");
                }
            }
            else {
                shopManagers.put(employeeName, newAppointment);
                if(newAppointment.hasPermission ( "ApproveBidPermission" ))
                    for(Bid bid : bids){
                        bid.addApproves(newAppointment.getAppointed ().getName ());
                    }
            }
        }

        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
    }

    private String getRole(Appointment newAppointment){
        String role="";
        if(newAppointment.isOwner()){
            role="shop owner";
        }
        else if(newAppointment.isManager()){
            role="shop manager";
        } else if (((ShopOwnerAppointment) newAppointment).isShopFounder()) {
            role= "shop founder";
        }
        return role;
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
            review.append("\n");
            i++;
        }
        EventLog eventLog = EventLog.getInstance ( );
        eventLog.Log ( "A user received the shop: " + this.shopName + " history." );
        return review;
    }

    //TODO why do we need this.
    public synchronized Item addItem(String shopOwnerName, String itemName, double price, Item.Category category, String info, List<String> keywords, double amount, int id) throws MarketException {
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
        if (!MarketConfig.IS_TEST_MODE) {
//        itemRepository.save(addedItem.toDalObject()); //todo
        }

        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
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

        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
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
        appointment.setRelatedShop(this);
        addEmployee ( appointment );

        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
    }

    public void appointShopManager(Member shopOwner, Member appointed) throws MarketException {
        if (appointed == null || isShopManager ( appointed.getName ( ) )) {
            DebugLog.getInstance ().Log ( "appointed shop manager is already a shop manager of the shop." );
            throw new MarketException ( "appointed shop manager is already a shop manager of the shop." );
        }
        if (shopOwner == null || !isShopOwner ( shopOwner.getName ( ) ))
            throw new MarketException ( "member is not a shop owner so is not authorized to appoint shop owner" );
        ShopManagerAppointment appointment = new ShopManagerAppointment ( appointed, shopOwner, this ,new ArrayList<>(),false);
//        appointment.addAllPermissions();
        appointed.addAppointmentToMe(appointment);
        addEmployee ( appointment );

        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
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
        for(Bid bid : bids){
            bid.removeApproves(firedAppointed);
        }
        shopOwners.remove(firedAppointed);
        removeFiredOwnerFromPending(firedAppointed);
        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
    }

    private void removeFiredOwnerFromPending(String firedAppointed) throws MarketException {
        List<String> completed= pendingAppointments.removeOwner(firedAppointed);
        for (String name:completed){
            shopOwners.put(name,pendingAppointments.getAppointments().get(name));
            pendingAppointments.removeAppointment(name);
        }
    }

    public synchronized void addDiscountToShop(String visitorName, DiscountType discountType) throws MarketException {
        if (!isShopOwner ( visitorName )) {
            DebugLog.getInstance().Log("member is not the shop owner so not authorized to add a discount to the shop");
            throw new MarketException("member is not the shop owner so not authorized to add a discount to the shop");
        }
        discountPolicy.addNewDiscount ( discountType );
        EventLog.getInstance().Log("Added a new discount to the shop:"+shopName);
        if (!MarketConfig.IS_TEST_MODE) {
            shopRep.save(this);
        }
    }


    public static void setShopRep(ShopRep shopRepToSet){
        shopRep = shopRepToSet;
    }
    public synchronized void removeDiscountFromShop(String visitorName, DiscountType discountType) throws MarketException {
        if (!isShopOwner ( visitorName )) {
            DebugLog.getInstance().Log("member is not the shop owner so not authorized to add a discount to the shop");
            throw new MarketException("member is not the shop owner so not authorized to add a discount to the shop");
        }
        discountPolicy.removeDiscount ( discountType );
        EventLog.getInstance().Log("Removed a discount from the shop:"+shopName);
    }

    public synchronized void addPurchasePolicyToShop(String visitorName, PurchasePolicyType purchasePolicyType) throws MarketException {
        if (!isShopOwner ( visitorName )) {
            DebugLog.getInstance().Log("member is not the shop owner so not authorized to add a purchase policy to the shop");
            throw new MarketException("member is not the shop owner so not authorized to add a purchase policy to the shop");
        }
        purchasePolicy.addNewPurchasePolicy( purchasePolicyType );
        EventLog.getInstance().Log("Added new purchase policy to the shop:"+shopName);
    }

    public synchronized void removePurchasePolicyFromShop(String visitorName, PurchasePolicyType purchasePolicyType) throws MarketException {
        if (!isShopOwner ( visitorName )) {
            DebugLog.getInstance().Log("member is not the shop owner so not authorized to add a purchase policy to the shop");
            throw new MarketException("member is not the shop owner so not authorized to add a discount to the shop");
        }
        purchasePolicy.removePurchasePolicy ( purchasePolicyType );
        EventLog.getInstance().Log("Removed a purchase policy from the shop:"+shopName);
    }

    public boolean hasItem(Item itemToCheck) {
        for(Map.Entry<Integer,Item> item: itemMap.entrySet()){
            if(item.getValue().getID().equals(itemToCheck.getID())){
                return item.getValue().equals(itemToCheck);
            }
        }
        return false;
    }

    public Bid addABid(String visitorName, Integer itemId, Double price, double amount) throws MarketException {
        Item item = itemMap.get(itemId);
        if (item == null) {
            DebugLog.getInstance().Log("tried to add a bid to an item which does not exist in shop");
            throw new MarketException ( "item does not exist in shop" );
        }
        if(price <= 0){
            DebugLog.getInstance().Log("price for bid cannot be negative");
            throw new MarketException ( "price for bid cannot be negative" );
        }
        List<String> approvingAppointments = new ArrayList<> (  );
        for(String name : shopOwners.keySet ())
            approvingAppointments.add ( name );
        for ( Map.Entry<String, Appointment> appointment: shopManagers.entrySet () ){
            if (appointment.getValue ().hasPermission ( "ApproveBidPermission" ))
                approvingAppointments.add ( appointment.getKey () );
        }
        Bid bid = new Bid (visitorName,UserController.getInstance ().isMember ( visitorName ), itemId, price, amount, approvingAppointments);
        bids.add ( bid );
        if (!MarketConfig.IS_TEST_MODE){
            shopRep.save(this);
        }
        NotificationHandler handler = NotificationHandler.getInstance ();
        String itemName = item.getName ();
        handler.sendNewBidToApprovalOfApprovesNotificationBatch ( approvingAppointments, visitorName, price, itemName, shopName);
        EventLog.getInstance().Log("New bid added to the shop:" + shopName+". The buyer name is:"+visitorName);
        return bid;
    }

    public boolean approveABid(String approves, String askedBy, Integer itemId) throws MarketException {
        if((shopManagers.get ( approves ) == null || shopManagers.get ( approves ).hasPermission ( "ApproveBidPermission" )) && (shopOwners.get ( approves ) == null)){
            DebugLog.getInstance ().Log ( "visitor does not has the authority to approve a bid." );
            throw new MarketException ( "visitor does not has the authority to approve a bid." );
        }
        Bid bidToApprove = findBid ( askedBy, itemId );
        if(bidToApprove.approveBid ( approves )){
            NotificationHandler handler = NotificationHandler.getInstance ();
            String itemName = itemMap.get ( bidToApprove.getItemId () ).getName ();
            if(itemsCurrentAmount.get ( itemId ) < 1){
                handler.sendBidRejectedNotification ( bidToApprove.getBuyerName (), bidToApprove.isMember (), bidToApprove.getPrice (), itemName, shopName);
                return false;
            }
            handler.sendBidApprovedNotification ( bidToApprove.getBuyerName (), bidToApprove.isMember (), bidToApprove.getPrice (), itemName, shopName );
            return true;
        }
        if(bidToApprove.getSideNeedToApprove ().equals ( Bid.Side.buyer )) {
            NotificationHandler handler = NotificationHandler.getInstance ();
            String itemName = itemMap.get ( bidToApprove.getItemId () ).getName ();
            handler.sendNewOfferOfBidNotification ( askedBy, bidToApprove.isMember ( ), bidToApprove.getPrice (), itemName, shopName );
        }
        return false;
    }

    public void suggestNewOfferToBid(String suggester, String askedBy, int itemId, double newPrice) throws MarketException {
        if((shopManagers.get ( suggester ) == null || shopManagers.get ( suggester ).hasPermission
                ( "ApproveBidPermission" )) && (shopOwners.get ( suggester ) == null)){
            DebugLog.getInstance ().Log ( "visitor does not has the authority to suggest a counter-bid." );
            throw new MarketException ( "visitor does not has the authority to suggest a counter-bid." );
        }
        Bid bidToApprove = findBid ( askedBy, itemId );
        bidToApprove.suggestNewOffer ( suggester, newPrice );
        NotificationHandler handler = NotificationHandler.getInstance ();
        String itemName = itemMap.get ( itemId ).getName ();
        handler.sendNewOfferOfBidToApprovalOfApprovesNotificationBatch ( bidToApprove.getShopOwnersStatus ().
                keySet ().stream( ).toList (), bidToApprove.getBuyerName (), newPrice, itemName, shopName );
        EventLog.getInstance().Log("The owner: "+suggester+" offered a counter - bid for the buyer:"+askedBy);
    }

    public void rejectABid(String opposed, String buyer, int itemId) throws MarketException {
        Bid bidToReject = findBid ( buyer, itemId );
        bidToReject.rejectBid ( opposed );
        NotificationHandler handler = NotificationHandler.getInstance ();
        String itemName = itemMap.get ( itemId ).getName ();
        if(opposed.equals ( buyer )){
            handler.sendBidRejectedToApprovesNotificationBatch ( bidToReject.getShopOwnersStatus ().keySet ().stream( ).toList (), buyer, bidToReject.getPrice (), itemName, shopName );
        }
        if((shopManagers.get ( opposed ) == null || shopManagers.get ( opposed ).hasPermission ( "ApproveBidPermission" )) && (shopOwners.get ( opposed ) == null)){
            DebugLog.getInstance ().Log ( "visitor does not has the authority to reject a bid." );
            throw new MarketException ( "visitor does not has the authority to reject a bid." );
        }
        bids.remove(bidToReject);
        handler.sendBidRejectedNotification ( buyer, bidToReject.isMember (), bidToReject.getPrice (), itemName, shopName );
        EventLog.getInstance().Log("The bid for the buyer:"+buyer+" in the shop:"+shopName+" has been rejected");
    }

    public void cancelABid(String buyer, int itemId) throws MarketException {
        Bid bidToCancel = findBid ( buyer, itemId );
        NotificationHandler handler = NotificationHandler.getInstance ();
        String itemName = itemMap.get ( itemId ).getName ();
        handler.sendBidCanceledToApprovesNotificationBatch ( bidToCancel.getShopOwnersStatus ().keySet ().stream( ).toList (), buyer, bidToCancel.getPrice (), itemName, shopName );
        EventLog.getInstance().Log(buyer+" has canceled his bid in the shop:"+shopName);
    }



    private Bid findBid(String buyer, int itemId) throws MarketException {
        Bid bidToApprove = null;
        for(Bid bid : bids){
            if (bid.getBuyerName ().equals ( buyer ) && bid.getItemId ().equals ( itemId ))
                bidToApprove = bid;
        }
        if(bidToApprove == null){
            DebugLog.getInstance ().Log ( "Bid does not exist in the shop." );
            throw new MarketException ( "Bid does not exist in the shop." );
        }
        return bidToApprove;
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

    public Map<Integer, Double> getItemsCurrentAmount() {
        return itemsCurrentAmount;
    }

    public List<StringBuilder> getPurchaseHistory() {
        return purchaseHistory;
    }

    public double calculateDiscount(ShoppingBasket shoppingBasket) throws MarketException {
        return discountPolicy.calculateDiscount ( shoppingBasket );
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void updateBidInLoggingOut(String visitorName) {
        List<Bid> bidsToRemove = new ArrayList<> (  );
        for(Bid bid : bids){
            if(bid.getBuyerName ().equals ( visitorName ))
                bidsToRemove.add ( bid );
        }
        for(Bid bid : bidsToRemove){
            bids.remove ( bid );
        }
    }


    public boolean approveAppointment(String appointedName, String ownerName) throws MarketException {
        if (!shopOwners.containsKey(ownerName)){
            DebugLog.getInstance().Log(ownerName+" is not an owner in this shop. Therefore he cannot approve any appointment.");
            throw new MarketException(ownerName+" is not an owner in this shop. Therefore he cannot approve any appointment.");
        }
        boolean approved = pendingAppointments.approve(appointedName,ownerName);
        if (approved) {
            ShopOwnerAppointment appointment = pendingAppointments.getAppointments().get(appointedName);
            shopOwners.put(appointedName, appointment);
            pendingAppointments.removeAppointment(appointedName);
            if (!MarketConfig.IS_TEST_MODE){
                shopRep.save(this);
            }
            EventLog.getInstance().Log("Finally " + appointedName+" appointment has been approved. Now he is a shop owner");
            for (Bid bid : bids) {
                bid.addApproves(appointedName);
            }
            try {
                NotificationHandler.getInstance().sendAppointmentApproved(appointedName, ownerName, shopName);
                EventLog.getInstance().Log("Message has been sent to user about the appointment approval.");
            } catch (Exception e) {
                ErrorLog.getInstance().Log("Could not send notification to the user for appointment approve.");
            }
            return true;
        }
        else {
            EventLog.getInstance().Log(ownerName +" approved "+appointedName+" appointment. Waiting for other owners approval.");
            return false;
        }
    }

    public void rejectAppointment(String appointedName,String ownerName) throws MarketException {
        if (!shopOwners.containsKey(ownerName)){
            DebugLog.getInstance().Log(ownerName+" is not an owner in this shop. Therefore he cannot approve any appointment.");
            throw new MarketException(ownerName+" is not an owner in this shop. Therefore he cannot approve any appointment.");
        }
        if (!pendingAppointments.getAppointments().containsKey(appointedName)){
            DebugLog.getInstance().Log("There is no pending appointment for "+ appointedName);
            throw new MarketException("There is no pending appointment for "+ appointedName);
        }
        if(pendingAppointments.getAgreements().get(appointedName).getOwnersAppointmentApproval().containsKey(ownerName)) {
            pendingAppointments.removeAppointment(appointedName);
        }
        else {
            DebugLog.getInstance().Log(ownerName+ " tried to reject appointment which he does not take part in.");
            throw new MarketException("You dont have the authority to reject this appointment.");
        }
        try {
            NotificationHandler.getInstance().sendAppointmentRejectedNotification(appointedName, ownerName, shopName);
            EventLog.getInstance().Log("Message has been sent to user about the appointment rejection.");
        } catch (Exception e){
            ErrorLog.getInstance().Log("Could not send notification to the user about appointment approval.");
        }
        EventLog.getInstance().Log("The appointment for:"+appointedName+" has been rejected.");

    }
    public List<String> getAllPendingForOwner(String ownerName) throws MarketException {
        if (!shopOwners.containsKey(ownerName)){
            DebugLog.getInstance().Log("You are not a shop owner");
            throw new MarketException("You are not a shop owner");
        }
        return pendingAppointments.getMyPendingAppointments(ownerName);

    }

    public void setClosed(boolean closed) {
        this.closed = closed;
        if (!MarketConfig.IS_TEST_MODE){
            shopRep.save(this);
        }
    }

    public void setShopManagers(Map<String, Appointment> shopManagers) {
        this.shopManagers = shopManagers;
    }

    public void setShopOwners(Map<String, Appointment> shopOwners) {
        this.shopOwners = shopOwners;
    }
    public void setPendingAppointments(PendingAppointments pendingAppointments) {
        this.pendingAppointments = pendingAppointments;
    }

    public PendingAppointments getPendingAppointments() {
        return pendingAppointments;
    }

    public void loadData(){
        getItemMap().toString();
        getItemsCurrentAmount().toString();
        getPurchaseHistory().toString();

        for (Appointment a : getEmployees().values()) {
            a.setRelatedShop(this); //set related shop in appointment
            a.getPermissions().toString();
        }
        bids.toString();
        List<DiscountType> dscnts = getDiscountPolicy().getValidDiscounts();
        dscnts.toString();
        for (DiscountType dt : dscnts){
            if (dt.getDiscountLevelState() instanceof CompositeDiscountLevelState)
                ((CompositeDiscountLevelState) dt.getDiscountLevelState()).getDiscountLevelStates().toString();
            if (dt instanceof CompositeDiscount)
                ((CompositeDiscount) dt).getDiscountTypes().toString();
        }
        getDiscountPolicy().toString();
        getPurchasePolicies().toString();
        for (PurchasePolicyType p : getPurchasePolicies()) {
            if (p instanceof CompositePurchasePolicyType)
                ((CompositePurchasePolicyType) p).getPolicies().toString();
            if (p.getPurchasePolicyLevelState() instanceof CompositePurchasePolicyLevelState)
                ((CompositePurchasePolicyLevelState) p.getPurchasePolicyLevelState()).getPurchasePolicyLevelStates().toString();
        }

        getPurchasePolicy().toString();
        getPendingAppointments().getAppointments().toString();
        getPendingAppointments().getAgreements().toString();
    }
}
