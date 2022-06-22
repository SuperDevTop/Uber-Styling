package com.mobi.arrive5d.API;

import com.mobi.arrive5d.Response.AcceptRejectResponse;
import com.mobi.arrive5d.Response.AddPickupResponse;
import com.mobi.arrive5d.Response.ArrivedResponse;
import com.mobi.arrive5d.Response.BillingResponse;
import com.mobi.arrive5d.Response.CancelBookingResponse;
import com.mobi.arrive5d.Response.CancelReasonResponse;
import com.mobi.arrive5d.Response.ChangePasswordResponse.ChangePassword;
import com.mobi.arrive5d.Response.CurrentBookingResponse;
import com.mobi.arrive5d.Response.DriverEarningResponse;
import com.mobi.arrive5d.Response.EarningFilterResponse;
import com.mobi.arrive5d.Response.FareReviewListResponse;
import com.mobi.arrive5d.Response.FareReviewResponse;
import com.mobi.arrive5d.Response.FinishRideResponse;
import com.mobi.arrive5d.Response.GetColorResponse;
import com.mobi.arrive5d.Response.GetModelsResponse;
import com.mobi.arrive5d.Response.GetPolicyResponse;
import com.mobi.arrive5d.Response.GetTermsResponse;
import com.mobi.arrive5d.Response.GetVehicleYearResponse;
import com.mobi.arrive5d.Response.HighZoneResponse;
import com.mobi.arrive5d.Response.LoginResponse.Login;
import com.mobi.arrive5d.Response.LogoutResponse;
import com.mobi.arrive5d.Response.MobileVerifyResponse.MobileVerify;
import com.mobi.arrive5d.Response.NotificationResponse;
import com.mobi.arrive5d.Response.OtpVerificationResponse;
import com.mobi.arrive5d.Response.OurServicesResponse;
import com.mobi.arrive5d.Response.RatingsCommentResponse;
import com.mobi.arrive5d.Response.ReasonListResponse;
import com.mobi.arrive5d.Response.ReviesListResponse;
import com.mobi.arrive5d.Response.SchedulePickupsResponse;
import com.mobi.arrive5d.Response.SignupResponse.Signup;
import com.mobi.arrive5d.Response.SubmitReasonResponse;
import com.mobi.arrive5d.Response.UpdateDriverResponse;
import com.mobi.arrive5d.Response.UpdateInfoResponse;
import com.mobi.arrive5d.Response.UpdateLatLongResponse;
import com.mobi.arrive5d.Response.UpdateProfileResponse;
import com.mobi.arrive5d.Response.UpdateStatusResponse;
import com.mobi.arrive5d.Response.VehicleMakeResponse;
import com.mobi.arrive5d.Response.VehicleModelResponse;
import com.mobi.arrive5d.Response.VehicleSubTypeResponse;
import com.mobi.arrive5d.Response.VehicleTypeResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("user/driver_login")
    Call<Login> userLogin(@Field("mobileno") String mobile,
                          @Field("password") String password,
                          @Field("token") String token,
                          @Field("appPlatform") String platform);

    @FormUrlEncoded
    @POST("user/driverVerify_mobile")
    Call<MobileVerify> verifyMobile(@Field("mobileno") String mobile);

    @FormUrlEncoded
    @POST("user/driverVerifyForgot_mobile")
    Call<MobileVerify> checkMobile(@Field("mobileno") String mobile);
    @FormUrlEncoded
    @POST("user/driverChange_password")
    Call<ChangePassword> changePassword(@Field("id") String id, @Field("password") String password);

    @POST("user/driverSignup")
    Call<Signup> newUser(@Body MultipartBody image);

    @POST("user/updateDriverinfo")
    Call<UpdateDriverResponse> updateDriver(@Body MultipartBody image);

    @GET("user/getColor")
    Call<GetColorResponse> geColors();

    @GET("user/getModal")
    Call<GetModelsResponse> getModels();

    @GET("user/getVehicletype")
    Call<VehicleTypeResponse> getVehicleType();

    @FormUrlEncoded
    @POST("user/getVehiclesubtype")
    Call<VehicleSubTypeResponse> getVehiclesSub(@Field("typeid") String typeId);

    @POST("user/updateDriverVechileinfo")
    Call<UpdateInfoResponse> updateInfo(@Body RequestBody file);

    @FormUrlEncoded
    @POST("User/update_driver_online_status")
    Call<UpdateStatusResponse> updateStatus(@Field("is_online") String isOnline, @Field("driver_id") String driverId);

    @FormUrlEncoded
    @POST("Booking/accept_reject")
    Call<AcceptRejectResponse> bookingResponse(@Field("booking_id") String bookingId, @Field("driver_id") String driverId, @Field("mode") String mode);

    @FormUrlEncoded
    @POST("User/update_driver_lat_long")
    Call<UpdateLatLongResponse> updateLatLng(@Field("driver_id") String driverId, @Field("lat") String lat, @Field("long") String lng);

    @FormUrlEncoded
    @POST("User/logout")
    Call<LogoutResponse> logout(@Field("id") String id, @Field("type") String type);

    @FormUrlEncoded
    @POST("Booking/arrived")
    Call<ArrivedResponse> arrived(@Field("user_id") String userId, @Field("booking_id") String bookingId, @Field("driver_id") String driverId);

    @FormUrlEncoded
    @POST("Booking/otp_verfication")
    Call<OtpVerificationResponse> verifyOTP(@Field("user_id") String userId, @Field("booking_id") String bookingId, @Field("driver_id") String driverId, @Field("otp") String otp);

    @GET("Booking/get_rating_comment")
    Call<RatingsCommentResponse> getRatingComments();

    @FormUrlEncoded
    @POST("Booking/get_cancel_reason")
    Call<CancelReasonResponse> getCancelReasons(@Field("type") String type);

    @FormUrlEncoded
    @POST("Booking/finish_ride")
    Call<FinishRideResponse> finishRide(@Field("user_id") String userId, @Field("booking_id") String bookingId, @Field("driver_id") String driverId);

    @FormUrlEncoded
    @POST("Booking/cancel_booking")
    Call<CancelBookingResponse> cancelBooking(@Field("booking_id") String bookingId, @Field("cancel_reason") String reason, @Field("type") String type);

    @FormUrlEncoded
    @POST("Booking/rate")
    Call<BillingResponse> billingResponse(@Field("driver_id") String driverId, @Field("booking_id") String bookingId, @Field("rate") String rating, @Field("comment") String comment, @Field("review") String review);

    @FormUrlEncoded
    @POST("user/notifacationToDriver")
    Call<NotificationResponse> getNotifications(@Field("driver_id") String driverId);

    @FormUrlEncoded
    @POST("Booking/ourServices")
    Call<OurServicesResponse> ourServices(@Field("driver_id") String driverId);

    @FormUrlEncoded
    @POST("user/reportReasonList")


    Call<ReasonListResponse> reasonsList(@Field("type") String type);

    @POST("user/submitReason")
    Call<SubmitReasonResponse> submitReason(@Body MultipartBody image);

    @POST("user/editDriverPofile")
    Call<UpdateProfileResponse> updateProfile(@Body MultipartBody image);

    @GET("user/driverEarningFilter")
    Call<EarningFilterResponse> filterEarning();

    @FormUrlEncoded
    @POST("user/driverEarning")
    Call<DriverEarningResponse> earnings(@Field("driver_id") String driverId, @Field("time_period") String timeId);

    @FormUrlEncoded
    @POST("Booking/driverreviewList")
    Call<ReviesListResponse> reviewsList(@Field("driver_id") String driverId);

    @GET("User/fare_review_question_list")
    Call<FareReviewListResponse> reviewList();

    @FormUrlEncoded
    @POST("User/fare_review")
    Call<FareReviewResponse> submitReview(@Field("driver_id") String driverId, @Field("fare_question_id") String reviewId,
                                          @Field("review") String reason);

    @FormUrlEncoded
    @POST("booking/getSchedulePickupList")
    Call<SchedulePickupsResponse> getScheduledPickups(@Field("driver_id") String driverId, @Field("type_pick_up") String type);

    @GET("user/highZone")
    Call<HighZoneResponse> getHighZones();

    @FormUrlEncoded
    @POST("Booking/addToMyPickup")
    Call<AddPickupResponse> addPickup(@Field("driver_id") String driverId, @Field("booking_id") String bookingId);

    @FormUrlEncoded
    @POST("Booking/removeFromMyPickup")
    Call<AddPickupResponse> removePickup(@Field("driver_id") String driverId, @Field("booking_id") String bookingId);

    @FormUrlEncoded
    @POST("Booking/getCurrentBookingForDriver")
    Call<CurrentBookingResponse> getCurrentBooking(@Field("driver_id") String driverId);

    @GET("user/getPrivacyPolicy")
    Call<GetPolicyResponse> getPolicy();

    @GET("user/getTerms")
    Call<GetTermsResponse> getTerms();

    @GET("user/getVehicleYear")
    Call<GetVehicleYearResponse> getVehicles();

    @FormUrlEncoded
    @POST("user/getVehicleModel")
    Call<VehicleModelResponse> getVehicleModels(@Field("makeid") String makeId);
    @FormUrlEncoded
    @POST("user/getVehicleMake")
    Call<VehicleMakeResponse> getVehicleMakes(@Field("yearid") String yearId);
}
