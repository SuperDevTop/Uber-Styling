package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by parangat on 4/18/18.
 */

public class VehicleDetailResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private ArrayList<VehicleDetail> details = null;
    @SerializedName("status")
    @Expose
    private String status;

    public ArrayList<VehicleDetail> getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
