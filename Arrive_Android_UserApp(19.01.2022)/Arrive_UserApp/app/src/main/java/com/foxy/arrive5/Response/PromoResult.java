package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 5/31/18.
 */

public class PromoResult {
    @SerializedName("promo_name")
    @Expose
    private String promoName;
    @SerializedName("promo_code")
    @Expose
    private String promoCode;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("valid_from")
    @Expose
    private String validFrom;
    @SerializedName("valid_to")
    @Expose
    private String validTo;

    public String getPromoName() {
        return promoName;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public String getDiscount() {
        return discount;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public String getValidTo() {
        return validTo;
    }
}
