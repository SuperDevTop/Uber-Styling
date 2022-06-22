package com.mobi.arrive5d.Response.MobileVerifyResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/12/18.
 */

public class MobileVerify {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("details")
    @Expose
    private VerifyDetails details;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("otp")
    @Expose
    private String otp;


    public String getStatus() {
        return status;
    }

    public VerifyDetails getDetails() {
        return details;
    }

    public String getMsg() {
        return message;
    }

    public String getOtp() {
        return otp;
    }

}
