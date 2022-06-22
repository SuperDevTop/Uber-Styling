package com.mobi.arrive5d.Response.LoginResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/12/18.
 */

public class Login {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("details")
    @Expose
    private LoginDetails details;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getStatus() {
        return status;
    }

    public LoginDetails getDetails() {
        return details;
    }

    public String getMsg() {
        return msg;
    }

}
