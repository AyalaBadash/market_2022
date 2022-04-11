import java.util.List;
import java.util.Map;

public class Shop {
    String ShopID;
    Map<String,Item> itemMap; //<ItemID,Item>
    List<String> employees;
    PurchasePolicy purchasePolicy;
    DiscountPolicy discountPolicy;
    ProductsSupplyService supplyService;
    PaymentService paymentService;
}
