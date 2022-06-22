package com.mobi.arrive5d.API;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.mobi.arrive5d.logger.Logger;


public class PreferenceModel {

    String myprefs = "seegetwearapp";
    int mode = Activity.MODE_PRIVATE;
    boolean result = false;
    String TAG = "PreferenceModel";
    Context ctx;
    String res = "";
    private SharedPreferences prefs;

    public PreferenceModel(Context ctx) {
        this.ctx = ctx;
        prefs = this.ctx.getSharedPreferences(myprefs, mode);
    }

    public String getStringValue(String key) {
        res = "";
        res = prefs.getString(key, "");
        Logger.i(TAG, res);
        return res;
    }

    public void saveStringValue(String key, String res) {
        result = false;
        prefs = ctx.getSharedPreferences(myprefs, mode);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, res);
        result = editor.commit();
    }

    public boolean getBooleanValue(String key) {
        boolean res = false;
        res = prefs.getBoolean(key, false);
        Logger.i(TAG, res + "");
        return res;
    }

    public void saveBooleanValue(String key, boolean res) {
        result = false;
        prefs = ctx.getSharedPreferences(myprefs, mode);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, res);
        result = editor.commit();
    }

    public void clearall() {
        prefs = ctx.getSharedPreferences(myprefs, mode);
        SharedPreferences.Editor editor = prefs.edit();
        editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
