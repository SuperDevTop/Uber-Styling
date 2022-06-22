package com.foxy.arrive5.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SavePref {

    String myprefs = "MyPrefs";
    int mode = Activity.MODE_PRIVATE;
    boolean result = false;
    String TAG = "SavePref";
    Context ctx;
    private SharedPreferences prefs;

    public SavePref(Context ctx) {
        this.ctx = ctx;
        prefs = this.ctx.getSharedPreferences(myprefs, mode);
    }

    void printlog(String res) {
        Log.i(TAG, res);
    }

    public void clearall() {
        prefs = ctx.getSharedPreferences(myprefs, mode);
        SharedPreferences.Editor editor = prefs.edit();
        editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public void saveLogin(boolean res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("login_token", res);
        result = editor.commit();
    }

    public void saveReasonId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("reason_id", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveEmail(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveCreditCardNum(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("cardNumber", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveExpMonth(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("expMonth", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveExpYear(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("expYear", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveCVV(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("CVV", res);
        result = editor.commit();
        //printlog(res);
    }

    public void savePayPalEmail(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("payPalEmail", res);
        result = editor.commit();
        //printlog(res);
    }
    public void savePayPalPass(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("payPalPass", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveCarType(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("car_type", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveVehicleType(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("vehicle_type", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("id", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveUserId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userId", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveSubTypeId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("subVehicleId", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveNoPass(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("passengers", res);
        result = editor.commit();
        //printlog(res);
    }
    public void saveCode(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("code", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveName(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveMobile(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("mobile_no", res);
        result = editor.commit();
        printlog(res);
    }

    public void saveImage(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("profile_image", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveLatitue(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("latitude", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveLonitutte(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("longitude", res);
        result = editor.commit();
        //printlog(res);
    }

    public void savePaswword(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("password", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveBookingId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("bookingId", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveScheduleStart(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("start", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveScheduleEnd(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("end", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveStartLat(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("start_lat", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveStartLong(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("start_long", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveEndLat(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("end_lat", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveEndLong(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("end_long", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveScheduleDate(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("schedule_date", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveScheduleTime(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("schedule_time", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveInviteCode(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("invite_code", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveCity(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("city", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveMusic(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("music", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveAbout(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("about", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveTotalPoints(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("total_points", res);
        result = editor.commit();
        // printlog(res);
    }

    public void saveUsedPoint(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("used_point", res);
        result = editor.commit();
        //printlog(res);
    }

    public void savePointsAvailable(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("points_available", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveCancelledPoint(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("cancelled_point", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveJoinDate(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("join_date", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveFname(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("first_name", res);
        result = editor.commit();
        printlog(res);
    }

    public void saveFinalAmount(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("final_amount", res);
        result = editor.commit();
        printlog(res);
    }

    public void saveRoundAmountStatus(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("round_amount_status", res);
        result = editor.commit();
        printlog(res);
    }

    public void saveLname(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("last_name", res);
        result = editor.commit();
        // printlog(res);
    }

    public void saveScheduledAmount(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("schedule_amount", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveUserRating(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_rating", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveDriverName(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("driver_name", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveDriverMobile(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("driver_mobile", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveCustomerId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("customer_id", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveBookingAmount(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("amount", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveProfileType(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("profile_type", res);
        result = editor.commit();
        //printlog(res);
    }

    public void saveCardId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("card_id", res);
        result = editor.commit();
        //printlog(res);
    }
}
