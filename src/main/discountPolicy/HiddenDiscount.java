package main.discountPolicy;

import main.discountPolicy.DiscountPolicy;

public class HiddenDiscount implements DiscountPolicy {
    @Override
    public boolean itemExistInPolicy(String itemId) {
        return false;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
