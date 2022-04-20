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
            review.append(basket.getReview());
            // TODO need to see if can prevent calculating twice
            review.append()
//            review.append(String.format("Basket for shop %s:\n", shop.getShopName()));
            review.append(String.format("%s\n",basket.getReview()));
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
