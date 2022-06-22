package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 6/14/18.
 */

public class ReasonListResponse {
    @SerializedName("result")
    @Expose
    private List<ReasonsList> result = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<ReasonsList> getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

}
