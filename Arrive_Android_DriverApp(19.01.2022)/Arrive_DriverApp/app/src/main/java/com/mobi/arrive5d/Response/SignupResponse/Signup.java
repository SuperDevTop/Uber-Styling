package com.mobi.arrive5d.Response.SignupResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobi.arrive5d.Response.LoginResponse.LoginDetails;

/**
 * Created by parangat on 4/12/18.
 */

public class Signup {
    @SerializedName("details")
    @Expose
    private LoginDetails details;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public LoginDetails getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
