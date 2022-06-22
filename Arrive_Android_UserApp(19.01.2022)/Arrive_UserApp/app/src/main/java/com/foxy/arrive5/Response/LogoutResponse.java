package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/25/18.
 */

public class LogoutResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;H

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
