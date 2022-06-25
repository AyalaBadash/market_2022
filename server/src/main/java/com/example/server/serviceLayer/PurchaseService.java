package com.example.server.serviceLayer;

import com.example.server.businessLayer.Market.ResourcesObjects.ErrorLog;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.Users.Visitor;
import com.example.server.businessLayer.Payment.PaymentMethod;
import com.example.server.businessLayer.Supply.Address;
import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ShoppingCart;
//import com.example.server.dataLayer.repositories.VisitorRep;
import com.example.server.serviceLayer.FacadeObjects.ItemFacade;
import com.example.server.serviceLayer.FacadeObjects.ShoppingCartFacade;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

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

    @Transactional(rollbackOn = Exception.class)
    public Response addItemToShoppingCart(ItemFacade itemToInsert, double amount, String visitorName) {
        try {
            Item item = itemToInsert.toBusinessObject();
            market.addItemToShoppingCart(item, amount, visitorName);
            return new Response (  );
        } catch (Exception e){
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response ( e.getMessage () );
        }
    }

    public ResponseT<ShoppingCartFacade> showShoppingCart(String visitorName) {
        try {
            ShoppingCart shoppingCart = market.showShoppingCart ( visitorName );
            ShoppingCartFacade shoppingCartFacade = new ShoppingCartFacade ( shoppingCart );
            return new ResponseT<> ( shoppingCartFacade );
        }catch (Exception e){
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<> ( e.getMessage () );
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public Response editItemFromShoppingCart(double amount, ItemFacade itemFacade, String shopName, String visitorName) {
        try{
            Item item = new Item(itemFacade.getId(),itemFacade.getName(),itemFacade.getPrice(),itemFacade.getInfo(),itemFacade.getCategory(),itemFacade.getKeywords());
            market.editCart(amount, item, shopName, visitorName);
            return new Response();
        }catch (Exception e){
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public ResponseT<ShoppingCartFacade> calculateShoppingCart(String visitorName) {
        try {
            ShoppingCartFacade cart =new ShoppingCartFacade(market.calculateShoppingCart(visitorName));
            ResponseT<ShoppingCartFacade> responseT = new ResponseT<>(cart);
            return responseT;
        }
        catch (Exception e){
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<>(e.getMessage());
        }
    }


    @Transactional(rollbackOn = MarketException.class)
    public ResponseT<ShoppingCartFacade> buyShoppingCart(String visitorName, double expectedPrice,
                                    PaymentMethod paymentMethod, Address address){
        try {
            ShoppingCart shoppingCart = this.market.buyShoppingCart(visitorName, expectedPrice, paymentMethod, address);
            //TODO fix condition in if
            if(shoppingCart != null)
            // null if items some items didn't found
            if(shoppingCart != null && !shoppingCart.isEmpty ())
                return new ResponseT<>("some of the items in the cart are missing. car was updated and the price was changed", new ShoppingCartFacade(shoppingCart));
            return new ResponseT<>(new ShoppingCartFacade(new ShoppingCart()));
        }catch (MarketException e){
            String message =e.getMessage();
            if(e.getMessage().equals("Error0")){
                message= "There is a problem with the supply service. Please try again later.";
            }
            if(e.getMessage().equals("Error1")){
                message= "There is a problem with setting the supply. Please try again later.";
            }
            if(e.getMessage().equals("Error2")){
                message= "There is a problem with the payment service. Please try again later.";
            }
            if(e.getMessage().equals("Error3")){
                message= "There is a problem with setting the payment. Please try again later.";
            }
            return new ResponseT(message);
        }catch (Exception e){
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT(e.getMessage());
        }
    }
}
