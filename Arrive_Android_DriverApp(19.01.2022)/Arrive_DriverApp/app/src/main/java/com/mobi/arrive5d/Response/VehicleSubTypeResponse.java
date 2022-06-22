package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 4/20/18.
 */

public class VehicleSubTypeResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("detail")
    @Expose
    private List<VehicleSubTypeDetail> detail = null;

    public String getStatus() {
        return status;
    }

    public List<VehicleSubTypeDetail> getDetail() {
        return detail;
    }
}
