package com.example.server.UnitTests;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Shop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.server.businessLayer.Item.Category.fruit;

public class ShopTest {
    Shop shop = new Shop("name_test");
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
    @DisplayName("Add item - fail test")
    public void addItemTestShouldFail(){
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

}
