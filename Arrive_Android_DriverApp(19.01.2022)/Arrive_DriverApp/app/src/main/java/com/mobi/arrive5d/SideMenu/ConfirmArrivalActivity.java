package com.mobi.arrive5d.SideMenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.FinishRideResponse;
import com.mobi.arrive5d.utils.ReadPref;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmArrivalActivity extends AppCompatActivity {
    TextView txt;
    Button arrive;
    ReadPref readPref;
    ProgressDialog progressDialog;
    CircleImageView imgUser;
    private String isDrop = "0";

    public static final void setSystemBarTheme(final Activity pActivity, final boolean pIsDark) {
        final int lFlags = pActivity.getWindow().getDecorView().getSystemUiVisibility();
        pActivity.getWindow().getDecorView().setSystemUiVisibility(pIsDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_arrival);
        imgUser = findViewById(R.id.imgUser);
        readPref = new ReadPref(ConfirmArrivalActivity.this);
        this.setFinishOnTouchOutside(false);
        Glide.with(ConfirmArrivalActivity.this).load(readPref.getImage()).into(imgUser);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(ConfirmArrivalActivity.this, true);
        txt = findViewById(R.id.txt);
        arrive = findViewById(R.id.arrive);
        if (getIntent().getStringExtra("isDrop") != null) {
            isDrop = getIntent().getStringExtra("isDrop");
        } else {
            isDrop = "0";
        }
        if (isDrop.equalsIgnoreCase("1")) {
            txt.setText(getResources().getString(R.string.did_drop));
            arrive.setBackground(getResources().getDrawable(R.drawable.confirm_drop_button));
        } else {
            txt.setText(getResources().getString(R.string.have_Arrived));
            arrive.setBackground(getResources().getDrawable(R.drawable.confirm_arrival_button));
        }
    }

    public void BackClick(View view) {
        finish();
    }

    public void ArriveClick(View view) {
        if (txt.getText().equals(getResources().getString(R.string.have_Arrived))) {
            Intent intent = new Intent(ConfirmArrivalActivity.this, ArriveClientActivity.class);
            startActivity(intent);
        } else if (txt.getText().equals(getResources().getString(R.string.did_drop))) {
            String userId = readPref.getUserId();
            String driverId = readPref.getDriverId();
            String bookingId = readPref.getBookingId();
            finishRide(userId, bookingId, driverId);
        }
    }

    private void finishRide(String userId, String bookingId, String driverId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        final Call<FinishRideResponse> finishRideResponseCall = service.finishRide(userId, bookingId, driverId);
        progressDialog = new ProgressDialog(ConfirmArrivalActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        finishRideResponseCall.enqueue(new Callback<FinishRideResponse>() {
            @Override
            public void onResponse(Call<FinishRideResponse> call, Response<FinishRideResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Intent intent = new Intent(ConfirmArrivalActivity.this, BilllingDetailsActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ConfirmArrivalActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<FinishRideResponse> call, Throwable t) {
                Toast.makeText(ConfirmArrivalActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void CancelClick(View view) {
        finish();
    }
}
