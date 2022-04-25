package main.serviceLayer;

import main.businessLayer.Item;
import main.businessLayer.ExternalServices.PaymentService;
import main.businessLayer.ExternalServices.ProductsSupplyService;
import main.serviceLayer.FacadeObjects.*;
import main.resources.Address;
import main.resources.PaymentMethod;

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

    @Override
    public Response initMarket() {
        return null;
    }

    @Override
    public Response firstInitMarket(PaymentService paymentService, ProductsSupplyService supplyService,
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

    @Override
    public ResponseT<List<ShopFacade>> getAllShops() {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> getAllItemsByShop(ShopFacade shop) {
        return null;
    }

    @Override
    public ResponseT<ItemFacade> searchProductByName(String name) {
        return null;
    }

    @Override
    public ResponseT<ItemFacade> searchProductByCategory(Item.Category category) {
        return null;
    }

    @Override
    public ResponseT<ItemFacade> searchProductByKeyword(String keyWord) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> filterItemByPrice(int minPrice, int maxPrice) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> filterItemByCategory(Item.Category category) {
        return null;
    }

    @Override
    public Response addItemToShoppingCart(ItemFacade itemToInsert, int amount, String shopName, String visitorName) {
        return null;
    }

    @Override
    public ResponseT<ShoppingCartFacade> showShoppingCart(String visitorName) {
        return null;
    }

    @Override
    public Response editItemFromShoppingCart(int amount, ItemFacade itemFacade, String shopName, String visitorName) {
        return null;
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

    @Override
    public Response openNewShop(String visitorName, String shopName) {
        return null;
    }

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

    @Override
    public ResponseT<Integer> getItemCurrentAmount(ItemFacade item, String shopName) {
        return null;
    }

    @Override //TODO check if we need to get shop owner name in here too ( like remove and add)
    public Response setItemCurrentAmount(ItemFacade item, int amount, String shopName) {
        return marketService.setItemCurrentAmount(item, amount, shopName);
    }

    @Override
    public Response changeShopItemInfo(String shopOwnerName, ItemFacade updatedItem, ItemFacade oldItem, String shopName) {
        return null;
    }

    @Override
    public Response appointShopOwner(String shopOwnerName, String appointedShopOwner, String shopName) {
        return null;
    }

    @Override
    public Response appointShopManager(String shopOwnerName, String appointedShopOwner, String shopName) {
        return null;
    }

    @Override
    public ResponseT<List<AppointmentFacade>> getSelfAppointed(String shopOwnerName) {
        return null;
    }

    @Override
    public ResponseT<List<ShopManagerAppointmentFacade>> getSelfManagerAppointed(String shopOwnerName) {
        return null;
    }

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
        return null;
    }

    @Override
    public ResponseT<String> getAllSystemPurchaseHistory(String SystemManagerName) {
        return null;
    }

    @Override
    public ResponseT<String> getHistoryByShop(String SystemManagerName, String shopName) {
        return null;
    }

    @Override
    public ResponseT<String> getHistoryByMember(String SystemManagerName, String memberName) {
        return null;
    }


}
