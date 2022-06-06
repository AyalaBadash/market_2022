package com.example.server.businessLayer.Market;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Publisher.Publisher;
import com.example.server.dataLayer.entities.DalShop;
import com.example.server.dataLayer.entities.DalShoppingBasket;
import com.example.server.dataLayer.entities.DalShoppingCart;
import com.example.server.businessLayer.Publisher.NotificationHandler;
import com.example.server.dataLayer.repositories.ShoppingBasketRepository;
import com.example.server.dataLayer.repositories.ShoppingCartRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingCart implements IHistory {

    private Map<Shop, ShoppingBasket> cart; // <Shop ,basket for the shop>
    private double currentPrice;

    private static ShoppingCartRepository shoppingCartRepository;

    private static ShoppingBasketRepository shoppingBasketRepository;


    public ShoppingCart() {
        this.currentPrice = 0;
        this.cart = new ConcurrentHashMap<>();
    }
    public ShoppingCart(Map<Shop,ShoppingBasket> cart , double currentPrice){
        this.cart = cart;
        this.currentPrice = currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }


    @Override
    public StringBuilder getReview() {
        StringBuilder review = new StringBuilder();
        for (Map.Entry<Shop, ShoppingBasket> shopToBasket : cart.entrySet()) {
            Shop shop = shopToBasket.getKey();
            ShoppingBasket basket = shopToBasket.getValue();
            if (basket.isEmpty()) {
                continue;
            }
            review.append(String.format("Basket for %s:\n%s\n", shop.getShopName(), basket.getReview()));
            review.append(String.format("Overall Cart Price: %f", currentPrice));

        }
        return review;
    }


    public double getCurrentPrice() {
        this.calculate();
        return this.currentPrice;
    }

    public void cancelShopSave() throws MarketException {
        for (Map.Entry<Shop, ShoppingBasket> shopToBasket : cart.entrySet()) {
            shopToBasket.getKey().releaseItems(shopToBasket.getValue());
        }
    }

    /**
     * this method save all items in shops
     *
     * @throws MarketException if one or more shops cannot supply all items -> returns to original state
     *                         return all items to shops, MarketException message include missing items
     */

    public synchronized double saveFromShops(NotificationHandler publisher, String buyer) throws MarketException {
        boolean succeeded = true;
        List<Shop> succeedShops = new ArrayList<>();
        StringBuilder missing = new StringBuilder();
        double price = 0;
        for (Map.Entry<Shop, ShoppingBasket> shopToBasket : cart.entrySet()) {
            try {
                price += shopToBasket.getKey().buyBasket(publisher,shopToBasket.getValue(),buyer);
                succeedShops.add(shopToBasket.getKey());
            } catch (MarketException e) {
                succeeded = false;
                shopToBasket.getKey ().validateBasket ( shopToBasket.getValue () );
                missing.append(e.getMessage());
                missing.append("\n");
            }
        }
        if (!succeeded) {
            for (Shop shop : succeedShops) {
                shop.releaseItems(cart.get(shop));
            }
            throw new MarketException(missing.toString());
        }
        return price;
    }

    public void clear() {
        this.cart.clear();
    }

    public void addItem(Shop shop, Item item, double amount) throws MarketException {
        ShoppingBasket shoppingBasket = cart.get ( shop );
        if (shoppingBasket == null){
            shoppingBasket = new ShoppingBasket ();
            cart.put ( shop, shoppingBasket );
        }
        shoppingBasket.addItem ( item, amount );
        DalShoppingCart dsc = this.toDalObject();
        shoppingCartRepository.save(dsc);
        shoppingBasketRepository.save(shoppingBasket.toDalObject());
    }

    public void removeItem(Shop shop, Item item) throws MarketException {
        ShoppingBasket shoppingBasket = cart.get ( shop );
        if(shoppingBasket == null)
            return;
        shoppingBasket.removeItem ( item);
    }

    public void calculate() {
        double price = 0;
        for(ShoppingBasket shoppingBasket : cart.values ())
            price += shoppingBasket.getPrice ();
        currentPrice = price;
    }

    public void editQuantity(double amount, Item item, String shopName) throws MarketException {
        ShoppingBasket basket=null;
        for (Map.Entry<Shop, ShoppingBasket> bask: cart.entrySet()){
            if(bask.getKey().getShopName().equals(shopName)){
                basket=bask.getValue();
                break;
            }
        }
        if(basket==null){
            throw new MarketException("The basket does not exist in the cart.");
        }
        basket.updateQuantity(amount, item);
    }
    public DalShoppingCart toDalObject(){
        Map<DalShop, DalShoppingBasket> baskets = new HashMap<>();
        for (Map.Entry<Shop,ShoppingBasket> entry:this.cart.entrySet()){
            baskets.put(entry.getKey().toDalObject(),entry.getValue().toDalObject());
        }
        return new DalShoppingCart(baskets,this.currentPrice);
    }


    public double getItemQuantity(Item item) {
        for (Map.Entry<Shop,ShoppingBasket> entry: cart.entrySet())
        {
            if (entry.getValue().getItems().containsKey(item.getID()))
                return entry.getValue().getItems().get(item.getID());
        }
        return 0;
    }

    public Map<Shop, ShoppingBasket> getCart() {
        return cart;
    }

    public void setCart(Map<Shop, ShoppingBasket> cart) {
        this.cart = cart;
    }


    public boolean isEmpty() {
        if (cart.isEmpty())
            return true;
        for(Map.Entry<Shop, ShoppingBasket> basket : cart.entrySet()){
            if(basket.getValue().isEmpty()){
                return true;
            }
        }
        return false;
    }

    public static void setShoppingCartRepository(ShoppingCartRepository scRepository){
        shoppingCartRepository = scRepository;
    }

    public static void setShoppingBasketRepository(ShoppingBasketRepository sbRepository){
        shoppingBasketRepository = sbRepository;
    }
}
