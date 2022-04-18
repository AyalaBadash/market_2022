package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketController {
    Market market;
    MemberController mc;

    public MarketController(){
        mc = MemberController.getInstance();
        market = Market.getInstance();

    }

    public boolean collectDebt(){
        throw new UnsupportedOperationException();
    }
    public boolean supplyProducts(){throw new UnsupportedOperationException();}

    //Should come after login / entering the market
    public void displayMenuForMember(){
        List<Shop> shopMemberOwn = findShopByOwner(mc.getCurrentLoggedIn().getName());
        List<Shop> shopMemberManage = findShopByManager(mc.getCurrentLoggedIn().getName());
        if (shopMemberOwn.isEmpty() && shopMemberManage.isEmpty()) // display only buyer menu
        {
            System.out.println("1.Actions as buyer \n" +
                    "2.Exit");
            int choice = 0;
            switch (choice) {
                case 1: {
                    displayBuyerMenu();
                    break;
                }
                case 2: {
                    exit();
                    break;
                }
                default:
                    System.out.println("Wrong input");
            }
        }
        else if (!shopMemberOwn.isEmpty() && shopMemberManage.isEmpty()) // display buyer and owner
        {
            System.out.println("1.Actions as buyer \n" +
                    "2.Actions as owner\n" +
                    "3.Exit");
            int choice = 0;
            switch (choice) {
                case 1: {
                    displayBuyerMenu();
                    break;
                }
                case 2: {
                    displayOwnerMenu(shopMemberOwn);
                    break;
                }
                case 3:{
                    exit();
                    break;
                }
                default:
                    System.out.println("Wrong input");
            }
        }
        else if(shopMemberOwn.isEmpty() && !shopMemberManage.isEmpty()) // display buyer and manager
        {
            System.out.println("1.Actions as buyer \n" +
                    "2.Actions as manager\n" +
                    "3.Exit");
            int choice = 0;
            switch (choice) {
                case 1: {
                    displayBuyerMenu();
                    break;
                }
                case 2: {
                    displayManagerMenu(shopMemberManage);
                    break;
                }
                case 3:{
                    exit();
                    break;
                }
                default:
                    System.out.println("Wrong input");
            }
        }
        else //display all - buyer owner manager
        {
            System.out.println("1.Actions as buyer \n" +
                    "2.Actions as owner\n" +
                    "3.Actions as manager\n" +
                    "4.Exit");
            int choice = 0;
            switch (choice) {
                case 1: {
                    displayBuyerMenu();
                    break;
                }
                case 2: {
                    displayOwnerMenu(shopMemberOwn);
                    break;
                }
                case 3:
                {
                    displayManagerMenu(shopMemberManage);
                }
                case 4:{
                    exit();
                    break;
                }
                default:
                    System.out.println("Wrong input");
            }
        }


        displayBuyerMenu();
        exit();
    }

    private List<Shop> findShopByManager(String name) {
        List<Shop> managedShops = new ArrayList<>();
        for (Map.Entry<String,Shop> entry: market.getShops().entrySet())
        {
            Map<String, Member> shopManagers = entry.getValue().getShopManagers();
            if (shopManagers.containsKey(name))
                managedShops.add(entry.getValue());
        }
        return managedShops;
    }

    private List<Shop> findShopByOwner(String name) {
        List<Shop> ownedShops = new ArrayList<>();
        for (Map.Entry<String,Shop> entry:market.getShops().entrySet())
        {
            Map<String, Member> shopOwners = entry.getValue().getShopOwners();
            if (shopOwners.containsKey(name))
                ownedShops.add(entry.getValue());
        }
        return ownedShops;
    }

    private void displayBuyerMenu() {

    }

    private void displayOwnerMenu(List<Shop> shopsOwned) {

    }

    private void displayManagerMenu(List<Shop> shopsManaged) {

    }

    private void exit() {

    }


    // use case - stock management
    //TODO - check if this method needs to be implemented here , if yes: add reader.
    public void manageStock(Shop shop) throws Exception
    {//Display menu
        System.out.println("1.Add a new item\n" +
                "2.Remove an existing item\n" +
                "3.Edit an existing item");
        int choice=0;
        switch (choice){
            case 1: //Add item
                System.out.println("Insert item's name:");
                String name="";
                System.out.println("Insert item price:");
                double price=0;
                System.out.println("Insert item weight:");
                double weight=0;
                Item toAdd = new Item(name,price,weight);
                boolean added=false;
                Map<String,Item> items;
                while (!added) {
                    try {
                        shop.addItem(toAdd);
                        added = true;
                    } catch (Exception e) { //TODO - add exception for name taken
                        System.out.println("Name is taken - please insert other name:");
                        String newName = "";
                        toAdd.setName(newName);
                        shop.addItem(toAdd); //TODO - change - what happens if new name also taken
                    }
                }

                break;
            case 2: //Remove item
                items= shop.getItems();
                for (Map.Entry<String,Item> entry:items.entrySet())
                {
                    System.out.println(entry.getKey());
                }

                System.out.println("Name item to remove:");
                name="";
                while(!items.containsKey(name))
                {
                    System.out.println("There is no item with that name. Please choose again:");
                    name = "";
                }
                Item toRemove = items.get(name);
                shop.deleteItem(toRemove);
                break;
            case 3: // edit item
                items= shop.getItems();
                for (Map.Entry<String,Item> entry:items.entrySet())
                {
                    System.out.println(entry.getValue().toString());
                }

                System.out.println("Name item to remove:");
                name="";
                while(!items.containsKey(name))
                {
                    System.out.println("There is no item with that name. Please choose again:");
                    name = "";
                }
                Item toEdit = items.get(name);
                shop.editItem(toEdit);
                break;
        }

    }
}
