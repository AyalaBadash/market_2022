package main.businessLayer;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Appointment.ShopManagerAppointment;
import main.businessLayer.Appointment.ShopOwnerAppointment;
import main.businessLayer.ExternalServices.PaymentService;
import main.businessLayer.ExternalServices.ProductsSupplyService;
import main.businessLayer.users.Member;
import main.businessLayer.users.UserController;
import main.businessLayer.users.Visitor;
import main.serviceLayer.FacadeObjects.*;
import main.resources.Address;
import main.resources.PaymentMethod;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // TODO need understand how to we want to handle error messages
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

    //TODO must check permission
    public String getAllSystemPurchaseHistory() {
        return null;
    }

    //TODO must check permission
    public String getHistoryByShop(String shopName) {
        return null;
    }

    //TODO must check permission
    public String getHistoryByMember(String memberName) {
        return null;
    }

    public void register(String name, String password) throws MarketException {
        Security security = Security.getInstance();
        security.validateRegister(name,password);
        userController.register(name);

    }

    public Shop getShopByName(String shopName) {
        throw new UnsupportedOperationException();
    }


    public boolean login(String name, String pass,
                         List<String> questions, List<String> answers) throws Exception {
        Security security = Security.getInstance();
        return security.validateLogin(name, pass, questions, answers);

    }


    public void buyShoppingCart(String visitorName, double expectedPrice,
                                PaymentMethod paymentMethod, Address address) throws MarketException {
        boolean succeed = true;
        Visitor visitor = userController.getVisitor(visitorName);
        ShoppingCart cart = visitor.getCart();
        double actualPrice = cart.saveFromShops();

        // checks the price is correct
        if (actualPrice != expectedPrice){
            throw new MarketException(String.format("Sorry, the price cart price change\n" +
                    "The new Price is: %f\nThe old Price was: %f\n",actualPrice,expectedPrice));
        }
        String supplyID = "";
        // tries to pay if fails - return items to shops
        try {
            supplyID = this.supplyService.supply(address, LocalDateTime.now());

            this.paymentService.pay(paymentMethod);
        }catch (Exception e){
            try {
                if (!supplyID.equals("")) {
                    supplyService.cancelSupply(supplyID);
                }
            }catch (Exception ignored){}
            cart.cancelShopSave();
            succeed = false;
        }
        if (succeed){
            cart.clear();
        }
    }

    public void addSecurity() {

    }

    public ShoppingCartFacade calculateShoppingCart(String visitorName) {
        ShoppingCart currentCart = userController.getVisitorsInMarket().get(visitorName).getCart();
        ShoppingCart updatedCart = validateCart(currentCart);

        ShoppingCartFacade cartFacade = new ShoppingCartFacade(updatedCart);
        return cartFacade;
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

    private int calculateShoppingCartPrice() {
        throw new UnsupportedOperationException();
    }

    public Map<String, Shop> getShops() {
        return shops;
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

    public Map<Integer, String> getAllItemsInMarketToShop() {
        return allItemsInMarketToShop;
    }


    public Map<String, List<Integer>> getItemByName() {
        return itemByName;
    }


    public static void setInstance(Market instance) {
        Market.instance = instance;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
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

    public List<String> memberLogin(String userName, String userPassword, String visitorName) throws Exception{ //TODO -Check whick Exception
        Security security = Security.getInstance();
        List<String> questions = security.validatePassword(userName,userPassword);
        return null;

    }

    public ResponseT<MemberFacade> validateSecurityQuestions(String userName, List<String> answers) throws Exception{
        Security security = Security.getInstance();
        security.validateQuestions(userName,answers);
        Member member =  userController.getMembers().get(userName);
        List<Appointment> appointmentByMe = member.getAppointedByMe();
        List<AppointmentFacade> appointmentFacadesByMe= new ArrayList<>();
        for (Appointment appointment: appointmentByMe)
        {
            if (appointment.isOwner()){
            ShopOwnerAppointment shopOwnerAppointment = (ShopOwnerAppointment) appointment; //TODO - approve casting
            ShopOwnerAppointmentFacade facade = new ShopOwnerAppointmentFacade(shopOwnerAppointment);
            appointmentFacadesByMe.add(facade);
            }
            else {
                ShopManagerAppointment shopManagerAppointment = (ShopManagerAppointment) appointment;
                ShopManagerAppointmentFacade facade = new ShopManagerAppointmentFacade(shopManagerAppointment);
                appointmentFacadesByMe.add(facade);
            }

        }
        List<Appointment> appointments = member.getAppointedByMe();
        List<AppointmentFacade> appointmentsFacades= new ArrayList<>();
        for (Appointment appointment: appointments)
        {
            //appointmentsFacades.add(null);//TODO
        }
        userController.finishLogin(userName);
        return new ResponseT<MemberFacade>(new MemberFacade(member.getName(),member.getMyCart(),appointmentFacadesByMe,appointmentsFacades));
    }



    public void visitorExitSystem(String visitorName) throws MarketException {
        userController.exitSystem(visitorName);
    }

    public Appointment getManagerAppointment(String shopOwnerName, String managerName, String relatedShop) throws MarketException {
        for (Map.Entry<String, Shop> shopEntry : this.shops.entrySet()){
            Shop shop = shopEntry.getValue();
            if (shop.getShopName().equals(relatedShop)){
                return shop.getManagerAppointment(shopOwnerName,managerName);
            }
        }
        throw new MarketException("shop couldn't be found");
    }

    public void editShopManagerPermissions(String shopOwnerName, String managerName, String relatedShop, Appointment updatedAppointment) throws MarketException {
        for (Map.Entry<String, Shop> shopEntry : this.shops.entrySet()){
            Shop shop = shopEntry.getValue();
            if (shop.getShopName().equals(relatedShop)){
                shop.editManagerPermission(shopOwnerName,managerName,updatedAppointment);
                return;
            }
        }
        throw new MarketException("shop couldn't be found");
    }

    public void closeShop(String shopOwnerName, String shopName) throws MarketException {
        Shop shopToClose = shops.get(shopName);
        if (shopToClose.getShopFounder().getName().equals(shopOwnerName))
        {
            shops.remove(shopName);
            removeClosedShopItemsFromMarket(shopToClose);
            //TODO send Notification
            History history = History.getInstance();
            history.closeShop(shopToClose);
        }
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
    }

    public void removeItemFromShop(String shopOwnerName, String itemName, String shopName) throws MarketException {
        Shop shop = shops.get(shopName);
        //Check if user indeed is the shop owner
        if(!shop.isShopOwner(shopOwnerName))
        {
            throw new MarketException(shopOwnerName+" is not "+ shopName+ " owner . Removing "+itemName + " from shop has failed.");
        }
        else //we can remove item
        {
            Item itemToDelete = shop.getItemMap().get(itemName);
            shop.deleteItem(itemToDelete);
            updateMarketOnDeleteItem(itemToDelete);
        }
    }

    private void updateMarketOnDeleteItem(Item itemToDelete) {
        allItemsInMarketToShop.remove(itemToDelete.getID());
        itemByName.get(itemToDelete.getName()).remove(itemToDelete.getID());
    }

    public void addItemToShop(String shopOwnerName,String itemName, double price,Item.Category category,String info,
                              List<String> keywords, int amount, String shopName) throws MarketException {
        Shop shop = shops.get(shopName);
        //Check if user indeed is the shop owner
        if(!shop.isShopOwner(shopOwnerName))
        {
            throw new MarketException(shopOwnerName+" is not "+ shopName+ " owner . adding "+itemName + " from shop has failed.");
        }
        else //we can add item
        {
            Item toAdd = new Item(nextItemID,itemName,price,info);
            shop.addItem(toAdd);
            updateMarketOnAddedItem(toAdd,shopName);
        }
    }

    private void updateMarketOnAddedItem(Item toAdd,String shopName) {
        allItemsInMarketToShop.put(toAdd.getID(),shopName);
        if (!itemByName.containsKey(toAdd.getName())) // No such item name in the entire market
        {
            List<Integer> newList = new ArrayList<>();
            newList.add(toAdd.getID());
            itemByName.put(toAdd.getName(),newList);
        }
        else {
            itemByName.get(toAdd.getName()).add(toAdd.getID());
        }
    }

    public Response setItemCurrentAmount(ItemFacade facadeItem, int amount, String shopName) {
            Shop shop = shops.get(shopName);
            Item item = facadeItem.toBusinessObject();
            shop.setItemAmount(item,amount);
            return new Response();
    }

    public String memberLogout(String member) throws MarketException {
        return userController.memberLogout(member);
    }
    public ResponseT<Boolean> addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, MemberFacade member)
    {
        ResponseT<Boolean> responseT;
        try {
            Security security = Security.getInstance();
            security.addPersonalQuery(userAdditionalQueries,userAdditionalAnswers,member);
            responseT = new ResponseT<>(false);
        }
        catch (MarketException e)
        {
            responseT = new ResponseT<>(false);

        }
        return responseT;
    }

    public Visitor guestLogin() {
        return userController.guestLogin();
    }

    public Map<String, Appointment> getShopEmployeesInfo(String shopManagerName, String shopName) throws MarketException {
        if (!shops.containsKey(shopName))
            throw new MarketException("shop does not exist");
        return shops.get(shopName).getShopEmployeesInfo(shopManagerName);
    }

    public Shop getShopInfo(String member, String shopName) throws MarketException {
        if (!shops.containsKey(shopName))
            throw new MarketException("no such shop");
        return shops.get(shopName).getShopInfo(member);
    }

    public void openNewShop(String visitorName, String shopName) throws MarketException {
        Member curMember;
        if(userController.isMember(visitorName)){
            curMember = userController.getMember (visitorName);
            if(shops.get ( shopName ) == null) {
                Shop shop = new Shop ( shopName );
                ShopOwnerAppointment shopFounder = new ShopOwnerAppointment (curMember, null, shop, true );
                shop.addEmployee(shopFounder);
                shops.put ( shopName, shop );
                curMember.addAppointmentToMe(shopFounder);
            } else
                throw new MarketException ( "Shop with the same shop name is already exists" );
        } else
            throw new MarketException ( "You are not a member. Only members can open a new shop in the market" );
    }
}
