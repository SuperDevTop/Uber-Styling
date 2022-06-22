package com.foxy.arrive5.Home;

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
import com.foxy.arrive5.Response.SaveSplitResponse;
import com.foxy.arrive5.utils.ReadPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplitFareActivity extends AppCompatActivity {
    ReadPref readPref;
    String userId, amount, bookingId, type;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_fare);
        readPref = new ReadPref(SplitFareActivity.this);
        setFinishOnTouchOutside(false);
        userId = readPref.getUserId();
        amount = getIntent().getStringExtra("amount");
        bookingId = getIntent().getStringExtra("booking_id");
    }

    public void CancelClick(View view) {
        type = "reject";
        saveSplit(bookingId, userId, type, amount);
    }

    private void saveSplit(String bookingId, String userId, String type, String amount) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<SaveSplitResponse> saveSplitResponseCall = service.saveSplit(bookingId, userId, type, amount);
        progressDialog = new ProgressDialog(SplitFareActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        saveSplitResponseCall.enqueue(new Callback<SaveSplitResponse>() {
            @Override
            public void onResponse(Call<SaveSplitResponse> call, Response<SaveSplitResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Intent intent = new Intent(SplitFareActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SplitFareActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SaveSplitResponse> call, Throwable t) {
                Toast.makeText(SplitFareActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void AcceptClick(View view) {
        type = "accept";
        saveSplit(bookingId, userId, type, amount);
    }
}
