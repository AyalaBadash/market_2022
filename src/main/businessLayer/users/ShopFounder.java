package main.businessLayer.users;

import main.businessLayer.Shop;

public class ShopFounder {
    private Member member;
    private Shop shop;

    public ShopFounder(Member member, Shop shop) {
        this.member = member;
        this.shop = shop;
    }

    public Member getMember() {
        return member;
    }

    public Shop getShop() {
        return shop;
    }
}
