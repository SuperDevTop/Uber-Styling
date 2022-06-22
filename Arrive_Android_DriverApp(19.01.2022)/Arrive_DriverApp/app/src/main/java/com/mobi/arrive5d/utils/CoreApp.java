package com.mobi.arrive5d.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by android on 7/10/17.
 */

public class CoreApp extends Application {
    public static final String TAG = CoreApp.class.getSimpleName();
    private static CoreApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        //MultiDex.install(this);
    }
}