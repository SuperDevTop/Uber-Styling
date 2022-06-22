package com.mobi.arrive5d.SideMenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.OtpVerificationResponse;
import com.mobi.arrive5d.utils.ReadPref;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DropOffOTPActivity extends AppCompatActivity implements TextWatcher {
    private static final String TAG = "OTPVerifyActivity";
    String otp;
    CircleImageView imgUser;
    ReadPref readPref;
    TextView txtName, txtAddress;
    ProgressDialog progressDialog;
    private EditText code_1, code_2, code_3, code_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_off_otp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        readPref = new ReadPref(DropOffOTPActivity.this);
        imgUser = findViewById(R.id.imgUser);
        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);
        code_1 = findViewById(R.id.et1);
        code_2 = findViewById(R.id.et2);
        code_3 = findViewById(R.id.et3);
        code_4 = findViewById(R.id.et4);
        Glide.with(DropOffOTPActivity.this).load(readPref.getImage()).into(imgUser);
        txtName.setText(readPref.getName());
        txtAddress.setText(readPref.getEndPoint());
        code_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (code_1.getText().toString().length() == 1)     //size as per your requirement
                {
                    code_2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (code_1.getText().toString().length() == 0)     //size as per your requirement
                {
                    code_1.requestFocus();
                }
            }
        });

        code_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (code_2.getText().toString().length() == 1)     //size as per your requirement
                {
                    code_3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (code_2.getText().toString().length() == 0)     //size as per your requirement
                {
                    code_1.requestFocus();
                }
            }
        });

        code_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (code_3.getText().toString().length() == 1)     //size as per your requirement
                {
                    code_4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (code_3.getText().toString().length() == 0)     //size as per your requirement
                {
                    code_2.requestFocus();
                }
            }
        });

        code_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (code_4.getText().toString().length() == 1) {
                    code_4.requestFocus();

                } else {
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (code_4.getText().toString().length() == 0)     //size as per your requirement
                {
                    code_3.requestFocus();
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void SubmitClick(View view) {
        otp = code_1.getText().toString() + code_2.getText().toString() + code_3.getText().toString() + code_4.getText().toString();
        String userId = readPref.getUserId();
        String bookingId = readPref.getBookingId();
        String driverId = readPref.getDriverId();
        String code = otp;
        checkOTP(userId, bookingId, driverId, code);
    }

    private void checkOTP(String userId, String bookingId, String driverId, String code) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<OtpVerificationResponse> otpVerificationResponseCall = service.verifyOTP(userId, bookingId, driverId, code);
        progressDialog = new ProgressDialog(DropOffOTPActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        otpVerificationResponseCall.enqueue(new Callback<OtpVerificationResponse>() {
            @Override
            public void onResponse(Call<OtpVerificationResponse> call, Response<OtpVerificationResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Intent intent = new Intent(DropOffOTPActivity.this, StartNavigationActivity.class);
                        intent.putExtra("isDrop", "1");
                        intent.putExtra("user_mobile", getIntent().getStringExtra("user_mobile"));
                        startActivity(intent);
                    } else {
                        Toast.makeText(DropOffOTPActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<OtpVerificationResponse> call, Throwable t) {
                Toast.makeText(DropOffOTPActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void BackClick(View view) {
        finish();
    }
}
