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
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
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

public class SignupActivity extends AppCompatActivity implements TextWatcher {
    public static double latitude = 0;
    public static double longitude = 0;
    CountryCodePicker ccp;
    EditText txtMobile;
    SavePref savePref;
    String code = "";
    ImageView imgCross;
    TextView txtPolicy;
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
        savePref = new SavePref(SignupActivity.this);
        imgCross = findViewById(R.id.imgCross);
        ccp = findViewById(R.id.ccp);
        txtPolicy = findViewById(R.id.txtPolicy);
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
        SpannableString spannableString = new SpannableString(getString(R.string.by_signUp));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(SignupActivity.this, PolicyActivity.class);
                intent.putExtra("isPolicy", "1");
                startActivity(intent);
            }
        };
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(SignupActivity.this, PolicyActivity.class);
                intent.putExtra("isPolicy", "0");
                startActivity(intent);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {

            }
        };
        spannableString.setSpan(clickableSpan1, 31,
                49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, 50,
                65, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan2, 65,
                spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtPolicy.setText(spannableString, TextView.BufferType.SPANNABLE);
        txtPolicy.setMovementMethod(LinkMovementMethod.getInstance());
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
        Call<MobileVerifyResponse> mobileVerifyResponseCall = service.verifyMobile(mobile);

        progressDialog = new ProgressDialog(SignupActivity.this);
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
                        Toast.makeText(SignupActivity.this, "" + getResources().getString(R.string.user_exists), Toast.LENGTH_SHORT).show();
                    }
                    else if (status.equalsIgnoreCase("false")) {
                        String otp = response.body().getOtp();
                        Intent intent = new Intent(SignupActivity.this, OTPActivity.class);
                        intent.putExtra("mobile", txtMobile.getText().toString());
                        intent.putExtra("code", code);
                        intent.putExtra("otp", otp);
                        savePref.saveMobile(txtMobile.getText().toString());
                        savePref.saveCode(code);
                        intent.putExtra("isForgot", "0");
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(SignupActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MobileVerifyResponse> call, Throwable t) {

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
