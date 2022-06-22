package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 6/18/18.
 */

public class EarningFilterResponse {
    @SerializedName("result")
    @Expose
    private List<EarningFilter> result = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<EarningFilter> getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

}
