package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobi.arrive5d.Response.LoginResponse.LoginDetails;

/**
 * Created by parangat on 6/15/18.
 */

public class UpdateProfileResponse {
    @SerializedName("result")
    @Expose
    private LoginDetails result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public LoginDetails getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

}
