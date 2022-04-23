package main.serviceLayer;

import main.businessLayer.*;
import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Appointment.ShopManagerAppointment;
import main.businessLayer.Appointment.ShopOwnerAppointment;
import main.businessLayer.ExternalServices.PaymentService;
import main.businessLayer.ExternalServices.ProductsSupplyService;
import main.serviceLayer.FacadeObjects.*;

import java.util.ArrayList;
import java.util.List;

public class MarketService {
    private static MarketService marketService = null;
    private Market market;

    private MarketService() {
        market = Market.getInstance();
    }

    public synchronized static MarketService getInstance() {
        if (marketService == null)
            marketService = new MarketService();
        return marketService;
    }

    public Response initMarket(PaymentService paymentService, ProductsSupplyService supplyService, String userName, String password) {
        return null;
    }


    // TODO returns only open shops

    /**
     *
     * @return only open shops
     */
    public ResponseT<List<ShopFacade>> getAllShops() {
        return null;
    }

    public ResponseT<List<ItemFacade>> getAllItemsByShop(ShopFacade shop) {
        return null;
    }

    public ResponseT<ItemFacade> searchProductByName(String name) {
        return null;
    }

    public ResponseT<ItemFacade> searchProductByCategory(Item.Category category) {
        return null;
    }

    public ResponseT<ItemFacade> searchProductByKeyword(String keyWord) {
        return null;
    }

    public ResponseT<List<ItemFacade>> filterItemByPrice(int minPrice, int maxPrice) {
        return null;
    }

    public ResponseT<List<ItemFacade>> filterItemByCategory(Item.Category category) {
        return null;
    }

    // TODO validate visitor is a member
    public Response openNewShop(String visitorName, String shopName) {
        return null;
    }

    public Response updateShopItemAmount(String shopOwnerName, ItemFacade item, int amount, String shopName) {
        return null;
    }

    // TODO need to check users are updated
    public Response removeItemFromShop(String shopOwnerName, ItemFacade item, String shopName) {
        Response response;
        try {
             market.removeItemFromShop(shopOwnerName,item.getName(),shopName);
             market.removeItemFromShop(shopOwnerName,item.getName(),shopName);
             response = new Response();
        }
        catch (MarketException e)
        {
             response = new Response(e.getMessage());
        }
        return response;

    }


    public Response addItemToShop(String shopOwnerName,String name, double price,Item.Category category,String info,
                                  List<String> keywords, int amount, String shopName) {
        Response response;
        try {
            market.addItemToShop(shopOwnerName,name,price,category,info,keywords,amount,shopName);
            response = new Response();
        }
        catch (MarketException e){
            response = new Response(e.getMessage());
        }
        return response;

    }

    public Response setItemCurrentAmount(ItemFacade item,int amount, String shopName){
        return market.setItemCurrentAmount(item,amount,shopName);
    }
    // TODO need to check users are updated
    public Response changeShopItemInfo(String shopOwnerName, ItemFacade updatedItem, ItemFacade oldItem, String shopName) {
        return null;
    }

    public Response closeShop(String shopOwnerName, String shopName) throws MarketException {
         market.closeShop(shopOwnerName,shopName);
         return null;
    }

    public ResponseT<Integer> getItemCurrentAmount(ItemFacade item, String shopName){return null;}

    // TODO need to remove casting
    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(String shopManagerName, String shopName) {
        ResponseT<List<AppointmentFacade>> toReturn;
        try {
            List <Appointment> employees = market.getShopEmployeesInfo(shopManagerName, shopName);
            List <AppointmentFacade> employeesFacadeList = new ArrayList<>();
            for (Appointment a : employees){
                AppointmentFacade employeeFacade;
                if (a.isOwner())
                    employeeFacade = new ShopOwnerAppointmentFacade((ShopOwnerAppointment) a);
                else
                    employeeFacade = new ShopManagerAppointmentFacade((ShopManagerAppointment) a);
                employeesFacadeList.add(employeeFacade);
            }
            return new ResponseT<>(employeesFacadeList);
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }


    public ResponseT<String> getShopPurchaseHistory(String shopManagerName, String shopName) {
        return null;
    }


    public ResponseT<String> getAllSystemPurchaseHistory(String SystemManagerName) {
        return null;
    }


    public ResponseT<String> getHistoryByShop(String SystemManagerName, String shopName) {
        return null;
    }


    public ResponseT<String> getHistoryByMember(String SystemManagerName, String memberName) {
        return null;
    }

    public ResponseT<ShopFacade> getShopInfo(String member, String shopName) {
        ResponseT<ShopFacade> toReturn;
        try {
            Shop shop = market.getShopInfo(member, shopName);
            toReturn = new ResponseT<>(new ShopFacade(shop));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<ShoppingCartFacade> calculateShoppingCart(String visitorName) {
        ShoppingCartFacade cart =  market.calculateShoppingCart(visitorName);
        ResponseT <ShoppingCartFacade> responseT = new ResponseT<>(cart);
        return responseT;
    }
}
