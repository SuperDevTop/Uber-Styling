package com.mobi.arrive5d.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by parangat on 1/31/18.
 */

public class ApplicationController extends Application implements Application.ActivityLifecycleCallbacks {

    public static final String TAG = CoreApp.class.getSimpleName();
    public static final String LATO_REGULAR = "fonts/Poppins-Light.ttf";
    private static ApplicationController instance;
    private static int numRunningActivities;

    public static boolean isAppInForeground() {
        return numRunningActivities > 0;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        numRunningActivities = 0;
        registerActivityLifecycleCallbacks(this);
        instance = this;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        numRunningActivities++;

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        numRunningActivities--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
//        MultiDex.install(this);
    }
}
