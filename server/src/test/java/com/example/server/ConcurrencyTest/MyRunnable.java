package com.example.server.ConcurrencyTest;

import com.example.server.businessLayer.MarketException;

public abstract class MyRunnable {
    int index;
    public MyRunnable(int i){
        index = i;
    }
    public abstract void run() throws MarketException;
}
