package com.mobi.arrive5d.IntroScreens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.MainActivity;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.LoginResponse.Login;
import com.mobi.arrive5d.utils.GPSTracker;
import com.mobi.arrive5d.utils.SavePref;
import com.mobi.arrive5d.utils.Validations;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements TextWatcher, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static double latitude = 0;
    public static double longitude = 0;
    GoogleApiClient mGoogleApiClient;
    CountryCodePicker ccp;
    String code = "";
    ImageView imgCross;
    ProgressDialog progressDialog;
    SavePref pref;
    List<String> vehicleImages;
    String[] permissions = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.RECEIVE_SMS,
            android.Manifest.permission.READ_SMS
    };
    GPSTracker gpsTrack;
    EditText txtMobile, txtPassword;
    String token;
    TextView txtPolicy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mobi.arrive5d.R.layout.activity_login);
        setupUI(findViewById(R.id.layout));
        ccp = findViewById(R.id.ccp);
        txtPolicy = findViewById(R.id.txtPolicy);
        pref = new SavePref(LoginActivity.this);
        imgCross = findViewById(R.id.imgCross);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        txtMobile = findViewById(R.id.txtMobile);
        txtPassword = findViewById(R.id.txtPassword);
        txtMobile.addTextChangedListener(this);
        gpsTrack = new GPSTracker(LoginActivity.this);
        if (gpsTrack.canGetLocation()) {
            latitude = gpsTrack.getLatitude();
            longitude = gpsTrack.getLongitude();
            Log.e("GPSLat", "" + latitude);
            Log.e("GPSLong", "" + longitude);
        } else {
            gpsTrack.showSettingsAlert();
            Log.e("ShowAlert", "ShowAlert");
        }
        String countryCode = getAddress(LoginActivity.this, latitude, longitude);
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
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMobile.setText("");
            }
        });
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            mGoogleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            builder.setAlwaysShow(true); // this is the key ingredient
            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(LoginActivity.this, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        }
        checkPermissions();
        token = FirebaseInstanceId.getInstance().getToken();
        SpannableString spannableString = new SpannableString(getString(R.string.by_signUp));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(LoginActivity.this, PolicyActivity.class);
                intent.putExtra("isPolicy", "1");
                startActivity(intent);
            }
        };
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(LoginActivity.this, PolicyActivity.class);
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

    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    Validations.hideSoftKeyboard(LoginActivity.this);
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

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }
    public void SignupClick(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    public void HomeClick(View view) {
        if (Validations.isValidPhone(txtMobile) && Validations.isValidPassword(txtPassword)) {
            String mobile = "+" + code + txtMobile.getText().toString();
            String pass = txtPassword.getText().toString();
            String type = "android";
            setLogin(mobile, pass, token, type);
        }
    }

    private void setLogin(String mobile, String pass, String token, String type) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Login> loginResponseCall = service.userLogin(mobile, pass, token, type);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        loginResponseCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                String status = response.body().getStatus();
                if (response.isSuccessful()) {
                    if (status.equalsIgnoreCase("true")) {
                        //Log.e("response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                        String name = response.body().getDetails().getFirstName() + " " + response.body().getDetails().getLastName();
                        String email = response.body().getDetails().getEmail();
                        String mobile = response.body().getDetails().getMobile();
                        String image = response.body().getDetails().getImage();
                        String city = response.body().getDetails().getCity();
                        String country = response.body().getDetails().getCountry();
                        String gender = response.body().getDetails().getGender();
                        String driverId = response.body().getDetails().getId();
                        String isOnline = response.body().getDetails().getIsOnline();
                        String address = response.body().getDetails().getAddress();
                        pref.saveFirstName(response.body().getDetails().getFirstName());
                        pref.saveMiddleName(response.body().getDetails().getMiddleName());
                        pref.saveLastName(response.body().getDetails().getLastName());
                        vehicleImages = response.body().getDetails().getVehicleImages();
                        pref.saveDoors(response.body().getDetails().getNoofdoor());
                        pref.saveBelts(response.body().getDetails().getNoofsbelt());
                        pref.saveVehicleType(response.body().getDetails().getVechileTypeName());
                        pref.saveVehicleSubType(response.body().getDetails().getVechileSubtypeName());
                        pref.saveModel(response.body().getDetails().getModelName());
                        pref.saveColor(response.body().getDetails().getColorName());
                        pref.saveYear(response.body().getDetails().getYear());
                        pref.saveMake(response.body().getDetails().getMake());
                        pref.saveVehicleImages(vehicleImages, "image_uris");
                        pref.saveAddress(address);
                        pref.saveGender(gender);
                        pref.saveCountry(country);
                        pref.saveCity(city);
                        pref.saveName(name);
                        pref.saveEmail(email);
                        pref.saveMobile(mobile);
                        pref.saveImage(image);
                        pref.saveLogin(true);
                        pref.saveDriverId(driverId);
                        pref.saveOnlineStatus(isOnline);
                        pref.saveInsuranceImg(response.body().getDetails().getInsuaranceImg());
                        pref.saveDrivingLicenseImg(response.body().getDetails().getLicenceImg());
                        pref.saveAdharImg(response.body().getDetails().getAdharImg());
                        pref.saveVehicleImg(response.body().getDetails().getVechileImg());
                        pref.saveEmail(response.body().getDetails().getEmail());
                        pref.saveFirstName(response.body().getDetails().getFirstName());
                        pref.saveMiddleName(response.body().getDetails().getMiddleName());
                        pref.saveLastName(response.body().getDetails().getLastName());
                        pref.saveName(response.body().getDetails().getFirstName() + " " + response.body().getDetails().getMiddleName() + " " + response.body().getDetails().getLastName());
                        pref.saveSSN(response.body().getDetails().getSocialSecrityno());
                        pref.saveDob(response.body().getDetails().getDob());
                        pref.saveLicenseNo(response.body().getDetails().getDrivingLicence());
                        pref.saveExpiryDate(response.body().getDetails().getExpiredate());
                        pref.saveAddress1(response.body().getDetails().getAddress1());
                        pref.saveAddress2(response.body().getDetails().getAddress2());
                        pref.saveAddress(response.body().getDetails().getAddress());
                        pref.saveMobile(response.body().getDetails().getMobile());
                        pref.saveImage(response.body().getDetails().getImage());
                        pref.saveZip(response.body().getDetails().getZipcode());
                        pref.saveState(response.body().getDetails().getState());
                        pref.saveCity(response.body().getDetails().getCity());
                        pref.saveCountry(response.body().getDetails().getCountry());
                        pref.saveLicensePlate(response.body().getDetails().getLicencePlate());
                        pref.saveVehicleReg(response.body().getDetails().getVechileReg());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void ForgotClick(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
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
