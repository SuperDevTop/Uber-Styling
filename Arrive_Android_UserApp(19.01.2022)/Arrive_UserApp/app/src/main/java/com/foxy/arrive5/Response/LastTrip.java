package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 6/8/18.
 */

public class LastTrip {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
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
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("promocode")
    @Expose
    private String promocode;
    @SerializedName("no_passanger")
    @Expose
    private String noPassanger;
    @SerializedName("no_luggage")
    @Expose
    private String noLuggage;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("cancel_reason")
    @Expose
    private String cancelReason;
    @SerializedName("booking_type")
    @Expose
    private String bookingType;
    @SerializedName("schedule_date")
    @Expose
    private String scheduleDate;
    @SerializedName("schedule_time")
    @Expose
    private String scheduleTime;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("vehicle_type_id")
    @Expose
    private String vehicleTypeId;
    @SerializedName("vehicle_sub_type_id")
    @Expose
    private String vehicleSubTypeId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("added_on")
    @Expose
    private String addedOn;
    @SerializedName("update_on")
    @Expose
    private String updateOn;
    @SerializedName("vehicle_sub_type_name")
    @Expose
    private String vehicleSubTypeName;
    @SerializedName("carNo.")
    @Expose
    private String carNo;

    public String getId() {
        return id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getTransactionId() {
        return transactionId;
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

    public String getAmount() {
        return amount;
    }

    public String getTax() {
        return tax;
    }

    public String getDuration() {
        return duration;
    }

    public String getDistance() {
        return distance;
    }

    public String getPromocode() {
        return promocode;
    }

    public String getNoPassanger() {
        return noPassanger;
    }

    public String getNoLuggage() {
        return noLuggage;
    }

    public String getMode() {
        return mode;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public String getBookingType() {
        return bookingType;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public String getOtp() {
        return otp;
    }

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    public String getVehicleSubTypeId() {
        return vehicleSubTypeId;
    }

    public String getStatus() {
        return status;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public String getUpdateOn() {
        return updateOn;
    }

    public String getVehicleSubTypeName() {
        return vehicleSubTypeName;
    }

    public String getCarNo() {
        return carNo;
    }
}
