package com.example.server.Bridge;

import com.example.server.businessLayer.Item;
import com.example.server.serviceLayer.AppointmentShopManagerRequest;
import com.example.server.serviceLayer.FacadeObjects.*;
import com.example.server.serviceLayer.IService;
import com.example.server.serviceLayer.Requests.*;

import java.util.List;

public class Proxy implements IService  {


    @Override
    public Response firstInitMarket(InitMarketRequest request) {
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
    public ResponseT<Boolean> register(NamePasswordRequest request) {
        return null;
    }

    @Override
    public ResponseT<Boolean> addPersonalQuery(AddPersonalQueryRequest request) {
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
    public ResponseT<List<ItemFacade>> filterItemByPrice(FilterItemByPriceRequest request) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> filterItemByCategory(FilterItemByCategoryRequest request) {
        return null;
    }

    @Override
    public Response addItemToShoppingCart(AddItemToShoppingCartRequest request) {
        return null;
    }

    @Override
    public ResponseT<ShoppingCartFacade> showShoppingCart(String visitorName) {
        return null;
    }

    @Override
    public Response editItemFromShoppingCart(EditItemFromShoppingCartRequest request) {
        return null;
    }

    @Override
    public ResponseT<ShoppingCartFacade> calculateShoppingCart(String visitorName) {
        return null;
    }

    @Override
    public Response buyShoppingCart(BuyShoppingCartRequest request) {
        return null;
    }

    @Override
    public ResponseT<List<String>> memberLogin(NamePasswordRequest request) {
        return null;
    }

    @Override
    public ResponseT<MemberFacade> validateSecurityQuestions(ValidateSecurityRequest request) {
        return null;
    }

    @Override
    public ResponseT<VisitorFacade> logout(String visitorName) {
        return null;
    }

    @Override
    public Response openNewShop(OpenNewShopRequest request) {
        return null;
    }

    @Override
    public ResponseT<ShopFacade> getShopInfo(TwoStringRequest request) {
        return null;
    }

    @Override
    public Response updateShopItemAmount(UpdateShopItemAmountRequest request) {
        return null;
    }

    @Override
    public Response removeItemFromShop(RemoveItemFromShopRequest request) {
        return null;
    }

    @Override
    public Response addItemToShop(AddItemToShopRequest addItemToShopRequest) {
        return null;
    }

    @Override
    public Response setItemCurrentAmount(SetItemCurrentAmountRequest request) {
        return null;
    }

    @Override
    public Response changeShopItemInfo(ChangeShopItemInfoRequest request) {
        return null;
    }

    @Override
    public Response appointShopOwner(AppointmentShopOwnerRequest request) {
        return null;
    }

    @Override
    public Response appointShopManager(AppointmentShopManagerRequest request) {
        return null;
    }

    @Override
    public Response editShopManagerPermissions(EditShopManagerPermissionsRequest request) {
        return null;
    }

    @Override
    public ResponseT getManagerPermission(GetManagerPermissionRequest request) {
        return null;
    }

    @Override
    public Response closeShop(CloseShopRequest request) {
        return null;
    }

    @Override
    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(GetShopEmployeesRequest request) {
        return null;
    }

    @Override
    public ResponseT<String> getShopPurchaseHistory(TwoStringRequest request) {
        return null;
    }

    @Override
    public ResponseT<String> getAllSystemPurchaseHistory(String SystemManagerName) {
        return null;
    }

    @Override
    public ResponseT<String> getHistoryByShop(TwoStringRequest request) {
        return null;
    }

    @Override
    public ResponseT<String> getHistoryByMember(GetHistoryByMemberRequest request) {
        return null;
    }
}
