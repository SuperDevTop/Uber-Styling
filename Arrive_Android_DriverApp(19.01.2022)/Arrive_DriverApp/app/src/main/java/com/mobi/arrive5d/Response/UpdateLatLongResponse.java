package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/25/18.
 */

public class UpdateLatLongResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("avg_rating")
    @Expose
    private Integer avgRating;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public Integer getAvgRating() {
        return avgRating;
    }
}
