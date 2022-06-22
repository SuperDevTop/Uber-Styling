package com.mobi.arrive5d.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("notification_msg")
    @Expose
    private String notificationMsg;
    @SerializedName("notification_date")
    @Expose
    private String notificationDate;
    @SerializedName("notification_time")
    @Expose
    private String notificationTime;

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

}
