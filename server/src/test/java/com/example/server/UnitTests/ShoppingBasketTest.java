package com.example.server.UnitTests;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingBasket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Map;

public class ShoppingBasketTest {
    ShoppingBasket basket = new ShoppingBasket();
    @Mock
    Item item;


    @Test
    @DisplayName("Add item - good test")
    public void AddItemTest (){
        item = Mockito.mock(Item.class);
        try {
            basket.addItem(item,5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
        Assertions.assertEquals(1,basket.getItems().size());
        Assertions.assertEquals(5.0,basket.getItems().get(item));
    }

    @Test
    @DisplayName("Add item - fail test - Negative item amount")
    public void AddItemFailTestNegativeAmount (){
        item = Mockito.mock(Item.class);
        try {
            basket.addItem(item,-1);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("Update item amount - good test")
    public void updateQuantityTest(){
        item = Mockito.mock(Item.class,Mockito.CALLS_REAL_METHODS);
        try {
            basket.addItem(item,5);
        } catch (MarketException e) {
            System.out.println(e.getMessage());
            assert false;
        }
        Item otherItem = Mockito.mock(Item.class,Mockito.CALLS_REAL_METHODS);
        try {
            basket.updateQuantity(3,item);
            Assertions.assertEquals(3,basket.getItems().get(item));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assert false;
        }
    }
    @Test
    @DisplayName("Update item amount - fail test - No such Item on basket")
    public void updateQuantityFailTestNoItem(){
        item = Mockito.mock(Item.class);
        try {
            basket.addItem(item,5);
        } catch (MarketException e) {
            assert false;
        }
        Item otherItem = Mockito.mock(Item.class);
        try {
            basket.updateQuantity(3,otherItem);
            assert false;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assert true;
        }
    }

    @Test
    @DisplayName("Update item amount - fail test - negative amount")
    public void updateQuantityFailTestNegativeAmount(){
        item = Mockito.mock(Item.class);
        try {
            basket.addItem(item,5);
        } catch (MarketException e) {
            assert false;
        }
        try {
            basket.updateQuantity(-1,item);
            assert false;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assert true;
        }
    }

    @Test
    @DisplayName("Remove item - good test - item in basket")
    public void removeItemTest(){
        item = Mockito.mock(Item.class);
        try {
            basket.addItem(item,5);
        } catch (MarketException e) {
            assert false;
        }
        Mockito.when(item.getPrice()).thenReturn(5.0);
        try {
            basket.removeItem(item);
            Assertions.assertEquals(0,basket.getItems().size());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    @DisplayName("Remove item - good test - no item in basket")
    public void removeItemTestNoItem(){
        item = Mockito.mock(Item.class);
        try {
            basket.addItem(item,5);
        } catch (Exception e) {
            assert false;
        }
        Item otherItem = Mockito.mock(Item.class);
        Assertions.assertEquals(1,basket.getItems().size());
        try {
            basket.removeItem(otherItem);
            Assertions.assertEquals(1,basket.getItems().size());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assert false;
        }
    }
    @Test
    @DisplayName("Calculate - good test")
    public void calculateTest(){
        item = Mockito.mock(Item.class,Mockito.CALLS_REAL_METHODS);
        Item otherItem = Mockito.mock(Item.class,Mockito.CALLS_REAL_METHODS);
        try {
            basket.addItem(item,5);
            basket.addItem(otherItem,9);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Mockito.when(item.getPrice()).thenReturn(5.0);
        Mockito.when(otherItem.getPrice()).thenReturn(3.1);
        Assertions.assertEquals(52.9,basket.getPrice());
    }
    @Test
    @DisplayName("Calculate - good test Empty Basket")
    public void calculateTestEmptyBasket(){
        Assertions.assertEquals(0,basket.getPrice());
    }
    @Test
    @DisplayName("Calculate - good test - calculate Twice")
    public void calculateTwiceTest(){
        item = Mockito.mock(Item.class,Mockito.CALLS_REAL_METHODS);
        Item otherItem = Mockito.mock(Item.class,Mockito.CALLS_REAL_METHODS);
        try{
            basket.addItem(item,5);
            basket.addItem(otherItem,9);}
        catch (Exception e)
        {
            assert false;
        }
        Mockito.when(item.getPrice()).thenReturn(5.0);
        Mockito.when(otherItem.getPrice()).thenReturn(3.0);
        Assertions.assertEquals(52,basket.getPrice());
        try {
            basket.removeItem(otherItem);
        } catch (MarketException e) {
            System.out.println(e.getMessage());
            assert false;
        }
        Assertions.assertEquals(25,basket.getPrice());
    }
}
