package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/20/18.
 */

public class ColorList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("color_name")
    @Expose
    private String colorName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("added_on")
    @Expose
    private String addedOn;

    public String getId() {
        return id;
    }

    public String getColorName() {
        return colorName;
    }

    public String getStatus() {
        return status;
    }

    public String getAddedOn() {
        return addedOn;
    }

}
