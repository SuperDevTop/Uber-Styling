package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/20/18.
 */

public class VehicleList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("active_flag")
    @Expose
    private String activeFlag;
    @SerializedName("inserttime_mysql")
    @Expose
    private String inserttimeMysql;
    @SerializedName("updatetime_php")
    @Expose
    private String updatetimePhp;

    public String getId() {
        return id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public String getInserttimeMysql() {
        return inserttimeMysql;
    }

    public String getUpdatetimePhp() {
        return updatetimePhp;
    }

}
