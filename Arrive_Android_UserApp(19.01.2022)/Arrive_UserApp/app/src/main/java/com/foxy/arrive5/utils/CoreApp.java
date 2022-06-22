package com.foxy.arrive5.utils;

import android.app.Application;
import android.content.Context;


public class CoreApp extends Application {
    private static CoreApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        // MultiDex.install(this);
    }
}