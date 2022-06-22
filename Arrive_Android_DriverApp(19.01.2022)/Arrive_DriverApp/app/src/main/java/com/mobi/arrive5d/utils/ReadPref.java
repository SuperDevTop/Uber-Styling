package com.mobi.arrive5d.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReadPref {

    String myprefs = "MyPrefs";
    int mode = Activity.MODE_PRIVATE;
    boolean result = false;
    String TAG = "ReadPref";
    Context ctx;
    String res = "";
    ArrayList<String> arrPackage;
    private SharedPreferences prefs;

    public ReadPref(Context ctx) {
        this.ctx = ctx;
        prefs = this.ctx.getSharedPreferences(myprefs, mode);
        arrPackage = new ArrayList<>();
    }

    public String getEmailid() {
        res = "";
        res = prefs.getString("email", "");
        return res;
    }

    public String getOnlinestatus() {
        res = "";
        res = prefs.getString("status", "");
        return res;
    }

    public String getImage() {
        res = "";
        res = prefs.getString("profile_image", "");
        return res;
    }

    public String getType() {
        res = "";
        res = prefs.getString("type", "");
        return res;
    }

    public String getGender() {
        res = "";
        res = prefs.getString("gender", "");
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

    public String getDriverId() {
        res = "";
        res = prefs.getString("driverId", "");
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

    public String getFirstName() {
        res = "";
        res = prefs.getString("firstName", "");
        return res;
    }

    public String getMiddleName() {
        res = "";
        res = prefs.getString("middleName", "");
        return res;
    }

    public String getLastName() {
        res = "";
        res = prefs.getString("lastName", "");
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

    public String getCountry() {
        res = "";
        res = prefs.getString("country", "");
        return res;
    }

    public String getCity() {
        res = "";
        res = prefs.getString("city", "");
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

    public String getStartPoint() {
        res = "";
        res = prefs.getString("start", "");
        return res;
    }

    public String getEndPoint() {
        res = "";
        res = prefs.getString("end", "");
        return res;
    }

    public String getUserRating() {
        res = "";
        res = prefs.getString("rating", "");
        return res;
    }

    public String getBookingId() {
        res = "";
        res = prefs.getString("bookingId", "");
        return res;
    }

    public String getVehicleSubType() {
        res = "";
        res = prefs.getString("subType", "");
        return res;
    }

    public String getDuration() {
        res = "";
        res = prefs.getString("duration", "");
        return res;
    }

    public String getReasonId() {
        res = "";
        res = prefs.getString("reason_id", "");
        return res;
    }

    public String getDoors() {
        res = "";
        res = prefs.getString("doors_value", "");
        return res;
    }

    public String getBelts() {
        res = "";
        res = prefs.getString("belts_value", "");
        return res;
    }

    public String getVehicleType() {
        res = "";
        res = prefs.getString("vehicle_type", "");
        return res;
    }

    public String getModel() {
        res = "";
        res = prefs.getString("model_type", "");
        return res;
    }

    public String getColor() {
        res = "";
        res = prefs.getString("color_type", "");
        return res;
    }

    public String getYear() {
        res = "";
        res = prefs.getString("year_type", "");
        return res;
    }

    public String getMake() {
        res = "";
        res = prefs.getString("make_type", "");
        return res;
    }


    public int getGenderIndex() {
        int index = prefs.getInt("gender_index", 0);
        return index;
    }

    public int getVehicleClassIndex() {
        int index = prefs.getInt("vehicleClass_index", 0);
        return index;
    }

    public int getVehicleSubtypeIndex() {
        int index = prefs.getInt("subType_index", 0);
        return index;
    }

    public int getYearIndex() {
        int index = prefs.getInt("year", 0);
        return index;
    }

    public int getModelIndex() {
        int index = prefs.getInt("model", 0);
        return index;
    }

    public int getColorIndex() {
        int index = prefs.getInt("color", 0);
        return index;
    }

    public int getMakeIndex() {
        int index = prefs.getInt("make", 0);
        return index;
    }

    public int getDoorsIndex() {
        int index = prefs.getInt("doors", 0);
        return index;
    }

    public int getBeltsIndex() {
        int index = prefs.getInt("belts", 0);
        return index;
    }

    public List<String> getVehicleImages(String key) {
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public String getSSN() {
        res = "";
        res = prefs.getString("ssn", "");
        return res;
    }

    public String getDob() {
        res = "";
        res = prefs.getString("dob", "");
        return res;
    }

    public String getLicenseNo() {
        res = "";
        res = prefs.getString("license", "");
        return res;
    }

    public String getExpiryDate() {
        res = "";
        res = prefs.getString("expiry", "");
        return res;
    }

    public String getAddress1() {
        res = "";
        res = prefs.getString("address1", "");
        return res;
    }

    public String getAddress2() {
        res = "";
        res = prefs.getString("address2", "");
        return res;
    }

    public String getZip() {
        res = "";
        res = prefs.getString("zip", "");
        return res;
    }

    public String getState() {
        res = "";
        res = prefs.getString("state", "");
        return res;
    }

    public String getLicensePlate() {
        res = "";
        res = prefs.getString("license_plate", "");
        return res;
    }

    public String getVehicleReg() {
        res = "";
        res = prefs.getString("vehicle_reg", "");
        return res;
    }

    public String getInsuranceImg() {
        res = "";
        res = prefs.getString("insurance_img", "");
        return res;
    }

    public String getDrivingLicenseImg() {
        res = "";
        res = prefs.getString("license_img", "");
        return res;
    }

    public String getAdharImg() {
        res = "";
        res = prefs.getString("aadhar_img", "");
        return res;
    }

    public String getVehicleImg() {
        res = "";
        res = prefs.getString("vehicle_img", "");
        return res;
    }

    public String getDriverRating() {
        res = "";
        res = prefs.getString("driver_rating", "");
        return res;
    }

    public String getBookingAmount() {
        res = "";
        res = prefs.getString("amount", "");
        return res;
    }
}
