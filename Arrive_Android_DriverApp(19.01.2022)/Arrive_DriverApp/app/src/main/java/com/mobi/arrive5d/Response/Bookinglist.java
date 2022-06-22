package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 6/18/18.
 */

public class Bookinglist {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("start_point")
    @Expose
    private String startPoint;
    @SerializedName("end_point")
    @Expose
    private String endPoint;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("image")
    @Expose
    private String image;

    public String getName() {
        return name;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getAmount() {
        return amount;
    }

    public String getImage() {
        return image;
    }

}
