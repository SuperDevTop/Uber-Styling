package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 25/9/18.
 */

public class Highpaying {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("zone_name")
    @Expose
    private String zoneName;
    @SerializedName("latitude1")
    @Expose
    private String latitude1;
    @SerializedName("latitude2")
    @Expose
    private String latitude2;
    @SerializedName("latitude3")
    @Expose
    private String latitude3;
    @SerializedName("latitude4")
    @Expose
    private String latitude4;
    @SerializedName("longitude1")
    @Expose
    private String longitude1;
    @SerializedName("longitude2")
    @Expose
    private String longitude2;
    @SerializedName("longitude3")
    @Expose
    private String longitude3;
    @SerializedName("longitude4")
    @Expose
    private String longitude4;
    @SerializedName("high_by")
    @Expose
    private String highBy;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("create_date")
    @Expose
    private String createDate;

    public String getId() {
        return id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getLatitude1() {
        return latitude1;
    }

    public String getLatitude2() {
        return latitude2;
    }

    public String getLatitude3() {
        return latitude3;
    }

    public String getLatitude4() {
        return latitude4;
    }

    public String getLongitude1() {
        return longitude1;
    }

    public String getLongitude2() {
        return longitude2;
    }

    public String getLongitude3() {
        return longitude3;
    }

    public String getLongitude4() {
        return longitude4;
    }

    public String getHighBy() {
        return highBy;
    }

    public String getStatus() {
        return status;
    }

    public String getCreateDate() {
        return createDate;
    }
}
