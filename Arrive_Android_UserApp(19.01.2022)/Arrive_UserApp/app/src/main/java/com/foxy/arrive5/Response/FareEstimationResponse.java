package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/8/18.
 */

public class FareEstimationResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private Integer result;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public Integer getResult() {
        return result;
    }

}
