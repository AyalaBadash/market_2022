package main.serviceLayer;

import main.businessLayer.Item;
import main.businessLayer.ExternalServices.PaymentService;
import main.businessLayer.ExternalServices.ProductsSupplyService;
import main.serviceLayer.FacadeObjects.*;
import main.resources.Address;
import main.resources.PaymentMethod;

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

//    @Override
//    public Response initMarket() {
//        return null;
//    }

    @Override
    public Response firstInitMarket(PaymentService paymentService, ProductsSupplyService supplyService,
                                    String userName, String password) {
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


//    @Override
//    public ResponseT<List<ShopFacade>> getAllShops() {
//        return null;
//    }

//    @Override
//    public ResponseT<List<ItemFacade>> getAllItemsByShop(ShopFacade shop) {
//        return null;
//    }

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
    public ResponseT<List<String>> memberLogin(String userName, String userPassword, String visitorName) {
        return userService.memberLogin(userName, userPassword, visitorName);
    }




    private ResponseT<MemberFacade> validateSecurityQuestions(String userName, List<String> answers) {
        return userService.validateSecurityQuestions(userName,answers);
    }



    @Override
    public ResponseT<VisitorFacade> logout(String visitorName) {
        return userService.logout(visitorName);
    }


    @Override
    public Response openNewShop(String visitorName, String shopName) {
        return marketService.openNewShop ( visitorName, shopName );
    }

    @Override
    public Response updateShopItemAmount(String shopOwnerName, ItemFacade item, int amount, String shopName) {
        return marketService.updateShopItemAmount ( shopOwnerName, item, amount, shopName );
    }

    @Override
    public Response removeItemFromShop(String shopOwnerName, ItemFacade item, String shopName) {
        return marketService.removeItemFromShop(shopOwnerName, item, shopName);
    }

    @Override
    public Response addItemToShop(String shopOwnerName, String name, double price,Item.Category category,String info,
                                  List<String> keywords, int amount, String shopName) {
        return marketService.addItemToShop(shopOwnerName,name,price,category,info,keywords,amount,shopName);
    }

    @Override
    public ResponseT<Integer> getItemCurrentAmount(ItemFacade item, String shopName) {
        return marketService.getItemCurrentAmount ( item, shopName );
    }

    @Override
    public Response setItemCurrentAmount(String shopOwnerName, ItemFacade item, double amount, String shopName) {
        return marketService.setItemCurrentAmount(shopOwnerName, item, amount, shopName);
    }


    @Override
    public Response changeShopItemInfo(String shopOwnerName, ItemFacade updatedItem, ItemFacade oldItem, String shopName) {
        return marketService.changeShopItemInfo ( shopOwnerName, updatedItem, oldItem, shopName );
    }

    @Override
    public Response appointShopOwner(String shopOwnerName, String appointedShopOwner, String shopName) {
        return userService.appointShopOwner ( shopOwnerName, appointedShopOwner, shopName );
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
    public Response editShopManagerPermissions(String shopOwnerName,String managerName, String relatedShop,
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
