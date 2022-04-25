package main.businessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingCart implements IHistory {
    private Map<Shop, ShoppingBasket> cart; // <Shop ,basket for the shop>
    private double currentPrice;


    public ShoppingCart() {
        this.currentPrice = -1;
        this.cart = new ConcurrentHashMap<>();
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    // TODO need to append visitor name when called
    @Override
    public StringBuilder getReview() {
        StringBuilder review = new StringBuilder();
        for (Map.Entry<Shop, ShoppingBasket> shopToBasket : cart.entrySet()) {
            Shop shop = shopToBasket.getKey();
            ShoppingBasket basket = shopToBasket.getValue();
            if (basket.isEmpty()) {
                continue;
            }
            // TODO need to make sure all items in cart is bought
            review.append(String.format("Basket for %s:\n%s\n", shop.getShopName(), basket.getReview()));
            review.append(String.format("Overall Cart Price: %f", currentPrice));

        }
        return review;
    }


    public double getCurrentPrice() {
        this.calculate();
        return this.currentPrice;
    }

    public void cancelShopSave() {
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
    public double saveFromShops() throws MarketException {
        List<Shop> canceledShops = new ArrayList<>();
        List<Shop> succeedShops = new ArrayList<>();
        StringBuilder missing = new StringBuilder();
        double price = 0;
        // TODO need to think how to return missing items
        for (Map.Entry<Shop, ShoppingBasket> shopToBasket : cart.entrySet()) {
            try {
                price += shopToBasket.getKey().buyBasket(shopToBasket.getValue());
                succeedShops.add(shopToBasket.getKey());
            } catch (Exception e) {
                missing.append(e.getMessage());
                missing.append("\n");
                canceledShops.add(shopToBasket.getKey());
            }
        }
        if (!canceledShops.isEmpty()) {
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

    public void addItem(Shop shop, Item item, double amount) {
        ShoppingBasket shoppingBasket = cart.get ( shop );
        if (shoppingBasket == null){
            shoppingBasket = new ShoppingBasket ();
            cart.put ( shop, shoppingBasket );
        }
        shoppingBasket.addItem ( item, amount );
    }

    public void removeItem(Shop shop, Item item, int amount) {
        throw new UnsupportedOperationException();
    }

    public void calculate() {
        throw new UnsupportedOperationException();
    }

    public void editQuantity(int amount, Item itemFacade, String shopName) {
        throw new UnsupportedOperationException();
    }


    public int getItemQuantity(Item item) {
        throw new UnsupportedOperationException();
    }

    public Map<Shop, ShoppingBasket> getCart() {
        return cart;
    }
}
