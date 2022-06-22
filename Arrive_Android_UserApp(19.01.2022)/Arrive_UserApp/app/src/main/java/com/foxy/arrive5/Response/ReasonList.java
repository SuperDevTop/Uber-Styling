package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 6/8/18.
 */

public class ReasonList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("reason")
    @Expose
    private String reason;

    public String getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

}
