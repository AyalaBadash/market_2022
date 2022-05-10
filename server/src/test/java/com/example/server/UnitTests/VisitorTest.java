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
    //TODO - For ayala and raz : do we really need this class?
    String name = "visitor";
    Member member = null;
    ShoppingCart cart = Mockito.mock(ShoppingCart.class);
    Visitor visitor;
    @BeforeEach
    public void initVisitor(){
        try {
            visitor =  new Visitor(name);
            assert true;
        } catch (MarketException e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    @DisplayName("Constructor test - invalid")
    public void testConstructorInvalid(){
        try{
            new Visitor("");
            assert false;
        }catch (Exception e){
            try {
                new Visitor(null);
                assert false;
            }
            catch (Exception ex){
                assert true;
            }
        }
    }

    @Test
    @DisplayName("Constructor test - valid details")
    public void ConstructorTest(){
        try {
            new Visitor("raz");
            assert true;
        }
        catch (Exception e)
        {
            assert false;
        }
    }
}