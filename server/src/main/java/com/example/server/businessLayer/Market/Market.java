package com.example.server.businessLayer.Market;

import com.example.server.businessLayer.Market.Appointment.Appointment;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Payment.PaymentServiceProxy;
import com.example.server.businessLayer.Publisher.Publisher;
import com.example.server.businessLayer.Supply.Address;
import com.example.server.businessLayer.Payment.PaymentMethod;
import com.example.server.businessLayer.Supply.SupplyServiceProxy;
import com.example.server.businessLayer.Supply.SupplyService;
import com.example.server.businessLayer.Market.ResourcesObjects.*;
import com.example.server.businessLayer.Publisher.NotificationDispatcher;
import com.example.server.businessLayer.Publisher.NotificationHandler;
import com.example.server.businessLayer.Security.Security;
import com.example.server.businessLayer.Market.Users.Member;
import com.example.server.businessLayer.Market.Users.UserController;
import com.example.server.businessLayer.Market.Users.Visitor;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.ConditionFacade;
import com.example.server.serviceLayer.Notifications.RealTimeNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.juli.logging.Log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Market {
    private UserController userController;
    private String systemManagerName;
    private Map<String, Shop> shops;                                 // <shopName, shop>

    private NotificationHandler notificationHandler;
    private Map<java.lang.Integer, String> allItemsInMarketToShop;             // <itemID,ShopName>
    private Map<String, List<java.lang.Integer>> itemByName;                   // <itemName ,List<itemID>>
    private SynchronizedCounter nextItemID;
    private PaymentServiceProxy paymentServiceProxy;
    private SupplyServiceProxy supplyServiceProxy;
    private Publisher dispatcher;

    private static Market instance;
    Map<String,Integer> numOfAcqsPerShop;

    private Market() {
        this.shops = new ConcurrentHashMap<>();
        this.allItemsInMarketToShop = new ConcurrentHashMap<>();
        this.itemByName = new ConcurrentHashMap<>();
        this.userController = UserController.getInstance();
        nextItemID = new SynchronizedCounter();
        this.numOfAcqsPerShop = new HashMap<>();
        this.dispatcher=null;
        //TODO - supply and payment service
    }


    public synchronized static Market getInstance() {
        if (instance == null) {
            instance = new Market();
        }
        return instance;
    }

    public synchronized void firstInitMarket(PaymentServiceProxy paymentServiceProxy1, SupplyServiceProxy supplyServiceProxy1, Publisher publisher,String userName, String password) throws MarketException {
        if (this.paymentServiceProxy != null || this.supplyServiceProxy != null){
            DebugLog.getInstance().Log("A market initialization failed .already initialized");
            throw new MarketException("market is already initialized");
        }
        if (paymentServiceProxy1 == null || supplyServiceProxy1 == null) {
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("A market initialization failed . Lack of payment / supply services ");
            throw new MarketException("market needs payment and supply services for initialize");
        }
        if(publisher==null){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("A market initialization failed . Lack of publisher services ");
            throw new MarketException("market needs publisher services for initialize");

        }
        register ( userName, password );
        instance.systemManagerName = userName;
        instance.paymentServiceProxy = paymentServiceProxy1;
        instance.supplyServiceProxy = supplyServiceProxy1;
        instance.dispatcher=publisher;
        EventLog eventLog = EventLog.getInstance();
        eventLog.Log("A market has been initialized successfully");

    }



    public StringBuilder getAllSystemPurchaseHistory(String memberName) throws MarketException {
        if (!userController.isLoggedIn(memberName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if(!systemManagerName.equals ( memberName )) {
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("Member who is not the system manager tried to access system purchase history");
            throw new MarketException("member is not a system manager so is not authorized to get th information");
        }
        StringBuilder history = new StringBuilder ( "Market history: \n" );
        for ( Shop shop: shops.values () ){
            history.append ( shop.getReview () );
        }
        EventLog eventLog = EventLog.getInstance();
        eventLog.Log("System manager got purchase history");
        return history;
    }


    public StringBuilder getHistoryByShop(String member, String shopName) throws MarketException {
        if (!userController.isLoggedIn(member)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if(!systemManagerName.equals ( member )) {
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("Member who is not the system manager tried to access system purchase history");
            throw new MarketException("member is not a system manager so is not authorized to get th information");
        }
        Shop shop = shops.get ( shopName );
        if(shop == null) {
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("User tried to get shop history for a non exiting shop");
            throw new MarketException("shop does not exist in the market");
        }
        return shop.getReview ();
    }

    public StringBuilder getHistoryByMember(String systemManagerName, String memberName) throws MarketException {
        if (!userController.isLoggedIn(systemManagerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if(systemManagerName.equals ( this.systemManagerName )){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("Member who is not the system manager tried to access system purchase history");
            throw new MarketException ( "member is not a system manager so is not authorized to get th information" );
        }

        Member member = userController.getMember ( memberName );
        if(member == null){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("Tried to get history for a non existing member");
            throw new MarketException ( "member does not exist" );
        }
        StringBuilder history = member.getPurchaseHistoryString();
        return history;
    }


    public void register(String name, String password) throws MarketException {
        Security security = Security.getInstance();
        security.validateRegister(name,password);
        userController.register(name);
        EventLog eventLog = EventLog.getInstance();
        eventLog.Log("A new user registered , welcome "+name);
    }


    public List<String> memberLogin(String userName, String userPassword) throws MarketException {
        Security security = Security.getInstance();
        return security.validatePassword(userName,userPassword);
    }


    public Visitor guestLogin() {
        return userController.guestLogin();
    }

    public Shop getShopByName(String shopName) {
        return shops.get ( shopName );
    }


    public void addSecurity() {

    }
    public ShoppingCart calculateShoppingCart(String visitorName) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        ShoppingCart currentCart = userController.getVisitorsInMarket().get(visitorName).getCart();
        ShoppingCart updatedCart = validateCart(currentCart);
        return updatedCart;
    }


    public Map<String, Shop> getShops() {
        return shops;
    }




    public Map<java.lang.Integer, String> getAllItemsInMarketToShop() {
        return allItemsInMarketToShop;
    }

    public List<Item> getItemByName(String name) {
        if (!itemByName.containsKey(name)) {
            return new ArrayList<> (  );
        }
        List<Item> toReturn = new ArrayList<>();
        List<java.lang.Integer> itemIds =  itemByName.get(name);
        for (int item : itemIds){
            String shopStr = allItemsInMarketToShop.get(item);
            Shop shop = shops.get(shopStr);
            toReturn.add(shop.getItemMap().get(item));
        }
        return toReturn;
    }

    public List<Item> getItemByCategory(Item.Category category){
        List<Item> toReturn = new ArrayList<>();
        for (Shop shop : shops.values()){
            toReturn.addAll(shop.getItemsByCategory(category));
        }
        return toReturn;
    }

    public List<Item> getItemsByKeyword(String keyword){
        List<Item> toReturn = new ArrayList<>();
        for (Shop shop : shops.values()){
            toReturn.addAll(shop.getItemsByKeyword(keyword));
        }
        return toReturn;
    }

    public List<Item> filterItemsByPrice(List<Item> items, double minPrice, double maxPrice) {
        List<Item> toReturn = new ArrayList<>();
        for (Item item : items){
            if (item.getPrice() >= minPrice && item.getPrice() <= maxPrice)
                toReturn.add(item);
        }
        return toReturn;
    }

    public List<Item> filterItemsByCategory(List<Item> items, Item.Category category){
        List<Item> toReturn = new ArrayList<>();
        for (Item item : items){
            if (item.getCategory().equals(category))
                toReturn.add(item);
        }
        return toReturn;
    }


    public static void setInstance(Market instance) {
        Market.instance = instance;
    }

    public PaymentServiceProxy getPaymentService() {
        return paymentServiceProxy;

    }

    //TODO make private

    /**
     *
     * @param memberName the paying member's name.
     * @throws MarketException
     */
    public void setPaymentService(PaymentServiceProxy paymentService1, String memberName) throws MarketException {
        if(!userController.isLoggedIn(memberName)){
            DebugLog.getInstance().Log("Member must be logged in for making this action");
            throw new MarketException("Member must be logged in for making this action");
        }
        if(!memberName.equals(systemManagerName)){
            DebugLog.getInstance().Log("Only a system manager can change the payment service");
            throw new MarketException("Only a system manager can change the payment service");
        }
        if(paymentService1 == null) {
            DebugLog.getInstance().Log("Try to initiate payment service with null");
            throw new MarketException("Try to initiate payment service with null");
        }
        this.paymentServiceProxy=paymentService1;
    }

    public SupplyServiceProxy getSupplyHandler() {
        return supplyServiceProxy;
    }

    public void setSupplyHandler(SupplyService supplyHandler) {
        this.supplyServiceProxy.setService(supplyHandler);
    }

    public Member validateSecurityQuestions(String userName, List<String> answers, String visitorName) throws MarketException{
        Security security = Security.getInstance();
        security.validateQuestions(userName,answers);
        Member member =  userController.getMembers().get(userName);
        List<Appointment> appointmentByMe = member.getAppointedByMe();
        List<Appointment> myAppointments = member.getMyAppointments ();
        userController.finishLogin(userName, visitorName);
        return new Member(member.getName(),member.getMyCart(), appointmentByMe, myAppointments,member.getPurchaseHistory());//,member.getPurchaseHistory()
    }


    public void visitorExitSystem(String visitorName) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        userController.exitSystem(visitorName);
    }

    public Appointment getManagerAppointment(String shopOwnerName, String managerName, String relatedShop) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        for (Map.Entry<String, Shop> shopEntry : this.shops.entrySet()){
            Shop shop = shopEntry.getValue();
            if (shop.getShopName().equals(relatedShop)){
                return shop.getManagerAppointment(shopOwnerName,managerName);
            }
        }
        throw new MarketException("shop couldn't be found");
    }

    public void editShopManagerPermissions(String shopOwnerName, String managerName, String relatedShop, Appointment updatedAppointment) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shop = shops.get ( relatedShop );
        if (shop == null) {
            DebugLog.getInstance ( ).Log ( String.format ( "related shop %s does not exist in the market", relatedShop));
            throw new MarketException ( String.format ( "related shop %s does not exist in the market" ), relatedShop );
        }
        shop.editManagerPermission ( shopOwnerName, managerName, updatedAppointment );
    }

    public void closeShop(String shopOwnerName, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }

        Shop shopToClose = shops.get(shopName);
        if (shopToClose == null)
        {
            DebugLog.getInstance().Log("Tried to close shop named "+ shopName +" but there is no such shop.");
            throw new MarketException("Tried to close non existing shop");
        }
        if (shopToClose.getShopFounder().getName().equals(shopOwnerName))
        {
            shops.remove(shopName);
            removeClosedShopItemsFromMarket(shopToClose);
            //send Notification V2
            ClosedShopsHistory history = ClosedShopsHistory.getInstance();
            history.closeShop(shopToClose);
            //send notifications to shop owners:
            try{
                notificationHandler.sendShopClosedBatchNotificationsBatch(new ArrayList<>(shopToClose.getShopOwners().values().stream()
                        .collect(Collectors.toList()).stream().map(appointment -> appointment.getAppointed().getName())
                       .collect(Collectors.toList())),shopName);
            }
            catch(Exception e){}
            //
            EventLog.getInstance().Log("The shop " +shopName+ " has been closed.");
        }
    }


    public void removeItemFromShop(String shopOwnerName, int itemID, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if (!userController.isLoggedIn(shopOwnerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shop = shops.get(shopName);
        if(shop == null) {
            DebugLog.getInstance().Log("Tried to remove item from non existing shop");
            throw new MarketException("shop does not exist in the market");
        }
        //Check if user indeed is the shop owner
        if(!shop.isShopOwner(shopOwnerName)) {
            DebugLog.getInstance().Log(shopOwnerName+" tried to remove item from the shop "+shopName+" but he is not a owner.");
            throw new MarketException(shopOwnerName + " is not " + shopName + " owner . Removing item from shop has failed.");
        }
        Item itemToDelete = shop.getItemMap().get(itemID);
        userController.updateVisitorsInRemoveOfItem(shop, itemToDelete);
        shop.deleteItem(itemToDelete);
        updateMarketOnDeleteItem(itemToDelete);
        EventLog.getInstance().Log("Item removed from and market.");
    }



    public Shop addItemToShop(String shopOwnerName, String itemName, double price, Item.Category category, String info,
                              List<String> keywords, double amount, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shop = shops.get(shopName);
        if(shop == null) {
            DebugLog.getInstance().Log("Tried to add item to a non existing shop.");
            throw new MarketException("shop does not exist in the market");
        }
        try {
            Item addedItem = shop.addItem(shopOwnerName, itemName, price, category, info, keywords, amount, nextItemID.increment() );
            updateMarketOnAddedItem ( addedItem, shopName );
            EventLog.getInstance().Log("Item added to shop "+shopName);
            return shop;
        }catch (MarketException e){
            nextItemID.decrement();
            throw e;
        }
    }


    public Double getItemCurrentAmount(int id) throws MarketException {
        String shopName = allItemsInMarketToShop.get(id);
        if(shopName == null)
            throw new MarketException("not such item in the market");
        Item item = getItemByID(id);
        return shops.get(shopName).getItemCurrentAmount(item);
    }

    public void setItemCurrentAmount(String shopOwnerName, Item item, double amount, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shop = shops.get(shopName);
        if(shop == null){
            DebugLog.getInstance().Log("Tried to edit item on a non existing shop.");
            throw new MarketException ( "shop does not exist in system" );
        }
        shop.setItemAmount(shopOwnerName,item.getID(),amount);
        EventLog.getInstance().Log("Item "+ item.getName() +" amount has been updated." );
    }

    public String memberLogout(String member) throws MarketException {
        if (!userController.isLoggedIn(member)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        return userController.memberLogout(member);
    }

    public void addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, String member) throws MarketException {
        if (!userController.isLoggedIn(member)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Security security = Security.getInstance();
        security.addPersonalQuery(userAdditionalQueries,userAdditionalAnswers,member);
    }


    public Map<String, Appointment> getShopEmployeesInfo(String shopManagerName, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopManagerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if (!shops.containsKey(shopName)) {
            DebugLog.getInstance().Log("Tried to get employees info in a non existing shop.");
            throw new MarketException("shop does not exist");
        }

        EventLog.getInstance().Log("Owner got shop info.");
        return shops.get(shopName).getShopEmployeesInfo(shopManagerName);
    }

    public Shop getShopInfo(String member, String shopName) throws MarketException {
        if (!userController.isLoggedIn(member)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if (!shops.containsKey(shopName)){
            if(!ClosedShopsHistory.getInstance ().isClosed(shopName)) {
                EventLog.getInstance().Log(String.format("Asked for shop info while shop %s does not exist", shopName));
                throw new MarketException("shop does not exist in the market");
            }
            if(member.equals ( systemManagerName )){
                return ClosedShopsHistory.getInstance().getClosedShops().get(shopName);
            }
            throw new MarketException("only a system manager can get information about a closed shop");
        }
        return shops.get(shopName).getShopInfo(member);
    }

    //TODO check that shop name is not ""
    public boolean openNewShop(String visitorName, String shopName) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Member curMember;
        if(userController.isMember(visitorName)){
            curMember = userController.getMember (visitorName);
            synchronized (shops) {
                if (shops.get(shopName) == null) {
                    if (shopName == null || shopName.length() == 0)
                        throw new MarketException("shop name length has to be positive");
                    Shop shop = new Shop(shopName, curMember);
//                ShopOwnerAppointment shopFounder = new ShopOwnerAppointment (curMember, null, shop, true );
//                shop.addEmployee(shopFounder);
                    shops.put(shopName, shop);

                } else {
                    DebugLog.getInstance().Log(visitorName + " tried to open a shop with taken name.");
                    throw new MarketException("Shop with the same shop name is already exists");
                }
            }
        } else {
            DebugLog.getInstance().Log("Non member tried to open a shop.");
            throw new MarketException("You are not a member. Only members can open a new shop in the market");
        }
        EventLog.getInstance().Log(visitorName+ " opened a new shop named:"+shopName);
        return true;
    }



    //TODO -delete shop name
    public void addItemToShoppingCart(Item item, double amount, String visitorName) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        ShoppingCart shoppingCart = userController.getVisitor ( visitorName ).getCart ();
        Shop curShop = shops.get ( allItemsInMarketToShop.get(item.getID()) );
        if(curShop == null) {
            EventLog.getInstance().Log("Tried to add item to cart from non existing shop.");
            throw new MarketException("this shop does not exist in the market");
        }
        if(item == null) {
            DebugLog.getInstance().Log("Tried to add null to cart.");
            throw new MarketException("this item does not exist in this shop");
        }
        if (amount<0 || amount == 0)
        {
            DebugLog.getInstance().Log("Cant add item with negative or zero amount");
            throw new MarketException("Cant add item with negative amount");
        }
        shoppingCart.addItem ( curShop, item, amount );
        EventLog.getInstance().Log(amount+" "+item.getName()+ " added to cart.");
    }

    public StringBuilder getShopPurchaseHistory(String shopManagerName, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopManagerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shopToHistory = shops.get ( shopName );
        if(shopToHistory == null) {
            DebugLog.getInstance().Log("Tried to get history for a non existing shop");
            throw new MarketException("shop does not exist in the market");
        }
        return shopToHistory.getPurchaseHistory(shopManagerName);
    }

    public void appointShopOwner(String shopOwnerName, String appointedShopOwner, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Member appointed = userController.getMember ( appointedShopOwner );
        if (appointed == null)
            throw new MarketException ( "appointed shop owner is not a member" );
        Shop shop = shops.get ( shopName );
        if (shop == null) {
            DebugLog.getInstance ( ).Log ( "Tried to appoint shop owner for non existing shop." );
            throw new MarketException ( "shop does not exist in the market" );
        }
        Member shopOwner = userController.getMember ( shopOwnerName );
        shop.appointShopOwner ( shopOwner, appointed );
    }

    public void appointShopManager(String shopOwnerName, String appointedShopManager, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Member appointed = userController.getMember ( appointedShopManager );
        if (appointed == null)
            throw new MarketException ( "appointed shop manager is not a member" );
        Shop shop = shops.get ( shopName );
        if (shop == null) {
            DebugLog.getInstance ( ).Log ( "Tried to appoint shop manager for non existing shop." );
            throw new MarketException ( "Tried to appoint shop manager for non existing shop." );
        }
        Member shopOwner = userController.getMember ( shopOwnerName );
        shop.appointShopManager ( shopOwner, appointed );
    }

    public boolean editCart(double amount, Item item, String shopName, String visitorName) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Visitor visitor= userController.getVisitor (visitorName);
        if(visitor == null){
            DebugLog.getInstance().Log("Non member ");
            throw new MarketException("member does not exists, cannot update amount.");
        }
        return visitor.updateAmountInCart(amount, item,shopName);
    }


    public void changeShopItemInfo(String shopOwnerName, String info, java.lang.Integer oldItem, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shop = shops.get ( shopName );
        if(shop == null) {
            DebugLog.getInstance().Log("Tried to edit item in a non existing shop");
            throw new MarketException("shop does not exist in the market");
        }
        EventLog.getInstance().Log("Edited the item with id "+oldItem+" in the shop "+shopName);
        shop.changeShopItemInfo(shopOwnerName, info, oldItem);
    }

    public ShoppingCart showShoppingCart(String visitorName) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        return userController.getVisitor ( visitorName ).getCart ();
    }

    public void editItem(Item newItem, String id) throws MarketException {
        String shopName = allItemsInMarketToShop.get ( java.lang.Integer.parseInt(id) );
        if(shopName == null)
            throw new MarketException ( "item does not exist in the market" );
        Shop shop = shops.get ( shopName );
        if(shop == null)
            throw new MarketException ( "shop does not exist in the market" );
        shop.editItem ( newItem, id );

    }

    public ShoppingCart buyShoppingCart(String visitorName, double expectedPrice,
                                        PaymentMethod paymentMethod, Address address) throws MarketException, JsonProcessingException {
        if (!userController.isLoggedIn(visitorName)) {
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Visitor visitor = userController.getVisitor(visitorName);
        ShoppingCart shoppingCart = visitor.getCart();
        Acquisition acquisition = new Acquisition(shoppingCart, visitorName);
        ShoppingCart shoppingCartToReturn = acquisition.buyShoppingCart(expectedPrice, paymentMethod, address, getPaymentHandler (), getSupplyHandler());
        //TODO - what is expected here?
        return shoppingCartToReturn;
    }


//    public ShoppingCart buyShoppingCart(String visitorName, double expectedPrice,
//                                PaymentMethod paymentMethod, Address address) throws MarketException {
//        if (!userController.isLoggedIn(visitorName)){
//            ErrorLog errorLog = ErrorLog.getInstance();
//            errorLog.Log("you must be a visitor in the market in order to make actions");
//            throw new MarketException("you must be a visitor in the market in order to make actions");
//        }
//        boolean succeed = true;
//        Visitor visitor = userController.getVisitor(visitorName);
//        ShoppingCart cart = visitor.getCart();
//        double actualPrice = cart.saveFromShops(visitorName);
//
//        // checks the price is correct
//        if (actualPrice != expectedPrice){
//            for (Shop shop : cart.getCart().keySet()) {
//                shop.releaseItems(cart.getCart().get(shop));
//            }
//            ErrorLog errorLog = ErrorLog.getInstance();
//            errorLog.Log("Shopping cart price has been changed for a costumer");
////            throw new MarketException(String.format("Sorry, the price cart price change\n" +
////                    "The new Price is: %f\nThe old Price was: %f\n",actualPrice,expectedPrice));
//            return cart;
//        }
//        String supplyID = "";
//        // tries to pay if fails - return items to shops
//        try {
//            supplyID = this.supplyService.supply(address, LocalDateTime.now());
//            this.paymentService.pay(paymentMethod);
//            EventLog eventLog = EventLog.getInstance();
//            eventLog.Log("Supply has been set up for the costumer +"+visitorName+".\n Payment has been done.");
//        }catch (Exception e){
//            try {
//                if (!supplyID.equals("")) {
//                    supplyService.cancelSupply(supplyID);
//                    ErrorLog errorLog = ErrorLog.getInstance();
//                    errorLog.Log("Supply has been failed.");
//                }
//            }catch (Exception ignored){}
//            cart.cancelShopSave();
//            succeed = false;
//        }
//        if (succeed){
//            Member member = visitor.getMember ();
//            if( member != null) {
//                AcquisitionHistory acq = new AcquisitionHistory(cart,member.getName());
//                member.savePurchase(acq);
//            }
//            cart.clear();
//        }
//        return null;
//    }


    private ShoppingCart validateCart(ShoppingCart currentCart) {
        ShoppingCart res = new ShoppingCart();
        double cartPrice = 0;
        Map<Shop, ShoppingBasket> baskets = currentCart.getCart();
        for (Map.Entry<Shop,ShoppingBasket> basketEntry: baskets.entrySet())
        {
            ShoppingBasket updatedBasket = basketEntry.getKey().validateBasket(basketEntry.getValue());
            basketEntry.setValue(updatedBasket);
            cartPrice = cartPrice + updatedBasket.getPrice();
        }
        currentCart.setCurrentPrice(cartPrice);
        return currentCart;
    }


    private void updateMarketOnDeleteItem(Item itemToDelete) {
        allItemsInMarketToShop.remove(itemToDelete.getID());
        itemByName.get(itemToDelete.getName()).remove(itemToDelete.getID());
    }

    private void updateMarketOnAddedItem(Item toAdd, String shopName) {
        allItemsInMarketToShop.put(toAdd.getID(),shopName);
        if(itemByName.get ( toAdd.getName () ) == null)
            itemByName.put ( toAdd.getName (), new ArrayList<> (  ));
        itemByName.get ( toAdd.getName () ).add ( nextItemID.value() - 1 );
    }

    private void removeClosedShopItemsFromMarket(Shop shopToClose) {
        for (Map.Entry<java.lang.Integer, Item> entry:shopToClose.getItemMap().entrySet())
        {
            allItemsInMarketToShop.remove(entry.getKey());
            String itemName = entry.getValue().getName();
            if(itemByName.containsKey(itemName)){
                itemByName.get(itemName).remove(entry.getKey());
            }
        }
        EventLog.getInstance().Log("Preparing to close the shop "+shopToClose.getShopName()+". Removed all shop items from market.");
    }

    public Item getItemByID (java.lang.Integer id) throws MarketException {
        String itemShopName = allItemsInMarketToShop.get(id);
        if(itemShopName == null)
            throw new MarketException("no such item in market");
        Shop itemShop = shops.get(itemShopName);
        Item item = itemShop.getItemMap().get(id);
        return item;
    }

    //TODO delete
    public void reset() throws MarketException {
        instance.shops = new HashMap<>();
        instance.allItemsInMarketToShop = new HashMap<>();
        instance.itemByName = new HashMap<>();
        instance.nextItemID.reset();
        Security.getInstance().reset();
        userController.reset();
        ClosedShopsHistory.getInstance().reset();
        userController.register(systemManagerName);
    }

    public String getSystemManagerName() {
        return systemManagerName;
    }

    public void setSystemManagerName(String systemManagerName) {
        this.systemManagerName = systemManagerName;
    }

    public void setShops(Map<String, Shop> shops) {
        this.shops = shops;
    }

    public int getNextItemID() {
        return nextItemID.increment();
    }

    public void removeShopOwnerAppointment(String boss, String firedAppointed, String shopName) throws MarketException{
        Shop shop = shops.get(shopName);
        if (shop==null)
        {
            DebugLog.getInstance().Log("There is no shop named:"+shopName +". Removing shop owner has failed.");
            throw new MarketException("There is no shop named:"+shopName +". Removing shop owner has failed.");
        }
        shop.removeShopOwnerAppointment(boss,firedAppointed);
        try{
            NotificationHandler handler=new NotificationHandler( dispatcher);
             handler.sendAppointmentRemovedNotification(firedAppointed,shopName);
        }
        catch (Exception e){}

    }


    public void removeMember(String manager, String memberToRemove) throws MarketException{
        if (manager.equals(memberToRemove))
        {
            DebugLog.getInstance().Log("You cant remove yourself.");
            throw new MarketException("You cant remove yourself.");
        }
        if (!manager.equals(systemManagerName))
        {
            DebugLog.getInstance().Log("You are not the market manager. You cant remove a member.");
            throw new MarketException("You are not the market manager. You cant remove a member.");
        }
        for (Map.Entry<String,Shop> entry:shops.entrySet())
        {
            Shop shop = entry.getValue();
            if (shop.isShopOwner(memberToRemove)|| shop.isManager(memberToRemove)) {
                DebugLog.getInstance().Log("You cant remove this member - He works in the market.");
                throw new MarketException("You cant remove this member - He works in the market.");
            }
        }
        if (userController.getVisitorsInMarket().containsKey(memberToRemove))
        {
            DebugLog.getInstance().Log(memberToRemove+" is in market. You can't remove him");
            throw new MarketException(memberToRemove+" is in market. You can't remove him");
        }
        Security security = Security.getInstance();
        security.removeMember(memberToRemove);
        //send the notification
        NotificationHandler handler= new NotificationHandler(NotificationDispatcher.getInstance());
        RealTimeNotifications not= new RealTimeNotifications();
        not.createMembershipDeniedMessage();
        handler.sendNotification(memberToRemove,not,true);
        //
    }

    public Item getItemById(String name, int itemId) throws MarketException {
        if (!userController.isLoggedIn(name))
            throw new MarketException("only visitors in market can get an item's info");
        if(allItemsInMarketToShop.get(itemId) == null)
            throw new MarketException("there is no such an item with this id");
        return shops.get(allItemsInMarketToShop.get(itemId)).getItemMap().get(itemId);
    }
    private String getBuyingStats(){
        StringBuilder s = new StringBuilder("How many customers bought per shop:\n");
        for (Map.Entry<String,Integer> entry:this.numOfAcqsPerShop.entrySet())
        {
            s.append(entry.getKey()).append(entry.getValue());
            s.append("\n");
        }
        s.append("----------------------------------------------");
        return s.toString();
    }

    public String getMarketInfo(String systemManagerName) throws MarketException{
        if (!systemManagerName.equals(this.systemManagerName)){
            DebugLog.getInstance().Log("Only the system manager can get info about the market.");
            throw new MarketException("Only the system manager can get info about the market.");
        }
        StringBuilder s = new StringBuilder("Getting market info");
        s.append(getBuyingStats());
        s.append(userController.getUsersInfo());
        return s.toString();

    }

    public Item addItemToShopItem(String shopOwnerName, String itemName, Double productPrice, Item.Category electricity, String info, List<String > keyWords, double productAmount, String shopName) throws MarketException {
        addItemToShop(shopOwnerName, itemName, productPrice, electricity, info, keyWords, productAmount,shopName);
        Item itemAdded = getItemByID(nextItemID.value() - 1);
        return itemAdded;
    }

    public void addDiscountToShop(String visitorName, String shopName, DiscountType discountType) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            DebugLog debugLog = DebugLog.getInstance();
            debugLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shop = shops.get(shopName);
        if(shop == null) {
            DebugLog.getInstance().Log("Tried to add item to a non existing shop.");
            throw new MarketException("shop does not exist in the market");
        }
        shop.addDiscountToShop(visitorName, discountType);
    }
}
