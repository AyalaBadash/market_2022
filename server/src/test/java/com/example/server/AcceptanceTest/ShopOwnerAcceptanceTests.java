package com.example.server.AcceptanceTest;

import com.example.server.businessLayer.Item;
import com.example.server.serviceLayer.FacadeObjects.ItemFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopFacade;
import com.example.server.serviceLayer.FacadeObjects.VisitorFacade;
import com.example.server.serviceLayer.Response;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopOwnerAcceptanceTests extends AcceptanceTests {
    static private String steakName;
    static int steakPrice = 10;
    static private Item.Category steakCategory;
    static private ArrayList<String> steakKeywords;
    static private String steakInfo;

    @BeforeAll
    public static void shopSetup() {
        steakName = "steak";
        steakCategory = Item.Category.meat;
        steakKeywords = new ArrayList<>();
        steakInfo = " best in town";
    }

    @BeforeEach
    public void reset() {
        try {
            setItemCurrentAmount(shopOwnerName, milk, productAmount, shopName);
        } catch (Exception e) {
            String msg = e.getMessage();
        }
    }

    @Test
    @Order(1)
    @DisplayName("add new item")
    public void addNewItem() {
        try {

            Response response = addItemToShop(shopOwnerName, steakName, steakPrice, steakCategory,
                    steakInfo, steakKeywords, 5.0, shopName);
            assert !response.isErrorOccurred();
            ShopFacade shop = getShopInfo(shopOwnerName, shopName);
            boolean found = false;
            for (Map.Entry<ItemFacade, Double> itemAmount : shop.getItemsCurrentAmount().entrySet()) {
                ItemFacade item = itemAmount.getKey();
                Double amount = itemAmount.getValue();
                if (item.getName().equals("steak")) {
                    found = amount.equals(5.0);
                    break;
                }
            }
            assert found;

        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @Order(2)
    @DisplayName("add existing item")
    public void addExistingItem() {
        try {
            Response response = addItemToShop(shopOwnerName, "milk", productPrice, Item.Category.general,
                    "soy", new ArrayList<>(), 10, shopName);
            assert response.isErrorOccurred();
            ShopFacade shop = getShopInfo(shopOwnerName, shopName);
            boolean found = false;
            for (Map.Entry<ItemFacade, Double> itemAmount : shop.getItemsCurrentAmount().entrySet()) {
                ItemFacade item = itemAmount.getKey();
                Double amount = itemAmount.getValue();
                if (item.getName().equals("steak")) {
                    found = amount.equals(productAmount);
                    break;
                }
            }
            assert found;

        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @Order(3)
    @DisplayName("set item amount - valid")
    public void setValidItemAmount() {
        try {

            Response response = setItemCurrentAmount(shopOwnerName, milk, 8.0, shopName);
            assert !response.isErrorOccurred();
            ShopFacade shop = getShopInfo(shopOwnerName, shopName);
            boolean found = false;
            for (Map.Entry<ItemFacade, Double> itemToAmount : shop.getItemsCurrentAmount().entrySet()) {
                ItemFacade item = itemToAmount.getKey();
                Double amount = itemToAmount.getValue();
                if (found) break;
                found = item.getName().equals("milk") && amount.equals(8.0);
            }
            assert found;

        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("set item amount 0")
    public void setValidItemAmountZero() {
        try {

            Response response = setItemCurrentAmount(shopOwnerName, milk, 0, shopName);
            assert !response.isErrorOccurred();
            ShopFacade shop = getShopInfo(shopOwnerName, shopName);
            boolean found = false;
            for (Map.Entry<ItemFacade, Double> itemToAmount : shop.getItemsCurrentAmount().entrySet()) {
                ItemFacade item = itemToAmount.getKey();
                Double amount = itemToAmount.getValue();
                if (found) break;
                found = item.getName().equals("milk") && amount.equals(0.0);
            }
            assert found;

        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("remove item from shop")
    public void removeItemFromShopTest() {
        try {
            ShopFacade shop = getShopInfo(shopOwnerName, shopName);
            ItemFacade steak = null;
            for (Map.Entry<Integer, ItemFacade> items : shop.getItemMap().entrySet()) {
                if (items.getValue().getName().equals(steakName)) {
                    steak = items.getValue();
                }
            }
            assert steak != null;
            Response response = removeItemFromShop(shopOwnerName, steak, shopName);
            assert !response.isErrorOccurred();
            shop = getShopInfo(shopOwnerName, shopName);
            for (Map.Entry<Integer, ItemFacade> items : shop.getItemMap().entrySet()) {
                assert !items.getValue().getName().equals(steakName);
            }


        } catch (Exception e) {
            assert false;
        }

    }

    @Test
    @DisplayName("set item amount negative")
    public void setValidItemAmountNegative() {
        try {
            Response response = setItemCurrentAmount(shopOwnerName, milk, -1, shopName);
            assert response.isErrorOccurred();
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("change item info")
    public void setItemInfo() {
        try {
            String newInfo = "out of Date!";
            Response response = changeShopItemInfo(shopOwnerName, newInfo, milk, shopName);
            assert !response.isErrorOccurred();
            ShopFacade newShop = getShopInfo(shopOwnerName, shopName);
            boolean found = false;
            for (Map.Entry<Integer, ItemFacade> idToItem : newShop.getItemMap().entrySet()) {
                ItemFacade item = idToItem.getValue();
                if (item.getName().equals(milk.getName())) {
                    found = item.getInfo().equals(newInfo);
                    break;
                }
            }
            assert found;

        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("change item info to empty")
    public void changeToEmptyInfo() {
        try {
            String newInfo = "";
            Response response = changeShopItemInfo(shopOwnerName, newInfo, milk, shopName);
            assert !response.isErrorOccurred();
            ShopFacade newShop = getShopInfo(shopOwnerName, shopName);
            boolean found = false;
            for (Map.Entry<Integer, ItemFacade> idToItem : newShop.getItemMap().entrySet()) {
                ItemFacade item = idToItem.getValue();
                if (item.getName().equals(milk.getName())) {
                    found = item.getInfo().equals(newInfo);
                    break;
                }
            }
            assert found;

        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("appoint new shop owner")
    public void appointOwner() {
        try {
            VisitorFacade nextOwner = guestLogin();
            String nextOwnerName = "raz1";
            String nextOwnerPass = "123";
            Response response = register(nextOwnerName, nextOwnerPass);
            ShopFacade shop = getShopInfo(shopOwnerName, shopName);
            int prevAppoints = shop.getEmployees().size();
            response = appointShopOwner(shopOwnerName, nextOwnerName, shopName);
            assert !response.isErrorOccurred();
            shop = getShopInfo(shopOwnerName, shopName);
            assert shop.getEmployees().size() + 1 == prevAppoints;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @DisplayName("appoint new shop manager")
    public void appointShopManagerTest() {
        try {
            VisitorFacade nextOwner = guestLogin();
            String nextOwnerName = "raz2";
            String nextOwnerPass = "123";
            Response response = register(nextOwnerName, nextOwnerPass);
            ShopFacade shop = getShopInfo(shopOwnerName, shopName);
            int prevAppoints = shop.getEmployees().size();
            response = appointShopManager(shopOwnerName, nextOwnerName, shopName);
            assert !response.isErrorOccurred();
            shop = getShopInfo(shopOwnerName, shopName);
            assert shop.getEmployees().size() + 1 == prevAppoints;
        } catch (Exception e) {
            assert false;
        }
    }


    @Test
    @DisplayName("close shop")
    public void closeShopTest() {
        try {
            VisitorFacade visitor = guestLogin();
            String password = "9may";
            String name = "razBam";
            register(name, password);
            List<String> questions = memberLogin(name, password);
            validateSecurityQuestions(name, new ArrayList<>(), visitor.getName());
            // open shop
            String shopName = "RealMadrid_Fuckers";
            openShop(name, shopName);
            ShopFacade shop = getShopInfo(name, shopName);
            Response response = closeShop(name, shopName);
            assert !response.isErrorOccurred();
            try {
                shop = getShopInfo(name, shopName);
                assert false;
            } catch (Exception e) {
                assert true;
            }
            ;

        } catch (Exception e) {
            assert false;
        }
    }
}
