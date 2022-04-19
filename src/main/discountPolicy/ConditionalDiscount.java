package main.discountPolicy;

public class ConditionalDiscount implements DiscountPolicy {
    Condition cond;

    @Override
    public boolean itemExistInPolicy(String itemId) {
        return false;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
