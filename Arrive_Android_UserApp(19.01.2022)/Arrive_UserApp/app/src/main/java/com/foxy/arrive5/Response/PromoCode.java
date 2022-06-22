package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 7/8/18.
 */

public class PromoCode {
    @SerializedName(("id"))
    @Expose
    private String id;
    @SerializedName("promo_code")
    @Expose
    private String promoCode;
    @SerializedName("promo_value")
    @Expose
    private String promoValue;
    @SerializedName("promo_type_name")
    @Expose
    private String promoTypeName;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("valid_to")
    @Expose
    private String validTo;

    public String getPromoCode() {
        return promoCode;
    }

    public String getPromoValue() {
        return promoValue;
    }

    public String getPromoTypeName() {
        return promoTypeName;
    }

    public String getDiscount() {
        return discount;
    }

    public String getValidTo() {
        return validTo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
