package com.mobi.arrive5d.IntroScreens;

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

import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.MobileVerifyResponse.MobileVerify;
import com.mobi.arrive5d.utils.GPSTracker;
import com.mobi.arrive5d.utils.SavePref;
import com.mobi.arrive5d.utils.Validations;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements TextWatcher {
    public static double latitude = 0;
    public static double longitude = 0;
    CountryCodePicker ccp;
    EditText txtMobile;
    SavePref savePref;
    String code = "";
    ImageView imgCross;
    ProgressDialog progressDialog;
    GPSTracker gpsTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        setupUI(findViewById(R.id.layout));
        savePref = new SavePref(SignupActivity.this);
        imgCross = findViewById(R.id.imgCross);
        ccp = findViewById(R.id.ccp);
        txtMobile = findViewById(R.id.txtMobile);
        txtMobile.addTextChangedListener(this);
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMobile.setText("");
            }
        });
        gpsTrack = new GPSTracker(SignupActivity.this);
        if (gpsTrack.canGetLocation()) {
            latitude = gpsTrack.getLatitude();
            longitude = gpsTrack.getLongitude();
            Log.e("GPSLat", "" + latitude);
            Log.e("GPSLong", "" + longitude);
        } else {
            gpsTrack.showSettingsAlert();
            Log.e("ShowAlert", "ShowAlert");
        }
        String countryCode = getAddress(SignupActivity.this, latitude, longitude);
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
        savePref.saveCode(code);
    }

    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    Validations.hideSoftKeyboard(SignupActivity.this);
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

    public void LoginClick(View view) {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void OTPClick(View view) {
        if (Validations.isValidPhone(txtMobile)) {
            String mobile = "+" + code + txtMobile.getText().toString();
            verifyUser(mobile);
        }
    }

    private void verifyUser(String mobile) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<MobileVerify> mobileVerifyResponseCall = service.verifyMobile(mobile);
        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        mobileVerifyResponseCall.enqueue(new Callback<MobileVerify>() {
            @Override
            public void onResponse(Call<MobileVerify> call, Response<MobileVerify> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(SignupActivity.this, "" + getResources().getString(R.string.user_exists), Toast.LENGTH_SHORT).show();
                    } else if (status.equalsIgnoreCase("false")) {
                        String otp = response.body().getOtp();
                        Intent intent = new Intent(SignupActivity.this, OTPActivity.class);
                        intent.putExtra("mobile", txtMobile.getText().toString());
                        intent.putExtra("code", code);
                        intent.putExtra("otp", otp);
                        savePref.saveMobile(txtMobile.getText().toString());
                        savePref.saveCode(code);
                        intent.putExtra("isForgot", "0");
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignupActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MobileVerify> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
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
