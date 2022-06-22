package com.mobi.arrive5d.IntroScreens;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.mobi.arrive5d.MainActivity;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.utils.ReadPref;
import com.mobi.arrive5d.utils.SavePref;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity implements Listener {
    private static int SPLASH_TIME_OUT = 3000;
    ReadPref readPref;
    double latitude, longitude;
    EasyWayLocation easyWayLocation;
    SavePref savePref;
    String[] permissions = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        easyWayLocation = new EasyWayLocation(this);
        easyWayLocation.setListener(this);
        savePref = new SavePref(SplashActivity.this);
        readPref = new ReadPref(SplashActivity.this);
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void locationOn() {
        if (checkPermissions()) {
            easyWayLocation.beginUpdates();
            latitude = easyWayLocation.getLatitude();
            longitude = easyWayLocation.getLongitude();
            savePref.saveLatitue(String.valueOf(latitude));
            savePref.saveLonitutte(String.valueOf(longitude));
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (readPref.getLoginToken() == false) {
//                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                        finish();
//                    } else {
//                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                        finish();
//                    }
//                }
//            }, SPLASH_TIME_OUT);
        }
    }

    @Override
    public void onPositionChanged() {
    }

    @Override
    public void locationCancelled() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermissions()) {
            easyWayLocation.beginUpdates();
            latitude = easyWayLocation.getLatitude();
            longitude = easyWayLocation.getLongitude();
            savePref.saveLatitue(String.valueOf(latitude));
            savePref.saveLonitutte(String.valueOf(longitude));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (readPref.getLoginToken() == false) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();

                    }
                }
            }, SPLASH_TIME_OUT);
        }
    }

    @Override
    protected void onPause() {
        if (checkPermissions()) {
            easyWayLocation.endUpdates();
        }
        super.onPause();
    }
}
