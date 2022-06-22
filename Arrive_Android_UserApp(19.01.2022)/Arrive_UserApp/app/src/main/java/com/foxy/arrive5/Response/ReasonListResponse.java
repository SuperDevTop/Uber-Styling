package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 6/8/18.
 */

public class ReasonListResponse {
    @SerializedName("result")
    @Expose
    private List<ReasonList> result = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<ReasonList> getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

}
