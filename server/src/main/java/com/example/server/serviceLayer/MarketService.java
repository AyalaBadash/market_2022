package com.example.server.serviceLayer;


import com.example.server.ResourcesObjects.ErrorLog;
import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.ExternalComponents.PaymentService;
import com.example.server.businessLayer.ExternalComponents.ProductsSupplyService;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Market;
import com.example.server.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Shop;
import com.example.server.serviceLayer.FacadeObjects.*;


import java.util.ArrayList;
import java.util.List;

public class MarketService {
    private static MarketService marketService = null;
    private Market market;

    private MarketService() {
        market = Market.getInstance();
    }

    public synchronized static MarketService getInstance() {
        if (marketService == null){
            marketService = new MarketService();
        }
        return marketService;
    }

    public Response firstInitMarket(PaymentService paymentService, ProductsSupplyService supplyService, String userName, String password) {
        try {
            market.firstInitMarket ( paymentService, supplyService, userName, password );
            return new Response (  );
        } catch (MarketException e){
            return new Response ( e.getMessage () );
        }
        catch (Exception e)
        {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public ResponseT<List<ItemFacade>> searchProductByName(String name) {
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> items = market.getItemByName(name);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : items){
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }


        return toReturn;
    }

    public ResponseT<List<ItemFacade>> searchProductByCategory(Item.Category category) {
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> items = market.getItemByCategory(category);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : items){
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<ItemFacade>> searchProductByKeyword(String keyWord) {
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> items = market.getItemsByKeyword(keyWord);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : items){
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<ItemFacade>> filterItemByPrice(List<ItemFacade> items, double minPrice, double maxPrice) {
        List<Item> businessItems = new ArrayList<>();
        for (ItemFacade item : items) {
            try {
                businessItems.add(item.toBusinessObject());
            } catch (MarketException e) {
                return new ResponseT<>(e.getMessage());

            }
        }
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> filteredItems = market.filterItemsByPrice(businessItems, minPrice, maxPrice);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : filteredItems){
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<ItemFacade>> filterItemByCategory(List<ItemFacade> items, Item.Category category) {
        List<Item> businessItems = new ArrayList<>();
        for (ItemFacade item : items) {
            try {
                businessItems.add(item.toBusinessObject());
            } catch (MarketException e) {
                return new ResponseT<>(e.getMessage());
            }
        }
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> filteredItems = market.filterItemsByCategory(businessItems, category);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : filteredItems){
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public Response openNewShop(String visitorName, String shopName) {
        try{
            market.openNewShop(visitorName, shopName);
            return new Response (  );
        }catch (MarketException marketException){
            return new Response ( marketException.getMessage () );
        }
        catch (Exception e)
        {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public Response updateShopItemAmount(String shopOwnerName, ItemFacade item, double amount, String shopName) {
        try{
            Item itemBL = new Item(item.getId(),item.getName(),item.getPrice(),item.getInfo(),item.getCategory(),item.getKeywords());
            market.setItemCurrentAmount ( shopOwnerName,  itemBL, amount, shopName);
            return new Response (  );
        } catch (MarketException e){
            return new Response ( e.getMessage () );
        }

        catch (Exception e)
        {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }


    public Response removeItemFromShop(String shopOwnerName, ItemFacade item, String shopName) {
        try {
            market.removeItemFromShop(shopOwnerName,item.getId(),shopName);
            return new Response();
        }
        catch (MarketException e)
        {
            return new Response(e.getMessage());
        }

        catch (Exception e)
        {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }


    public ResponseT<ShopFacade> addItemToShop(String shopOwnerName, String name, double price, Item.Category category, String info,
                                               List<String> keywords, double amount, String shopName) {
        ResponseT<ShopFacade> response;
        try {
            Shop shop = market.addItemToShop(shopOwnerName,name,price,category,info,keywords,amount,shopName);
            response = new ResponseT(new ShopFacade(shop));
        }
        catch (MarketException e){
            response = new ResponseT(e.getMessage());
        }

        catch (Exception e)
        {
            ErrorLog.getInstance().Log(e.getMessage());
            response = new ResponseT<>(e.getMessage());
        }
        return response;

    }

    public Response setItemCurrentAmount(String shopOwnerName,ItemFacade item, double amount, String shopName){
        try{
            Item itemBL = new Item(item.getId(),item.getName(),item.getPrice(),item.getInfo(),item.getCategory(),item.getKeywords());
            market.setItemCurrentAmount(shopOwnerName, itemBL ,amount,shopName);
            return new Response (  );
        }catch (MarketException e){
            return new Response ( e.getMessage () );
        }

        catch (Exception e)
        {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public Response changeShopItemInfo(String shopOwnerName, String info, ItemFacade oldItem, String shopName) {
        try{
            market.changeShopItemInfo(shopOwnerName, info, oldItem.getId(), shopName);
            return new Response (  );
        }catch (MarketException e){
            return new Response ( e.getMessage () );
        }

        catch (Exception e)
        {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public Response editItem(ItemFacade newItem, String id){
        try{
            market.editItem(newItem.toBusinessObject (), id);
            return new Response (  );
        } catch (MarketException e){
            return new Response ( e.getMessage () );
        }

        catch (Exception e)
        {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public Response closeShop(String shopOwnerName, String shopName) {
        Response response;
        try {
            market.closeShop(shopOwnerName,shopName);
            response = new Response();
        }
        catch (MarketException e)
        {
            response = new Response(e.getMessage());
        }
        catch (Exception e)
        {
            ErrorLog.getInstance().Log(e.getMessage());
            response= new Response(e.getMessage());
        }
        return response;
    }

    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(String shopManagerName, String shopName) {
        ResponseT<List<AppointmentFacade>> toReturn;
        try {
            List <Appointment> employees = market.getShopEmployeesInfo(shopManagerName, shopName).values ().stream( ).toList ();
            List <AppointmentFacade> employeesFacadeList = new ArrayList<>();
            for (Appointment appointment : employees){
                AppointmentFacade employeeFacade;
                if (appointment.isOwner()) {
                    //employeeFacade = new ShopOwnerAppointmentFacade((ShopOwnerAppointment) appointment);
                    employeeFacade = new ShopOwnerAppointmentFacade ( );
                    employeeFacade = employeeFacade.toFacade ( appointment );
                }
                else {
                    employeeFacade = new ShopManagerAppointmentFacade ( );
                    employeeFacade = employeeFacade.toFacade ( appointment );
                }
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
            String history = market.getShopPurchaseHistory(shopManagerName, shopName).toString ();
            return new ResponseT<> ( history );
        } catch (Exception e){
            ErrorLog.getInstance().Log(e.getMessage());
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
            String history = market.getAllSystemPurchaseHistory (systemManagerName).toString ();
            return new ResponseT<> ( history );
        }catch (Exception e){
            ErrorLog.getInstance().Log(e.getMessage());
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
            String history = market.getHistoryByShop ( systemManagerName, shopName ).toString ();
            return new ResponseT<> ( history );
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<> ( e.getMessage () ) ;
        }
    }


    public ResponseT<String> getHistoryByMember(String systemManagerName, String memberName) {
        try {
            String history = market.getHistoryByMember ( systemManagerName, memberName ).toString ();
            return new ResponseT<> ( history );
        } catch (Exception e){
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<> ( e.getMessage () );
        }
    }

    public ResponseT<ShopFacade> getShopInfo(String member, String shopName) {
        ResponseT<ShopFacade> toReturn;
        try {
            Shop shop = market.getShopInfo(member, shopName);
            toReturn = new ResponseT<>(new ShopFacade(shop));
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public Response removeShopOwnerAppointment(String boss, String firedAppointed, String shopName){
        Response response;
        try{
            market.removeShopOwnerAppointment(boss,firedAppointed,shopName);
            response = new Response();
        }
        catch (Exception e)
        {
            ErrorLog.getInstance().Log(e.getMessage());
            response = new Response(e.getMessage());
        }
        return response;

    }

    public Response removeMember(String manager, String memberToRemove) {
        Response response;
        try
        {
            market.removeMember(manager,memberToRemove);
            response = new Response();
        }
        catch (MarketException e)
        {
            ErrorLog.getInstance().Log(e.getMessage());
            response = new Response(e.getMessage());
        }
        return response;
    }

    public ResponseT<ItemFacade> getItemInfo(String name, int itemId) {
        try {
            Item item = market.getItemById(name, itemId);
            return new ResponseT<>(new ItemFacade(item));
        } catch (Exception e){
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<ItemFacade> getItemById(int id) {
        try {
            Item item = market.getItemByID(id);
            return new ResponseT<>(new ItemFacade(item));
        }catch (Exception e){
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<String> getMarketInfo(String sysManager) {
        try{
            return new ResponseT<>(market.getMarketInfo(sysManager));
        }
        catch (Exception e){
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<>(e.getMessage());
        }
    }
}
