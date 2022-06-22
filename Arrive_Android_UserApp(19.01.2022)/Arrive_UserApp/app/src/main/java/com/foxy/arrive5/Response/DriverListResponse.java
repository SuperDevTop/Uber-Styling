package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 6/13/18.
 */

public class DriverListResponse {
    @SerializedName("result")
    @Expose
    private List<DriverList> result = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<DriverList> getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }
}
