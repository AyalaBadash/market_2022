package main.discountPolicy;

public interface DiscountPolicy {
    boolean itemExistInPolicy(String itemId);
    String getInfo();
}
