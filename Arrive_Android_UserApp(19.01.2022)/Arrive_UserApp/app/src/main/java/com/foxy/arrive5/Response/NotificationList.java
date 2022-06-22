package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 5/17/18.
 */

public class NotificationList {
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
