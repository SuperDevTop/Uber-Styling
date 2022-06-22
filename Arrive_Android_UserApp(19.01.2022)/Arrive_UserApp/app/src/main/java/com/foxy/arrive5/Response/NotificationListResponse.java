package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 5/17/18.
 */

public class NotificationListResponse {
    @SerializedName("result")
    @Expose
    private List<NotificationList> result = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<NotificationList> getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }
}
