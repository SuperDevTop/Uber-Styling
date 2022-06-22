package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 9/7/18.
 */

public class ReviesListResponse {
    @SerializedName("message")
    @Expose
    private List<ReviewsList> message = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<ReviewsList> getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
