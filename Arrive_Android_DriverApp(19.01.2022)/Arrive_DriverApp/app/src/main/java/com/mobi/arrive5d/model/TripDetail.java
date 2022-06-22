package com.mobi.arrive5d.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TripDetail {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("start_point")
    @Expose
    private String startPoint;
    @SerializedName("end_point")
    @Expose
    private String endPoint;
    @SerializedName("start_point_lat")
    @Expose
    private String startPointLat;
    @SerializedName("start_point_long")
    @Expose
    private String startPointLong;
    @SerializedName("end_point_lat")
    @Expose
    private String endPointLat;
    @SerializedName("end_point_long")
    @Expose
    private String endPointLong;
    @SerializedName("schedule_date")
    @Expose
    private String scheduleDate;
    @SerializedName("schedule_time")
    @Expose
    private String scheduleTime;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("mode_text")
    @Expose
    private String modeText;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_img")
    @Expose
    private String userImg;

    public String getUserId() {
        return userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getStartPointLat() {
        return startPointLat;
    }

    public String getStartPointLong() {
        return startPointLong;
    }

    public String getEndPointLat() {
        return endPointLat;
    }

    public String getEndPointLong() {
        return endPointLong;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public String getMode() {
        return mode;
    }

    public String getModeText() {
        return modeText;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImg() {
        return userImg;
    }
}
