package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 5/18/18.
 */

public class BillingResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("status")
    @Expose
    private String status;
    public String getMessage(){
        return message;
    }
    public Integer getResult(){
        return result;
    }
    public String getStatus() {
        return status;
    }

}
