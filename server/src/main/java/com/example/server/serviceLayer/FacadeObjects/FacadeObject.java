package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.ResourcesObjects.MarketException;

public interface FacadeObject<T> {
    public T toBusinessObject() throws MarketException;
}
