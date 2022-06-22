package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 4/18/18.
 */

public class SearchDriverResponse {
    @SerializedName("result")
    @Expose
    private List<SearchDetail> details = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SearchDetail> getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
