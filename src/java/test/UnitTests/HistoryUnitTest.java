package test.UnitTests;
import main.businessLayer.Item;
import main.businessLayer.Shop;
import main.businessLayer.ShoppingBasket;
import main.businessLayer.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
public class HistoryUnitTest extends mainTest {

    @Mock
    Shop shop;
    Item item = Mockito.mock(Item.class);
    ShoppingCart shoppingCart;
    ShoppingBasket shoppingBasket = Mockito.mock(ShoppingBasket.class);

    @Test
    @DisplayName("History Unit Test - empty purchase")
    public void testEmptyPurchaseHistory() throws IllegalAccessException {
        Mockito.when(item.getName()).thenReturn("test1");
        Assertions.assertEquals("test1", item.getName());
        HashMap<Item,Double> map = new HashMap<>();
        map.put(item,0.0);
        ReflectionTestUtils.setField(shoppingBasket,"items",map);
        System.out.println(shoppingBasket.isEmpty());
    }


}
