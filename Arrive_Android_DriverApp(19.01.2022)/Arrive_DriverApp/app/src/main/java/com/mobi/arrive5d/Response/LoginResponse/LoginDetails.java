package com.mobi.arrive5d.Response.LoginResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 4/12/18.
 */

public class LoginDetails {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("middle_name")
    @Expose
    private String middleName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("social_secrityno")
    @Expose
    private String socialSecrityno;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("referal_code")
    @Expose
    private String referalCode;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("appPlatform")
    @Expose
    private String appPlatform;
    @SerializedName("is_available")
    @Expose
    private String isAvailable;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("driving_licence")
    @Expose
    private String drivingLicence;
    @SerializedName("driving_licence_name")
    @Expose
    private String drivingLicenceName;
    @SerializedName("driving_licence_code")
    @Expose
    private String drivingLicenceCode;
    @SerializedName("licence_plate")
    @Expose
    private String licencePlate;
    @SerializedName("vechile_reg")
    @Expose
    private String vechileReg;
    @SerializedName("expiredate")
    @Expose
    private String expiredate;
    @SerializedName("insuarance_img")
    @Expose
    private String insuaranceImg;
    @SerializedName("licence_img")
    @Expose
    private String licenceImg;
    @SerializedName("adhar_img")
    @Expose
    private String adharImg;
    @SerializedName("vechile_img")
    @Expose
    private String vechileImg;
    @SerializedName("police_verification")
    @Expose
    private String policeVerification;
    @SerializedName("is_online")
    @Expose
    private String isOnline;
    @SerializedName("driving_licence_status")
    @Expose
    private String drivingLicenceStatus;
    @SerializedName("police_verification_status")
    @Expose
    private String policeVerificationStatus;
    @SerializedName("driving_licence_rejection_reason")
    @Expose
    private String drivingLicenceRejectionReason;
    @SerializedName("police_verification_rejection_reason")
    @Expose
    private String policeVerificationRejectionReason;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("added_on")
    @Expose
    private String addedOn;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("vechile_type")
    @Expose
    private String vechileType;
    @SerializedName("vechile_type_name")
    @Expose
    private String vechileTypeName;
    @SerializedName("vechile_subtype")
    @Expose
    private String vechileSubtype;
    @SerializedName("vechile_subtype_name")
    @Expose
    private String vechileSubtypeName;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("color_name")
    @Expose
    private String colorName;
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
    @SerializedName("vehicle_images")
    @Expose
    private List<String> vehicleImages = null;

    public String getId() {
        return id;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getImg() {
        return img;
    }

    public String getSocialSecrityno() {
        return socialSecrityno;
    }

    public String getDob() {
        return dob;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getReferalCode() {
        return referalCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getToken() {
        return token;
    }

    public String getAppPlatform() {
        return appPlatform;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public String getGender() {
        return gender;
    }

    public String getDrivingLicence() {
        return drivingLicence;
    }

    public String getDrivingLicenceName() {
        return drivingLicenceName;
    }

    public String getDrivingLicenceCode() {
        return drivingLicenceCode;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public String getVechileReg() {
        return vechileReg;
    }

    public String getExpiredate() {
        return expiredate;
    }

    public String getInsuaranceImg() {
        return insuaranceImg;
    }

    public String getLicenceImg() {
        return licenceImg;
    }

    public String getAdharImg() {
        return adharImg;
    }

    public String getVechileImg() {
        return vechileImg;
    }

    public String getPoliceVerification() {
        return policeVerification;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public String getDrivingLicenceStatus() {
        return drivingLicenceStatus;
    }

    public String getPoliceVerificationStatus() {
        return policeVerificationStatus;
    }

    public String getDrivingLicenceRejectionReason() {
        return drivingLicenceRejectionReason;
    }

    public String getPoliceVerificationRejectionReason() {
        return policeVerificationRejectionReason;
    }

    public String getStatus() {
        return status;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public String getVechileType() {
        return vechileType;
    }

    public String getVechileTypeName() {
        return vechileTypeName;
    }

    public String getVechileSubtype() {
        return vechileSubtype;
    }

    public String getVechileSubtypeName() {
        return vechileSubtypeName;
    }

    public String getModel() {
        return model;
    }

    public String getModelName() {
        return modelName;
    }

    public String getColor() {
        return color;
    }

    public String getColorName() {
        return colorName;
    }

    public String getMake() {
        return make;
    }

    public String getYear() {
        return year;
    }

    public String getNoofdoor() {
        return noofdoor;
    }

    public String getNoofsbelt() {
        return noofsbelt;
    }

    public List<String> getVehicleImages() {
        return vehicleImages;
    }
}
