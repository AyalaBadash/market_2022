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
    private Market2 market2;

    private MarketService() {
        market2 = Market2.getInstance();
    }

    public synchronized static MarketService getInstance() {
        if (marketService == null){
            marketService = new MarketService();
        }
        return marketService;
    }

    public Response firstInitMarket(PaymentService paymentService, ProductsSupplyService supplyService, String userName, String password) {
        try {
            market2.firstInitMarket ( paymentService, supplyService, userName, password );
            return new Response (  );
        } catch (MarketException e){
            return new Response ( e.getMessage () );
        }
    }


    // TODO returns only open shops
    // TODO implement
    /**
     *
     * @return only open shops
     */
    public ResponseT<List<ShopFacade>> getAllShops() {
        return null;
    }
    // TODO implement
    public ResponseT<List<ItemFacade>> getAllItemsByShop(ShopFacade shop) {
        return null;
    }

    public ResponseT<List<ItemFacade>> searchProductByName(String name) {
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> items = market2.getItemByName(name);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : items){
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<ItemFacade>> searchProductByCategory(Item.Category category) {
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> items = market2.getItemByCategory(category);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : items){
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<ItemFacade>> searchProductByKeyword(String keyWord) {
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> items = market2.getItemsByKeyword(keyWord);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : items){
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<ItemFacade>> filterItemByPrice(List<ItemFacade> items, int minPrice, int maxPrice) {
        List<Item> businessItems = new ArrayList<>();
        for (ItemFacade item : items)
            businessItems.add(item.toBusinessObject());
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> filteredItems = market2.filterItemsByPrice(businessItems, minPrice, maxPrice);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : filteredItems){
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<ItemFacade>> filterItemByCategory(List<ItemFacade> items, Item.Category category) {
        List<Item> businessItems = new ArrayList<>();
        for (ItemFacade item : items)
            businessItems.add(item.toBusinessObject());
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> filteredItems = market2.filterItemsByCategory(businessItems, category);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : filteredItems){
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public Response openNewShop(String visitorName, String shopName) {
        try{
            market2.openNewShop(visitorName, shopName);
            return new Response (  );
        }catch (MarketException marketException){
            return new Response ( marketException.getMessage () );
        }
    }
    // TODO implement
    public Response updateShopItemAmount(String shopOwnerName, ItemFacade item, int amount, String shopName) {
        return null;
    }

    // TODO need to check users are updated
    public Response removeItemFromShop(String shopOwnerName, ItemFacade item, String shopName) {
        Response response;
        try {
             market2.removeItemFromShop(shopOwnerName,item.getName(),shopName);
             market2.removeItemFromShop(shopOwnerName,item.getName(),shopName);
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
            market2.addItemToShop(shopOwnerName,name,price,category,info,keywords,amount,shopName);
            response = new Response();
        }
        catch (MarketException e){
            response = new Response(e.getMessage());
        }
        return response;

    }

    public Response setItemCurrentAmount(String shopOwnerName,ItemFacade item, double amount, String shopName){
        try{
            market2.setItemCurrentAmount(shopOwnerName, item,amount,shopName);
            return new Response (  );
        }catch (MarketException e){
            return new Response ( e.getMessage () );
        }
    }
    // TODO need to check users are updated
    // TODO implement
    public Response changeShopItemInfo(String shopOwnerName, ItemFacade updatedItem, ItemFacade oldItem, String shopName) {
        try{
            market2.changeShopItemInfo(shopOwnerName, updatedItem, oldItem, shopName);
            return new Response (  );
        }catch (MarketException e){
            return new Response ( e.getMessage () );
        }
    }

    public Response closeShop(String shopOwnerName, String shopName) {
        Response response;
        try {
            market2.closeShop(shopOwnerName,shopName);
            response = new Response();
        }
        catch (MarketException e)
        {
            response = new Response(e.getMessage());
        }
         return response;
    }
    // TODO implement
    public ResponseT<Integer> getItemCurrentAmount(ItemFacade item, String shopName){return null;}

    // TODO need to remove casting
    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(String shopManagerName, String shopName) {
        ResponseT<List<AppointmentFacade>> toReturn;
        try {
            List <Appointment> employees = market2.getShopEmployeesInfo(shopManagerName, shopName).values ().stream( ).toList ();
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

    /**
     * relevant to shop manager
     * @param shopManagerName
     * @param shopName
     * @return
     */
    public ResponseT<String> getShopPurchaseHistory(String shopManagerName, String shopName) {
        try {
            String history = market2.getShopPurchaseHistory(shopManagerName, shopName).toString ();
            return new ResponseT<> ( history );
        } catch (MarketException e){
            return new ResponseT<> ( e.getMessage () );
        }
    }


    /**
     * relevant to system manager
     * @param systemManagerName
     * @return
     */
    public ResponseT<String> getAllSystemPurchaseHistory(String systemManagerName) {
        try {
            String history = market2.getAllSystemPurchaseHistory (systemManagerName).toString ();
            return new ResponseT<> ( history );
        }catch (MarketException e){
            return new ResponseT<> ( e.getMessage () );
        }
    }


    /**
     * relevant to system manager
     * @param systemManagerName
     * @param shopName
     * @return
     */
    public ResponseT<String> getHistoryByShop(String systemManagerName, String shopName) {
        try {
            String history = market2.getHistoryByShop ( systemManagerName, shopName ).toString ();
            return new ResponseT<> ( history );
        } catch (MarketException e) {
           return new ResponseT<> ( e.getMessage () ) ;
        }
    }


    public ResponseT<String> getHistoryByMember(String systemManagerName, String memberName) {
        try {
            String history = market2.getHistoryByMember ( systemManagerName, memberName ).toString ();
            return new ResponseT<> ( history );
        } catch (MarketException e){
            return new ResponseT<> ( e.getMessage () );
        }
    }

    public ResponseT<ShopFacade> getShopInfo(String member, String shopName) {
        ResponseT<ShopFacade> toReturn;
        try {
            Shop shop = market2.getShopInfo(member, shopName);
            toReturn = new ResponseT<>(new ShopFacade(shop));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<ShoppingCartFacade> calculateShoppingCart(String visitorName) {
        ShoppingCartFacade cart =  market2.calculateShoppingCart(visitorName);
        ResponseT <ShoppingCartFacade> responseT = new ResponseT<>(cart);
        return responseT;
    }
}
