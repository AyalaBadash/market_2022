package com.example.server.serviceLayer;

import com.example.server.businessLayer.Market.Item;
import com.example.server.serviceLayer.FacadeObjects.*;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.DiscountTypeFacade;
import com.example.server.serviceLayer.Requests.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


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
    public Response exitSystem(ExitSystemRequest request);

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

    public Response addPersonalQuery(AddPersonalQueryRequest request);

    /**
     * SearchProductByNameRequest request
     * @param request
     * @return
     */
    public ResponseT<List<ItemFacade>> searchProductByName(SearchProductByNameRequest request);

    /**
     * @param category
     * @return
     */
    public ResponseT<List<ItemFacade>> searchProductByCategory(Item.Category category);


    /**
     *
     * @param request
     * @return
     */
    public ResponseT<List<ItemFacade>> searchProductByKeyword(SearchProductByNameRequest request);

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
     *
     * @param request
     * @return
     */
    public ResponseT<ShoppingCartFacade> showShoppingCart(RequestVisitorName request);


    /**
     *
     * @param request
     * @return
     */
    public Response editItemFromShoppingCart(EditItemFromShoppingCartRequest request);

    /**
     *
     * @param request
     * @return
     */
    public ResponseT<ShoppingCartFacade> calculateShoppingCart(RequestVisitorName request);

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
     *
     * @param request
     * @return
     */
    public ResponseT<VisitorFacade> logout(RequestVisitorName request);

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
    public ResponseT<ShopFacade> addItemToShop(AddItemToShopRequest addItemToShopRequest);


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

    @RequestMapping(value = "/editItemRequest")
    @CrossOrigin
    Response editItem(@RequestBody editItemRequest request);

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
     *
     * @param request
     * @return
     */
    public ResponseT<String> getAllSystemPurchaseHistory(GetAllSystemPurchaseHistoryRequest request);


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

    /**
     *
     * @param request
     * @return
     */
    public Response removeShopOwnerAppointment(removeAppointmentRequest request);

    /**
     *
     * @param request
     * @return
     */
    public Response removeMember(removeMemberRequest request);

    /**
     *
     * @param request
     * @return
     */
    ResponseT<ItemFacade> getItemInfo(GetItemInfoRequest request);

    /**
     *
     * @param request
     * @return
     */
    ResponseT<String> getMarketInfo(GetMarketInfoRequest request);

    /**
     * @param request
     * @return
     */
    Response addDiscountToShop(AddDiscountToShopRequest request);

    /**
     *
     * @return empty response if true, error message if not
     */
    public Response isServerInit();
}