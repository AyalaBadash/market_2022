package com.example.server.serviceLayer;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.PaymentMethod;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.serviceLayer.FacadeObjects.ItemFacade;
import com.example.server.serviceLayer.FacadeObjects.Response;
import com.example.server.serviceLayer.FacadeObjects.ResponseT;
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
            market.addItemToShoppingCart(itemToInsert, amount, shopName, visitorName);
            return new Response (  );
        } catch (MarketException e){
            return new Response ( e.getMessage () );
        }
    }

    // TODO implement
    public ResponseT<ShoppingCartFacade> showShoppingCart(String visitorName) {
        return null;

    }

    public Response editItemFromShoppingCart(int amount, ItemFacade itemFacade, String shopName, String visitorName) {
        try{
            market.editCart(amount, itemFacade, shopName, visitorName);
            return new Response();
        }catch (MarketException e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<ShoppingCartFacade> calculateShoppingCart(String visitorName) {
        ShoppingCartFacade cart =  market.calculateShoppingCart(visitorName);
        ResponseT <ShoppingCartFacade> responseT = new ResponseT<>(cart);
        return responseT;
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
