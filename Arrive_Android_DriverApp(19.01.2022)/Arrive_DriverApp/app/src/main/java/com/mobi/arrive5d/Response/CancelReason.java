package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 5/2/18.
 */

public class CancelReason {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cancel_reason")
    @Expose
    private String cancelReason;

    public String getId() {
        return id;
    }

    public String getCancelReason() {
        return cancelReason;
    }

}
