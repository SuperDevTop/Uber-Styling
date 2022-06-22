package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 7/8/18.
 */

public class PromoListResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("promoCode")
    @Expose
    private List<PromoCode> promoCode = null;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public List<PromoCode> getPromoCode() {
        return promoCode;
    }
}
