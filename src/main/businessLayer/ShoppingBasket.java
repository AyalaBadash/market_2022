package main.businessLayer;

import java.util.HashMap;
import java.util.Map;

public class ShoppingBasket implements IHistory{
    Map<Item,Double> items;//<Item,quantity>
    public ShoppingBasket()
    {
        items= new HashMap<>();
    }


    @Override
    public String getReview() {
        StringBuilder review = new StringBuilder();
        for (Map.Entry<Item, Double> itemToAmount : items.entrySet()){
            Item item = itemToAmount.getKey();
            Double amount = itemToAmount.getValue();
            // TODO a price should be added as well as discounts
            review.append(String.format("%s X %f\n",item.getReview(),amount));
        }
        return review.toString();
    }

    public boolean isEmpty(){
        return this.items == null || this.items.isEmpty();
    }
}
