package com.example.server.UnitTests;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Shop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.format.annotation.NumberFormat;

import java.util.ArrayList;
import java.util.List;

import static com.example.server.businessLayer.Item.Category.fruit;

public class ShopTest {
    Shop shop = new Shop("name_test");

    //TODO check how to do before each
    @Test
    @DisplayName("Add item - good test")
    public void addItemTest() {
        try {
            List<String> keywords = new ArrayList<>();
            keywords.add("fruit");
            shop.addItem(new Item(1, "item_test", 5,"", Item.Category.general, keywords));
            Assertions.assertEquals(1,shop.getItemMap().size());

        } catch (Exception e) {
            assert false;
        }
    }
    @Test
    @DisplayName("Add item - fail test - duplicate name")
    public void addItemTestDupName(){
        try {
            List<String> keywords = new ArrayList<>();
            keywords.add("fruit");
            shop.addItem(new Item(1, "item_test", 5,"", Item.Category.general, keywords));
            shop.addItem(new Item(1, "item_test", 5,"", Item.Category.general, keywords));
            assert false;

        } catch (Exception e) {
            assert true;
        }
    }
    @Test
    @DisplayName("edit item - good test")
    public void editItemTest(){
        try {
            List<String> keywords = new ArrayList<>();
            keywords.add("fruit");
            shop.addItem(new Item(1, "item_test", 5,"", Item.Category.general, keywords));
            keywords.add("tasty");
            Item editItem = new Item(1,"itemTestNewName" , 4 ,"brand new info", fruit,keywords);
            shop.editItem(editItem,"1");
            Assertions.assertEquals("itemTestNewName" , shop.getItemMap().get(1));

        } catch (Exception e) {
            System.out.println(e.getMessage());;
            assert false;
        }
    }

    @Test
    @DisplayName("edit item - fail test - change ID")
    public void editItemFailTestChangeID(){
        try {
            List<String> keywords = new ArrayList<>();
            keywords.add("fruit");
            shop.addItem(new Item(1, "item_test", 5,"", Item.Category.general, keywords));
            Item editItem = new Item(2,"itemTestNewName" , 4 ,"brand new info", fruit,keywords);
            shop.editItem(editItem,"1");
            assert false;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert true;
        }
    }
    @Test
    @DisplayName("edit item - good test - Does not add")
    public void editItemFailTestDoesNotAdd(){
        try {
            List<String> keywords = new ArrayList<>();
            keywords.add("fruit");
            shop.addItem(new Item(1, "item_test", 5,"", Item.Category.general, keywords));
            Item editItem = new Item(1,"itemTestNewName" , 4 ,"brand new info", fruit,keywords);
            shop.editItem(editItem,"1");
            Assertions.assertEquals(1,shop.getItemMap().size());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }
    @Test
    @DisplayName("delete item - good test")
    public void deleteItemTest(){
        try {
            List<String> keywords = new ArrayList<>();
            keywords.add("fruit");
            Item itemTest = new Item(1, "item_test", 5,"", Item.Category.general, keywords);
            shop.addItem(itemTest);
            Assertions.assertEquals(1,shop.getItemMap().size());
            shop.deleteItem(itemTest);
            Assertions.assertEquals(0,shop.getItemMap().size());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }
    @Test
    @DisplayName("Set item amount - good test")
    public void setItemAmountTest(){
        try {
            //todo - how to do with mock
            List<String> keywords = new ArrayList<>();
            keywords.add("fruit");
            Item itemTest = new Item(1, "item_test", 5,"", Item.Category.general, keywords);
            shop.addItem(itemTest);
            shop.setItemAmount("",itemTest,10);
            Assertions.assertEquals(0,shop.getItemMap().size());
            assert false;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    @DisplayName("Set item amount - fail test - negative amount")
    public void setItemAmountFailTest(){
        try {
            //todo - how to do with mock
            List<String> keywords = new ArrayList<>();
            keywords.add("fruit");
            Item itemTest = new Item(1, "item_test", 5,"", Item.Category.general, keywords);
            shop.addItem(itemTest);
            shop.setItemAmount("",itemTest,-10);
            Assertions.assertEquals(0,shop.getItemMap().size());
            assert false;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }
    @Test
    @DisplayName("Set item amount - good test - zero amount")
    public void setItemZeroAmountTest(){
        try {
            //todo - how to do with mock
            List<String> keywords = new ArrayList<>();
            keywords.add("fruit");
            Item itemTest = new Item(1, "item_test", 5,"", Item.Category.general, keywords);
            shop.addItem(itemTest);
            shop.setItemAmount("",itemTest,0);
            Assertions.assertEquals(0,shop.getItemMap().size());
            assert false;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }




}
