package com.example.server.businessLayer;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.ErrorLog;
import com.example.server.ResourcesObjects.EventLog;
import com.example.server.ResourcesObjects.PaymentMethod;
import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Appointment.ShopOwnerAppointment;
import com.example.server.businessLayer.ExternalServices.PaymentService;
import com.example.server.businessLayer.ExternalServices.ProductsSupplyService;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.UserController;
import com.example.server.businessLayer.Users.Visitor;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Market {
    private UserController userController;
    private String systemManagerName;
    private Map<String, Shop> shops;                                 // <shopName, shop>
    private Map<Integer, String> allItemsInMarketToShop;             // <itemID,ShopName>
    private Map<String, List<Integer>> itemByName;                   // <itemName ,List<itemID>>
    private int nextItemID;
    private PaymentService paymentService;
    private ProductsSupplyService supplyService;

    private static Market instance;

    private Market() {
        this.shops = new ConcurrentHashMap<>();
        this.allItemsInMarketToShop = new ConcurrentHashMap<>();
        this.itemByName = new ConcurrentHashMap<>();
        this.userController = UserController.getInstance();
        nextItemID = 1;
    }


    public synchronized static Market getInstance() {
        if (instance == null) {
            instance = new Market();
        }
        return instance;
    }

    public synchronized void firstInitMarket(PaymentService paymentService, ProductsSupplyService supplyService, String userName, String password) throws MarketException {
        if (this.paymentService != null || this.supplyService != null){
            ErrorLog.getInstance().Log("A market initialization failed .already initialized");
            throw new MarketException("market is already initialized");
        }
        if (paymentService == null || supplyService == null) {
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("A market initialization failed . Lack of payment / supply services ");
            throw new MarketException("market needs payment and supply services for initialize");
        }
        register ( userName, password );
        instance.systemManagerName = userName;
        instance.paymentService = paymentService;
        instance.supplyService = supplyService;
        EventLog eventLog = EventLog.getInstance();
        eventLog.Log("A market has been initialized successfully");

    }



    public StringBuilder getAllSystemPurchaseHistory(String memberName) throws MarketException {
        if (!userController.isLoggedIn(memberName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if(!systemManagerName.equals ( memberName )) {
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Member who is not the system manager tried to access system purchase history");
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
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if(!systemManagerName.equals ( member )) {
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Member who is not the system manager tried to access system purchase history");
            throw new MarketException("member is not a system manager so is not authorized to get th information");
        }
        Shop shop = shops.get ( shopName );
        if(shop == null) {
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("User tried to get shop history for a non exiting shop");
            throw new MarketException("shop does not exist in the market");
        }
        return shop.getReview ();
    }

    public StringBuilder getHistoryByMember(String systemManagerName, String memberName) throws MarketException {
        if (!userController.isLoggedIn(systemManagerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if(systemManagerName.equals ( this.systemManagerName )){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Member who is not the system manager tried to access system purchase history");
            throw new MarketException ( "member is not a system manager so is not authorized to get th information" );
        }

        Member member = userController.getMember ( memberName );
        if(member == null){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Tried to get history for a non existing member");
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
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        ShoppingCart currentCart = userController.getVisitorsInMarket().get(visitorName).getCart();
        ShoppingCart updatedCart = validateCart(currentCart);
        return updatedCart;
    }


    public Map<String, Shop> getShops() {
        return shops;
    }




    public Map<Integer, String> getAllItemsInMarketToShop() {
        return allItemsInMarketToShop;
    }

    public List<Item> getItemByName(String name) {
        if (!itemByName.containsKey(name)) {
            return new ArrayList<> (  );
        }
        List<Item> toReturn = new ArrayList<>();
        List<Integer> itemIds =  itemByName.get(name);
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

    public PaymentService getPaymentService() {
        return paymentService;
    }

    //TODO make private and add authentication checks
    public void setPaymentService(PaymentService paymentService, String memberName) throws MarketException {
        if(!userController.isLoggedIn(memberName)){
            ErrorLog.getInstance().Log("Member must be logged in for making this action");
            throw new MarketException("Member must be logged in for making this action");
        }
        if(!memberName.equals(systemManagerName)){
            ErrorLog.getInstance().Log("Only a system manager can change the payment service");
            throw new MarketException("Only a system manager can change the payment service");
        }
        if(paymentService == null) {
            ErrorLog.getInstance().Log("Try to initiate payment service with null");
            throw new MarketException("Try to initiate payment service with null");
        }
        this.paymentService = paymentService;
    }

    public ProductsSupplyService getSupplyService() {
        return supplyService;
    }

    public void setSupplyService(ProductsSupplyService supplyService) {
        this.supplyService = supplyService;
    }

    public synchronized int getNextItemID() {
        int temp = nextItemID;
        nextItemID++;
        return temp;
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
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        userController.exitSystem(visitorName);
    }

    public Appointment getManagerAppointment(String shopOwnerName, String managerName, String relatedShop) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
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
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shop = shops.get ( relatedShop );
        if (shop == null) {
            ErrorLog.getInstance ( ).Log ( String.format ( "related shop %s does not exist in the market", relatedShop));
            throw new MarketException ( String.format ( "related shop %s does not exist in the market" ), relatedShop );
        }
        shop.editManagerPermission ( shopOwnerName, managerName, updatedAppointment );
    }

    public void closeShop(String shopOwnerName, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }

        Shop shopToClose = shops.get(shopName);
        if (shopToClose == null)
        {
            ErrorLog.getInstance().Log("Tried to close shop named "+ shopName +" but there is no such shop.");
            throw new MarketException("Tried to close non existing shop");
        }
        if (shopToClose.getShopFounder().getName().equals(shopOwnerName))
        {
            shops.remove(shopName);
            removeClosedShopItemsFromMarket(shopToClose);
            //send Notification V2
            ClosedShopsHistory history = ClosedShopsHistory.getInstance();
            history.closeShop(shopToClose);
            EventLog.getInstance().Log("The shop " +shopName+ " has been closed.");
        }
    }


    public void removeItemFromShop(String shopOwnerName, int itemID, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if (!userController.isLoggedIn(shopOwnerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shop = shops.get(shopName);
        if(shop == null) {
            ErrorLog.getInstance().Log("Tried to remove item from non existing shop");
            throw new MarketException("shop does not exist in the market");
        }
        //Check if user indeed is the shop owner
        if(!shop.isShopOwner(shopOwnerName)) {
            ErrorLog.getInstance().Log(shopOwnerName+" tried to remove item from the shop "+shopName+" but he is not a owner.");
            throw new MarketException(shopOwnerName + " is not " + shopName + " owner . Removing item from shop has failed.");
        }
        Item itemToDelete = shop.getItemMap().get(itemID);
        userController.updateVisitorsInRemoveOfItem(shop, itemToDelete);
        shop.deleteItem(itemToDelete);
        updateMarketOnDeleteItem(itemToDelete);
        EventLog.getInstance().Log("Item removed from and market.");
    }



    public void addItemToShop(String shopOwnerName, String itemName, double price, Item.Category category, String info,
                              List<String> keywords, double amount, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shop = shops.get(shopName);
        if(shop == null) {
            ErrorLog.getInstance().Log("Tried to add item to a non existing shop.");
            throw new MarketException("shop does not exist in the market");
        }
        Item addedItem = shop.addItem(shopOwnerName, itemName, price, category, info, keywords, amount, nextItemID );
        this.nextItemID++;
        updateMarketOnAddedItem ( addedItem, shopName );
        EventLog.getInstance().Log("Item added to shop "+shopName);
    }



    public void setItemCurrentAmount(String shopOwnerName, Item item, double amount, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shop = shops.get(shopName);
        if(shop == null){
            ErrorLog.getInstance().Log("Tried to edit item on a non existing shop.");
            throw new MarketException ( "shop does not exist in system" );
        }
        shop.setItemAmount(shopOwnerName,item,amount);
        EventLog.getInstance().Log("Item "+ item.getName() +" amount has been updated." );
    }

    public String memberLogout(String member) throws MarketException {
        if (!userController.isLoggedIn(member)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        return userController.memberLogout(member);
    }

    public void addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, String member) throws MarketException {
        if (!userController.isLoggedIn(member)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Security security = Security.getInstance();
        security.addPersonalQuery(userAdditionalQueries,userAdditionalAnswers,member);
    }


    public Map<String, Appointment> getShopEmployeesInfo(String shopManagerName, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopManagerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if (!shops.containsKey(shopName)) {
            ErrorLog.getInstance().Log("Tried to get employees info in a non existing shop.");
            throw new MarketException("shop does not exist");
        }

        EventLog.getInstance().Log("Owner got shop info.");
        return shops.get(shopName).getShopEmployeesInfo(shopManagerName);
    }

    public Shop getShopInfo(String member, String shopName) throws MarketException {
        if (!userController.isLoggedIn(member)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        if (!shops.containsKey(shopName) && !ClosedShopsHistory.getInstance ().isClosed(shopName)) {
            EventLog.getInstance().Log(String.format ("Asked for shop info while shop %s does not exist", shopName));
            throw new MarketException("shop does not exist in the market");
        }
        if(member.equals ( systemManagerName ))
            return shops.get ( shopName );
        return shops.get(shopName).getShopInfo(member);
    }

    //TODO check that shop name is not ""
    public boolean openNewShop(String visitorName, String shopName) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Member curMember;
        if(userController.isMember(visitorName)){
            curMember = userController.getMember (visitorName);
            if(shops.get ( shopName ) == null) {
                Shop shop = new Shop ( shopName,  curMember);
//                ShopOwnerAppointment shopFounder = new ShopOwnerAppointment (curMember, null, shop, true );
//                shop.addEmployee(shopFounder);
                shops.put ( shopName, shop );

            } else {
                ErrorLog.getInstance().Log(visitorName+" tried to open a shop with taken name.");
                throw new MarketException("Shop with the same shop name is already exists");
            }
        } else {
            ErrorLog.getInstance().Log("Non member tried to open a shop.");
            throw new MarketException("You are not a member. Only members can open a new shop in the market");
        }
        EventLog.getInstance().Log(visitorName+ " opened a new shop named:"+shopName);
        return true;
    }



    public void addItemToShoppingCart(Item item, double amount, String shopName, String visitorName) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        ShoppingCart shoppingCart = userController.getVisitor ( visitorName ).getCart ();
        Shop curShop = shops.get ( shopName );
        if(curShop == null) {
            EventLog.getInstance().Log("Tried to add item to cart from non existing shop.");
            throw new MarketException("this shop does not exist in the market");
        }
        if(item == null) {
            ErrorLog.getInstance().Log("Tried to add null to cart.");
            throw new MarketException("this item does not exist in this shop");
        }
        double curAmount = curShop.getItemCurrentAmount ( item );
        if(curAmount < amount) {
            ErrorLog.getInstance().Log("Tried to item with amount bigger than what the shop holds.");
            throw new MarketException("the shop amount of this item is less then the wanted amount");
        }
        if (amount<0)
        {
            ErrorLog.getInstance().Log("Cant add item with negative amount");
            throw new MarketException("Cant add item with negative amount");
        }
        shoppingCart.addItem ( curShop, item, amount );
        EventLog.getInstance().Log(amount+" "+item+ " added to cart.");
    }

    public StringBuilder getShopPurchaseHistory(String shopManagerName, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopManagerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shopToHistory = shops.get ( shopName );
        if(shopToHistory == null) {
            ErrorLog.getInstance().Log("Tried to get history for a non existing shop");
            throw new MarketException("shop does not exist in the market");
        }
        return shopToHistory.getPurchaseHistory(shopManagerName);
    }

    public void appointShopOwner(String shopOwnerName, String appointedShopOwner, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Member appointed = userController.getMember ( appointedShopOwner );
        if (appointed == null)
            throw new MarketException ( "appointed shop owner is not a member" );
        Shop shop = shops.get ( shopName );
        if (shop == null) {
            ErrorLog.getInstance ( ).Log ( "Tried to appoint shop owner for non existing shop." );
            throw new MarketException ( "shop does not exist in the market" );
        }
        Member shopOwner = userController.getMember ( shopOwnerName );
        shop.appointShopOwner ( shopOwner, appointed );
    }

    public void appointShopManager(String shopOwnerName, String appointedShopManager, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Member appointed = userController.getMember ( appointedShopManager );
        if (appointed == null)
            throw new MarketException ( "appointed shop manager is not a member" );
        Shop shop = shops.get ( shopName );
        if (shop == null) {
            ErrorLog.getInstance ( ).Log ( "Tried to appoint shop manager for non existing shop." );
            throw new MarketException ( "Tried to appoint shop manager for non existing shop." );
        }
        Member shopOwner = userController.getMember ( shopOwnerName );
        shop.appointShopManager ( shopOwner, appointed );
    }

    public boolean editCart(double amount, Item item, String shopName, String visitorName) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Visitor visitor= userController.getVisitor (visitorName);
        if(visitor == null){
            ErrorLog.getInstance().Log("Non member ");
            throw new MarketException("member does not exists, cannot update amount.");
        }
        return visitor.updateAmountInCart(amount, item,shopName);
    }


    public void changeShopItemInfo(String shopOwnerName, Item updatedItem, Item oldItem, String shopName) throws MarketException {
        if (!userController.isLoggedIn(shopOwnerName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        Shop shop = shops.get ( shopName );
        if(shop == null) {
            ErrorLog.getInstance().Log("Tried to edit item in a non existing shop");
            throw new MarketException("shop does not exist in the market");
        }
        EventLog.getInstance().Log("Edited the item "+updatedItem.getName()+" in the shop "+shopName);
        shop.changeShopItemInfo(shopOwnerName, updatedItem, oldItem);
    }

    public ShoppingCart showShoppingCart(String visitorName) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        return userController.getVisitor ( visitorName ).getCart ();
    }

    public void editItem(Item newItem, String id) throws MarketException {
        String shopName = allItemsInMarketToShop.get ( Integer.parseInt(id) );
        if(shopName == null)
            throw new MarketException ( "item does not exist in the market" );
        Shop shop = shops.get ( shopName );
        if(shop == null)
            throw new MarketException ( "shop does not exist in the market" );
        shop.editItem ( newItem, id );

    }

    public void buyShoppingCart(String visitorName, double expectedPrice,
                                PaymentMethod paymentMethod, Address address) throws MarketException {
        if (!userController.isLoggedIn(visitorName)){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("you must be a visitor in the market in order to make actions");
            throw new MarketException("you must be a visitor in the market in order to make actions");
        }
        boolean succeed = true;
        Visitor visitor = userController.getVisitor(visitorName);
        ShoppingCart cart = visitor.getCart();
        double actualPrice = cart.saveFromShops();

        // checks the price is correct
        if (actualPrice != expectedPrice){
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Shopping cart price has been changed for a costumer");
            throw new MarketException(String.format("Sorry, the price cart price change\n" +
                    "The new Price is: %f\nThe old Price was: %f\n",actualPrice,expectedPrice));
        }
        String supplyID = "";
        // tries to pay if fails - return items to shops
        try {
            supplyID = this.supplyService.supply(address, LocalDateTime.now());
            this.paymentService.pay(paymentMethod);
            EventLog eventLog = EventLog.getInstance();
            eventLog.Log("Supply has been set up for the costumer +"+visitorName+".\n Payment has been done.");
        }catch (Exception e){
            try {
                if (!supplyID.equals("")) {
                    supplyService.cancelSupply(supplyID);
                    ErrorLog errorLog = ErrorLog.getInstance();
                    errorLog.Log("Supply has been failed.");
                }
            }catch (Exception ignored){}
            cart.cancelShopSave();
            succeed = false;
        }
        //TODO check that member is not null
        if (succeed){
            Member member = visitor.getMember ();
            member.savePurchase(cart);
            cart.clear();
        }
    }


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
        res.setCurrentPrice(cartPrice);
        return res;
    }


    private void updateMarketOnDeleteItem(Item itemToDelete) {
        allItemsInMarketToShop.remove(itemToDelete.getID());
        itemByName.get(itemToDelete.getName()).remove(itemToDelete.getID());
    }

    private void updateMarketOnAddedItem(Item toAdd,String shopName) {
        allItemsInMarketToShop.put(toAdd.getID(),shopName);
        if(itemByName.get ( toAdd.getName () ) == null)
            itemByName.put ( toAdd.getName (), new ArrayList<> (  ));
        itemByName.get ( toAdd.getName () ).add ( nextItemID - 1 );
    }

    private void removeClosedShopItemsFromMarket(Shop shopToClose) {
        for (Map.Entry<Integer,String> entry:allItemsInMarketToShop.entrySet())
        {
            if (entry.getValue().equals(shopToClose.getShopName()))
                allItemsInMarketToShop.remove(entry.getKey());
        }
        Map<Integer,Item> itemMap = shopToClose.getItemMap();
        for (Map.Entry<Integer,Item> entry : itemMap.entrySet())
        {
            //Get list of items by the name . then delete by the specific ID
            itemByName.get(entry.getValue().getName()).remove(entry.getValue().getID());
        }
        EventLog.getInstance().Log("Preparing to close the shop "+shopToClose.getShopName()+". Removed all shop items from market.");
    }

    public Item getItemByID (Integer id){
        String itemShopName = allItemsInMarketToShop.get(id);
        Shop itemShop = shops.get(itemShopName);
        Item item = itemShop.getItemMap().get(id);
        return item;
    }

    //TODO delete
    public void reset() throws MarketException {
        instance.shops = new HashMap<>();
        instance.allItemsInMarketToShop = new HashMap<>();
        instance.itemByName = new HashMap<>();
        instance.nextItemID = 1;
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
}
