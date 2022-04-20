package main.serviceLayer;

import main.businessLayer.Item;
import main.businessLayer.services.PaymentService;
import main.businessLayer.services.ProductsSupplyService;
import main.serviceLayer.FacadeObjects.*;

import java.util.List;

public class MarketService {
    private static MarketService marketService = null;
    private MarketService(){
    }
    public synchronized static MarketService getInstance(){
        if (marketService == null)
            marketService = new MarketService ();
        return marketService;
    }

    public Response initMarket(PaymentService paymentService, ProductsSupplyService supplyService, String userName, String password) {
        return null;
    }

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

    public ResponseT<List<ItemFacade>> filterItemByItemRank(int minItemRank) {
        return null;
    }

    public ResponseT<List<ItemFacade>> filterItemByShopRank(int minShopRank) {
        return null;
    }

    public ResponseT<List<ItemFacade>> filterItemByCategory(Item.Category category) {
        return null;
    }

    public Response openNewShop(String visitorName, String shopName) {
        return null;
    }

    public Response updateShopItemAmount(String shopOwnerName, ItemFacade item, int amount, String shopName) {
        return null;
    }


    public Response removeItemFromShop(String shopOwnerName, ItemFacade item, String shopName) {
        return null;
    }


    public Response addItemToShop(String shopOwnerName, ItemFacade item, int amount, String shopName) {
        return null;
    }


    public Response changeShopItemInfo(String shopOwnerName, ItemFacade updatedItem, ItemFacade oldItem, String shopName) {
        return null;
    }

    public Response closeShop(String shopOwnerName, String shopName) {
        return null;
    }


    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(String shopManagerName, String shopName) {
        return null;
    }


    public ResponseT<String> getShopPurchaseHistory(String shopManagerName, String shopName) {
        return null;
    }


    public ResponseT<String> getAllSystemPurchaseHistory() {
        return null;
    }


    public ResponseT<String> getHistoryByShop() {
        return null;
    }


    public ResponseT<String> getHistoryByMember() {
        return null;
    }
}
