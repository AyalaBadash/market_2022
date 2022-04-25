package com.example.server.serviceLayer;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.PaymentMethod;
import com.example.server.businessLayer.ExternalServices.PaymentService;
import com.example.server.businessLayer.ExternalServices.ProductSupplyService;
import com.example.server.businessLayer.Item;
import com.example.server.serviceLayer.FacadeObjects.*;


import java.util.ArrayList;
import java.util.List;

public class Service implements IService {
    private static Service service = null;
    MarketService marketService;
    PurchaseService purchaseService;
    UserService userService;

    private Service() {
        marketService = MarketService.getInstance();
        purchaseService = PurchaseService.getInstance();
        userService = UserService.getInstance();
    }

    public synchronized static Service getInstance() {
        if (service == null)
            service = new Service();
        return service;
    }
    // TODO implement V2
//    @Override
//    public Response initMarket() {
//        return null;
//    }

    @Override
    public Response firstInitMarket(PaymentService paymentService, ProductSupplyService supplyService,
                                    String userName, String password) {
        // TODO need to create a user and add as system manager
        return marketService.firstInitMarket ( paymentService, supplyService,userName,password );
    }

    @Override
    public ResponseT<VisitorFacade> guestLogin() {
        return this.userService.guestLogin();
    }

    @Override
    public Response exitSystem(String visitorName) {
        return this.userService.exitSystem(visitorName);
    }

    @Override
    public ResponseT<Boolean> register(String userName, String userPassword) {
        return userService.register(userName, userPassword);
    }

    @Override
    public ResponseT<Boolean> addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, MemberFacade member) {
        return userService.addPersonalQuery(userAdditionalQueries, userAdditionalAnswers, member);
    }

    // TODO implement V2
    @Override
    public ResponseT<List<ShopFacade>> getAllShops() {
        return null;
    }
    // TODO implement V2
    @Override
    public ResponseT<List<ItemFacade>> getAllItemsByShop(ShopFacade shop) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> searchProductByName(String name) {
        return marketService.searchProductByName(name);
    }

    @Override
    public ResponseT<List<ItemFacade>> searchProductByCategory(Item.Category category) {
        return marketService.searchProductByCategory(category);
    }

    @Override
    public ResponseT<List<ItemFacade>> searchProductByKeyword(String keyWord) {
        return marketService.searchProductByKeyword(keyWord);
    }

    @Override
    public ResponseT<List<ItemFacade>> filterItemByPrice(List<ItemFacade> items, int minPrice, int maxPrice) {
        return marketService.filterItemByPrice(items, minPrice, maxPrice);
    }

    @Override
    public ResponseT<List<ItemFacade>> filterItemByCategory(List<ItemFacade> items, Item.Category category) {
        return marketService.filterItemByCategory(items, category);
    }

    @Override
    public Response addItemToShoppingCart(ItemFacade itemToInsert, double amount, String shopName, String visitorName) {
        return purchaseService.addItemToShoppingCart(itemToInsert,amount,shopName,visitorName);
    }
    @Override
    public ResponseT<ShoppingCartFacade> showShoppingCart(String visitorName) {
        return purchaseService.showShoppingCart(visitorName);
    }
    @Override
    public Response editItemFromShoppingCart(int amount, ItemFacade itemFacade, String shopName, String visitorName) {
        return purchaseService.editItemFromShoppingCart(amount, itemFacade, shopName, visitorName);
    }

    @Override
    public ResponseT<ShoppingCartFacade> calculateShoppingCart(String visitorName) {
        return marketService.calculateShoppingCart(visitorName);
    }

    @Override
    public Response buyShoppingCart(String visitorName, double expectedPrice, PaymentMethod paymentMethod, Address address) {
        return this.purchaseService.buyShoppingCart(visitorName, expectedPrice, paymentMethod, address);
    }

    @Override
    public ResponseT<MemberFacade> memberLogin(String userName, String userPassword, String visitorName) {
        try {
            ResponseT<List<String>> responseQs = userService.memberLogin(userName, userPassword, visitorName);
            List<String> Qs = responseQs.getValue(); // TODO -  display Qs to user.
            List<String> ans = new ArrayList<>(); //TODO users input
            return validateSecurityQuestions(userName,ans);
        }
        catch (Exception e){
            //TODO - What to do here
            return null; //TODO change here
        }
    }

    private ResponseT<MemberFacade> validateSecurityQuestions(String userName, List<String> answers) {

        try{
            return userService.validateSecurityQuestions(userName,answers);
        }
        catch (Exception e)
        {
            //TODO
            return null; //TODO change here
        }

    }



    @Override
    public ResponseT<VisitorFacade> logout(String visitorName) {
        return userService.logout(visitorName);
    }

    // TODO implement
    @Override
    public Response openNewShop(String visitorName, String shopName) {
        return null;
    }
    // TODO implement
    @Override
    public Response updateShopItemAmount(String shopOwnerName, ItemFacade item, int amount, String shopName) {
        return null;
    }

    @Override
    public Response removeItemFromShop(String shopOwnerName, ItemFacade item, String shopName) {
        return marketService.removeItemFromShop(shopOwnerName, item, shopName);
    }

    @Override
    public Response addItemToShop(String shopOwnerName,String name, double price,Item.Category category,String info,
                                  List<String> keywords, int amount, String shopName) {
        return marketService.addItemToShop(shopOwnerName,name,price,category,info,keywords,amount,shopName);
    }

    // TODO implement
    @Override
    public ResponseT<Integer> getItemCurrentAmount(ItemFacade item, String shopName) {
        return null;
    }

    @Override //TODO check if we need to get shop owner name in here too ( like remove and add)
    public Response setItemCurrentAmount(ItemFacade item, int amount, String shopName) {
        return marketService.setItemCurrentAmount(item, amount, shopName);
    }

    // TODO implement
    @Override
    public Response changeShopItemInfo(String shopOwnerName, ItemFacade updatedItem, ItemFacade oldItem, String shopName) {
        return null;
    }
    // TODO implement
    @Override
    public Response appointShopOwner(String shopOwnerName, String appointedShopOwner, String shopName) {
        return null;
    }
    // TODO implement
    @Override
    public Response appointShopManager(String shopOwnerName, String appointedShopManager, String shopName) {
        return null;
    }
    // TODO implement
    @Override
    public ResponseT<List<AppointmentFacade>> getSelfAppointed(String shopOwnerName) {
        return null;
    }
    // TODO implement
    @Override
    public ResponseT<List<ShopManagerAppointmentFacade>> getSelfManagerAppointed(String shopOwnerName) {
        return null;
    }
    // TODO implement
    @Override
    public ResponseT<List<ShopOwnerAppointmentFacade>> getSelfShopOwnerAppointed(String shopOwnerName) {
        return null;
    }

    @Override
    public Response editShopManagerPermissions(String shopOwnerName, String managerName, String relatedShop,
                                               ShopManagerAppointmentFacade updatedAppointment) {
        return this.userService.editShopManagerPermissions(shopOwnerName,managerName , relatedShop,updatedAppointment);
    }

    @Override
    public ResponseT getManagerPermission(String shopOwnerName, String managerName, String relatedShop){
        return this.userService.getManagerAppointment(shopOwnerName,managerName, relatedShop);
    }

    @Override
    public Response closeShop(String shopOwnerName, String shopName) {
        return this.marketService.closeShop(shopOwnerName,shopName);
    }

    @Override
    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(String shopManagerName, String shopName) {
        return marketService.getShopEmployeesInfo(shopManagerName, shopName);
    }

    @Override
    public ResponseT<ShopFacade> getShopInfo(String member, String shopName) {
        return marketService.getShopInfo(member, shopName);
    }

    @Override
    public ResponseT<String> getShopPurchaseHistory(String shopManagerName, String shopName) {
        return marketService.getShopPurchaseHistory ( shopManagerName, shopName );
    }

    @Override
    public ResponseT<String> getAllSystemPurchaseHistory(String systemManagerName) {
        return marketService.getAllSystemPurchaseHistory ( systemManagerName );
    }

    @Override
    public ResponseT<String> getHistoryByShop(String systemManagerName, String shopName) {
        return marketService.getHistoryByShop ( systemManagerName, shopName );
    }

    @Override
    public ResponseT<String> getHistoryByMember(String systemManagerName, String memberName) {
        return marketService.getHistoryByMember ( systemManagerName, memberName );
    }


}