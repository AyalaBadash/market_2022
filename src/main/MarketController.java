package main;

import java.util.Map;

public class MarketController {
    Map<String,Shop> shops;

    public boolean collectDebt(){
        throw new UnsupportedOperationException();
    }
    public boolean supplyProducts(){throw new UnsupportedOperationException();}



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
                        added = false;
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
