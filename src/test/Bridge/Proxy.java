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
import main.serviceLayer.FacadeObjects.ItemFacade;
import main.serviceLayer.FacadeObjects.ShopFacade;

import java.util.List;

public class Proxy implements SystemBridge {

    @Override
    public Market initMarket(PaymentService paymentService, ProductsSupplyService supplyService, String userName, String password) {
        return null;
    }


    @Override
    public boolean makePayment(String accountDetails) {
        return false;
    }

    @Override
    public boolean makeSupply(String packageDetails, String userDetails) {
        return false;
    }


    @Override
    public Visitor guestLogin() {
        return null;
    }

    @Override
    public void exitSystem() {

    }

    @Override
    public boolean register(String userName, String userPassword) {
        return false;
    }

    @Override
    public boolean addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers) {
        return false;
    }


    @Override
    public Visitor memberLogin(String userName, String userPassword, List<String> userAdditionalAnswers) {
        return null;
    }

    @Override
    public Visitor logout() {
        return null;
    }

    @Override
    public List<ShopFacade> getAllShops() {
        return null;
    }

    @Override
    public List<ItemFacade> getAllItemsByShop(ShopFacade shop) {
        return null;
    }

    @Override
    public ItemFacade searchProductByName(String name) {
        return null;
    }

    @Override
    public ItemFacade searchProductByKeyword(String keyWord) {
        return null;
    }

    @Override
    public void openNewShop() {

    }

    @Override
    public void submitItemReview(Item item, String review) {

    }

    @Override
    public void rankShop(Shop shop, String review, int rank) {

    }

    @Override
    public void rankItem(Item item, String review, int rank) {

    }

    @Override
    public void submitQuestionToShop(Shop shop, String query) {

    }

    @Override
    public void complainForMissingIntegrity(String text, PurchasePolicy purchasePolicy, DiscountPolicy discountPolicy) {

    }

    @Override
    public String getAllBuyingHistory(Member member) {
        return null;
    }

    @Override
    public String getMemberInfo(Member member) {
        return null;
    }

    @Override
    public String changeMemberPassword(String oldPass, String newPass) {
        return null;
    }

    @Override
    public String changeMemberAdditionalQueries(String pass, List<String> newQueries, String answers) {
        return null;
    }

    @Override
    public void removeItem(Item item, int amount, Shop shop) {

    }

    @Override
    public void addItem(Item item, int amount, Shop shop) {

    }

    @Override
    public void changeItemInfo(Item item, String info, Shop shop) {

    }

    @Override
    public void addNewDiscount(DiscountPolicy discount, Shop shop) {

    }

    @Override
    public void removeDiscount(DiscountPolicy discount, Shop shop) {

    }

    @Override
    public void removePurchasePolicy(PurchasePolicy purchasePolicy, Shop shop) {

    }

    @Override
    public void addPurchasePolicy(PurchasePolicy purchasePolicy, Shop shop) {

    }

    @Override
    public void setNewShopOwner(Member member, Shop shop) {

    }

    @Override
    public void setNewShopManager(Member member, Shop shop) {

    }

    @Override
    public void removeShopOwner(Member member, Shop shop) {

    }

    @Override
    public void removeShopManager(Member member, Shop shop) {

    }

    @Override
    public void setShopManagerPermissions() {

    }

    @Override
    public void closeShop(Shop shop) {

    }

    @Override
    public Shop reOpenShop(Shop closedShop) {
        return null;
    }

    @Override
    public String getShopEmployeesInfo(Shop shop) {
        return null;
    }

    @Override
    public List<String> getAllShopMessages(Shop shop) {
        return null;
    }

    @Override
    public String getShopPurchaseHistory(Shop shop) {
        return null;
    }

    @Override
    public void shutDownSystem() {

    }

    @Override
    public void removeMember(Member member) {

    }

    @Override
    public List<String> getSystemMessages() {
        return null;
    }

    @Override
    public void replayToMessage(Member member, String answer) {

    }

    @Override
    public String systemPurchaseHistory(Shop shop) {
        return null;
    }

    @Override
    public String systemPurchaseHistory(Member member) {
        return null;
    }

    @Override
    public String getAllSystemPurchaseHistory() {
        return null;
    }

    @Override
    public String getSystemOperationDetails() {
        return null;
    }
}
