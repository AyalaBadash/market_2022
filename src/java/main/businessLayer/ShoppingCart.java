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

    // TODO need to append visitor name when called
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
            review.append(String.format("Basket for %s:\n%s\n",shop.getShopName() , basket.getReview()));
            review.append(String.format("Overall Cart Price: %f", currentPrice));

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

    public void editQuantity(int amount, Item itemFacade, String shopName){throw new UnsupportedOperationException();}


    public int getItemQuantity(Item item) {throw new UnsupportedOperationException();}
}
