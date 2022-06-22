package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 6/14/18.
 */

public class ReasonsList {
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
