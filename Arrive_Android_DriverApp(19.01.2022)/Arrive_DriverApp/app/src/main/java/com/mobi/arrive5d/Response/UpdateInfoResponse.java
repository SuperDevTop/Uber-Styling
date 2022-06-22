package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/20/18.
 */

public class UpdateInfoResponse {

    @SerializedName("result")
    @Expose
    private InfoResult result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private String status;

    public InfoResult getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public String getStatus() {
        return status;
    }

}
