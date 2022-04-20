package test.Bridge;

import main.businessLayer.Item;
import main.businessLayer.Market;
import main.businessLayer.PurchasePolicy;
import main.businessLayer.Shop;
import main.businessLayer.discountPolicy.DiscountPolicy;
import main.businessLayer.services.PaymentService;
import main.businessLayer.services.ProductsSupplyService;
import main.businessLayer.users.Member;
import main.businessLayer.users.Visitor;
import main.serviceLayer.FacadeObjects.*;

import java.util.List;

public interface SystemBridge {
    //     System UseCases
    public Response initMarket(PaymentService paymentService, ProductsSupplyService supplyService,
                                                         String userName, String password);

//    public boolean changePaymentService(PaymentService newPaymentService);

//    public boolean changeSupplyService(ProductsSupplyService newSupplyServ);

    public ResponseT<Boolean> makePayment(String accountDetails);

    public ResponseT<Boolean> makeSupply(String packageDetails, String userDetails);

//    public String getAllGatheredNotifications(String userName, String userPassword);

    // Visitor Use cases

    /**
     * generates a unique name (temporary)
     * example -visitor123
     * @return
     */
    public ResponseT<VisitorFacade> guestLogin();

    /**
     * if not a member - deletes from data
     */
    public Response exitSystem(String visitorName);

    public ResponseT<Boolean> register(String userName, String userPassword);

    public ResponseT<Boolean> addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers,
                                    MemberFacade member);


    public ResponseT<List<ShopFacade>> getAllShops();

    public ResponseT<List<ItemFacade>> getAllItemsByShop(ShopFacade shop);

    public ResponseT<ItemFacade> searchProductByName(String name);

    public ResponseT<ItemFacade> searchProductByCategory(Item.Category category);

    public ResponseT<ItemFacade> searchProductByKeyword(String keyWord);


    public ResponseT<List<ItemFacade>> filterItemByPrice(int minPrice, int maxPrice);

    public ResponseT<List<ItemFacade>> filterItemByItemRank(int minItemRank);

    public ResponseT<List<ItemFacade>> filterItemByShopRank(int minShopRank);

    public ResponseT<List<ItemFacade>> filterItemByCategory(Item.Category category);

    public Response addItemToShoppingCart(ItemFacade itemToInsert, int amount, String shopName,
                                          String visitorName);

    public ResponseT<ShoppingCartFacade> showShoppingCart(String visitorName);

    public ResponseT<> editItemFromShoppingCart(int amount, ItemFacade itemFacade, String shopName,
                                                String visitorName);

    /**
     * need to delete the temporary VisitorName from data
     * @param userName
     * @param userPassword
     * @param userAdditionalAnswers - empty list if no additional queries exist
     * @return
     */
    public ResponseT<MemberFacade> memberLogin(String userName, String userPassword, List<String> userAdditionalAnswers,
                                               String visitorName);
    // Member Use cases

    public Response logout();

    public void openNewShop();

    public void submitItemReview(Item item, String review);

    public void rankShop(Shop shop, String review, int rank);

    public void rankItem(Item item, String review, int rank);

    public void submitQuestionToShop(Shop shop, String query);

    public void complainForMissingIntegrity(String text, PurchasePolicy purchasePolicy, DiscountPolicy discountPolicy);

    public String getAllBuyingHistory(Member member);

    public String getMemberInfo(Member member);

    public String changeMemberPassword(String oldPass, String newPass);

    public String changeMemberAdditionalQueries(String pass, List<String> newQueries, String answers);

    // Shop Owner
    public void removeItem(Item item, int amount, Shop shop);

    public void addItem(Item item, int amount, Shop shop);

    public void changeItemInfo(Item item, String info, Shop shop);

    public void addNewDiscount(DiscountPolicy discount, Shop shop);

    public void removeDiscount(DiscountPolicy discount, Shop shop);

    public void removePurchasePolicy(PurchasePolicy purchasePolicy, Shop shop);

    public void addPurchasePolicy(PurchasePolicy purchasePolicy, Shop shop);

    public void setNewShopOwner(Member member, Shop shop);

    public void setNewShopManager(Member member, Shop shop);

    public void removeShopOwner(Member member, Shop shop);

    public void removeShopManager(Member member, Shop shop);

    // TODO need to insert permissions here as param
    public void setShopManagerPermissions();

    public void closeShop(Shop shop);

    public Shop reOpenShop(Shop closedShop);

    public String getShopEmployeesInfo(Shop shop);

    public List<String> getAllShopMessages(Shop shop);

    public String getShopPurchaseHistory(Shop shop);

    // System Manager
    public void shutDownSystem();

    public void removeMember(Member member);

    public List<String> getSystemMessages();

    public void replayToMessage(Member member, String answer);

    public String systemPurchaseHistory(Shop shop);

    public String systemPurchaseHistory(Member member);

    public String getAllSystemPurchaseHistory();

    public String getSystemOperationDetails();

}
