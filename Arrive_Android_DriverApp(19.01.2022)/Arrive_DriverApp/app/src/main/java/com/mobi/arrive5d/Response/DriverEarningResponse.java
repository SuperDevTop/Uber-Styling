package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 6/18/18.
 */

public class DriverEarningResponse {
    @SerializedName("result")
    @Expose
    private EarningResult result;
    @SerializedName("status")
    @Expose
    private String status;

    public EarningResult getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

}
