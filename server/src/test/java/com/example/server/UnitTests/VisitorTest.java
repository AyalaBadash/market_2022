package com.example.server.UnitTests;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingCart;
import com.example.server.businessLayer.Market.Users.Member;
import com.example.server.businessLayer.Market.Users.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VisitorTest {
    //TODO - For ayala and raz : do we really need this class?
    String name = "visitor";
    Member member = null;
    ShoppingCart cart = Mockito.mock(ShoppingCart.class);
    Visitor visitor;
    int nextCartID;
    @BeforeEach
    public void initVisitor(){
        try {
            visitor =  new Visitor(name,1);
            nextCartID=2;
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
            new Visitor("",nextCartID);
            assert false;
        }catch (Exception e){
            try {
                new Visitor(null,nextCartID);
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
            new Visitor("raz",nextCartID);
            assert true;
        }
        catch (Exception e)
        {
            assert false;
        }
    }
}