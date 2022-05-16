package com.example.server.UnitTests;

import com.example.server.businessLayer.Item;
import com.example.server.ResourcesObjects.MarketException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ItemTest {


    @Test
    @DisplayName("Invalid item details")
    public void InvalidItemDetails()
    {
        String name = "Milk";
        double price = 5.0;
        String info ="1.0L";
        List<String> keywords = new ArrayList<>();
        keywords.add("dairy");
        Item.Category category = Item.Category.general;
        try {
            Item testItem = new Item(-1,name,price,info,category,keywords);
            assert false;
            testItem = new Item(2,name,-1,info,category,keywords);
            assert false;
        }
        catch (MarketException e)
        {
            assert true;
        }
    }
    @Test
    @DisplayName("Valid item details")
    public void ValidItemDetails()
    {
        String name = "Milk";
        double price = 5.0;
        String info ="";
        List<String> keywords = new ArrayList<>();
        Item.Category category = Item.Category.general;
        try {
            Item testItem = new Item(1,name , price,info,category,keywords);
            testItem = new Item(2,name,price,null,null,null);
            assert true;
        }
        catch (MarketException e){
            assert false;
        }
    }

    @Test
    @DisplayName("Null input in category for item")
    public void NullCategory()
    {
        String name = "Milk";
        double price = 5.0;
        String info ="";
        List<String> keywords = new ArrayList<>();
        Item.Category category = Item.Category.general;
        try {
            Item testItem = new Item(1,name , price,info,null,keywords);
            Assertions.assertEquals(Item.Category.general.name(),testItem.getCategory().name());
            //assert true;
        }
        catch (MarketException e){
            assert false;
        }
    }
}
