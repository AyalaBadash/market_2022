package main.serviceLayer.FacadeObjects;

import main.businessLayer.Shop;
import main.businessLayer.ShoppingBasket;
import main.businessLayer.ShoppingCart;
import org.springframework.jdbc.object.StoredProcedure;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartFacade implements FacadeObject<ShoppingCart> {
    Map<ShopFacade, ShoppingBasketFacade> cart; // <Shop ,basket for the shop>

    public ShoppingCartFacade(Map<ShopFacade, ShoppingBasketFacade> cart) {
        this.cart = cart;
    }

    public ShoppingCartFacade(ShoppingCart cart) {
        this.cart = new HashMap<>();
        for (Map.Entry<Shop, ShoppingBasket> fromCart: cart.getCart().entrySet()){
            ShopFacade toShop = new ShopFacade(fromCart.getKey());
            ShoppingBasketFacade toBasket = new ShoppingBasketFacade(fromCart.getValue());
            this.cart.put(toShop,toBasket);
        }

    }

    public Map<ShopFacade, ShoppingBasketFacade> getCart() {
        return cart;
    }

    public void setCart(Map<ShopFacade, ShoppingBasketFacade> cart) {
        this.cart = cart;
    }

    @Override
    public ShoppingCart toBusinessObject() {
        return null;
    }
}
