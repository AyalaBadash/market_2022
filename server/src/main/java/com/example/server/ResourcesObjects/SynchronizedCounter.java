package com.example.server.ResourcesObjects;

public class SynchronizedCounter {
    private int c = 1;

    public synchronized int increment() {
        c++;
        return (c - 1);
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }

    public synchronized void reset() {
        c = 1;
    }

}
