package main.serviceLayer;

import main.businessLayer.Item;
import main.businessLayer.ExternalServices.PaymentService;
import main.businessLayer.ExternalServices.ProductsSupplyService;
import main.businessLayer.Market;
import main.businessLayer.MarketException;
import main.serviceLayer.FacadeObjects.*;

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
        try{
            market.openNewShop(visitorName, shopName);
            return new Response (  );
        }catch (MarketException marketException){
            return new Response ( marketException.getMessage () );
        }
    }

    public Response updateShopItemAmount(String shopOwnerName, ItemFacade item, int amount, String shopName) {
        return null;
    }

    // TODO need to check users are updated
    public Response removeItemFromShop(String shopOwnerName, ItemFacade item, String shopName) {
        return null;
    }


    public Response addItemToShop(String shopOwnerName, ItemFacade item, int amount, String shopName) {
        return null;
    }

    public Response setItemCurrentAmount(ItemFacade item,int amount, String shopName){
        return null;
    }
    // TODO need to check users are updated
    public Response changeShopItemInfo(String shopOwnerName, ItemFacade updatedItem, ItemFacade oldItem, String shopName) {
        return null;
    }

    public Response closeShop(String shopOwnerName, String shopName) {
        return null;
    }

    public ResponseT<Integer> getItemCurrentAmount(ItemFacade item, String shopName){return null;}

    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(String shopManagerName, String shopName) {
        return null;
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
}
