package com.example.server.UnitTests;

import com.example.server.businessLayer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;

import static org.mockito.Mockito.CALLS_REAL_METHODS;

public class ClosedShopHistoryTest extends mainTest {

    @Mock
    Shop shop;
    Item item ;
    ShoppingCart shoppingCart;
    ShoppingBasket shoppingBasket;
    HashMap<Item, Double> items;
    HashMap<Shop,ShoppingBasket> cart;
    String itemName;
    String shopName;
    ClosedShopsHistory closedShopsHistory =   ClosedShopsHistory.getInstance();

    @BeforeEach
    public void historyUnitTestInit(){
        closedShopsHistory.getClosedShops().clear();
        closedShopsHistory.setOverallHistory(new StringBuilder());
        shoppingBasket = Mockito.mock(ShoppingBasket.class, CALLS_REAL_METHODS);
        item = Mockito.mock(Item.class, CALLS_REAL_METHODS);
        shoppingCart = Mockito.mock(ShoppingCart.class, CALLS_REAL_METHODS);
        shop = Mockito.mock(Shop.class, CALLS_REAL_METHODS);
        items =  new HashMap();
        cart =  new HashMap();
        itemName = "item_test";
        shopName = "shop_test";
    }

    @Test
    @DisplayName("History Unit Test - empty purchase")
    public void testEmptyPurchaseHistory() throws IllegalAccessException {
        // empty item name
        String purchase = "";
        closedShopsHistory.addPurchaseHistory(purchase, shop);
        Assertions.assertEquals(closedShopsHistory.getOverallHistory().toString(),"");
        purchase = "purchase";
        try{
            closedShopsHistory.addPurchaseHistory(purchase,null);
            assert false;
        } catch (Exception ignored){}
        closedShopsHistory.addPurchaseHistory(purchase, shop);
        Assertions.assertTrue(closedShopsHistory.getOverallHistory().toString().contains(purchase));
    }

    @Test
    @DisplayName("History Unit Test - valid")
    public void testValidPurchaseHistory() throws IllegalAccessException {
        String purchase = "purchase";
        closedShopsHistory.addPurchaseHistory(purchase,shop);
        Assertions.assertTrue(closedShopsHistory.getOverallHistory().toString().contains(purchase));

    }
    @Test
    @DisplayName("History - close a shop")
    public void closeShopValid() throws MarketException {
        Assertions.assertTrue(closedShopsHistory.getClosedShops().isEmpty());
        closedShopsHistory.closeShop(shop);
        Assertions.assertTrue(closedShopsHistory.getClosedShops().containsKey (shop.getShopName ()));
    }
    @Test
    @DisplayName("History - close null shop")
    public void closeNullShop() throws MarketException {
        try{
            closedShopsHistory.closeShop(null);
            assert false;
        }catch (Exception ignored){};
        Assertions.assertTrue(closedShopsHistory.getClosedShops().isEmpty());
    }




    private void setField(Object obj, String field, Object value){
        ReflectionTestUtils.setField(obj, field,value);
    }


}
