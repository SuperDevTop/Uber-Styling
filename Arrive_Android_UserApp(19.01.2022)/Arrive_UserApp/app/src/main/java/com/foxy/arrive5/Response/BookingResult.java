package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/24/18.
 */

public class BookingResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bookingCode")
    @Expose
    private String bookingCode;
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
    @SerializedName("no_passanger")
    @Expose
    private String noPassanger;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("deduct_amt")
    @Expose
    private String deductAmt;
    @SerializedName("amount")
    @Expose
    private Double amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getStartPointLat() {
        return startPointLat;
    }

    public void setStartPointLat(String startPointLat) {
        this.startPointLat = startPointLat;
    }

    public String getStartPointLong() {
        return startPointLong;
    }

    public void setStartPointLong(String startPointLong) {
        this.startPointLong = startPointLong;
    }

    public String getEndPointLat() {
        return endPointLat;
    }

    public void setEndPointLat(String endPointLat) {
        this.endPointLat = endPointLat;
    }

    public String getEndPointLong() {
        return endPointLong;
    }

    public void setEndPointLong(String endPointLong) {
        this.endPointLong = endPointLong;
    }

    public String getNoPassanger() {
        return noPassanger;
    }

    public void setNoPassanger(String noPassanger) {
        this.noPassanger = noPassanger;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDeductAmt() {
        return deductAmt;
    }

    public void setDeductAmt(String deductAmt) {
        this.deductAmt = deductAmt;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
