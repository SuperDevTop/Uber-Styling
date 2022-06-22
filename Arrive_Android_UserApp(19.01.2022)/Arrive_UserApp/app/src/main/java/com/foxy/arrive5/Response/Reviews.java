package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 5/16/18.
 */

public class Reviews {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("added_on")
    @Expose
    private String addedOn;
    @SerializedName("dname")
    @Expose
    private String dname;
    @SerializedName("demail")
    @Expose
    private String demail;
    @SerializedName("start_point")
    @Expose
    private String startPoint;
    @SerializedName("end_point")
    @Expose
    private String endPoint;
    @SerializedName("user_img")
    @Expose
    private String userImg;
    @SerializedName("comment_list")
    @Expose
    private List<String> commentList = null;

    public String getId() {
        return id;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public String getDname() {
        return dname;
    }

    public String getDemail() {
        return demail;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public List<String> getCommentList() {
        return commentList;
    }

    public String getUserImg() {
        return userImg;
    }
}
