package com.example.server.serviceLayer;

import com.example.server.businessLayer.ExternalServices.PaymentMock;
import com.example.server.businessLayer.ExternalServices.SupplyMock;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Users.Member;
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

    @Override
    @RequestMapping(value = "/firstInitMarket")
    @CrossOrigin
    public Response firstInitMarket(@RequestBody InitMarketRequest request) {
        PaymentMock paymentService = new PaymentMock();
        SupplyMock supplyMock = new SupplyMock();
        return marketService.firstInitMarket ( paymentService,supplyMock, request.getUserName(), request.getPassword() );
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
    public Response exitSystem(@RequestBody ExitSystemRequest request) {
        return this.userService.exitSystem(request.getVisitorName());
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
    public Response addPersonalQuery(@RequestBody AddPersonalQueryRequest request) {
        return userService.addPersonalQuery(request.getUserAdditionalQueries(), request.getUserAdditionalAnswers(), request.getMember());
    }



    @Override
    @RequestMapping(value = "/searchProductByName")
    @CrossOrigin
    public ResponseT<List<ItemFacade>> searchProductByName(@RequestBody SearchProductByNameRequest request) {
        return marketService.searchProductByName(request.getProductName());
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
    public ResponseT<List<ItemFacade>> searchProductByKeyword(@RequestBody SearchProductByNameRequest request) {
        return marketService.searchProductByKeyword(request.getProductName());
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
    public ResponseT<ShoppingCartFacade> showShoppingCart(@RequestBody RequestVisitorName request) {
        return purchaseService.showShoppingCart(request.getName());
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
    public ResponseT<ShoppingCartFacade> calculateShoppingCart(@RequestBody RequestVisitorName request) {
        return purchaseService.calculateShoppingCart(request.getName());
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
    }//TODO- where are we getting visitorName from



    @Override
    @RequestMapping(value = "/logout")
    @CrossOrigin
    public ResponseT<VisitorFacade> logout(@RequestBody RequestVisitorName request) {
        return userService.logout(request.getName());
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
        return marketService.changeShopItemInfo (request.getShopOwnerName(), request.getUpdatedInfo(),
                request.getOldItem(), request.getShopName() );
    }

    @Override
    @RequestMapping(value = "/editItemRequest")
    @CrossOrigin
    public Response editItem(@RequestBody editItemRequest request) {
        return marketService.editItem (request.getNewItem (), request.getId ());
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
    public ResponseT<String> getAllSystemPurchaseHistory(@RequestBody GetAllSystemPurchaseHistoryRequest request) {
        return marketService.getAllSystemPurchaseHistory ( request.getSystemManagerName() );
    }

    @Override
    @RequestMapping(value = "/getHistoryByShop")
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

    @Override
    @RequestMapping(value = "/removeShopOwnerAppointment")
    @CrossOrigin
    public Response removeShopOwnerAppointment(removeAppointmentRequest request) {
        return marketService.removeShopOwnerAppointment(request.getBoss(),request.getFiredAppointed(),request.getShopName());
    }

    @Override
    public Response removeMember(removeMemberRequest request) {
        return marketService.removeMember(request.getManager(),request.getMemberToRemove());
    }


    public ResponseT<MemberFacade> getMember(String memberName) {
        try {
            Member member = userService.getMember(memberName);
            return new ResponseT<>(new MemberFacade(member));
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
}
