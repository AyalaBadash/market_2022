package com.example.server.serviceLayer;


import com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount.MaxCompositeDiscount;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.AmountOfItemCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.AndCompositeCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.CompositionCondition.OrCompositeCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.PriceCondition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.ConditionalDiscount;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.*;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.*;
import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.*;
import com.example.server.businessLayer.Market.ResourcesObjects.ErrorLog;
import com.example.server.businessLayer.Market.Appointment.Appointment;
import com.example.server.businessLayer.Payment.PaymentService;
import com.example.server.businessLayer.Publisher.Publisher;
import com.example.server.businessLayer.Supply.SupplyService;
import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.Shop;
import com.example.server.serviceLayer.FacadeObjects.*;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.*;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.Wrappers.*;
import org.springframework.boot.ansi.Ansi8BitColor;


import java.util.ArrayList;
import java.util.List;

public class MarketService {
    private static MarketService marketService = null;
    private Market market;

    private MarketService() {
        market = Market.getInstance();
    }

    public synchronized static MarketService getInstance() {
        if (marketService == null) {
            marketService = new MarketService();
        }
        return marketService;
    }

    public Response firstInitMarket(String userName, String password) {
        try {
            market.firstInitMarket(userName, password,false);
            return new Response();
        } catch (MarketException e) {
            return new Response(e.getMessage());
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public Response firstInitMarket(String userName, String password,String services,String data) {
        try {
            market.firstInitMarket(userName, password,services,data,false);
            return new Response();
        } catch (MarketException e) {
            return new Response(e.getMessage());
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public Response firstInitMarket(boolean b) {
        try {
            market.firstInitMarket(b);
            return new Response();
        } catch (MarketException e) {
            return new Response(e.getMessage());
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }
    public ResponseT<List<ItemFacade>> searchProductByName(String name) {
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> items = market.getItemByName(name);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : items) {
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }


        return toReturn;
    }

    public ResponseT<List<ItemFacade>> searchProductByCategory(Item.Category category) {
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> items = market.getItemByCategory(category);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : items) {
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<ItemFacade>> searchProductByKeyword(String keyWord) {
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> items = market.getItemsByKeyword(keyWord);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : items) {
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<ItemFacade>> filterItemByPrice(List<ItemFacade> items, double minPrice, double maxPrice) {
        List<Item> businessItems = new ArrayList<>();
        for (ItemFacade item : items) {
            try {
                businessItems.add(item.toBusinessObject());
            } catch (MarketException e) {
                return new ResponseT<>(e.getMessage());

            }
        }
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> filteredItems = market.filterItemsByPrice(businessItems, minPrice, maxPrice);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : filteredItems) {
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<ItemFacade>> filterItemByCategory(List<ItemFacade> items, Item.Category category) {
        List<Item> businessItems = new ArrayList<>();
        for (ItemFacade item : items) {
            try {
                businessItems.add(item.toBusinessObject());
            } catch (MarketException e) {
                return new ResponseT<>(e.getMessage());
            }
        }
        ResponseT<List<ItemFacade>> toReturn;
        try {
            List<Item> filteredItems = market.filterItemsByCategory(businessItems, category);
            List<ItemFacade> facadeItems = new ArrayList<>();
            for (Item item : filteredItems) {
                facadeItems.add(new ItemFacade(item));
            }
            toReturn = new ResponseT<>(facadeItems);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public Response openNewShop(String visitorName, String shopName) {
        try {
            market.openNewShop(visitorName, shopName);
            return new Response();
        } catch (MarketException marketException) {
            return new Response(marketException.getMessage());
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public Response updateShopItemAmount(String shopOwnerName, ItemFacade item, double amount, String shopName) {
        try {
            Item itemBL = new Item(item.getId(), item.getName(), item.getPrice(), item.getInfo(), item.getCategory(), item.getKeywords());
            market.setItemCurrentAmount(shopOwnerName, itemBL, amount, shopName);
            return new Response();
        } catch (MarketException e) {
            return new Response(e.getMessage());
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }


    public Response removeItemFromShop(String shopOwnerName, ItemFacade item, String shopName) {
        try {
            market.removeItemFromShop(shopOwnerName, item.getId(), shopName);
            return new Response();
        } catch (MarketException e) {
            return new Response(e.getMessage());
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }


    public ResponseT<ShopFacade> addItemToShop(String shopOwnerName, String name, double price, Item.Category category, String info,
                                               List<String> keywords, double amount, String shopName) {
        ResponseT<ShopFacade> response;
        try {
            Shop shop = market.addItemToShop(shopOwnerName, name, price, category, info, keywords, amount, shopName);
            response = new ResponseT(new ShopFacade(shop));
        } catch (MarketException e) {
            response = new ResponseT(e.getMessage());
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            response = new ResponseT<>(e.getMessage());
        }
        return response;

    }

    public Response setItemCurrentAmount(String shopOwnerName, ItemFacade item, double amount, String shopName) {
        try {
            Item itemBL = new Item(item.getId(), item.getName(), item.getPrice(), item.getInfo(), item.getCategory(), item.getKeywords());
            market.setItemCurrentAmount(shopOwnerName, itemBL, amount, shopName);
            return new Response();
        } catch (MarketException e) {
            return new Response(e.getMessage());
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public Response changeShopItemInfo(String shopOwnerName, String info, ItemFacade oldItem, String shopName) {
        try {
            market.changeShopItemInfo(shopOwnerName, info, oldItem.getId(), shopName);
            return new Response();
        } catch (MarketException e) {
            return new Response(e.getMessage());
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public Response editItem(ItemFacade newItem, String id) {
        try {
            market.editItem(newItem.toBusinessObject(), id);
            return new Response();
        } catch (MarketException e) {
            return new Response(e.getMessage());
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    public Response closeShop(String shopOwnerName, String shopName) {
        Response response;
        try {
            market.closeShop(shopOwnerName, shopName);
            response = new Response();
        } catch (MarketException e) {
            response = new Response(e.getMessage());
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            response = new Response(e.getMessage());
        }
        return response;
    }

    public ResponseT<List<AppointmentFacade>> getShopEmployeesInfo(String shopManagerName, String shopName) {
        ResponseT<List<AppointmentFacade>> toReturn;
        try {
            List<Appointment> employees = market.getShopEmployeesInfo(shopManagerName, shopName).values().stream().toList();
            List<AppointmentFacade> employeesFacadeList = new ArrayList<>();
            for (Appointment appointment : employees) {
                AppointmentFacade employeeFacade;
                if (appointment.isOwner()) {
                    //employeeFacade = new ShopOwnerAppointmentFacade((ShopOwnerAppointment) appointment);
                    employeeFacade = new ShopOwnerAppointmentFacade();
                    employeeFacade = employeeFacade.toFacade(appointment);
                } else {
                    employeeFacade = new ShopManagerAppointmentFacade();
                    employeeFacade = employeeFacade.toFacade(appointment);
                }
                employeesFacadeList.add(employeeFacade);
            }
            return new ResponseT<>(employeesFacadeList);
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    /**
     * relevant to shop manager
     *
     * @param shopManagerName
     * @param shopName
     * @return
     */
    public ResponseT<String> getShopPurchaseHistory(String shopManagerName, String shopName) {
        try {
            String history = market.getShopPurchaseHistory(shopManagerName, shopName).toString();
            return new ResponseT<>(history);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<>(e.getMessage());
        }
    }


    /**
     * relevant to system manager
     *
     * @param systemManagerName
     * @return
     */
    public ResponseT<String> getAllSystemPurchaseHistory(String systemManagerName) {
        try {
            String history = market.getAllSystemPurchaseHistory(systemManagerName).toString();
            return new ResponseT<>(history);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<>(e.getMessage());
        }
    }


    /**
     * relevant to system manager
     *
     * @param systemManagerName
     * @param shopName
     * @return
     */
    public ResponseT<String> getHistoryByShop(String systemManagerName, String shopName) {
        try {
            String history = market.getHistoryByShop(systemManagerName, shopName).toString();
            return new ResponseT<>(history);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<>(e.getMessage());
        }
    }


    public ResponseT<String> getHistoryByMember(String systemManagerName, String memberName) {
        try {
            String history = market.getHistoryByMember(systemManagerName, memberName).toString();
            return new ResponseT<>(history);
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<ShopFacade> getShopInfo(String member, String shopName) {
        ResponseT<ShopFacade> toReturn;
        try {
            Shop shop = market.getShopInfo(member, shopName);
            toReturn = new ResponseT<>(new ShopFacade(shop));
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public Response removeShopOwnerAppointment(String boss, String firedAppointed, String shopName) {
        Response response;
        try {
            market.removeShopOwnerAppointment(boss, firedAppointed, shopName);
            response = new Response();
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            response = new Response(e.getMessage());
        }
        return response;

    }

    public Response removeMember(String manager, String memberToRemove) {
        Response response;
        try {
            market.removeMember(manager, memberToRemove);
            response = new Response();
        } catch (MarketException e) {
            ErrorLog.getInstance().Log(e.getMessage());
            response = new Response(e.getMessage());
        }
        return response;
    }

    public ResponseT<ItemFacade> getItemInfo(String name, int itemId) {
        try {
            Item item = market.getItemById(name, itemId);
            return new ResponseT<>(new ItemFacade(item));
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<ItemFacade> getItemById(int id) {
        try {
            Item item = market.getItemByID(id);
            return new ResponseT<>(new ItemFacade(item));
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<String> getMarketInfo(String sysManager) {
        try {
            return new ResponseT<>(market.getMarketInfo(sysManager));
        } catch (Exception e) {
            ErrorLog.getInstance().Log(e.getMessage());
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response addDiscountToShop(String visitorName, String shopName, DiscountTypeWrapper discountTypeWrapper) {
        try {
            DiscountType discountType = discountTypeWrapper.toBusinessObject();
            market.addDiscountToShop(visitorName, shopName, discountType);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeDiscountFromShop(String visitorName, String shopName, DiscountTypeWrapper discountTypeWrapper) {
        try {
            DiscountType discountType = discountTypeWrapper.toBusinessObject();
            market.removeDiscountFromShop(visitorName, shopName, discountType);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addPurchasePolicyToShop(String visitorName, String shopName, PurchasePolicyTypeWrapper purchasePolicyTypeWrapper) {
        try {
            PurchasePolicyType purchasePolicyType = purchasePolicyTypeWrapper.toBusinessObject();
            market.addPurchasePolicyToShop (visitorName, shopName, purchasePolicyType);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removePurchasePolicyFromShop(String visitorName, String shopName, PurchasePolicyTypeWrapper purchasePolicyTypeWrapper) {
        try {
            PurchasePolicyType purchasePolicyType = purchasePolicyTypeWrapper.toBusinessObject();
            market.removePurchasePolicyFromShop (visitorName, shopName, purchasePolicyType);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response isServerInit() {
        try {
            if (market.isInit()) {
                return new Response();
            } else {
                return new Response("server has not yet been initialized ");
            }
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response setPaymentService(PaymentService o, String managerName) {
        try {
            if (market.setPaymentService(o,managerName)) {
                return new Response();
            } else {
                return new Response("fAILED TO SET SERVICE");
            }
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public Response setSupplyService(SupplyService o, String managerName) {
        try {
            if (market.setSupplyService(o,managerName)) {
                return new Response();
            } else {
                return new Response("fAILED TO SET SERVICE");
            }
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public Response setPublishService(Publisher o, String managerName) {
        try {
            if (market.setPublishService(o,managerName)) {
                return new Response();
            } else {
                return new Response("fAILED TO SET SERVICE");
            }
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<List<PurchasePolicyTypeWrapper>> getPurchasePoliciesOfShop(String visitorName, String shopName) {
        try {
            List<PurchasePolicyType> purchasePolicyTypes = market.getPurchasePoliciesOfShop(visitorName, shopName);
            List<PurchasePolicyTypeWrapper> purchasePolicyTypeWrappers = new ArrayList<> (  );
            for(PurchasePolicyType purchasePolicyType: purchasePolicyTypes)
                purchasePolicyTypeWrappers.add (createPurchasePolicyWrapper(purchasePolicyType));
            return new ResponseT(purchasePolicyTypeWrappers);
        } catch (Exception e) {
            return new ResponseT(e.getMessage());
        }
    }

    private PurchasePolicyTypeWrapper createPurchasePolicyWrapper(PurchasePolicyType purchasePolicyType) {
        PurchasePolicyTypeWrapper purchasePolicyTypeWrapper = new PurchasePolicyTypeWrapper ( );;
        if(purchasePolicyType.isAtLeast ()){
            AtLeastPurchasePolicyType atLeastPurchasePolicyType = (AtLeastPurchasePolicyType) purchasePolicyType;
            purchasePolicyTypeWrapper.setPurchasePolicyTypeWrapperType ( PurchasePolicyTypeWrapper.PurchasePolicyTypeWrapperType.AtLeastPurchasePolicyTypeFacade );
            purchasePolicyTypeWrapper.setPurchasePolicyLevelStateWrapper ( createPurchasePolicyLevelStateWrapper(purchasePolicyType.getPurchasePolicyLevelState () ));
            purchasePolicyTypeWrapper.setAmount ( atLeastPurchasePolicyType.getAmount () );
        } else if (purchasePolicyType.isAtMost ()){
            AtMostPurchasePolicyType atMostPurchasePolicyType = (AtMostPurchasePolicyType) purchasePolicyType;
            purchasePolicyTypeWrapper.setPurchasePolicyTypeWrapperType ( PurchasePolicyTypeWrapper.PurchasePolicyTypeWrapperType.AtMostPurchasePolicyTypeFacade );
            purchasePolicyTypeWrapper.setPurchasePolicyLevelStateWrapper ( createPurchasePolicyLevelStateWrapper(purchasePolicyType.getPurchasePolicyLevelState () ));
            purchasePolicyTypeWrapper.setAmount ( atMostPurchasePolicyType.getAmount () );
        } else {
            List<PurchasePolicyTypeWrapper> purchasePolicyTypeWrappers = new ArrayList<> (  );
            OrCompositePurchasePolicyType orCompositePurchasePolicyType = (OrCompositePurchasePolicyType) purchasePolicyType;
            for( PurchasePolicyType cur : orCompositePurchasePolicyType.getPolicies ()){
                purchasePolicyTypeWrappers.add ( createPurchasePolicyWrapper ( cur ) );
            }
            purchasePolicyTypeWrapper.setCompositePurchasePolicyTypeWrapperType ( PurchasePolicyTypeWrapper.PurchasePolicyTypeWrapperType.OrCompositePurchasePolicyTypeFacade );
            purchasePolicyTypeWrapper.setPurchasePolicyTypeWrappers ( purchasePolicyTypeWrappers );
        }
        return purchasePolicyTypeWrapper;
    }

    private PurchasePolicyLevelStateWrapper createPurchasePolicyLevelStateWrapper(PurchasePolicyLevelState purchasePolicyLevelState){
        PurchasePolicyLevelStateWrapper purchasePolicyLevelStateWrapper = new PurchasePolicyLevelStateWrapper ();
        if(purchasePolicyLevelState.isItemLevel ()){
            ItemPurchasePolicyLevelState itemPurchasePolicyLevelState = (ItemPurchasePolicyLevelState) purchasePolicyLevelState;
            purchasePolicyLevelStateWrapper.setCompositeDiscountLevelStateWrapperType ( PurchasePolicyLevelStateWrapper.PurchasePolicyLevelStateWrapperType.ItemPurchasePolicyLevelStateFacade );
            purchasePolicyLevelStateWrapper.setItemID ( itemPurchasePolicyLevelState.getItemId () );
        } else if (purchasePolicyLevelState.isCategoryLevel ()) {
            CategoryPurchasePolicyLevelState categoryPurchasePolicyLevelState = (CategoryPurchasePolicyLevelState) purchasePolicyLevelState;
            purchasePolicyLevelStateWrapper.setCompositeDiscountLevelStateWrapperType ( PurchasePolicyLevelStateWrapper.PurchasePolicyLevelStateWrapperType.CategoryPurchasePolicyLevelStateFacade );
            purchasePolicyLevelStateWrapper.setCategory ( categoryPurchasePolicyLevelState.getCategory () );
        } else if (purchasePolicyLevelState.isShopLevel ()) {
            purchasePolicyLevelStateWrapper.setCompositeDiscountLevelStateWrapperType ( PurchasePolicyLevelStateWrapper.PurchasePolicyLevelStateWrapperType.ShopPurchasePolicyLevelStateFacade );
        } else if (purchasePolicyLevelState.isOrLevel ()){
            OrCompositePurchasePolicyLevelState orCompositePurchasePolicyLevelState = (OrCompositePurchasePolicyLevelState) purchasePolicyLevelState;
            List<PurchasePolicyLevelStateWrapper> purchasePolicyLevelStateWrappers = new ArrayList<> (  );
            for(PurchasePolicyLevelState cur : orCompositePurchasePolicyLevelState.getPurchasePolicyLevelStates ()){
                purchasePolicyLevelStateWrappers.add ( createPurchasePolicyLevelStateWrapper ( (cur) ) );
            }
            purchasePolicyLevelStateWrapper.setCompositeDiscountLevelStateWrapperType ( PurchasePolicyLevelStateWrapper.PurchasePolicyLevelStateWrapperType.OrCompositePurchasePolicyLevelStateFacade );
            purchasePolicyLevelStateWrapper.setPurchasePolicyLevelStateWrappers ( purchasePolicyLevelStateWrappers );
        } else if (purchasePolicyLevelState.isXorLevel ()) {
            XorCompositePurchasePolicyLevelState xorCompositePurchasePolicyLevelState = (XorCompositePurchasePolicyLevelState) purchasePolicyLevelState;
            List<PurchasePolicyLevelStateWrapper> purchasePolicyLevelStateWrappers = new ArrayList<> (  );
            for(PurchasePolicyLevelState cur : xorCompositePurchasePolicyLevelState.getPurchasePolicyLevelStates ()){
                purchasePolicyLevelStateWrappers.add ( createPurchasePolicyLevelStateWrapper ( (cur) ) );
            }
            purchasePolicyLevelStateWrapper.setCompositeDiscountLevelStateWrapperType ( PurchasePolicyLevelStateWrapper.PurchasePolicyLevelStateWrapperType.XorCompositePurchasePolicyLevelStateFacade );
            purchasePolicyLevelStateWrapper.setPurchasePolicyLevelStateWrappers ( purchasePolicyLevelStateWrappers );
        } else {
            AndCompositePurchasePolicyLevelState andCompositePurchasePolicyLevelState = (AndCompositePurchasePolicyLevelState) purchasePolicyLevelState;
            List<PurchasePolicyLevelStateWrapper> purchasePolicyLevelStateWrappers = new ArrayList<> (  );
            for(PurchasePolicyLevelState cur : andCompositePurchasePolicyLevelState.getPurchasePolicyLevelStates ()){
                purchasePolicyLevelStateWrappers.add ( createPurchasePolicyLevelStateWrapper ( (cur) ) );
            }
            purchasePolicyLevelStateWrapper.setCompositeDiscountLevelStateWrapperType ( PurchasePolicyLevelStateWrapper.PurchasePolicyLevelStateWrapperType.AndCompositePurchasePolicyLevelStateFacade );
            purchasePolicyLevelStateWrapper.setPurchasePolicyLevelStateWrappers ( purchasePolicyLevelStateWrappers );
        }
        return purchasePolicyLevelStateWrapper;
    }

    public ResponseT<List<DiscountTypeWrapper>> getDiscountTypesOfShop(String visitorName, String shopName){
        try {
            List<DiscountType> discountTypeList = market.getDiscountTypesOfShop(visitorName, shopName);
            List<DiscountTypeWrapper> discountTypeWrappers = new ArrayList<> (  );
            for(DiscountType discountType: discountTypeList)
                discountTypeWrappers.add (createDiscountTypeWrapper(discountType));
            return new ResponseT(discountTypeWrappers);
        } catch (Exception e) {
            return new ResponseT(e.getMessage());
        }
    }

    private DiscountTypeWrapper createDiscountTypeWrapper(DiscountType discountType) {
        DiscountTypeWrapper discountTypeWrapper = new DiscountTypeWrapper (  );
        discountTypeWrapper.setPercentageOfDiscount ( discountType.getPercentageOfDiscount () );
        discountTypeWrapper.setDiscountLevelStateWrapper ( createDiscountLevelStateWrapper(discountType.getDiscountLevelState ()) );
        if(discountType.isSimple ()){
            discountTypeWrapper.setCompositeDiscountTypeWrapperType ( DiscountTypeWrapper.DiscountTypeWrapperType.SimpleDiscountFacade );
        }else if(discountType.isConditional ()){
            ConditionalDiscount conditionalDiscount = (ConditionalDiscount) discountType;
            discountTypeWrapper.setCompositeDiscountTypeWrapperType ( DiscountTypeWrapper.DiscountTypeWrapperType.ConditionalDiscountFacade );
            discountTypeWrapper.setConditionWrapper ( createConditionWrapper(conditionalDiscount.getCondition ()) );
        }else{
            MaxCompositeDiscount maxCompositeDiscount = (MaxCompositeDiscount) discountType;
            List<DiscountTypeWrapper> discountTypeWrappers = new ArrayList<> (  );
            for(DiscountType cur : maxCompositeDiscount.getDiscountTypes ()){
                discountTypeWrappers.add ( createDiscountTypeWrapper ( cur ) );
            }
            discountTypeWrapper.setCompositeDiscountTypeWrapperType ( DiscountTypeWrapper.DiscountTypeWrapperType.MaxCompositeDiscountTypeFacade );
            discountTypeWrapper.setDiscountTypeWrappers ( discountTypeWrappers );
        }
        return discountTypeWrapper;
    }

    private ConditionWrapper createConditionWrapper(Condition condition) {
        ConditionWrapper conditionWrapper = new ConditionWrapper (  );
        if(condition.isPrice ()){
            PriceCondition priceCondition = (PriceCondition) condition;
            conditionWrapper.setCompositeConditionWrapperType ( ConditionWrapper.ConditionWrapperType.PriceConditionFacade );
            conditionWrapper.setPrice ( priceCondition.getPriceNeeded () );
        } else if (condition.isAmountOfItem ()) {
            AmountOfItemCondition amountOfItemCondition = (AmountOfItemCondition) condition;
            conditionWrapper.setCompositeConditionWrapperType ( ConditionWrapper.ConditionWrapperType.AmountOfItemConditionFacade );
            conditionWrapper.setAmount ( amountOfItemCondition.getAmountNeeded () );
            conditionWrapper.setItemID ( amountOfItemCondition.getItemNeeded () );
        } else if (condition.isAnd ()) {
            AndCompositeCondition andCompositeCondition = (AndCompositeCondition) condition;
            List<ConditionWrapper> conditionWrappers = new ArrayList<> (  );
            for(Condition cur : andCompositeCondition.getConditions ()){
                conditionWrappers.add ( createConditionWrapper ( cur ) );
            }
            conditionWrapper.setCompositeConditionWrapperType ( ConditionWrapper.ConditionWrapperType.AndCompositeConditionFacade );
            conditionWrapper.setConditionWrappers ( conditionWrappers );
        } else{
            OrCompositeCondition orCompositeCondition = (OrCompositeCondition) condition;
            List<ConditionWrapper> conditionWrappers = new ArrayList<> (  );
            for(Condition cur : orCompositeCondition.getConditions ()){
                conditionWrappers.add ( createConditionWrapper ( cur ) );
            }
            conditionWrapper.setCompositeConditionWrapperType ( ConditionWrapper.ConditionWrapperType.OrCompositeConditionFacade );
            conditionWrapper.setConditionWrappers ( conditionWrappers );
        }
        return conditionWrapper;
    }

    private DiscountLevelStateWrapper createDiscountLevelStateWrapper(DiscountLevelState discountLevelState) {
        DiscountLevelStateWrapper discountLevelStateWrapper = new DiscountLevelStateWrapper (  );
        if(discountLevelState.isItem ()){
            ItemLevelState itemLevelState = (ItemLevelState) discountLevelState;
            discountLevelStateWrapper.setCompositeDiscountLevelStateWrapperType ( DiscountLevelStateWrapper.DiscountLevelStateWrapperType.ItemLevelStateFacade );
            discountLevelStateWrapper.setItemID ( itemLevelState.getItemID () );
        } else if (discountLevelState.isCategory ()) {
            CategoryLevelState categoryLevelState = (CategoryLevelState) discountLevelState;
            discountLevelStateWrapper.setCompositeDiscountLevelStateWrapperType ( DiscountLevelStateWrapper.DiscountLevelStateWrapperType.CategoryLevelStateFacade );
            discountLevelStateWrapper.setCategory ( categoryLevelState.getCategory () );
        } else if (discountLevelState.isShop ()) {
            discountLevelStateWrapper.setCompositeDiscountLevelStateWrapperType ( DiscountLevelStateWrapper.DiscountLevelStateWrapperType.ShopLevelStateFacade );
        } else if (discountLevelState.isAnd ()) {
            AndCompositeDiscountLevelState andCompositeDiscountLevelState = (AndCompositeDiscountLevelState) discountLevelState;
            List<DiscountLevelStateWrapper> discountLevelStateWrappers = new ArrayList<> (  );
            for(DiscountLevelState cur : andCompositeDiscountLevelState.getDiscountLevelStates ()){
                discountLevelStateWrappers.add ( createDiscountLevelStateWrapper ( cur ) );
            }
            discountLevelStateWrapper.setCompositeDiscountLevelStateWrapperType ( DiscountLevelStateWrapper.DiscountLevelStateWrapperType.AndCompositeDiscountLevelStateFacade );
            discountLevelStateWrapper.setDiscountLevelStateWrappers ( discountLevelStateWrappers );
        } else {
            MaxXorCompositeDiscountLevelState maxXorCompositeDiscountLevelState = (MaxXorCompositeDiscountLevelState) discountLevelState;
            List<DiscountLevelStateWrapper> discountLevelStateWrappers = new ArrayList<> (  );
            for(DiscountLevelState cur : maxXorCompositeDiscountLevelState.getDiscountLevelStates ()){
                discountLevelStateWrappers.add ( createDiscountLevelStateWrapper ( cur ) );
            }
            discountLevelStateWrapper.setCompositeDiscountLevelStateWrapperType ( DiscountLevelStateWrapper.DiscountLevelStateWrapperType.MaxXorCompositeDiscountLevelStateFacade );
            discountLevelStateWrapper.setDiscountLevelStateWrappers ( discountLevelStateWrappers );
        }
        return discountLevelStateWrapper;
    }


}
