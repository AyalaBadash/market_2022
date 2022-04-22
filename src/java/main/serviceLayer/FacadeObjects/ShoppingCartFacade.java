package main.serviceLayer.FacadeObjects;

import main.businessLayer.ShoppingCart;

import java.util.Map;

public class ShoppingCartFacade implements FacadeObject<ShoppingCart> {
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

    @Override
    public ShoppingCart toBusinessObject() {
        return null;
    }
}
