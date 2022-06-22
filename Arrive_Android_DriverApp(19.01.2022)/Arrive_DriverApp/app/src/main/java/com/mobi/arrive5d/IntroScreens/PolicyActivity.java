package com.mobi.arrive5d.IntroScreens;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.GetPolicyResponse;
import com.mobi.arrive5d.Response.GetTermsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PolicyActivity extends AppCompatActivity {
    TextView txtDesc, txtTitle;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        txtDesc = findViewById(R.id.txtDesc);
        txtTitle = findViewById(R.id.txtTitle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        String isPolicy = getIntent().getStringExtra("isPolicy");
        if (isPolicy.equalsIgnoreCase("1")) {
            getTermsData();
        } else if (isPolicy.equalsIgnoreCase("0")) {
            getPolicyData();
        }
    }

    private void getTermsData() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<GetPolicyResponse> contentResponseCall = service.getPolicy();
        progressDialog = new ProgressDialog(PolicyActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        contentResponseCall.enqueue(new Callback<GetPolicyResponse>() {
            @Override
            public void onResponse(Call<GetPolicyResponse> call, Response<GetPolicyResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        String data = response.body().getGet_data().getDescription();
                        String title = response.body().getGet_data().getTitle();
                        txtTitle.setText(title);
                        txtDesc.setText(data);
                    }
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetPolicyResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void getPolicyData() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<GetTermsResponse> contentResponseCall = service.getTerms();
        progressDialog = new ProgressDialog(PolicyActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        contentResponseCall.enqueue(new Callback<GetTermsResponse>() {
            @Override
            public void onResponse(Call<GetTermsResponse> call, Response<GetTermsResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        String data = response.body().getGet_terms().getDescription();
                        txtDesc.setText(data);
                        String title = response.body().getGet_terms().getTitle();
                        txtTitle.setText(title);
                    }
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetTermsResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void BackClick(View view) {
        finish();
    }
}
