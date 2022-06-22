package com.foxy.arrive5.Home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.CurrentBookingResponse;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.SavePref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmScheduleRidePopup extends Activity {

    private List<CurrentBookingResponse.BookingBean> currentBookingList;
    String mode;
    SavePref savePref;
    ReadPref readPref;
    String id;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_schedule_ride_popup);
        savePref = new SavePref(ConfirmScheduleRidePopup.this);
        readPref = new ReadPref(ConfirmScheduleRidePopup.this);
        id = readPref.getUserId();


        Button btnAccept = findViewById(R.id.btnAccept);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCurrentBooking(id);
            }
        });

        time = findViewById(R.id.time);
        TextView address1 = findViewById(R.id.address1);
        TextView txtType = findViewById(R.id.txtType);
        TextView startPoint = findViewById(R.id.startPoint);
        TextView endPoint = findViewById(R.id.endPoint);
        String duration = getIntent().getStringExtra("duration");
        String driverName = getIntent().getStringExtra("driverName");
        String carType = getIntent().getStringExtra("carType");

        String startPoints = getIntent().getStringExtra("startPoint");
        startPoint.setText(startPoints);

        String endPoints = getIntent().getStringExtra("endPoint");
        endPoint.setText(endPoints);

        txtType.setText(carType);
        address1.setText(driverName);
        time.setText(duration);
    }


    private void getCurrentBooking(String id) {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Schedule Ride");
        dialog.setMessage("Please wait..");
        dialog.setProgress(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<CurrentBookingResponse> currentBookingResponseCall = service.getCurrentBooking(id);
        currentBookingResponseCall.enqueue(new Callback<CurrentBookingResponse>() {
            @Override
            public void onResponse(Call<CurrentBookingResponse> call, Response<CurrentBookingResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        currentBookingList = response.body().getBooking();
                        if (currentBookingList.size() > 0) {
                            mode = response.body().getBooking().get(0).getMode();

                            if (mode.equalsIgnoreCase("1")) {
                                dialog.dismiss();

                                Log.e("ifhjhjh", "if" + " " + response.body().getBooking().get(0).getPush_tag());
                                Intent intent = new Intent(ConfirmScheduleRidePopup.this, FindingRideActivity.class);
                                intent.putExtra("pushtag", response.body().getBooking().get(0).getPush_tag());
                                intent.putExtra("openActivity", true);
                                intent.putExtra("driverImage", response.body().getBooking().get(0).getDriverImg());
                                intent.putExtra("driverName", response.body().getBooking().get(0).getDriverName());
                                intent.putExtra("duration", response.body().getBooking().get(0).getDuration());
                                intent.putExtra("driverPhone", response.body().getBooking().get(0).getMobile());
                                intent.putExtra("bookingId", response.body().getBooking().get(0).getId());
                                intent.putExtra("amount", response.body().getBooking().get(0).getAmount());
                                intent.putExtra("driverId", response.body().getBooking().get(0).getDriver_id());
                                intent.putExtra("startPoint", response.body().getBooking().get(0).getStart_point());
                                intent.putExtra("endPoint", response.body().getBooking().get(0).getEnd_point());
                                intent.putExtra("driverLat", response.body().getBooking().get(0).getDriver_latitude());
                                intent.putExtra("driverLong", response.body().getBooking().get(0).getDriver_longitude());
                                intent.putExtra("rating", response.body().getBooking().get(0).getDriver_rating());
                                intent.putExtra("otp", response.body().getBooking().get(0).getOtp());
                                intent.putExtra("carType", response.body().getBooking().get(0).getVehicleSubTypeName());
                                intent.putExtra("carNo", response.body().getBooking().get(0).getCarNo());
                                savePref.saveDriverName(response.body().getBooking().get(0).getDriverName());
                                savePref.saveDriverMobile(response.body().getBooking().get(0).getMobile());
                                savePref.saveBookingId(response.body().getBooking().get(0).getId());
                                startActivity(intent);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CurrentBookingResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ConfirmScheduleRidePopup.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
