package com.mobi.arrive5d.Response.MobileVerifyResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/12/18.
 */

public class VerifyDetails {
    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

}
