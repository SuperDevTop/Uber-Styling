package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by parangat on 4/20/18.
 */

public class ModelList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("modelname")
    @Expose
    private String modelname;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("added_on")
    @Expose
    private String addedOn;

    public String getId() {
        return id;
    }

    public String getModelname() {
        return modelname;
    }

    public String getStatus() {
        return status;
    }

    public String getAddedOn() {
        return addedOn;
    }
}
