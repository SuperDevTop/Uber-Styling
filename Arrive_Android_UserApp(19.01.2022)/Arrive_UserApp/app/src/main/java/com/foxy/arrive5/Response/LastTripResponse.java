package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 6/8/18.
 */

public class LastTripResponse {
    @SerializedName("result")
    @Expose
    private List<LastTrip> result = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<LastTrip> getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
