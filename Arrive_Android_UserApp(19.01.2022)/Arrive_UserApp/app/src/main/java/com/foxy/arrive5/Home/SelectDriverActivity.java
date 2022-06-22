package com.foxy.arrive5.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Adapter.SelectDriverAdapter;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.DriverList;
import com.foxy.arrive5.Response.DriverListResponse;
import com.foxy.arrive5.Response.ScheduleBookingResponse;
import com.foxy.arrive5.utils.ReadPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.foxy.arrive5.Home.RequestRideActivity.setSystemBarTheme;

public class SelectDriverActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SelectDriverAdapter selectDriverAdapter;
    List<DriverList> driverLists = new ArrayList<>();
    ReadPref readPref;
    ProgressDialog progressDialog;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_driver);
        readPref = new ReadPref(SelectDriverActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(SelectDriverActivity.this, true);
        recyclerView = findViewById(R.id.recycler_view);
        btnSend = findViewById(R.id.btnSend);
        String userId = readPref.getUserId();
        getDriversList(userId);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = readPref.getUserId();
                String driverId = "";
                String startPoint = readPref.getScheduleStart();
                String endPoint = readPref.getScheduleEnd();
                String startLat = readPref.getStartLat();
                String startLong = readPref.getStartLong();
                String endLat = readPref.getEndLat();
                String endLong = readPref.getEndLong();
                String date = readPref.getScheduleDate();
                String time = readPref.getScheduleTime();
                String subtypeId = readPref.getSubTypeId();
                String amount = readPref.getScheduledAmount();
                scheduleBooking(userId, driverId, startPoint, endPoint, startLat, startLong, endLat, endLong, date, time, subtypeId, amount);
            }
        });
    }


    private void scheduleBooking(String userId, String driverId, String startPoint, String endPoint, String startLat, String startLong, String endLat, String endLong, String date, String time, String subtypeId, String amount) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ScheduleBookingResponse> scheduleBookingResponseCall = service.scheduleBooking(userId, driverId, startPoint, endPoint, startLat, startLong, endLat, endLong, date, time, subtypeId, amount);
        progressDialog = new ProgressDialog(SelectDriverActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        scheduleBookingResponseCall.enqueue(new Callback<ScheduleBookingResponse>() {
            @Override
            public void onResponse(Call<ScheduleBookingResponse> call, Response<ScheduleBookingResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(SelectDriverActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SelectDriverActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SelectDriverActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ScheduleBookingResponse> call, Throwable t) {
                Toast.makeText(SelectDriverActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void getDriversList(String userId) {
        final ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<DriverListResponse> driverListResponseCall = service.getDriverList(userId);
        progressDialog = new ProgressDialog(SelectDriverActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        driverListResponseCall.enqueue(new Callback<DriverListResponse>() {
            @Override
            public void onResponse(Call<DriverListResponse> call, Response<DriverListResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        driverLists = response.body().getResult();
                        if (driverLists.size() > 0) {
                            btnSend.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            setListAdapter();
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            btnSend.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(SelectDriverActivity.this, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DriverListResponse> call, Throwable t) {
                Toast.makeText(SelectDriverActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setListAdapter() {
        selectDriverAdapter = new SelectDriverAdapter(getApplicationContext(), driverLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(selectDriverAdapter);
    }

    public void BackClick(View view) {
        finish();
    }
}
