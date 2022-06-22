package com.foxy.arrive5.IntroScreens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.MobileVerifyResponse;
import com.foxy.arrive5.utils.Validations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity implements TextWatcher {
    private static final String TAG = "OTPVerifyActivity";
    String isForgot;
    TextView txtPhone, txtTime;
    String otp, otpCode;
    CountDownTimer timer;
    ImageView tv_resend;
    ProgressDialog progressDialog;
    private EditText code_1, code_2, code_3, code_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        setupUI(findViewById(R.id.layout));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        isForgot = getIntent().getStringExtra("isForgot");
        txtPhone = findViewById(R.id.txtPhone);
        otpCode = getIntent().getStringExtra("otp");
        Log.e("dfsdihfsdf", otpCode);
       // Toast.makeText(OTPActivity.this, "OTP is:" + " " + otpCode, Toast.LENGTH_SHORT).show();
        String code = "+" + getIntent().getStringExtra("code");
        String mobile = getIntent().getStringExtra("mobile");
        txtPhone.setText(code + mobile);
        txtTime = findViewById(R.id.txtTime);
        tv_resend = findViewById(R.id.tv_resend);
        code_1 = findViewById(R.id.et1);
        code_2 = findViewById(R.id.et2);
        code_3 = findViewById(R.id.et3);
        code_4 = findViewById(R.id.et4);
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
                if (code_1.getText().toString().length() == 0)  //size as per your requirement
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
                if (code_2.getText().toString().length() == 1) //size as per your requirement
                {
                    code_3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (code_2.getText().toString().length() == 0) //size as per your requirement
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
        timer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                txtTime.setVisibility(View.VISIBLE);
                tv_resend.setVisibility(View.GONE);
                txtTime.setText("Seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                txtTime.setVisibility(View.GONE);
                tv_resend.setVisibility(View.VISIBLE);
            }
        }.start();
    }


    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    Validations.hideSoftKeyboard(OTPActivity.this);
                    return false;
                }

            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void NextClick(View view) {
        otp = code_1.getText().toString() + code_2.getText().toString() + code_3.getText().toString() + code_4.getText().toString();
        if (!otp.equalsIgnoreCase("") && otp.equalsIgnoreCase(otpCode)) {
            if (isForgot.equalsIgnoreCase("0")) {
                startActivity(new Intent(OTPActivity.this, CreateProfileActivity.class));
                finish();
            } else {
                startActivity(new Intent(OTPActivity.this, ResetPasswordActivity.class));
                finish();
            }
        } else {
            Toast.makeText(this, "Please enter Valid OTP", Toast.LENGTH_SHORT).show();
        }
    }

    public void BackClick(View view) {
        finish();
    }

    public void ResendClick(View view) {
        String mobile = txtPhone.getText().toString();
        verifyUser(mobile);
        timer.start();
        tv_resend.setVisibility(View.GONE);
        txtTime.setVisibility(View.VISIBLE);
    }

    private void verifyUser(String mobile) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<MobileVerifyResponse> mobileVerifyResponseCall = service.verifyMobile(mobile);
        progressDialog = new ProgressDialog(OTPActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        mobileVerifyResponseCall.enqueue(new Callback<MobileVerifyResponse>() {
            @Override
            public void onResponse(Call<MobileVerifyResponse> call, Response<MobileVerifyResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(OTPActivity.this, "" + getResources().getString(R.string.user_exists), Toast.LENGTH_SHORT).show();
                    } else if (status.equalsIgnoreCase("false")) {
                        String otp = String.valueOf(response.body().getOtp());
                        otpCode = otp;
                    } else {
                        Toast.makeText(OTPActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MobileVerifyResponse> call, Throwable t) {
                Toast.makeText(OTPActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
