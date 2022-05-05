package com.example.server.UnitTests;

import com.example.server.businessLayer.*;
import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Appointment.ShopManagerAppointment;
import com.example.server.businessLayer.Appointment.ShopOwnerAppointment;
import com.example.server.businessLayer.Users.Member;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopTest {
    Shop shop = new Shop("shop");
    @Mock
    Appointment appointment;
    Appointment managerAppointment;
    Appointment ownerAppointment;
    Member memberFounder;
    ShoppingBasket basket;
    Item item;

    List<String> keywords;

    @BeforeEach
    public void reset(){
        memberFounder = Mockito.mock(Member.class);
        item = Mockito.mock(Item.class);
        basket = Mockito.mock(ShoppingBasket.class);
        managerAppointment = Mockito.mock(ShopManagerAppointment.class);
        ownerAppointment = Mockito.mock(ShopOwnerAppointment.class);
        keywords = new ArrayList<>();
        keywords.add("fruit");
        Mockito.when(item.getID()).thenReturn(1);
        Mockito.when(item.getName()).thenReturn("item_test");
        Mockito.when(memberFounder.getName()).thenReturn("The founder");
        Member otherMember = Mockito.mock(Member.class);
        Mockito.when(otherMember.getName()).thenReturn("The twin");
        Mockito.when(ownerAppointment.getAppointed()).thenReturn(memberFounder);
        Mockito.when(ownerAppointment.getAppointed().getName()).thenReturn("The founder");
        Mockito.when(ownerAppointment.isOwner()).thenReturn(true);
        Mockito.when(managerAppointment.getAppointed()).thenReturn(otherMember);
        Mockito.when(managerAppointment.getAppointed().getName()).thenReturn("The twin");
        Mockito.when(managerAppointment.getSuperVisor()).thenReturn(memberFounder);
        Mockito.when(managerAppointment.getSuperVisor().getName()).thenReturn("The founder");



        try{
            shop.addEmployee(ownerAppointment);
            shop.addItem("The founder", item.getName(),item.getPrice(), item.getCategory(),item.getInfo(),keywords,1.0,item.getID());
        }
        catch (MarketException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Add item - good test")
    public void addItemTest() {
        try {
            Assertions.assertEquals(1,shop.getItemMap().size());
            shop.addItem("The founder", item.getName(),item.getPrice(), item.getCategory(),item.getInfo(),keywords,1.0,2);
            Assertions.assertEquals(2,shop.getItemMap().size());

        } catch (Exception e) {
            assert false;
        }
    }
    @Test
    @DisplayName("Add item - fail test - ID taken")
    public void addItemTestIDTaken(){
        try {
            shop.addItem("The founder", item.getName(),item.getPrice(), item.getCategory(),item.getInfo(),keywords,1.0,item.getID());
            assert false;

        } catch (MarketException e) {
            assert true;
        }
    }
    @Test
    @DisplayName("edit item - good test")
    public void editItemTest(){
        try {
            keywords.add("tasty");
            Mockito.when(item.getName()).thenReturn("itemTestNewName");
            Mockito.when(item.getID()).thenReturn(1);
            shop.editItem(item,"1");
            Assertions.assertEquals("itemTestNewName" , shop.getItemMap().get(0));

        } catch (Exception e) {
            System.out.println(e.getMessage());;
            assert false;
        }
    }

    @Test
    @DisplayName("edit item - fail test - change ID")
    public void editItemFailTestChangeID(){
        try {
            Mockito.when(item.getID()).thenReturn(2);
            shop.editItem(item,"1");
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
            Mockito.when(item.getID()).thenReturn(1);
            Mockito.when(item.getInfo()).thenReturn("brand new info");
            Mockito.when(item.getName()).thenReturn("itemTestNewName");
            shop.editItem(item,"1");
            Assertions.assertEquals(1,shop.getItemMap().size());
            Assertions.assertTrue(shop.getItemMap().containsKey("itemTestNewName"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }
    @Test
    @DisplayName("delete item - good test")
    public void deleteItemTest(){
        try {
            Assertions.assertEquals(1,shop.getItemMap().size());
            shop.deleteItem(item);
            Assertions.assertEquals(0,shop.getItemMap().size());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    @DisplayName("Add employee test - good test")
    public void addEmployeeTest(){
        try {
            Member testMember = Mockito.mock(Member.class);
            Mockito.when(testMember.getName()).thenReturn("raz");
            ShopOwnerAppointment testOwnerApp = Mockito.mock(ShopOwnerAppointment.class);
            Mockito.when(testOwnerApp.getAppointed()).thenReturn(testMember);
            Mockito.when(testOwnerApp.getSuperVisor()).thenReturn(memberFounder);
            Mockito.when(testOwnerApp.isOwner()).thenReturn(true);
            shop.addEmployee(testOwnerApp);
            Assertions.assertTrue(shop.isShopOwner("raz"));
        }
        catch (MarketException e)
        {
            System.out.println(e.getMessage());
            assert false;
        }
    }
    @Test
    @DisplayName("Add employee test - fail test - add same employee ")
    public void addEmployeeFailTestAddTwice(){
        try {
            shop.addEmployee(ownerAppointment);
            assert false;
        }
        catch (MarketException e)
        {
            System.out.println(e.getMessage());
            assert true;
        }
    }
    @Test
    @DisplayName("Add employee test - good test - add same owner as manager")
    public void addEmployeeFailTestOwnerAndManager(){
        try {
            ShopOwnerAppointment testOwnerApp = Mockito.mock(ShopOwnerAppointment.class);
            ShopManagerAppointment testManagerApp = Mockito.mock(ShopManagerAppointment.class);
            Member testMember = Mockito.mock(Member.class);
            Member testMember2 = Mockito.mock(Member.class);
            Mockito.when(testOwnerApp.getAppointed()).thenReturn(testMember);
            Mockito.when(testOwnerApp.getAppointed().getName()).thenReturn("raz");
            Mockito.when(testOwnerApp.isOwner()).thenReturn(true);
            Mockito.when(testManagerApp.getAppointed()).thenReturn(testMember);
            Mockito.when(testManagerApp.getSuperVisor()).thenReturn(memberFounder);
            shop.addEmployee(testOwnerApp);
            shop.addEmployee(testManagerApp);

            assert true;
        }
        catch (MarketException e)
        {
            System.out.println(e.getMessage());
            assert false;
        }
    }
    @Test
    @DisplayName("Set item amount - good test")
    public void setItemAmountTest(){
        try {
            shop.setItemAmount("The founder",item,10);
            Assertions.assertEquals(1,shop.getItemMap().size());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    @DisplayName("Set item amount - fail test - negative amount")
    public void setItemAmountFailTest(){
        try {
            shop.setItemAmount("The founder",item,-10);
            assert false;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert true;
        }
    }
    @Test
    @DisplayName("Set item amount - good test - zero amount")
    public void setItemZeroAmountTest(){
        try {

            shop.setItemAmount("The founder",item,0);
            Assertions.assertEquals(1,shop.getItemMap().size());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    @DisplayName("release items - good test")
    public void releaseItemsTest(){
        try {
            shop.setItemAmount("The founder",item,1);
            Map<Item,Double> map = new HashMap<>();
            map.put(item,1.0);
            Mockito.when(basket.getItems()).thenReturn(map);
            //Mockito.when(basket.getItems().get(item)).thenReturn(10.0);
            shop.releaseItems(basket);
            Assertions.assertEquals(11,shop.getItemCurrentAmount(item));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    @DisplayName("release items - good test - no relevant item , stays the same")
    public void releaseItemsTestNoRelevantItem(){
        try {
            Item item2 = Mockito.mock(Item.class);
            Mockito.when(item2.getName()).thenReturn("item2");
            Mockito.when(item2.getID()).thenReturn(2);
            shop.setItemAmount("The founder",item,0);
            basket.addItem(item2,10);
            shop.releaseItems(basket);
            Assertions.assertEquals(0,shop.getItemCurrentAmount(item));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    @DisplayName("Buy basket - good test.")
    public void buyBasketTest() {//TODO check with ido.
    Map<Item,Double> itemsMap = new HashMap<>();
    Item item2 = Mockito.mock(Item.class);
    itemsMap.put(item,5.0);
    itemsMap.put(item2,10.0);
    Mockito.when(item.getPrice()).thenReturn(5.0);
    Mockito.when(item2.getPrice()).thenReturn(1.0);
    Mockito.when(basket.getItems()).thenReturn(itemsMap);
    try{
        shop.setItemAmount("The founder",item,10.0);
        shop.setItemAmount("The founder",item2,20.0);
        Assertions.assertEquals(35.0,shop.buyBasket(basket));
    }
    catch (MarketException e){
        System.out.println(e.getMessage());
        assert false;
    }
    }

    @Test
    @DisplayName("Validate basket - good test.")
    public void validateBasketTest(){
        Map<Item,Double> items = new HashMap<>();
        Item item2 = Mockito.mock(Item.class);
        items.put(item,5.0);
        items.put(item2,5.0);
        Mockito.when(basket.getItems()).thenReturn(items);
        try {
            shop.setItemAmount("The founder",item,1.0);
            shop.setItemAmount("The founder",item2,10.0);
            shop.validateBasket(basket);
            Assertions.assertEquals(1.0,basket.getItems().get(item));
            Assertions.assertEquals(5.0,basket.getItems().get(item2));
        }
        catch (MarketException e){
            System.out.println(e.getMessage());
            assert false;
        }
    }
    
    @Test
    @DisplayName("removeItemMissing - good test.")
    public void removeItemMissingTest(){
        assert false;
    }
    @Test
    @DisplayName("removeItemMissing - fail test - negative amount.")
    public void removeItemMissingFailTest(){
        assert false;
    }


}
