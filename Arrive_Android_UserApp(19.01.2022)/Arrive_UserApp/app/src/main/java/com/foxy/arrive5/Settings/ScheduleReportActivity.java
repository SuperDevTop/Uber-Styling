package com.foxy.arrive5.Settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.AddProfileResponse;
import com.foxy.arrive5.utils.ReadPref;
import com.suke.widget.SwitchButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleReportActivity extends AppCompatActivity {
    SwitchButton weeklySwitch, monthlySwitch;
    String reportType = "1";
    ReadPref readPref;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_report);
        readPref = new ReadPref(ScheduleReportActivity.this);
        weeklySwitch = findViewById(R.id.weeklySwitch);
        monthlySwitch = findViewById(R.id.monthlySwitch);
        weeklySwitch.toggle();
        weeklySwitch.toggle(false);
        weeklySwitch.setShadowEffect(true);
        weeklySwitch.setEnableEffect(false);
        weeklySwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    reportType = "1";
                    monthlySwitch.setChecked(false);
                } else {
                    reportType = "2";
                }
            }
        });
        monthlySwitch.toggle();
        monthlySwitch.toggle(false);
        monthlySwitch.setShadowEffect(true);
        monthlySwitch.setEnableEffect(false);
        monthlySwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    reportType = "2";
                    weeklySwitch.setChecked(false);
                } else {
                    reportType = "1";
                }
            }
        });
    }

    public void BackClick(View view) {
        finish();
    }

    public void ScheduleReportClick(View view) {
        String userId = readPref.getUserId();
        String businessEmail = getIntent().getStringExtra("business_email");
        String paymentMethod = getIntent().getStringExtra("payment_type");
        String reportStatus = reportType;
        String type = "2";
        addProfile(userId, businessEmail, paymentMethod, reportStatus, type);
    }

    private void addProfile(String userId, String businessEmail, String paymentMethod, String reportStatus, String type) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<AddProfileResponse> addProfileResponseCall = service.addProfile(userId, businessEmail, paymentMethod, reportStatus, type);
        progressDialog = new ProgressDialog(ScheduleReportActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        addProfileResponseCall.enqueue(new Callback<AddProfileResponse>() {
            @Override
            public void onResponse(Call<AddProfileResponse> call, Response<AddProfileResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(ScheduleReportActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ScheduleReportActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ScheduleReportActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AddProfileResponse> call, Throwable t) {
                Toast.makeText(ScheduleReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
