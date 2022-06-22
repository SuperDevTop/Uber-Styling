package com.mobi.arrive5d;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
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
import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.Fragments.EarningFragment;
import com.mobi.arrive5d.Fragments.HelpFragment;
import com.mobi.arrive5d.Fragments.HomeFragment;
import com.mobi.arrive5d.Fragments.MyTripFragment;
import com.mobi.arrive5d.Fragments.NotificationFragment;
import com.mobi.arrive5d.Fragments.RatingReviewsFragment;
import com.mobi.arrive5d.Fragments.SchedulePickups;
import com.mobi.arrive5d.Fragments.SettingFragment;
import com.mobi.arrive5d.IntroScreens.LoginActivity;
import com.mobi.arrive5d.IntroScreens.VehicleInfoActivity;
import com.mobi.arrive5d.Response.LogoutResponse;
import com.mobi.arrive5d.Response.UpdateStatusResponse;
import com.mobi.arrive5d.SideMenu.ProfileActivity;
import com.mobi.arrive5d.utils.ReadPref;
import com.mobi.arrive5d.utils.SavePref;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;
    CircleImageView imgProfile;
    RelativeLayout cart_click;
    ReadPref readPref;
    RelativeLayout layoutTop;
    ToggleButton tb;
    TextView txtTitle;
    SavePref savePref;
    ProgressDialog progressDialog;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private String[] activityTitles;
    private Handler mHandler;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readPref = new ReadPref(MainActivity.this);
        savePref = new SavePref(MainActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tb = findViewById(R.id.tb);
        txtTitle = findViewById(R.id.txtTitle);
        cart_click = findViewById(R.id.cart_click);
        layoutTop = findViewById(R.id.layoutTop);
        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = navHeader.findViewById(R.id.img_profile);
        activityTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        displaySelectedScreen(R.id.nav_Home);
        loadNavHeader();
        final String driverId = readPref.getDriverId();
        final String lat = readPref.getLatitude();
        final String lng = readPref.getLongitude();
        String isOnline = readPref.getOnlinestatus();
        if (isOnline.equalsIgnoreCase("1")) {
            tb.setChecked(true);
        } else {
            tb.setChecked(false);
        }
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setOnline(driverId, "1");
                }
                else {
                    setOnline(driverId, "0");
                }
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
                                status.startResolutionForResult(MainActivity.this, 1000);
                            }
                            catch (IntentSender.SendIntentException e) {


                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        }
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_Home:
                layoutTop.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.GONE);
                fragment = new HomeFragment();
                cart_click.setVisibility(View.VISIBLE);
                tb.setVisibility(View.GONE);
                break;
            case R.id.nav_your_rides:
                layoutTop.setVisibility(View.GONE);
                txtTitle.setVisibility(View.VISIBLE);
                fragment = new SchedulePickups();
                break;
            case R.id.nav_free_rides:
                fragment = new MyTripFragment();
                cart_click.setVisibility(View.GONE);
                tb.setVisibility(View.GONE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText(getResources().getString(R.string.my_trips));
                break;
            case R.id.nav_payment:
                Intent intent = new Intent(MainActivity.this, VehicleInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("isDirect", "1");
                startActivity(intent);
                //fragment = new BlankFragment();
                break;
            case R.id.nav_notify:
                fragment = new EarningFragment();
                cart_click.setVisibility(View.GONE);
                tb.setVisibility(View.GONE);
                cart_click.setVisibility(View.GONE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText(getResources().getString(R.string.earnings));
                break;
            case R.id.nav_ratings:
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText(getResources().getString(R.string.notification));
                fragment = new NotificationFragment();
                cart_click.setVisibility(View.GONE);
                tb.setVisibility(View.GONE);
                break;
            case R.id.nav_promo:
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText(getResources().getString(R.string.reviews));
                cart_click.setVisibility(View.GONE);
                tb.setVisibility(View.GONE);
                fragment = new RatingReviewsFragment();
                break;
            case R.id.nav_rewards:
                fragment = new HelpFragment();
                cart_click.setVisibility(View.GONE);
                tb.setVisibility(View.GONE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText(getResources().getString(R.string.help));
                break;
            case R.id.nav_help:
                layoutTop.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText(getResources().getString(R.string.action_settings));
                cart_click.setVisibility(View.GONE);
                tb.setVisibility(View.GONE);
                //fragment = new HelpFragment();
                fragment = new SettingFragment();
                break;

            case R.id.nav_settings:
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this, R.style.alertDialog);
                builder.setTitle(getResources().getString(R.string.sign_out));
                builder.setMessage(getResources().getString(R.string.dia_are_you_sure));
                builder.setCancelable(true);

                builder.setPositiveButton(
                        getResources().getString(R.string.dia_yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                savePref.saveLogin(false);
                                String driverId = readPref.getDriverId();
                                String type = "driver";
                                logoutUser(driverId, type);
                            }
                        });

                builder.setNegativeButton(
                        getResources().getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                getSupportActionBar().setTitle(activityTitles[0]);
                                dialog.cancel();
                            }
                        });

                android.support.v7.app.AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.nav_logout:
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.foxy.arrive5");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                } else {
                    Toast.makeText(this, "No App Found", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment).addToBackStack("my_fragment");
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void setOnline(String driverId, String s) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<UpdateStatusResponse> updateStatusResponseCall = service.updateStatus(s, driverId);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        updateStatusResponseCall.enqueue(new Callback<UpdateStatusResponse>() {
            @Override
            public void onResponse(Call<UpdateStatusResponse> call, Response<UpdateStatusResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        String isOnline = response.body().getOnLineStatus();
                        savePref.saveOnlineStatus(isOnline);
                    } else {
                        Toast.makeText(MainActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UpdateStatusResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadNavHeader() {
        txtName.setText(readPref.getFirstName()+" "+readPref.getLastName());
        txtWebsite.setText(readPref.getMobile());
        image = readPref.getImage();
        Glide.with(MainActivity.this).load(readPref.getVehicleImg()).into(imgProfile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    private void logoutUser(String driverId, String type) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<LogoutResponse> logoutResponseCall = service.logout(driverId, type);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        logoutResponseCall.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(MainActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);
        return true;
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
}
