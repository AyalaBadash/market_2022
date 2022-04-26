package com.example.server.serviceLayer;

import com.example.server.businessLayer.ExternalServices.*;
import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.PaymentMethod;
import com.example.server.businessLayer.ExternalServices.PaymentService;
import com.example.server.businessLayer.Item;
import com.example.server.serviceLayer.FacadeObjects.*;
import com.example.server.serviceLayer.Requests.*;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

public interface IService {
    //  ************************** System UseCases *******************************//
//
//    /**
//     * to be done in the version with the DB
//     * @return
//     */
//    public Response initMarket();



    Response firstInitMarket(InitMarketRequest request);

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
     *
     * @param request
     * @return
     */
    public ResponseT<Boolean> register(NamePasswordRequest request);

    /**
     *
     * @param request
     * @return
     */
    public ResponseT<Boolean> addPersonalQuery(AddPersonalQueryRequest request);


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
     *
     * @param request
     * @return
     */
    public ResponseT<List<ItemFacade>> filterItemByPrice(FilterItemByPriceRequest request);

    /**
     *
     * @param request
     * @return
     */
    public ResponseT<List<ItemFacade>> filterItemByCategory(FilterItemByCategoryRequest request);

    /**
     *
     * @param request
     * @return
     */
    public Response addItemToShoppingCart(AddItemToShoppingCartRequest request);

    /**
     * @param visitorName
     * @return
     */
    public ResponseT<ShoppingCartFacade> showShoppingCart(String visitorName);


    /**
     *
     * @param request
     * @return
     */
    public Response editItemFromShoppingCart(EditItemFromShoppingCartRequest request);

    /**
     *
     * @param visitorName
     * @return
     */
    public ResponseT<ShoppingCartFacade> calculateShoppingCart(String visitorName);

    /**
     *
     * @param request
     * @return
     */
    public Response buyShoppingCart(BuyShoppingCartRequest request);


    /**
     *
     * @param request
     * @return
     */
    public ResponseT<List<String>> memberLogin(NamePasswordRequest request);

    /**
     *
     * @param request
     * @return
     */
    public ResponseT<MemberFacade> validateSecurityQuestions(ValidateSecurityRequest request);


    //************************* Member Use cases *************************************//

    /**
     * @param visitorName
     * @return
     */
    public ResponseT<VisitorFacade> logout(String visitorName);

    /**
     *
     * @param request
     * @return
     */
    public Response openNewShop(OpenNewShopRequest request);

    /**
     *
     * @param request
     * @return
     */
    public ResponseT<ShopFacade> getShopInfo(TwoStringRequest request);


    // *********************** Shop Owner use cases *******************************//

    /**
     *
     * @param request
     * @return
     */
    public Response updateShopItemAmount(UpdateShopItemAmountRequest request);

    /**
     *
     * @param request
     * @return
     */
    public Response removeItemFromShop(RemoveItemFromShopRequest request);

    /**
     *
     * @param addItemToShopRequest
     * @return
     */
    public Response addItemToShop(AddItemToShopRequest addItemToShopRequest);


    /**
     *
     * @param request
     * @return
     */
    public Response setItemCurrentAmount(SetItemCurrentAmountRequest request);


    /**
     *
     * @param request
     * @return
     */
    public Response changeShopItemInfo(ChangeShopItemInfoRequest request);

    /**
     *
     * @param request
     * @return
     */
    public Response appointShopOwner(AppointmentShopOwnerRequest request);

    /**
     *
     * @param request
     * @return
     */
    public Response appointShopManager(AppointmentShopManagerRequest request);


    /**
     *
     * @param request
     * @return
     */
    public Response editShopManagerPermissions(EditShopManagerPermissionsRequest request);

    /**
     *
     * @param request
     * @return
     */
    public ResponseT getManagerPermission(GetManagerPermissionRequest request);

    /**
     *
     * @param request
     * @return
     */
    public Response closeShop(CloseShopRequest request);

    // ************************** Shop Manager and Shop Owner use cases ********************************//

    /**
     *
     * @param request
     * @return
     */
    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(GetShopEmployeesRequest request);

    /**
     *
     * @param request
     * @return
     */
    public ResponseT<String> getShopPurchaseHistory(TwoStringRequest request);


    // ************************** System Manager use cases ********************************//

    /**
     * @return Market purchase history
     */
    public ResponseT<String> getAllSystemPurchaseHistory(String SystemManagerName);


    /**
     *
     * @param request
     * @return
     */
    public ResponseT<String> getHistoryByShop(TwoStringRequest request);

    /**
     * @return Member purchase history
     */
    public ResponseT<String> getHistoryByMember(GetHistoryByMemberRequest request);
}