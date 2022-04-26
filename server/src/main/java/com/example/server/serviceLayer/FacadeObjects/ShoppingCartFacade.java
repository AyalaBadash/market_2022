package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.ShoppingBasket;
import com.example.server.businessLayer.ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartFacade implements FacadeObject<ShoppingCart> {
    Map<ShopFacade, ShoppingBasketFacade> cart; // <Shop ,basket for the shop>
    double price;

    public ShoppingCartFacade(Map<ShopFacade, ShoppingBasketFacade> cart,double price) {
        this.cart = cart;
        this.price = price;
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
    public ShoppingCart toBusinessObject() throws MarketException {
        Map<Shop,ShoppingBasket> baskets = new HashMap<>();
        for (Map.Entry<ShopFacade,ShoppingBasketFacade> entry:this.cart.entrySet())
        {
            baskets.put(entry.getKey().toBusinessObject(),entry.getValue().toBusinessObject());
        }
        return new ShoppingCart(baskets,this.price);

    }
}