package main;

import java.util.List;
import java.util.Map;

public class Shop {
    String ShopID;
    Map<String,Item> itemMap; //<ItemID,main.Item>
    List<String> employees;
    PurchasePolicy purchasePolicy;
    DiscountPolicy discountPolicy;
    ProductsSupplyService supplyService;
    PaymentService paymentService;

    //use case - receive info of a shop
    public String receiveInfo(){throw new UnsupportedOperationException();} // TODO - check if returned value is indeed String

    //use case - Stock management
    public void editItem(Item item){throw new UnsupportedOperationException();};
    public void deleteItem(Item item){throw new UnsupportedOperationException();}
    public void addItem(Item item){throw new UnsupportedOperationException();}

    //use case - Change shop's policy
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
        this.purchasePolicy = purchasePolicy;
    }
}
