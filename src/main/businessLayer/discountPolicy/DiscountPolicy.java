package main.businessLayer.discountPolicy;

public interface DiscountPolicy {
    boolean itemExistInPolicy(String itemId);
    String getInfo();
}
