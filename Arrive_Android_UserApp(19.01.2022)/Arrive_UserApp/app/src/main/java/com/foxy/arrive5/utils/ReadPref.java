package com.foxy.arrive5.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ReadPref {

    String myprefs = "MyPrefs";
    int mode = Activity.MODE_PRIVATE;
    boolean result = false;
    String TAG = "ReadPref";
    Context ctx;
    String res = "";
    private SharedPreferences prefs;

    public ReadPref(Context ctx) {
        this.ctx = ctx;
        prefs = this.ctx.getSharedPreferences(myprefs, mode);
    }

    public String getEmailid() {
        res = "";
        res = prefs.getString("email", "");
        return res;
    }

    public String getCreditCard() {
        res = "";
        res = prefs.getString("cardNumber", "");
        return res;
    }

    public String getExpMonth() {
        res = "";
        res = prefs.getString("expMonth", "");
        return res;
    }

    public String getExpYear() {
        res = "";
        res = prefs.getString("expYear", "");
        return res;
    }

    public String getCVV() {
        res = "";
        res = prefs.getString("CVV", "");
        return res;
    }

    public String getPayPalEmail() {
        res = "";
        res = prefs.getString("payPalEmail", "");
        return res;
    }

    public String getPayPalPass() {
        res = "";
        res = prefs.getString("payPalPass", "");
        return res;
    }

    public String getCarType() {
        res = "";
        res = prefs.getString("car_type", "");
        return res;
    }

    public String getVehicleType() {
        res = "";
        res = prefs.getString("vehicle_type", "");
        return res;
    }

    public String getImage() {
        res = "";
        res = prefs.getString("profile_image", "");
        return res;
    }

    public String getLatitude() {
        res = "";
        res = prefs.getString("latitude", "");
        return res;
    }

    public String getLongitude() {
        res = "";
        res = prefs.getString("longitude", "");
        return res;
    }

    public String getType() {
        res = "";
        res = prefs.getString("type", "");
        return res;
    }

    public String getUserId() {
        res = "";
        res = prefs.getString("userId", "");
        return res;
    }

    public String getId() {
        res = "";
        res = prefs.getString("id", "");
        return res;
    }

    public String getNoPass() {
        res = "";
        res = prefs.getString("passengers", "");
        return res;
    }

    public String getSubTypeId() {
        res = "";
        res = prefs.getString("subVehicleId", "");
        return res;
    }
    public String getCode() {
        res = "";
        res = prefs.getString("code", "");
        return res;
    }

    public String getName() {
        res = "";
        res = prefs.getString("name", "");
        return res;
    }

    public String getMobile() {
        res = "";
        res = prefs.getString("mobile_no", "");
        return res;
    }

    public String getAddress() {
        res = "";
        res = prefs.getString("address", "");
        return res;
    }

    public boolean getLoginToken() {
        boolean res = false;
        res = prefs.getBoolean("login_token", false);
        return res;
    }

    public String getBookingId() {
        res = "";
        res = prefs.getString("bookingId", "");
        return res;
    }

    public String getPass() {
        res = "";
        res = prefs.getString("password", "");
        return res;
    }

    public String getNotification() {
        res = "";
        res = prefs.getString("push_notification", "");
        return res;
    }

    public String getInviteCode() {
        res = "";
        res = prefs.getString("invite_code", "");
        return res;
    }

    public String getScheduleStart() {
        res = "";
        res = prefs.getString("start", "");
        return res;
    }

    public String getScheduleEnd() {
        res = "";
        res = prefs.getString("end", "");
        return res;
    }

    public String getStartLat() {
        res = "";
        res = prefs.getString("start_lat", "");
        return res;
    }

    public String getStartLong() {
        res = "";
        res = prefs.getString("start_long", "");
        return res;
    }

    public String getEndLat() {
        res = "";
        res = prefs.getString("end_lat", "");
        return res;
    }

    public String getEndLong() {
        res = "";
        res = prefs.getString("end_long", "");
        return res;
    }

    public String getScheduleDate() {
        res = "";
        res = prefs.getString("schedule_date", "");
        return res;
    }

    public String getScheduleTime() {
        res = "";
        res = prefs.getString("schedule_time", "");
        return res;
    }

    public String getCity() {
        res = "";
        res = prefs.getString("city", "");
        return res;
    }

    public String getMusic() {
        res = "";
        res = prefs.getString("music", "");
        return res;
    }

    public String getAbout() {
        res = "";
        res = prefs.getString("about", "");
        return res;
    }

    public String getTotalPoints() {
        res = "";
        res = prefs.getString("total_points", "");
        return res;
    }

    public String getUsedPoint() {
        res = "";
        res = prefs.getString("used_point", "");
        return res;
    }

    public String getPointsAvailable() {
        res = "";
        res = prefs.getString("points_available", "");
        return res;
    }

    public String getCancelledPoint() {
        res = "";
        res = prefs.getString("cancelled_point", "");
        return res;
    }

    public String getJoinDate() {
        res = "";
        res = prefs.getString("join_date", "");
        return res;
    }

    public String getFname() {
        res = "";
        res = prefs.getString("first_name", "");
        return res;
    }

    public String getFinalAount() {
        res = "";
        res = prefs.getString("final_amount", "");
        return res;
    }

    public String getLname() {
        res = "";
        res = prefs.getString("last_name", "");
        return res;
    }

    public String getScheduledAmount() {
        res = "";
        res = prefs.getString("schedule_amount", "");
        return res;
    }

    public String getUserRating() {
        res = "";
        res = prefs.getString("user_rating", "");
        return res;
    }

    public String getDriverName() {
        res = "";
        res = prefs.getString("driver_name", "");
        return res;
    }

    public String getRoundAmountStatus() {
        res = "";
        res = prefs.getString("round_amount_status", "");
        return res;
    }

    public String getDriverMobile() {
        res = "";
        res = prefs.getString("driver_mobile", "");
        return res;
    }

    public String getCustomerID() {
        res = "";
        res = prefs.getString("customer_id", "");
        return res;
    }

    public String getBookingAmount() {
        res = "";
        res = prefs.getString("amount", "");
        return res;
    }


    public String getProfileType() {
        res = "";
        res = prefs.getString("profile_type", "");
        return res;
    }

    public String getCardId() {
        res = "";
        res = prefs.getString("card_id", "");
        return res;
    }

}
