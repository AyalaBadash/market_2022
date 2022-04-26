package com.example.server.UnitTests;

import com.example.server.businessLayer.*;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.Visitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class VisitorTest {

    String name = "visitor";
    Member member = null;
    ShoppingCart cart = Mockito.mock(ShoppingCart.class);
    Visitor visitor;
    @BeforeEach
    public void initVisitor(){
        visitor =  new Visitor(name);
        visitor.setCart(cart);
        visitor.setMember(member);
    }

    @Test
    @DisplayName("Constructor test - invalid")
    public void testConstructorInvalid(){
        try{
            new Visitor("");
            new Visitor(null);
            // TODO need to be MarketException
        }catch (Exception ignored){}
        assert true;
    }

    @Test
    void leaveMarket() {
    }

    //TODO what is this
    @Test
    void saveShoppingCart() {
        assert false;
    }
    // TODO remove from class and here
    @Test
    void getShopInfo() {
        assert false;
    }


    //TODO method from visitor
    @Test
    void addToShoppingCart() {
        assert false;
//        Mockito.when(cart.addItem()).thenReturn()
    }

    // TODO remove from visitor
    @Test
    void displayShoppingCart() {
        assert false;
    }
    // TODO remove from visitor
    @Test
    void purchase() {
        assert false;
    }

    @Test
    void getName() {
        Assertions.assertEquals(visitor.getName(),name);
    }

    @Test
    @DisplayName("get and set member")
    void getMember() {
        member = Mockito.mock(Member.class);
        ShoppingBasket shoppingBasket =  Mockito.mock(ShoppingBasket.class);
        Shop shop = Mockito.mock(Shop.class);
        ShoppingCart memberCart = Mockito.mock(ShoppingCart.class);
        HashMap<Shop, ShoppingBasket> memberCartInfo =  new HashMap<>();
        memberCartInfo.put(shop,shoppingBasket);
        Mockito.when(memberCart.getCart()).thenReturn(memberCartInfo);
        Assertions.assertNull(visitor.getMember());
        Assertions.assertEquals(visitor.getCart().getCart().size(),0 );
        visitor.setMember(member);
        Assertions.assertEquals(visitor.getMember(),member );
        Assertions.assertEquals(visitor.getCart().getCart().size(),1 );

    }


}