package com.example.server.serviceLayer.Requests;

import com.example.server.serviceLayer.FacadeObjects.ItemFacade;

import java.util.List;

public class FilterItemByPriceRequest {
    private List<ItemFacade> items;
    private int minPrice;
    private int maxPrice;

    public FilterItemByPriceRequest() {
        this.items = items;
    }

    public FilterItemByPriceRequest(List<ItemFacade> items, int minPrice, int maxPrice) {
        this.items = items;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public List<ItemFacade> getItems() {
        return items;
    }

    public void setItems(List<ItemFacade> items) {
        this.items = items;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }
}
