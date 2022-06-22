package com.foxy.arrive5.API;

import com.foxy.arrive5.Response.AddProfileResponse;
import com.foxy.arrive5.Response.BookingListResponse;
import com.foxy.arrive5.Response.CancelBookingResponse;
import com.foxy.arrive5.Response.CancelReasonResponse;
import com.foxy.arrive5.Response.CardListResponse;
import com.foxy.arrive5.Response.ChangePasswordResponse;
import com.foxy.arrive5.Response.CheckPromoCodeResponse;
import com.foxy.arrive5.Response.CheckPromoResponse;
import com.foxy.arrive5.Response.ConfirmBookingResponse;
import com.foxy.arrive5.Response.ContactListResponse;
import com.foxy.arrive5.Response.CreateCustomerResponse;
import com.foxy.arrive5.Response.CurrentBookingResponse;
import com.foxy.arrive5.Response.DonationResponse;
import com.foxy.arrive5.Response.DriverListResponse;
import com.foxy.arrive5.Response.FareEstimationResponse;
import com.foxy.arrive5.Response.GetPolicyResponse;
import com.foxy.arrive5.Response.GetProfileResponse;
import com.foxy.arrive5.Response.GetTermsResponse;
import com.foxy.arrive5.Response.LastTripResponse;
import com.foxy.arrive5.Response.LocationUpdateResponse;
import com.foxy.arrive5.Response.LoginResponse;
import com.foxy.arrive5.Response.LogoutResponse;
import com.foxy.arrive5.Response.MobileVerifyResponse;
import com.foxy.arrive5.Response.NotificationListResponse;
import com.foxy.arrive5.Response.PaymentResponse;
import com.foxy.arrive5.Response.PromoListResponse;
import com.foxy.arrive5.Response.RatingsCommentResponse;
import com.foxy.arrive5.Response.ReasonListResponse;
import com.foxy.arrive5.Response.ReviewsListResponse;
import com.foxy.arrive5.Response.SaveCardResponse;
import com.foxy.arrive5.Response.SaveSplitResponse;
import com.foxy.arrive5.Response.ScheduleBookingResponse;
import com.foxy.arrive5.Response.SearchDriverResponse;
import com.foxy.arrive5.Response.SignupResponse;
import com.foxy.arrive5.Response.SplitFareResponse;
import com.foxy.arrive5.Response.SubmitReasonResponse;
import com.foxy.arrive5.Response.UpdateProfileResponse;
import com.foxy.arrive5.Response.VehicleDetailResponse;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResponse> userLogin(@Field("mobileno") String mobile, @Field("password") String password, @Field("token") String token, @Field("appPlatform") String platform);

    @FormUrlEncoded
    @POST("user/verify_mobile")
    Call<MobileVerifyResponse> verifyMobile(@Field("mobileno") String mobile);

    @FormUrlEncoded
    @POST("user/verify_mobile_forgot")
    Call<MobileVerifyResponse> checkMobile(@Field("mobileno") String mobile);

    @FormUrlEncoded
    @POST("user/change_password")
    Call<ChangePasswordResponse> changePassword(@Field("id") String id, @Field("password") String password);

    @POST("user/signup")
    Call<SignupResponse> newUser(@Body MultipartBody image);

    @FormUrlEncoded
    @POST("User/driversearch")
    Call<SearchDriverResponse> searchDriver(@Field("lat") String lat, @Field("lng") String lng);


    @FormUrlEncoded
    @POST("Booking/getVehcletollpricedetails")
    Call<VehicleDetailResponse> getVehicles(@Field("start_point_lat") String startLat, @Field("start_point_long") String startLong,
                                            @Field("end_point_lat") String endLat, @Field("end_point_long") String endLong);

    @FormUrlEncoded
    @POST("user/locationUpdate")
    Call<LocationUpdateResponse> updateLoc(@Field("lat") String lat, @Field("lng") String lng, @Field("type") String type, @Field("id") String id);

    @FormUrlEncoded
    @POST("Booking/booking")
    Call<ConfirmBookingResponse> booking(@Field("user_id") String userId, @Field("start_point") String startPoint, @Field("end_point") String endPoint, @Field("start_point_lat") String startLat,
                                         @Field("start_point_long") String startLong, @Field("end_point_lat") String endLat,
                                         @Field("end_point_long") String endLong, @Field("no_passanger") String pass,
                                         @Field("vehicle_type_id") String vehicleId, @Field("vehicle_sub_type_id") String subTypeId, @Field("promocode") String promoCode);

    @FormUrlEncoded
    @POST("User/logout")
    Call<LogoutResponse> logout(@Field("id") String id, @Field("type") String type);

    @FormUrlEncoded
    @POST("Booking/schedule_later_booking")
    Call<ScheduleBookingResponse> scheduleBooking(@Field("user_id") String userId, @Field("driver_id") String driverId, @Field("start_point") String startPoint,
                                                  @Field("end_point") String endPoint, @Field("start_point_lat") String startLat,
                                                  @Field("start_point_long") String startLong, @Field("end_point_lat") String endLat,
                                                  @Field("end_point_long") String endLong, @Field("schedule_date") String date,
                                                   @Field("schedule_time") String time, @Field("vehicle_sub_type_id") String subTypeId, @Field("amount") String amount);

    @FormUrlEncoded
    @POST("Booking/notification_list")
    Call<NotificationListResponse> notifyList(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("Booking/booking_list")
    Call<BookingListResponse> bookingList(@Field("user_id") String userId, @Field("type") String type);

    @FormUrlEncoded
    @POST("Booking/get_cancel_reason")
    Call<CancelReasonResponse> getCancelReasons(@Field("type") String type);

    @FormUrlEncoded
    @POST("Booking/cancel_booking")
    Call<CancelBookingResponse> cancelBooking(@Field("booking_id") String bookingId, @Field("cancel_reason") String reason, @Field("type") String type);

    @FormUrlEncoded
    @POST("Booking/checkPromo")
    Call<CheckPromoResponse> checkPromo(@Field("id") String userId, @Field("promo") String code);

    @FormUrlEncoded
    @POST("Ngo/ngoDonation")
    Call<DonationResponse> donate(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("Booking/reviewList")
    Call<ReviewsListResponse> reviewsList(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("user/bookedDriverList")
    Call<DriverListResponse> getDriverList(@Field("user_id") String userId);

    @POST("user/editUserPofile")
    Call<UpdateProfileResponse> updateProfile(@Body MultipartBody image);

    @GET("Booking/get_rating_comment_user")
    Call<RatingsCommentResponse> getRatingComments();

    @FormUrlEncoded
    @POST("Booking/rate_user")
    Call<ResponseBody> rateUser(@Field("user_id") String userId, @Field("booking_id") String bookingId, @Field("rate") String rate,
                                @Field("comment") String comment, @Field("review") String review);

    @FormUrlEncoded
    @POST("Booking/fare_estimation")
    Call<FareEstimationResponse> estimateFare(@Field("start_point_lat") String startLat, @Field("start_point_long") String startLong, @Field("end_point_lat") String endLat, @Field("end_point_long") String endLong,
                                              @Field("vehicle_sub_type_id") String subtypeId);
    @FormUrlEncoded
    @POST("user/reportReasonList")
    Call<ReasonListResponse> reasonsList(@Field("type") String type);

    @FormUrlEncoded
    @POST("Booking/user_last_trip")
    Call<LastTripResponse> lastTrip(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("User/promoCode")
    Call<PromoListResponse> promoList(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("user/submitReason")
    Call<SubmitReasonResponse> submitReason(@Field("user_id") String userId, @Field("booking_id") String bookingId,
                                            @Field("driver_id") String driverId, @Field("reason_id") String reasonId,
                                            @Field("image") String image, @Field("comment") String comment,
                                            @Field("type") String type);

    @FormUrlEncoded
    @POST("User/Promo_code_check")
    Call<CheckPromoCodeResponse> checkPromoCode(@Field("user_id") String userId, @Field("promo_code") String promoCode);

    @FormUrlEncoded
    @POST("user/add_business_profile")
    Call<AddProfileResponse> addProfile(@Field("user_id") String userId, @Field("email") String email, @Field("payment_method_type") String methodType,
                                        @Field("report_status") String reportStatus, @Field("type") String type);

    @FormUrlEncoded
    @POST("user/businessProfileList")
    Call<GetProfileResponse> getProfileList(@Field("user_id") String userId);

    @GET("user/getPrivacyPolicy")
    Call<GetPolicyResponse> getPolicy();

    @GET("user/getTerms")
    Call<GetTermsResponse> getTerms();

    @FormUrlEncoded
    @POST("user/contactList")
    Call<ContactListResponse> getContactList(@Field("contact_info") String info);

    @FormUrlEncoded
    @POST("Stripe/customer")
    Call<CreateCustomerResponse> createCustomer(@Field("user_id") String userId, @Field("email") String email, @Field("description") String desc);

    @FormUrlEncoded
    @POST("Stripe/saveCard")
    Call<SaveCardResponse> saveCard(@Field("customer_id") String custId, @Field("exp_month") String expMonth, @Field("exp_year") String expYear,
                                    @Field("number") String cardNo, @Field("cvc") String cvv, @Field("user_id") String userId,
                                    @Field("email") String email, @Field("description") String desc, @Field("name") String name);

    @FormUrlEncoded
    @POST("Booking/getCurrentBookingForUser")
    Call<CurrentBookingResponse> getCurrentBooking(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("Stripe/stripe_payment")
    Call<PaymentResponse> finalPayment(@Field("booking_id") String bookingId,
                                       @Field("user_id") String userId,
                                       @Field("customer_id") String custId,
                                       @Field("card_id") String cardId,
                                       @Field("amount") String amount);

    @FormUrlEncoded
    @POST("Stripe/cardList")
    Call<CardListResponse> getCardList(@Field("customer_id") String cusId);

    @FormUrlEncoded
    @POST("user/splitFare")
    Call<SplitFareResponse> splitFare(@Field("booking_id") String bookingId,
                                      @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("user/saveSplitFare")
    Call<SaveSplitResponse> saveSplit(@Field("booking_id") String bookingId,
                                      @Field("user_id") String userId,
                                      @Field("type") String type,
                                      @Field("split_amount") String amount);
}
