package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 6/11/18.
 */

public class DriverList {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("driver_code")
    @Expose
    private String driverCode;

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getDriverCode() {
        return driverCode;
    }
}
