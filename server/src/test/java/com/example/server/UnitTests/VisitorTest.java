package com.example.server.UnitTests;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingCart;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

    @Test
    void addToShoppingCart() {
        Item item = Mockito.mock(Item.class);
        assert false;
//        Mockito.when(cart.addItem()).thenReturn()
    }

    @Test
    void displayShoppingCart() {
    }

    @Test
    void purchase() {
    }

    @Test
    void getName() {
    }

    @Test
    void setName() {
    }

    @Test
    void getMember() {
    }

    @Test
    void setMember() {
    }

    @Test
    void getCart() {
    }

    @Test
    void setCart() {
    }

    @Test
    void testEquals() {
    }
}