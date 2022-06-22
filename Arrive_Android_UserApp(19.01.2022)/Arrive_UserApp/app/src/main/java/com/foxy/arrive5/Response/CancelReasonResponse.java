package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 5/29/18.
 */

public class CancelReasonResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<CancelReason> result = null;
    @SerializedName("status")
    @Expose
    private String status;

    public String getMessage() {
        return message;
    }

    public List<CancelReason> getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }
}
