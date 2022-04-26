package com.example.server.Bridge;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.PaymentMethod;
import com.example.server.businessLayer.ExternalServices.PaymentService;
import com.example.server.businessLayer.ExternalServices.ProductsSupplyService;
import com.example.server.businessLayer.Item;
import com.example.server.serviceLayer.FacadeObjects.*;
import com.example.server.serviceLayer.IService;

import java.util.List;

public class Proxy implements IService {
    @Override
    public Response firstInitMarket(PaymentService paymentService, ProductsSupplyService supplyService, String userName, String password) {
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
    public Response addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, MemberFacade member) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> searchProductByName(String name) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> searchProductByCategory(Item.Category category) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> searchProductByKeyword(String keyWord) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> filterItemByPrice(List<ItemFacade> items, double minPrice, double maxPrice) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> filterItemByCategory(List<ItemFacade> items, Item.Category category) {
        return null;
    }

    @Override
    public Response addItemToShoppingCart(ItemFacade itemToInsert, double amount, String shopName, String visitorName) {
        return null;
    }

    @Override
    public ResponseT<ShoppingCartFacade> showShoppingCart(String visitorName) {
        return null;
    }

    @Override
    public Response editItemFromShoppingCart(double amount, ItemFacade itemFacade, String shopName, String visitorName) {
        return null;
    }

    @Override
    public ResponseT<ShoppingCartFacade> calculateShoppingCart(String visitorName) {
        return null;
    }

    @Override
    public Response buyShoppingCart(String visitorName, double expectedPrice, PaymentMethod paymentMethod, Address address) {
        return null;
    }

    @Override
    public ResponseT<List<String>> memberLogin(String userName, String userPassword) {
        return null;
    }

    @Override
    public ResponseT<MemberFacade> validateSecurityQuestions(String userName, List<String> answers, String visitorName) {
        return null;
    }

    @Override
    public ResponseT<VisitorFacade> logout(String visitorName) {
        return null;
    }

    @Override
    public Response openNewShop(String visitorName, String shopName) {
        return null;
    }

    @Override
    public ResponseT<ShopFacade> getShopInfo(String member, String shopName) {
        return null;
    }

    @Override
    public Response updateShopItemAmount(String shopOwnerName, ItemFacade item, double amount, String shopName) {
        return null;
    }

    @Override
    public Response removeItemFromShop(String shopOwnerName, ItemFacade item, String shopName) {
        return null;
    }

    @Override
    public Response addItemToShop(String shopOwnerName, String name, double price, Item.Category category, String info, List<String> keywords, double amount, String shopName) {
        return null;
    }

    @Override
    public Response setItemCurrentAmount(String shopOwnerName, ItemFacade item, double amount, String shopName) {
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
    public Response editShopManagerPermissions(String shopOwnerName, String shopManagerName, String relatedShop, ShopManagerAppointmentFacade updatedAppointment) {
        return null;
    }

    @Override
    public ResponseT getManagerPermission(String shopOwnerName, String managerName, String relatedShop) {
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
