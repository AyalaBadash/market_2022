package main.businessLayer;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements IHistory {
    private Map<Shop,ShoppingBasket> cart; // <Shop ,basket for the shop>
    private double currentPrice;


    public ShoppingCart(){
        this.currentPrice = -1;
        this.cart = new HashMap<>();
    }

    @Override
    public String getReview() {
        StringBuilder review = new StringBuilder();
        for (Map.Entry<Shop, ShoppingBasket> shopToBasket : cart.entrySet()){
            Shop shop = shopToBasket.getKey();
            ShoppingBasket basket = shopToBasket.getValue();
            if (basket.isEmpty()){
                continue;
            }
            // TODO implement
//            review.append(String.format("Basket for shop %s:\n", shop.getShopName()));
            review.append(String.format("%s\n",basket.getReview()));
        }
        return review.toString();
    }

    public void addItem(Shop shop , Item item, int amount){
        throw new UnsupportedOperationException();
    }
    public void removeItem(Shop shop , Item item, int amount){
        throw new UnsupportedOperationException();
    }

    public void calculate(){
        throw new UnsupportedOperationException();
    }




}
