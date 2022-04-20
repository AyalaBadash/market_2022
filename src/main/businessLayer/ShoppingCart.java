package main.businessLayer;

import java.util.Map;

public class ShoppingCart implements IHistory {
    Map<Shop,ShoppingBasket> cart; // <Shop ,basket for the shop>

    @Override
    public String getReview() {
        StringBuilder review = new StringBuilder();
        for (Map.Entry<Shop, ShoppingBasket> shopToBasket : cart.entrySet()){
            Shop shop = shopToBasket.getKey();
            ShoppingBasket basket = shopToBasket.getValue();
            if (basket.isEmpty()){
                continue;
            }
            review.append(String.format("Basket for shop %s:\n", shop.getShopName()));
            review.append(String.format("%s\n",basket.getReview()));
        }
        return review.toString();
    }
}
