package com.mobi.arrive5d.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SavePref {

    String myprefs = "MyPrefs";
    int mode = Activity.MODE_PRIVATE;
    boolean result = false;
    String TAG = "SavePref";
    Context ctx;
    ArrayList<String> arrPackage;

    private SharedPreferences prefs;

    public SavePref(Context ctx) {
        this.ctx = ctx;
        prefs = this.ctx.getSharedPreferences(myprefs, mode);
        arrPackage = new ArrayList<>();
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

    public void saveEmail(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", res);
        result = editor.commit();
    }

    public void saveDriverId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("driverId", res);
        result = editor.commit();
    }

    public void saveId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("id", res);
        result = editor.commit();
    }

    public void saveUserId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userId", res);
        result = editor.commit();
    }

    public void saveOnlineStatus(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("status", res);
        result = editor.commit();
    }

    public void saveCode(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("code", res);
        result = editor.commit();
    }

    public void saveName(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", res);
        result = editor.commit();
    }

    public void saveFirstName(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("firstName", res);
        result = editor.commit();
    }

    public void saveMiddleName(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("middleName", res);
        result = editor.commit();
    }

    public void saveLastName(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastName", res);
        result = editor.commit();
    }

    public void saveMobile(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("mobile_no", res);
        result = editor.commit();
    }

    public void saveGender(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("gender", res);
        result = editor.commit();
    }

    public void saveImage(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("profile_image", res);
        result = editor.commit();
    }

    public void saveLatitue(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("latitude", res);
        result = editor.commit();
    }

    public void saveLonitutte(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("longitude", res);
        result = editor.commit();
    }

    public void saveCountry(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("country", res);
        result = editor.commit();
    }

    public void saveCity(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("city", res);
        result = editor.commit();
    }

    public void saveStartPoint(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("start", res);
        result = editor.commit();
    }

    public void saveEndPoint(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("end", res);
        result = editor.commit();
    }

    public void saveUserRating(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("rating", res);
        result = editor.commit();
    }

    public void saveBookingId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("bookingId", res);
        result = editor.commit();
    }

    public void saveVehicleSubType(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("subType", res);
        result = editor.commit();
    }

    public void saveDuration(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("duration", res);
        result = editor.commit();
    }

    public void saveReasonId(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("reason_id", res);
        result = editor.commit();
    }

    public void saveAddress(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("address", res);
        result = editor.commit();
    }

    public void saveDoors(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("doors_value", res);
        result = editor.commit();
    }

    public void saveBelts(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("belts_value", res);
        result = editor.commit();
    }

    public void saveModel(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("model_type", res);
        result = editor.commit();
    }

    public void saveColor(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("color_type", res);
        result = editor.commit();
    }

    public void saveVehicleType(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("vehicle_type", res);
        result = editor.commit();
    }

    public void saveYear(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("year_type", res);
        result = editor.commit();
    }

    public void saveMake(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("make_type", res);
        result = editor.commit();
    }

    public void saveGenderIndex(int index) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("gender_index", index);
        result = editor.commit();
    }

    public void saveVehicleClassIndex(int index) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("vehicleClass_index", index);
        result = editor.commit();
    }

    public void saveVehicleSubtypeIndex(int index) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("subType_index", index);
        result = editor.commit();
    }

    public void saveYearIndex(int index) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("year", index);
        result = editor.commit();
    }

    public void saveModelIndex(int index) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("model", index);
        result = editor.commit();
    }

    public void saveColorIndex(int index) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("color", index);
        result = editor.commit();
    }

    public void saveMakeIndex(int index) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("make", index);
        result = editor.commit();
    }

    public void saveDoorsIndex(int index) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("doors", index);
        result = editor.commit();
    }

    public void saveBeltsIndex(int index) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("belts", index);
        result = editor.commit();
    }

    public void saveVehicleImages(List<String> images, String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(images);
        editor.putString(res, json);
        editor.apply();
    }

    public void saveSSN(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ssn", res);
        result = editor.commit();
    }

    public void saveDob(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("dob", res);
        result = editor.commit();
    }

    public void saveLicenseNo(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("license", res);
        result = editor.commit();
    }

    public void saveExpiryDate(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("expiry", res);
        result = editor.commit();

    }

    public void saveAddress1(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("address1", res);
        result = editor.commit();
    }

    public void saveAddress2(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("address2", res);
        result = editor.commit();
    }

    public void saveZip(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("zip", res);
        result = editor.commit();

    }

    public void saveState(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("state", res);
        result = editor.commit();

    }

    public void saveLicensePlate(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("license_plate", res);
        result = editor.commit();

    }

    public void saveVehicleReg(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("vehicle_reg", res);
        result = editor.commit();

    }

    public void saveInsuranceImg(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("insurance_img", res);
        result = editor.commit();

    }

    public void saveDrivingLicenseImg(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("license_img", res);
        result = editor.commit();

    }

    public void saveAdharImg(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("aadhar_img", res);
        result = editor.commit();

    }

    public void saveVehicleImg(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("vehicle_img", res);
        result = editor.commit();

    }

    public void saveDriverRating(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("driver_rating", res);
        result = editor.commit();

    }

    public void saveBookingAmount(String res) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("amount", res);
        result = editor.commit();
    }
}
