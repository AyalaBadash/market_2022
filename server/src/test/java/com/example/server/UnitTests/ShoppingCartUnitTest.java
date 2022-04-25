package com.example.server.UnitTests;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class ShoppingCartUnitTest extends mainTest {


    @Mock
    Item item = Mockito.mock(Item.class);
    Shop shop = Mockito.mock(Shop.class);
    ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
    @Test
    public void test1(){
        int x = 3;
        int y = 6;
        Assertions.assertEquals(x,y/2);
    }

    @Test
    @DisplayName("ShoppingCart Unit Test - edit cart")
    public void editShoppingCart() throws IllegalAccessException, MarketException {
        shoppingCart.addItem(shop,item,10);
        Mockito.when(shoppingCart.getItemQuantity(item)).thenReturn(10);
        Assertions.assertEquals(10, shoppingCart.getItemQuantity(item));
        shoppingCart.editQuantity(15,item,shop.getShopName());
        Mockito.when(shoppingCart.getItemQuantity(item)).thenReturn(15);
        Assertions.assertEquals(15, shoppingCart.getItemQuantity(item));
    }
}
