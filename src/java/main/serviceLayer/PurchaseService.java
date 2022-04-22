package main.serviceLayer;

import main.businessLayer.Market;
import main.serviceLayer.FacadeObjects.ItemFacade;
import main.serviceLayer.FacadeObjects.Response;
import main.serviceLayer.FacadeObjects.ResponseT;
import main.serviceLayer.FacadeObjects.ShoppingCartFacade;
import main.resources.Address;
import main.resources.PaymentMethod;

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

    public Response addItemToShoppingCart(ItemFacade itemToInsert, int amount, String shopName, String visitorName) {
        return null;
    }


    public ResponseT<ShoppingCartFacade> showShoppingCart(String visitorName) {
        return null;
    }


    public Response editItemFromShoppingCart(int amount, ItemFacade itemFacade, String shopName, String visitorName) {
        return null;
    }


    public ResponseT<ShoppingCartFacade> calculateShoppingCart(String visitorName) {
        return null;
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
