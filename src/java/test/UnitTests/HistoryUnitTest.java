package test.UnitTests;
import main.businessLayer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;


import java.util.HashMap;

import static org.mockito.Mockito.CALLS_REAL_METHODS;

//@ExtendWith(MockitoExtension.class)
public class HistoryUnitTest extends mainTest {

    @Mock
    Shop shop;
    Item item ;
    ShoppingCart shoppingCart;
    ShoppingBasket shoppingBasket;
    HashMap<Item, Double> items;
    HashMap<Shop,ShoppingBasket> cart;
    String itemName;
    String shopName;
    @BeforeEach
    public void historyUnitTestInit(){
        shoppingBasket = Mockito.mock(ShoppingBasket.class, CALLS_REAL_METHODS);
        item = Mockito.mock(Item.class, CALLS_REAL_METHODS);
        shoppingCart = Mockito.mock(ShoppingCart.class, CALLS_REAL_METHODS);
        shop = Mockito.mock(Shop.class, CALLS_REAL_METHODS);
        items =  new HashMap();
        cart =  new HashMap();
        itemName = "item_test";
        shopName = "shop_test";
    }

    @Test
    @DisplayName("History Unit Test - empty purchase")
    public void testEmptyPurchaseHistory() throws IllegalAccessException {
        // empty item name
        Mockito.when(item.getName()).thenReturn(itemName);
        Assertions.assertEquals(itemName,item.getReview().toString());
        Assertions.assertEquals("", new ShoppingBasket().getReview().toString());
        items.put(item, 0.0);
        // empty basket
        ReflectionTestUtils.setField(shoppingBasket,"items", items);
        Assertions.assertEquals("", shoppingBasket.getReview().toString());
        // empty cart
        Assertions.assertEquals("",new ShoppingCart().getReview().toString());
        cart.put(shop, shoppingBasket);
        ReflectionTestUtils.setField(shoppingCart,"cart", cart);
        String t = shoppingCart.getReview().toString();
        Assertions.assertEquals("", shoppingCart.getReview().toString());
    }

    @Test
    @DisplayName("History Unit Test - valid")
    public void testValidPurchaseHistory() throws IllegalAccessException {
        String itemName = "item_test";
        String shopName = "shop_test";
        Mockito.when(item.getName()).thenReturn(itemName);
        Mockito.when(shop.getShopName()).thenReturn(shopName);
        Assertions.assertEquals(itemName, item.getReview().toString() );
        items.put(item,1.0);
        setField(shoppingBasket, "items" , items);
        Assertions.assertTrue(shoppingBasket.getReview().toString().contains(itemName));
        cart.put(shop,shoppingBasket);
        setField(shoppingCart, "cart", cart);
        Assertions.assertTrue(shoppingCart.getReview().toString().contains(shopName));
        Assertions.assertTrue(shoppingCart.getReview().toString().contains(itemName));

    }
    @Test
    @DisplayName("History - close a shop")
    public void closeShopValid() throws MarketException {
        ClosedShopsHistory instance = ClosedShopsHistory.getInstance();
        instance.closeShop(shop);
        Assertions.assertFalse(instance.getClosedShops().isEmpty());
        Assertions.assertTrue(instance.getClosedShops().contains(shop));
    }

    private void setField(Object obj, String field, Object value){
        ReflectionTestUtils.setField(obj, field,value);
    }


}
