package main.businessLayer;

import main.IHistory;

import java.util.Map;

public class ShoppingCart implements IHistory {
    Map<Shop,ShoppingBasket> cart; // <Shop ,basket for the shop>

    @Override
    public StringBuilder getReview() {
        StringBuilder review = new StringBuilder();
        for (Map.Entry<Shop, ShoppingBasket> shopToBasket : cart.entrySet()){
            Shop shop = shopToBasket.getKey();
            ShoppingBasket basket = shopToBasket.getValue();
            if (basket.isEmpty()){
                continue;
            }
            // TODO need to make sure all items in cart is bought
            review.append(String.format("Basket for %s:\n%s",shop.getShopName() , basket.getReview()));
            review.append(String.format("Overall Cart Price: %f", calculate());

        }
        return review;
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
