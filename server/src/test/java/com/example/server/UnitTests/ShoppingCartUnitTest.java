package com.example.server.UnitTests;

import com.example.server.businessLayer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartUnitTest extends mainTest {


    @Mock
    Item item = Mockito.mock(Item.class);
    Item item2 = Mockito.mock(Item.class);
    Shop shop = Mockito.mock(Shop.class);
    Shop shop2 = Mockito.mock(Shop.class);
    ShoppingBasket basket = Mockito.mock(ShoppingBasket.class);
    ShoppingBasket basket2 = Mockito.mock(ShoppingBasket.class);
    ShoppingCart shoppingCart = new ShoppingCart();
    Map<Shop,ShoppingBasket> cart;
    Map<Item,Double> basket1Map;
    Map<Item,Double> basket2Map;

    @BeforeEach
    public void reset (){
        basket1Map = new HashMap<>();
        basket2Map = new HashMap<>();
        basket1Map.put(item,5.0);
        Map<Item,Double> basket2Map=new HashMap<>();
        basket1Map.put(item2,5.0);
        Mockito.when(basket.getItems()).thenReturn(basket1Map);
        Mockito.when(basket2.getItems()).thenReturn(basket2Map);
        cart = new HashMap<>();
        cart.put(shop,basket);
        cart.put(shop2,basket2);
        Mockito.when(shop.getShopName()).thenReturn("shop");
        shoppingCart.setCart(cart);

    }


    @Test
    @DisplayName("ShoppingCart Unit Test - edit cart")
    public void editShoppingCart(){
        try {
            Assertions.assertEquals(5, shoppingCart.getItemQuantity(item));
            Mockito.doNothing().when(basket).updateQuantity(15,item);
            Mockito.when(basket.getItems()).thenReturn(basket1Map);
            shoppingCart.editQuantity(15, item, shop.getShopName());
            HashMap<Item, Double> tempHash = new HashMap<>();
            tempHash.put(item,15.0);
            Mockito.when(basket.getItems()).thenReturn(tempHash);
            Assertions.assertEquals(15, shoppingCart.getItemQuantity(item));
        }
        catch (MarketException e){
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    @DisplayName("Save from shops - good test")
    public void saveFromShopsTest()
    {
            try{

                Mockito.when(shop.buyBasket(basket,"some buyer name")).thenReturn(25.0);
                Mockito.when(shop2.buyBasket(basket2, "some buyer name")).thenReturn(30.0);
                double x = shoppingCart.saveFromShops("some buyer name");
                Assertions.assertEquals(55.0,x);
            }
            catch (MarketException e){
                System.out.println(e.getMessage());
                assert false;
            }
    }
    @Test
    @DisplayName("Clear cart test")
    public void clearCart(){
        shoppingCart.clear();
        Assertions.assertEquals(0,cart.size());
    }

    @Test
    @DisplayName("Add item")
    public void addItemTest(){
        Item testItem = Mockito.mock(Item.class);
        Shop newShop = Mockito.mock(Shop.class);
        shoppingCart.addItem(newShop,testItem,1.0);
        Assertions.assertEquals(3,shoppingCart.getCart().size());
    }
}
