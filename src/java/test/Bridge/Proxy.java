package test.Bridge;

import main.businessLayer.Item;
import main.businessLayer.ExternalServices.PaymentService;
import main.businessLayer.ExternalServices.ProductsSupplyService;
import main.serviceLayer.FacadeObjects.*;
import main.serviceLayer.IService;
import main.resources.Address;
import main.resources.PaymentMethod;

import java.util.List;

public class Proxy implements IService {
    @Override
    public Response initMarket(PaymentService paymentService, ProductsSupplyService supplyService, String userName, String password) {
        return null;
    }

    @Override
    public ResponseT<VisitorFacade> guestLogin() {
        return null;
    }

    @Override
    public Response exitSystem(String visitorName) {
        return null;
    }

    @Override
    public ResponseT<Boolean> register(String userName, String userPassword) {
        return null;
    }

    @Override
    public ResponseT<Boolean> addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, MemberFacade member) {
        return null;
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
        return null;
    }

    @Override
    public Response buyShoppingCart(String visitorName, int expectedPrice, PaymentMethod paymentMethod, Address address) {
        return null;
    }

    @Override
    public ResponseT<MemberFacade> memberLogin(String userName, String userPassword, List<String> userAdditionalAnswers, String visitorName) {
        return null;
    }

    @Override
    public Response logout(String visitorName) {
        return null;
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
        return null;
    }

    @Override
    public Response addItemToShop(String shopOwnerName, ItemFacade item, int amount, String shopName) {
        return null;
    }

    @Override
    public ResponseT<Integer> getItemCurrentAmount(ItemFacade item, String shopName) {
        return null;
    }

    @Override
    public Response setItemCurrentAmount(ItemFacade item, int amount, String shopName) {
        return null;
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
    public Response editShopManagerPermissions(String shopOwnerName,
                                               ShopManagerAppointmentFacade updatedAppointment) {
        return null;
    }

    @Override
    public Response closeShop(String shopOwnerName, String shopName) {
        return null;
    }

    @Override
    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(String shopManagerName, String shopName) {
        return null;
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
    public ResponseT<String> getHistoryByMember(String SystemManagerName, String shopName) {
        return null;
    }


}