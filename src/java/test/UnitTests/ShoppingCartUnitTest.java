package test.UnitTests;

import main.businessLayer.Item;
import main.businessLayer.Shop;
import main.businessLayer.ShoppingBasket;
import main.businessLayer.ShoppingCart;
import main.serviceLayer.FacadeObjects.ItemFacade;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;

public class ShoppingCartUnitTest extends mainTest {


    @Mock
    Item item = Mockito.mock(Item.class);
    Shop shop = Mockito.mock(Shop.class);
    ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
    @Test
    @DisplayName("ShoppingCart Unit Test - edit cart")
    public void editShoppingCart() throws IllegalAccessException {
        shoppingCart.addItem(shop,item,10);
        Mockito.when(shoppingCart.getItemQuantity(item)).thenReturn(10);
        Assertions.assertEquals(10, shoppingCart.getItemQuantity(item));
        shoppingCart.editQuantity(15,item,shop.getShopName());
        Mockito.when(shoppingCart.getItemQuantity(item)).thenReturn(15);
        Assertions.assertEquals(15, shoppingCart.getItemQuantity(item));
    }
}
