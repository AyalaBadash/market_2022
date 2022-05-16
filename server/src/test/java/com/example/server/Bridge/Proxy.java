package com.example.server.Bridge;

import com.example.server.businessLayer.Item;
import com.example.server.serviceLayer.AppointmentShopManagerRequest;
import com.example.server.serviceLayer.FacadeObjects.*;
import com.example.server.serviceLayer.IService;
import com.example.server.serviceLayer.Requests.*;
import com.example.server.serviceLayer.Response;
import com.example.server.serviceLayer.ResponseT;

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
    public Response exitSystem(ExitSystemRequest request) {
        return null;
    }

    @Override
    public ResponseT<Boolean> register(NamePasswordRequest request) {
        return null;
    }

    @Override
    public Response addPersonalQuery(AddPersonalQueryRequest request) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> searchProductByName(SearchProductByNameRequest request) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> searchProductByCategory(Item.Category category) {
        return null;
    }

    @Override
    public ResponseT<List<ItemFacade>> searchProductByKeyword(SearchProductByNameRequest request) {
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
    public ResponseT<ShoppingCartFacade> showShoppingCart(RequestVisitorName request) {
        return null;
    }

    @Override
    public Response editItemFromShoppingCart(EditItemFromShoppingCartRequest request) {
        return null;
    }

    @Override
    public ResponseT<ShoppingCartFacade> calculateShoppingCart(RequestVisitorName request) {
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
    public ResponseT<VisitorFacade> logout(RequestVisitorName request) {
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
    public Response editItem(editItemRequest request) {
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
    public ResponseT<String> getAllSystemPurchaseHistory(GetAllSystemPurchaseHistoryRequest request) {
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

    @Override
    public Response removeShopOwnerAppointment(removeAppointmentRequest request) {
        return null;
    }

    @Override
    public Response removeMember(removeMemberRequest request) {
        return null;
    }

    @Override
    public ResponseT<ItemFacade> getItemInfo(GetItemInfoRequest request) {
        return null;
    }
}
