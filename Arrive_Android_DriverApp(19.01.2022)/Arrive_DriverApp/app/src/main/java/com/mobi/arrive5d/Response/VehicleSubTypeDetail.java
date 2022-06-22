package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/20/18.
 */

public class VehicleSubTypeDetail {
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
    @SerializedName("active_flag")
    @Expose
    private String activeFlag;
    @SerializedName("insertime_mysql")
    @Expose
    private String insertimeMysql;
    @SerializedName("updatetime_php")
    @Expose
    private String updatetimePhp;

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

    public String getActiveFlag() {
        return activeFlag;
    }

    public String getInsertimeMysql() {
        return insertimeMysql;
    }

    public String getUpdatetimePhp() {
        return updatetimePhp;
    }

}
