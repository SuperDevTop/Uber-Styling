package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by parangat on 4/18/18.
 */

public class VehicleDetail implements Serializable {
    @SerializedName("vehicleTypeId")
    @Expose
    private String vehicleTypeId;
    @SerializedName("vehicleTypeName")
    @Expose
    private String vehicleTypeName;
    @SerializedName("vehicleType")
    @Expose
    private List<VehicleType> vehicleType = null;

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public List<VehicleType> getVehicleType() {
        return vehicleType;
    }

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }
}
