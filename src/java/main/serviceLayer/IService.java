package main.serviceLayer;

import main.businessLayer.*;
import main.businessLayer.ExternalServices.PaymentService;
import main.businessLayer.ExternalServices.ProductsSupplyService;
import main.serviceLayer.FacadeObjects.*;
import main.resources.Address;
import main.resources.PaymentMethod;

import java.util.List;

public interface IService {
    //  ************************** System UseCases *******************************//

    /**
     * @param paymentService
     * @param supplyService
     * @param userName
     * @param password
     * @return
     */
    public Response initMarket(PaymentService paymentService, ProductsSupplyService supplyService,
                               String userName, String password);


    // ************************** Visitor Use cases ******************************//

    /**
     * generates a unique name (temporary)
     * example -visitor123
     *
     * @return
     */
    public ResponseT<VisitorFacade> guestLogin();

    /**
     * if not a member - deletes from data
     */
    public Response exitSystem(String visitorName);

    /**
     * @param userName
     * @param userPassword
     * @return
     */
    public ResponseT<Boolean> register(String userName, String userPassword);

    /**
     * @param userAdditionalQueries
     * @param userAdditionalAnswers
     * @param member
     * @return
     */
    public ResponseT<Boolean> addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers,
                                               MemberFacade member);


    /**
     * @return
     */
    public ResponseT<List<ShopFacade>> getAllShops();

    /**
     * @param shop
     * @return
     */
    public ResponseT<List<ItemFacade>> getAllItemsByShop(ShopFacade shop);

    /**
     * @param name
     * @return
     */
    public ResponseT<List<ItemFacade>> searchProductByName(String name);

    /**
     * @param category
     * @return
     */
    public ResponseT<List<ItemFacade>> searchProductByCategory(Item.Category category);

    /**
     * @param keyWord
     * @return
     */
    public ResponseT<List<ItemFacade>> searchProductByKeyword(String keyWord);

    /**
     * @param minPrice
     * @param maxPrice
     * @return
     */
    public ResponseT<List<ItemFacade>> filterItemByPrice(List<ItemFacade> items, int minPrice, int maxPrice);

    /**
     * @param category
     * @return
     */
    public ResponseT<List<ItemFacade>> filterItemByCategory(List<ItemFacade> items, Item.Category category);

    /**
     * @param itemToInsert
     * @param amount
     * @param shopName
     * @param visitorName
     * @return
     */
    public Response addItemToShoppingCart(ItemFacade itemToInsert, int amount, String shopName,
                                          String visitorName);

    /**
     * @param visitorName
     * @return
     */
    public ResponseT<ShoppingCartFacade> showShoppingCart(String visitorName);

    /**
     * @param amount
     * @param itemFacade
     * @param shopName
     * @param visitorName
     * @return
     */
    public Response editItemFromShoppingCart(int amount, ItemFacade itemFacade, String shopName,
                                             String visitorName);

    /**
     * TODO add price (initial to -1) to shopping cart and update after calculating
     *
     * @param visitorName
     * @return
     */
    public ResponseT<ShoppingCartFacade> calculateShoppingCart(String visitorName);

    /**
     * update if nothing changed and the buying is actually occurred shops for purchase history and items updating
     *
     * @param visitorName
     * @param paymentMethod
     * @param address
     * @return
     */
    public Response buyShoppingCart(String visitorName, double expectedPrice, PaymentMethod paymentMethod, Address address);


    /**
     * need to delete the temporary VisitorName from data
     *
     * @param userName
     * @param userPassword
     * @return
     */
    public ResponseT<MemberFacade> memberLogin(String userName, String userPassword,
                                               String visitorName);


    //************************* Member Use cases *************************************//

    /**
     * @param visitorName
     * @return
     */
    public ResponseT<VisitorFacade> logout(String visitorName);

    /**
     * @param visitorName
     * @param shopName
     * @return
     */
    public Response openNewShop(String visitorName, String shopName);

    /**
     *
     * @param member
     * @param shopName
     * @return
     */
    public ResponseT<ShopFacade> getShopInfo(String member, String shopName);


    // *********************** Shop Owner use cases *******************************//

    /**
     * !this method is not used for updating amount when buying a shopping cart!
     *
     * @param shopOwnerName
     * @param item
     * @param amount
     * @param shopName
     * @return
     */
    public Response updateShopItemAmount(String shopOwnerName, ItemFacade item, int amount, String shopName);

    /**
     * @param shopOwnerName
     * @param item
     * @param shopName
     * @return
     */
    public Response removeItemFromShop(String shopOwnerName, ItemFacade item, String shopName);

    /**
     * need to check if the item is not already exist
     *
     * @param shopOwnerName
     * @param item
     * @param amount
     * @param shopName
     * @return
     */
    public Response addItemToShop(String shopOwnerName,String name, double price,Item.Category category,String info,
                                  List<String> keywords, int amount, String shopName);

    /**
     *
     * @param item
     * @param shopName
     * @return items current amount in the shop
     */
    public ResponseT<Integer> getItemCurrentAmount(ItemFacade item, String shopName);

    /**
     *
     * @param item
     * @param amount
     * @param shopName
     * @return sets item current amount in shop
     */
    public Response setItemCurrentAmount(ItemFacade item,int amount, String shopName);

    /**
     * if the change is in a unique key then after changing need to update all uses like shopping cart
     *
     * @param shopOwnerName
     * @param updatedItem
     * @param oldItem
     * @param shopName
     * @return
     */
    public Response changeShopItemInfo(String shopOwnerName, ItemFacade updatedItem, ItemFacade oldItem, String shopName);


    /**
     * needed to be implemented as Mira showed (shaked sent the relevant photo)
     *
     * @param shopOwnerName
     * @param appointedShopOwner
     * @param shopName
     * @return
     */
    public Response appointShopOwner(String shopOwnerName, String appointedShopOwner, String shopName);

    /**
     * initial to all permissions
     *
     * @param shopOwnerName
     * @param appointedShopOwner
     * @param shopName
     */
    public Response appointShopManager(String shopOwnerName, String appointedShopOwner, String shopName);

    /**
     * @return managers and shop owners I appointed
     */
    public ResponseT<List<AppointmentFacade>> getSelfAppointed(String shopOwnerName);

    /**
     * @return managers I appointed
     */
    public ResponseT<List<ShopManagerAppointmentFacade>> getSelfManagerAppointed(String shopOwnerName);

    /**
     * @return shop owners I appointed
     */
    public ResponseT<List<ShopOwnerAppointmentFacade>> getSelfShopOwnerAppointed(String shopOwnerName);


    /**
     * @param shopOwnerName
     * @param updatedAppointment
     * @return
     */
    public Response editShopManagerPermissions(String shopOwnerName, String shopManagerName, String relatedShop,
                                               ShopManagerAppointmentFacade updatedAppointment);

    /**
     * @param shopOwnerName
     * @param managerName
     * @return
     */
    public ResponseT getManagerPermission(String shopOwnerName, String managerName, String relatedShop);

    /**
     * need to update all shop employees
     *
     * @param shopOwnerName
     * @param shopName
     * @return
     */
    public Response closeShop(String shopOwnerName, String shopName);

    // ************************** Shop Manager and Shop Owner use cases ********************************//

    /**
     * need to check permissions (shop owner or manager)
     *
     * @param shopManagerName
     * @param shopName
     * @return
     */
    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(String shopManagerName, String shopName);

    /**
     * @param shopManagerName
     * @param shopName
     * @return
     */
    public ResponseT<String> getShopPurchaseHistory(String shopManagerName, String shopName);


    // ************************** System Manager use cases ********************************//

    /**
     * @return Market purchase history
     */
    public ResponseT<String> getAllSystemPurchaseHistory(String SystemManagerName);


    /**
     * @return Shop purchase history
     */
    public ResponseT<String> getHistoryByShop(String SystemManagerName, String shopName);

    /**
     * @return Member purchase history
     */
    public ResponseT<String> getHistoryByMember(String SystemManagerName, String shopName);
}
