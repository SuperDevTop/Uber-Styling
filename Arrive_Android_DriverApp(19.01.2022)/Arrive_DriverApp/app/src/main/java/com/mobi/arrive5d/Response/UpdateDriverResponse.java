package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobi.arrive5d.Response.LoginResponse.LoginDetails;

/**
 * Created by parangat on 4/20/18.
 */

public class UpdateDriverResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("result")
    @Expose
    private LoginDetails details;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public LoginDetails getDetails() {
        return details;
    }
}
