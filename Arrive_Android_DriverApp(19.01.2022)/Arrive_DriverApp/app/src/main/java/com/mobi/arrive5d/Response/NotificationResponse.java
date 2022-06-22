package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobi.arrive5d.model.Notification;

import java.util.List;

/**
 * Created by parangat on 5/29/18.
 */

public class NotificationResponse {
    @SerializedName("result")
    @Expose
    private List<Notification> result = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Notification> getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

}
