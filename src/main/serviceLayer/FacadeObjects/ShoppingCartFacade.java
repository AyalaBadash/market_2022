package main.serviceLayer.FacadeObjects;

import main.businessLayer.Shop;
import main.businessLayer.ShoppingBasket;

import java.util.Map;

public class ShoppingCartFacade {
    Map<ShopFacade, ShoppingBasketFacade> cart; // <Shop ,basket for the shop>

    public ShoppingCartFacade(Map<ShopFacade, ShoppingBasketFacade> cart) {
        this.cart = cart;
    }

    public Map<ShopFacade, ShoppingBasketFacade> getCart() {
        return cart;
    }

    public void setCart(Map<ShopFacade, ShoppingBasketFacade> cart) {
        this.cart = cart;
    }
}
