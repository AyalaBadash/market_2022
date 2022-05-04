package com.example.server.ConcurrencyTest;

import com.example.server.ResourcesObjects.Address;
import com.example.server.ResourcesObjects.CreditCard;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Security;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.UserController;
import com.example.server.businessLayer.Users.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class MarketConcurrencyTest {

    //TODO - special exceptions
    Market market;
    UserController userController;
    Security security;
    MyThread[] threads;
    final String password = "password";
    final String initialLoggedIn = "Mira";
    final String[] names = {"Raz", "Raz", "Raz", "Raz", "Raz", "Raz",
            "Ido", "Ido", "Ido", "Ido", "Ido", "Ido",
            "Shaked", "Shaked", "Shaked", "Shaked", "Shaked", "Shaked",
            "Bar", "Bar", "Bar", "Bar", "Bar", "Bar",
            "Ayala", "Ayala", "Ayala", "Ayala", "Ayala", "Ayala"};
    Visitor[] visitors = new Visitor[5];
    Member[] members = new Member[5];
    SynchronizedCounter i = new SynchronizedCounter ();
    SynchronizedBoolean first = new SynchronizedBoolean ();
    int test;

    @BeforeEach
    public void initMemberTest() throws MarketException {
        market = Market.getInstance();
        security = Security.getInstance();
        userController = UserController.getInstance();
        threads = new MyThread[30];
        try {
            market.register(initialLoggedIn,password);
            market.memberLogin(initialLoggedIn,password);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Register concurrency test")
    public void registerConcurrencyTest(){
        int numOfExceptions = 0;
        for(i.reset (); i.value () < 30; i.increment ()){
            threads[i.value ()] = new MyThread ();
            threads[i.value ()].setRunnable ( new MyRunnable ( i.value ()) {
                @Override
                public void run() throws MarketException {
                    market.register ( names[index], password );
                }
            } ) ;
        }
        for(i.reset (); i.value () < 30; i.increment ()) {
            threads[i.value ()].run ( );
        }
        for(i.reset (); i.value () < 30; i.increment ()){
            if(threads[i.value ()].getEx () != null)
                numOfExceptions ++;
        }
        if(numOfExceptions < 25)
            assert false;
        else
            assert true;
    }

    @Test
    @DisplayName("Member login concurrency test")
    public void loginConcurrencyTest() {
        int numOfExceptions = 0;
        for ( i.reset (); i.value () < 30 ; i.increment () ) {
            threads[i.value ()] = new MyThread ( );
            threads[i.value ()].setRunnable ( new MyRunnable ( i.value ()) {
                @Override
                public void run() throws MarketException{
                    Visitor visitor = market.guestLogin ();
                    market.memberLogin ( names[index], password );
                    market.validateSecurityQuestions ( names[index] , new ArrayList<> (  ), visitor.getName ());
                }
            } );
        }
        for ( i.reset (); i.value () < 30 ; i.increment () ) {
            threads[i.value ()].start ( );
        }
        for ( i.reset () ; i.value () < 30 ; i.increment () ) {
            if(threads[i.value ()].getEx () != null)
                numOfExceptions ++;
        }
        if(numOfExceptions < 25)
            assert false;
        else
            assert true;
    }

    @Test
    @DisplayName("Open new shop concurrency test")
    public void openNewShopConcurrencyTest() throws MarketException {
        int numOfExceptions = 0;
        for ( i.reset (); i.value () < 5 ; i.increment () ) {
            Visitor visitor = market.guestLogin ();
            market.register ( names[i.value () * 6], password );
            market.memberLogin ( names[i.value () * 6], password );
            market.validateSecurityQuestions ( names[i.value () * 6] , new ArrayList<> (  ), visitor.getName ());
        }
        for ( i.reset () ; i.value () < 5 ; i.increment () ) {
            threads[i.value ()] = new MyThread ( );
            threads[i.value ()].setRunnable ( new MyRunnable ( i.value () ) {
                @Override
                public void run() throws MarketException{
                    market.openNewShop ( names[index * 6], "sameShopName" );
                }
            } );
        }
        for (i.reset () ; i.value () < 5 ; i.increment () ) {
            threads[i.value ()].start ( );
        }
        for (i.reset () ; i.value () < 5 ; i.increment () ) {
            if(threads[i.value ()].getEx () != null)
                numOfExceptions ++;
        }
        if(numOfExceptions < 4)
            assert false;
        else
            assert true;
    }

    @Test
    @DisplayName("Add an item to shop concurrency test")
    public void addItemToSHopShopConcurrencyTest() throws MarketException {
        int numOfExceptions = 0;
        for ( i.reset (); i.value () < 5 ; i.increment () ) {
            Visitor visitor = market.guestLogin ();
            market.register ( names[i.value () * 6], password );
            market.memberLogin ( names[i.value () * 6], password );
            market.validateSecurityQuestions ( names[i.value () * 6] , new ArrayList<> (  ), visitor.getName ());
        }
        market.openNewShop ( names[0],  "shopName");
        for ( i.reset () ; i.value () < 5 ; i.increment () ) {
            if(i.value () != 0)
                market.appointShopOwner ( names[0], names[i.value () * 6],  "shopName");
        }

        for ( i.reset () ; i.value () < 5 ; i.increment () ) {
            threads[i.value ()] = new MyThread ( );
            threads[i.value ()].setRunnable ( new MyRunnable ( i.value () ) {
                @Override
                public void run() throws MarketException{
                    if(first.value ()){
                        first.makeFalse ();
                        test = i.value ();
                    }
                    market.addItemToShop ( names[index * 6], "item", i.value (), Item.Category.fruit, null, null, 1, "shopName" );
                }
            } );
        }
        for (i.reset () ; i.value () < 5 ; i.increment () ) {
            threads[i.value ()].start ( );
        }
        for (i.reset () ; i.value () < 5 ; i.increment () ) {
            if(threads[i.value ()].getEx () != null)
                numOfExceptions ++;
        }
        if(numOfExceptions < 4)
            assert false;
        else
            assert true;
    }

    @Test
    @DisplayName("Purchase concurrency test")
    public void purchaseConcurrencyTest() throws MarketException {
        int numOfExceptions = 0;
        for ( i.reset (); i.value () < 5 ; i.increment () ) {
            Visitor visitor = market.guestLogin ();
            market.register ( names[i.value () * 6], password );
            market.memberLogin ( names[i.value () * 6], password );
            market.validateSecurityQuestions ( names[i.value () * 6] , new ArrayList<> (  ), visitor.getName ());
        }
        market.openNewShop ( names[0],  "shopName");
        market.addItemToShop ( names[0], "item", i.value (), Item.Category.fruit, null, null, 1, "shopName" );

        for ( i.reset () ; i.value () < 5 ; i.increment () ) {
            threads[i.value ()] = new MyThread ( );
            threads[i.value ()].setRunnable ( new MyRunnable ( i.value () ) {
                @Override
                public void run() throws MarketException{
                    if(first.value ()){
                        first.makeFalse ();
                        test = i.value ();
                    }
                    List<Item> items = market.getItemByName ( "item" );
                    Item item = items.get ( 0 );
                    market.addItemToShoppingCart ( item, 1, "shopName", names[index * 6]);
                    market.buyShoppingCart ( names[index * 6], 10, new CreditCard ( "1111111111111111", "03/25", "555" ), new Address () );
                }
            } );
        }
        for (i.reset () ; i.value () < 5 ; i.increment () ) {
            threads[i.value ()].start ( );
        }
        for (i.reset () ; i.value () < 5 ; i.increment () ) {
            if(threads[i.value ()].getEx () != null)
                numOfExceptions ++;
        }
        if(numOfExceptions < 4)
            assert false;
        else
            assert true;
    }

    private void registerAndLogin() throws MarketException {
        for ( i.reset (); i.value () < 5 ; i.increment () ) {
            Visitor visitor = market.guestLogin ();
            visitors[i.value ()] = visitor;
            market.register ( names[i.value () * 6], password );
            market.memberLogin ( names[i.value () * 6], password );
            market.validateSecurityQuestions ( names[i.value () * 6] , new ArrayList<> (  ), visitor.getName ());
        }
    }

}
