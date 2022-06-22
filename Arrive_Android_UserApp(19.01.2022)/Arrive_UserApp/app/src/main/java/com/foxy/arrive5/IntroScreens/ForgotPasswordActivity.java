package com.foxy.arrive5.IntroScreens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.MobileVerifyResponse;
import com.foxy.arrive5.utils.SavePref;
import com.foxy.arrive5.utils.Validations;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements TextWatcher {
    public static double latitude = 0;
    public static double longitude = 0;
    EditText txtMobile;
    CountryCodePicker ccp;
    ImageView imgCross;
    SavePref savePref;
    String code = "";
    ProgressDialog progressDialog;
    GPSTracker gpsTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        setupUI(findViewById(R.id.layout));
        imgCross = findViewById(R.id.imgCross);
        savePref = new SavePref(ForgotPasswordActivity.this);
        ccp = findViewById(R.id.ccp);
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMobile.setText("");
            }
        });
        gpsTrack = new GPSTracker(ForgotPasswordActivity.this);
        if (gpsTrack.canGetLocation()) {
            latitude = gpsTrack.getLatitude();
            longitude = gpsTrack.getLongitude();
            Log.e("GPSLat", "" + latitude);
            Log.e("GPSLong", "" + longitude);
        } else {
            gpsTrack.showSettingsAlert();
            Log.e("ShowAlert", "ShowAlert");
        }
        String countryCode = getAddress(ForgotPasswordActivity.this, latitude, longitude);
        String[] rl = getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(countryCode.trim())) {
                code = g[0];
                break;
            }
        }
        Log.e("countryCode", "" + countryCode);
        Log.e("Code", "" + code);
        ccp.setCountryForNameCode(countryCode);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                code = selectedCountry.getPhoneCode();
            }
        });
        txtMobile = findViewById(R.id.txtMobile);
        txtMobile.addTextChangedListener(this);
    }

    public String getAddress(Context ctx, double latitude, double longitude) {
        String region_code = null;
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                region_code = address.getCountryCode();
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        return region_code;
    }


    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    Validations.hideSoftKeyboard(ForgotPasswordActivity.this);
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

    public void OTPClick(View view) {
        if (Validations.isValidPhone(txtMobile)) {
            String mobile = "+" + code + txtMobile.getText().toString();
            verifyUser(mobile);
        }
    }

    private void verifyUser(String mobile) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<MobileVerifyResponse> mobileVerifyResponseCall = service.checkMobile(mobile);
        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
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
                        String otp = String.valueOf(response.body().getOtp());
                        String id = response.body().getDetails().getId();
                        savePref.saveId(id);
                        Intent intent = new Intent(ForgotPasswordActivity.this, OTPActivity.class);
                        intent.putExtra("isForgot", "1");
                        intent.putExtra("mobile", txtMobile.getText().toString());
                        intent.putExtra("code", code);
                        intent.putExtra("otp", otp);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MobileVerifyResponse> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void BackClick(View view) {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count > 0) {
            imgCross.setVisibility(View.VISIBLE);
        } else {
            imgCross.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
