package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 25/9/18.
 */

public class HighZoneResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("highpaying")
    @Expose
    private List<Highpaying> highpaying = null;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public List<Highpaying> getHighpaying() {
        return highpaying;
    }

}
