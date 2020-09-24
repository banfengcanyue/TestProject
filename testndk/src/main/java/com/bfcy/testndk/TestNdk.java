package com.bfcy.testndk;

public class TestNdk {
    static {
        System.loadLibrary("demo-lib");
    }

    public native String getString();

    public native String getName();
}
