package com.bfcy.testndk;

public class TestDemo {
    static {
        System.loadLibrary("demo-lib");
    }

    public native String getString();

    public native String getName();
}
