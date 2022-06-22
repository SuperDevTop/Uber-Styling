package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 6/1/18.
 */

public class ReviewsListResponse {

    @SerializedName("message")
    @Expose
    private List<Reviews> message = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Reviews> getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
