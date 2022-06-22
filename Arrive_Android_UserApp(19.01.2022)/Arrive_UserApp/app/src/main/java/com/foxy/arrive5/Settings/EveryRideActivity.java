package com.foxy.arrive5.Settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.DonationResponse;
import com.foxy.arrive5.utils.ReadPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.foxy.arrive5.Home.RequestRideActivity.setSystemBarTheme;

public class EveryRideActivity extends AppCompatActivity {
    LinearLayout layoutDonate;
    ReadPref readPref;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_ride);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(EveryRideActivity.this, true);
        readPref = new ReadPref(EveryRideActivity.this);
        layoutDonate = findViewById(R.id.layoutDonate);
        final String userId = readPref.getUserId();
        layoutDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donate(userId);
            }
        });
    }

    private void donate(String userId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<DonationResponse> donationResponseCall = service.donate(userId);
        progressDialog = new ProgressDialog(EveryRideActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        donationResponseCall.enqueue(new Callback<DonationResponse>() {
            @Override
            public void onResponse(Call<DonationResponse> call, Response<DonationResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(EveryRideActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EveryRideActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EveryRideActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DonationResponse> call, Throwable t) {
                Toast.makeText(EveryRideActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void BackClick(View view) {
        finish();
    }
}
