package com.mobi.arrive5d.SideMenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.MainActivity;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.CancelBookingResponse;
import com.mobi.arrive5d.Response.CancelReason;
import com.mobi.arrive5d.Response.CancelReasonResponse;
import com.mobi.arrive5d.utils.ReadPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelRideActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    String reason;
    List<CancelReason> cancelReasonList = new ArrayList<>();
    RadioButton rdbtn;
    ProgressDialog progressDialog;
    ReadPref readPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_ride);
        readPref = new ReadPref(CancelRideActivity.this);
        String type = "driver";
        getCancelReasons(type);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                reason = radioButton.getText().toString();
            }
        });
    }

    private void getCancelReasons(String type) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<CancelReasonResponse> cancelReasonResponseCall = service.getCancelReasons(type);
        cancelReasonResponseCall.enqueue(new Callback<CancelReasonResponse>() {
            @Override
            public void onResponse(Call<CancelReasonResponse> call, Response<CancelReasonResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        cancelReasonList = response.body().getResult();
                        for (int i = 0; i < cancelReasonList.size(); i++) {
                            rdbtn = new RadioButton(CancelRideActivity.this);
                            rdbtn.setId(i);
                            rdbtn.setText(cancelReasonList.get(i).getCancelReason());
                            radioGroup.addView(rdbtn);
                        }
                    } else {
                        Toast.makeText(CancelRideActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CancelReasonResponse> call, Throwable t) {
                Toast.makeText(CancelRideActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void BackClick(View view) {
        finish();
    }

    public void CancelClick(View view) {
        finish();
    }

    public void CancelRideClick(View view) {
        String bookingId = readPref.getBookingId();
        String cancelReason = reason;
        String type = "driver";
        cancelBooking(bookingId, cancelReason, type);
    }

    private void cancelBooking(String bookingId, String cancelReason, String type) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<CancelBookingResponse> cancelBookingResponseCall = service.cancelBooking(bookingId, cancelReason, type);
        progressDialog = new ProgressDialog(CancelRideActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        cancelBookingResponseCall.enqueue(new Callback<CancelBookingResponse>() {
            @Override
            public void onResponse(Call<CancelBookingResponse> call, Response<CancelBookingResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Intent intent = new Intent(CancelRideActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CancelRideActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CancelBookingResponse> call, Throwable t) {
                Toast.makeText(CancelRideActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
