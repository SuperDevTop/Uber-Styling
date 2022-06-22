package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 6/19/18.
 */

public class InfoResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("driverid")
    @Expose
    private String driverid;
    @SerializedName("vechile_type")
    @Expose
    private String vechileType;
    @SerializedName("vechile_subtype")
    @Expose
    private String vechileSubtype;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("noofdoor")
    @Expose
    private String noofdoor;
    @SerializedName("noofsbelt")
    @Expose
    private String noofsbelt;
    @SerializedName("addedon")
    @Expose
    private String addedon;
    @SerializedName("vechile_type_name")
    @Expose
    private String vechileTypeName;
    @SerializedName("vechile_subtype_name")
    @Expose
    private String vechileSubtypeName;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("color_name")
    @Expose
    private String colorName;
    @SerializedName("vehicle_images")
    @Expose
    private List<String> vehicleImages = null;

    public String getId() {
        return id;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getVechileType() {
        return vechileType;
    }

    public void setVechileType(String vechileType) {
        this.vechileType = vechileType;
    }

    public String getVechileSubtype() {
        return vechileSubtype;
    }

    public void setVechileSubtype(String vechileSubtype) {
        this.vechileSubtype = vechileSubtype;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNoofdoor() {
        return noofdoor;
    }

    public void setNoofdoor(String noofdoor) {
        this.noofdoor = noofdoor;
    }

    public String getNoofsbelt() {
        return noofsbelt;
    }

    public void setNoofsbelt(String noofsbelt) {
        this.noofsbelt = noofsbelt;
    }

    public String getAddedon() {
        return addedon;
    }

    public void setAddedon(String addedon) {
        this.addedon = addedon;
    }

    public String getVechileTypeName() {
        return vechileTypeName;
    }

    public void setVechileTypeName(String vechileTypeName) {
        this.vechileTypeName = vechileTypeName;
    }

    public String getVechileSubtypeName() {
        return vechileSubtypeName;
    }

    public void setVechileSubtypeName(String vechileSubtypeName) {
        this.vechileSubtypeName = vechileSubtypeName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public List<String> getVehicleImages() {
        return vehicleImages;
    }

    public void setVehicleImages(List<String> vehicleImages) {
        this.vehicleImages = vehicleImages;
    }
}
