package test.Bridge;

import main.*;
import main.discountPolicy.DiscountPolicy;
import main.services.PaymentService;
import main.services.ProductsSupplyService;
import main.users.Member;
import main.users.Visitor;

import java.util.List;

public interface SystemBridge {
    // System Use Cases
    public Market initMarket(PaymentService paymentService, ProductsSupplyService supplyService,
                             String userName, String password);

    public boolean changePaymentService(PaymentService newPaymentService);

    public boolean changeSupplyService(ProductsSupplyService newSupplyServ);

    public boolean makePayment(String accountDetails);

    public boolean makeSupply(String packageDetails, String userDetails);

    public String getAllGatheredNotifications(String userName, String userPassword);
    //TODO need to understand how to implement service for live notifications

    // Visitor Use cases
    public Visitor guestLogin();

    public void exitSystem();

    public boolean register(String userName, String userPassword,
                            List<String> userAdditionalQueries, List<String> userAdditionalAnswers);

    public Visitor memberLogin(String userName, String userPassword, List<String> userAdditionalAnswers);


    // Member Use cases
    public Visitor logout();

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
