package com.example.server.serviceLayer;

import com.example.server.businessLayer.Item;
import com.example.server.serviceLayer.FacadeObjects.*;
import com.example.server.serviceLayer.Requests.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
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
    @RequestMapping(value = "/firstInitMarket")
    @CrossOrigin
    public Response firstInitMarket(@RequestBody InitMarketRequest request) {
        return marketService.firstInitMarket ( request.getPaymentService(), request.getSupplyService(), request.getUserName(), request.getPassword() );
    }

    @Override
    @RequestMapping(value = "/guestLogin")
    @CrossOrigin
    public ResponseT<VisitorFacade> guestLogin() {
        return this.userService.guestLogin();
    }

    @Override
    @RequestMapping(value = "/exitSystem")
    @CrossOrigin
    public Response exitSystem(@RequestBody String visitorName) {
        return this.userService.exitSystem(visitorName);
    }

    @Override
    @RequestMapping(value = "/register")
    @CrossOrigin
    public ResponseT<Boolean> register(@RequestBody NamePasswordRequest request) {
        return userService.register(request.getName(), request.getPassword());
    }

    @Override
    @RequestMapping(value = "/addPersonalQuery")
    @CrossOrigin
    public ResponseT<Boolean> addPersonalQuery(@RequestBody AddPersonalQueryRequest request) {
        return userService.addPersonalQuery(request.getUserAdditionalQueries(), request.getUserAdditionalAnswers(), request.getMember());
    }



    @Override
    @RequestMapping(value = "/searchProductByName")
    @CrossOrigin
    public ResponseT<List<ItemFacade>> searchProductByName(@RequestBody String name) {
        return marketService.searchProductByName(name);
    }

    @Override
    @RequestMapping(value = "/searchProductByCategory")
    @CrossOrigin
    public ResponseT<List<ItemFacade>> searchProductByCategory(@RequestBody Item.Category category) {
        return marketService.searchProductByCategory(category);
    }

    @Override
    @RequestMapping(value = "/searchProductByKeyword")
    @CrossOrigin
    public ResponseT<List<ItemFacade>> searchProductByKeyword(@RequestBody String keyWord) {
        return marketService.searchProductByKeyword(keyWord);
    }

    @Override
    @RequestMapping(value = "/filterItemByCategory")
    @CrossOrigin
    public ResponseT<List<ItemFacade>> filterItemByCategory(@RequestBody FilterItemByCategoryRequest request) {
        return marketService.filterItemByCategory(request.getItems(),request.getCategory());
    }

    @Override
    @RequestMapping(value = "/filterItemByPrice")
    @CrossOrigin
    public ResponseT<List<ItemFacade>> filterItemByPrice(@RequestBody FilterItemByPriceRequest request) {
        return marketService.filterItemByPrice(request.getItems(), request.getMinPrice(), request.getMaxPrice());
    }

    @Override
    @RequestMapping(value = "/addItemToShoppingCart")
    @CrossOrigin
    public Response addItemToShoppingCart(@RequestBody AddItemToShoppingCartRequest request) {
        return purchaseService.addItemToShoppingCart(request.getItemToInsert(),request.getAmount(),request.getShopName(),request.getVisitorName());
    }
    @Override
    @RequestMapping(value = "/showShoppingCart")
    @CrossOrigin
    public ResponseT<ShoppingCartFacade> showShoppingCart(@RequestBody String visitorName) {
        return purchaseService.showShoppingCart(visitorName);
    }
    @Override
    @RequestMapping(value = "/editItemFromShoppingCart")
    @CrossOrigin
    public Response editItemFromShoppingCart(@RequestBody EditItemFromShoppingCartRequest request) {
        return purchaseService.editItemFromShoppingCart(request.getAmount(),request.getItemFacade() ,
                request.getShopName(),request.getVisitorName());
    }

    @Override
    @RequestMapping(value = "/calculateShoppingCart")
    @CrossOrigin
    public ResponseT<ShoppingCartFacade> calculateShoppingCart(@RequestBody String visitorName) {
        return purchaseService.calculateShoppingCart(visitorName);
    }

    @Override
    @RequestMapping(value = "/buyShoppingCart")
    @CrossOrigin
    public Response buyShoppingCart(@RequestBody BuyShoppingCartRequest request) {
        return this.purchaseService.buyShoppingCart(request.getVisitorName(), request.getExpectedPrice(),
                request.getPaymentMethod(), request.getAddress());
    }

    @Override
    @RequestMapping(value = "/memberLogin")
    @CrossOrigin
    public ResponseT<List<String>> memberLogin(@RequestBody NamePasswordRequest request) {
        return userService.memberLogin(request.getName(),request.getPassword());
    }

    @Override
    @RequestMapping(value = "/validateSecurityQuestions")
    @CrossOrigin
    public ResponseT<MemberFacade> validateSecurityQuestions(@RequestBody ValidateSecurityRequest request) {
        return userService.validateSecurityQuestions (request.getUserName(), request.getAnswers(), request.getVisitorName() );
    }



    @Override
    @RequestMapping(value = "/logout")
    @CrossOrigin
    public ResponseT<VisitorFacade> logout(@RequestBody String visitorName) {
        return userService.logout(visitorName);
    }

    @Override
    @RequestMapping(value = "/openNewShop")
    @CrossOrigin
    public Response openNewShop(@RequestBody OpenNewShopRequest request) {
        return marketService.openNewShop ( request.getMemberName(), request.getShopName() );
    }

    @Override
    @RequestMapping(value = "/updateShopItemAmount")
    @CrossOrigin
    public Response updateShopItemAmount(@RequestBody UpdateShopItemAmountRequest request) {

        return marketService.updateShopItemAmount (request.getShopOwnerName(), request.getItem(),
                request.getAmount(), request.getShopName() );
    }


    @Override
    @RequestMapping(value = "/removeItemFromShop")
    @CrossOrigin
    public Response removeItemFromShop(@RequestBody RemoveItemFromShopRequest request) {
        return marketService.removeItemFromShop(request.getShopOwnerName(),request.getItem(),request.getShopName());
    }

    @Override
    @RequestMapping(value = "/addItemToShop")
    @CrossOrigin
    public Response addItemToShop(@RequestBody AddItemToShopRequest request) {
        return marketService.addItemToShop(request.getShopOwnerName(), request.getName(), request.getPrice(),
                request.getCategory(), request.getInfo(), request.getKeywords(), request.getAmount(), request.getShopName());
    }

    @Override
    @RequestMapping(value = "/setItemCurrentAmount")
    @CrossOrigin
    public Response setItemCurrentAmount(@RequestBody SetItemCurrentAmountRequest request) {
        return marketService.setItemCurrentAmount(request.getShopOwnerName(), request.getItem(),
                request.getAmount(), request.getShopName());
    }


    @Override
    @RequestMapping(value = "/changeShopItemInfo")
    @CrossOrigin
    public Response changeShopItemInfo(@RequestBody ChangeShopItemInfoRequest request) {
        return marketService.changeShopItemInfo (request.getShopOwnerName(), request.getUpdatedItem(),
                request.getOldItem(), request.getShopName() );
    }

    @Override
    @RequestMapping(value = "/appointShopOwner")
    @CrossOrigin
    public Response appointShopOwner(@RequestBody AppointmentShopOwnerRequest request) {
        return userService.appointShopOwner (request.getShopOwnerName(), request.getAppointedShopOwner(), request.getShopName() );
    }
    @Override
    @RequestMapping(value = "/appointShopManager")
    @CrossOrigin
    public Response appointShopManager(@RequestBody AppointmentShopManagerRequest request) {
        return null;
    }

    @Override
    @RequestMapping(value = "/editShopManagerPermissions")
    @CrossOrigin
    public Response editShopManagerPermissions(@RequestBody EditShopManagerPermissionsRequest request) {
        return this.userService.editShopManagerPermissions(
                request.getShopOwnerName(), request.getManagerName() , request.getRelatedShop(), request.getUpdatedAppointment());
    }

    @Override
    @RequestMapping(value = "/getManagerPermission")
    @CrossOrigin
    public ResponseT getManagerPermission(@RequestBody GetManagerPermissionRequest request){
        return this.userService.getManagerAppointment(request.getShopOwnerName(),
                request.getManagerName(), request.getRelatedShop());
    }

    @Override
    @RequestMapping(value = "/closeShop")
    @CrossOrigin
    public Response closeShop(@RequestBody CloseShopRequest request) {
        return this.marketService.closeShop(request.getShopOwnerName(), request.getShopName());
    }

    @Override
    @RequestMapping(value = "/getShopEmployeesInfo")
    @CrossOrigin
    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(@RequestBody GetShopEmployeesRequest request) {
        return marketService.getShopEmployeesInfo(request.getShopName(), request.getShopName());
    }

    @Override
    @RequestMapping(value = "/getShopInfo")
    @CrossOrigin
    public ResponseT<ShopFacade> getShopInfo(@RequestBody TwoStringRequest request) {
        return marketService.getShopInfo(request.getName(), request.getShopName());
    }

    @Override
    @RequestMapping(value = "/getShopPurchaseHistory")
    @CrossOrigin
    public ResponseT<String> getShopPurchaseHistory(@RequestBody TwoStringRequest request) {
        return marketService.getShopPurchaseHistory (request.getName(), request.getShopName() );
    }

    @Override
    @RequestMapping(value = "/getAllSystemPurchaseHistory")
    @CrossOrigin
    public ResponseT<String> getAllSystemPurchaseHistory(@RequestBody String systemManagerName) {
        return marketService.getAllSystemPurchaseHistory ( systemManagerName );
    }

    @Override
    @RequestMapping(value = "/getAllSystemPurchaseHistory")
    @CrossOrigin
    public ResponseT<String> getHistoryByShop(@RequestBody TwoStringRequest request) {
        return marketService.getHistoryByShop (request.getName(), request.getShopName() );
    }

    @Override
    @RequestMapping(value = "/getHistoryByMember")
    @CrossOrigin
    public ResponseT<String> getHistoryByMember(@RequestBody GetHistoryByMemberRequest request) {
        return marketService.getHistoryByMember (request.getSystemManagerName(), request.getMemberName() );
    }


}
