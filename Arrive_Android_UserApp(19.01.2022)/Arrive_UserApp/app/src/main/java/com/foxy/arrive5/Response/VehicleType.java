package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by parangat on 4/18/18.
 */

public class VehicleType implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vehicle_type_id")
    @Expose
    private String vehicleTypeId;
    @SerializedName("vehicle_model")
    @Expose
    private String vehicleModel;
    @SerializedName("base_price")
    @Expose
    private String basePrice;
    @SerializedName("booking_fare")
    @Expose
    private String bookingFare;
    @SerializedName("minimum_fare")
    @Expose
    private String minimumFare;
    @SerializedName("charge_per_min")
    @Expose
    private String chargePerMin;
    @SerializedName("charge_per_mile")
    @Expose
    private String chargePerMile;
    @SerializedName("vehicle_capacity")
    @Expose
    private String vehicleCapacity;


    @SerializedName("vehicle_door")
    @Expose
    private String vehicleDoors;

    @SerializedName("active_flag")
    @Expose
    private String activeFlag;
    @SerializedName("insertime_mysql")
    @Expose
    private String insertimeMysql;
    @SerializedName("updatetime_php")
    @Expose
    private String updatetimePhp;
    @SerializedName("fare_amt")
    @Expose
    private Integer fareAmt;


    @SerializedName("toll_amount")
    @Expose
    private String tollAmount;

    @SerializedName("total_toll_amount")
    @Expose
    private String totalTollAmount;

    @SerializedName("drivertaketimetoreach")
    @Expose
    private String drivertaketimetoreach;
    @SerializedName("driverdistancefronuser")
    @Expose
    private String driverdistancefronuser;
    @SerializedName("selectimg")
    @Expose
    private String selectimg;
    @SerializedName("nonselectimg")
    @Expose
    private String nonselectimg;

    public String getVehicleDoors() {
        return vehicleDoors;
    }

    public void setVehicleDoors(String vehicleDoors) {
        this.vehicleDoors = vehicleDoors;
    }

    public String getTollAmount() {
        return tollAmount;
    }

    public void setTollAmount(String tollAmount) {
        this.tollAmount = tollAmount;
    }

    public String getTotalTollAmount() {
        return totalTollAmount;
    }

    public void setTotalTollAmount(String totalTollAmount) {
        this.totalTollAmount = totalTollAmount;
    }

    public String getId() {
        return id;
    }

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public String getBookingFare() {
        return bookingFare;
    }

    public String getMinimumFare() {
        return minimumFare;
    }

    public String getChargePerMin() {
        return chargePerMin;
    }

    public String getChargePerMile() {
        return chargePerMile;
    }

    public String getVehicleCapacity() {
        return vehicleCapacity;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public String getInsertimeMysql() {
        return insertimeMysql;
    }

    public String getUpdatetimePhp() {
        return updatetimePhp;
    }

    public Integer getFareAmt() {
        return fareAmt;
    }

    public String getDrivertaketimetoreach() {
        return drivertaketimetoreach;
    }

    public String getDriverdistancefronuser() {
        return driverdistancefronuser;
    }

    public String getSelectimg() {
        return selectimg;
    }

    public String getNonselectimg() {
        return nonselectimg;
    }
}
