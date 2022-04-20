package main.businessLayer.discountPolicy;

public class ApparentDiscount implements DiscountPolicy {
    @Override
    public boolean itemExistInPolicy(String itemId) {
        return false;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
