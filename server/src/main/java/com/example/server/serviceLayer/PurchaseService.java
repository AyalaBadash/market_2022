package com.example.server.serviceLayer;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.PaymentMethod;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingCart;
import com.example.server.serviceLayer.FacadeObjects.ItemFacade;
import com.example.server.serviceLayer.FacadeObjects.ShoppingCartFacade;

public class PurchaseService {
    private Market market;
    private static PurchaseService purchaseService = null;

    private PurchaseService() {
        market = Market.getInstance();
    }

    public synchronized static PurchaseService getInstance() {
        if (purchaseService == null)
            purchaseService = new PurchaseService();
        return purchaseService;
    }

    public Response addItemToShoppingCart(ItemFacade itemToInsert, double amount, String shopName, String visitorName) {
        try {
            Item item = itemToInsert.toBusinessObject();
            market.addItemToShoppingCart(item, amount, shopName, visitorName);
            return new Response (  );
        } catch (MarketException e){
            return new Response ( e.getMessage () );
        }
    }

    public ResponseT<ShoppingCartFacade> showShoppingCart(String visitorName) {
        try {
            ShoppingCart shoppingCart = market.showShoppingCart ( visitorName );
            ShoppingCartFacade shoppingCartFacade = new ShoppingCartFacade ( shoppingCart );
            return new ResponseT<> ( shoppingCartFacade );
        }catch (MarketException e){
            return new ResponseT<> ( e.getMessage () );
        }
    }

    public Response editItemFromShoppingCart(double amount, ItemFacade itemFacade, String shopName, String visitorName) {
        try{
            Item item = new Item(itemFacade.getId(),itemFacade.getName(),itemFacade.getPrice(),itemFacade.getInfo(),itemFacade.getCategory(),itemFacade.getKeywords());
            market.editCart(amount, item, shopName, visitorName);
            return new Response();
        }catch (MarketException e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<ShoppingCartFacade> calculateShoppingCart(String visitorName) {
        try {
            ShoppingCartFacade cart =new ShoppingCartFacade(market.calculateShoppingCart(visitorName));
            ResponseT<ShoppingCartFacade> responseT = new ResponseT<>(cart);
            return responseT;
        }
        catch (MarketException e){
            return new ResponseT<>(e.getMessage());
        }
    }


    public Response buyShoppingCart(String visitorName, double expectedPrice,
                                    PaymentMethod paymentMethod, Address address) {
        try {
            this.market.buyShoppingCart(visitorName, expectedPrice, paymentMethod, address);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }
}
