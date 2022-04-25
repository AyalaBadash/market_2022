package test.UnitTests;

import main.businessLayer.*;
import main.serviceLayer.FacadeObjects.ItemFacade;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class ShoppingCartUnitTest extends mainTest {


    @Mock
    Item item = Mockito.mock(Item.class);
    Shop shop = Mockito.mock(Shop.class);
    ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
    @Test
    @DisplayName("ShoppingCart Unit Test - edit cart")
    public void editShoppingCart() throws IllegalAccessException, MarketException {
        shoppingCart.addItem(shop,item,10);
        Mockito.when(shoppingCart.getItemQuantity(item)).thenReturn(10);
        Assertions.assertEquals(10, shoppingCart.getItemQuantity(item));
        shoppingCart.editQuantity(15,item,shop.getShopName());
        Mockito.when(shoppingCart.getItemQuantity(item)).thenReturn(15);
        Assertions.assertEquals(15, shoppingCart.getItemQuantity(item));
    }
}
