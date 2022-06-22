package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobi.arrive5d.model.TripDetail;

import java.util.List;

/**
 * Created by parangat on 6/13/18.
 */

public class OurServicesResponse {
    @SerializedName("result")
    @Expose
    private List<TripDetail> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<TripDetail> getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
