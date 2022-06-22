package com.foxy.arrive5.Home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Adapter.CustomPagerAdapter;
import com.foxy.arrive5.Fragments.SelectPoolFragment;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.ConfirmBookingResponse;
import com.foxy.arrive5.Response.GetProfileResponse;
import com.foxy.arrive5.Response.VehicleDetail;
import com.foxy.arrive5.Response.VehicleDetailResponse;
import com.foxy.arrive5.TestCallback;
import com.foxy.arrive5.utils.Constants;
import com.foxy.arrive5.utils.DirectionsJSONParser;
import com.foxy.arrive5.utils.MapAnimator;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.SavePref;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectPoolActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, TestCallback {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    String promoCode = "", promoValue = "";
    LinearLayout layoutType;
    ArrayList<VehicleDetail> vehicledetails;
    DetailBottomSheet fragmentModalBottomSheet1;
    SelectPassengerBottomSheet fragmentModalBottomSheet;
    TextView etPromo;
    ReadPref readPref;
    String vehicleTypeId = "0";
    SavePref savePref;
    String vehicleType;
    String startLat, startLong, endLat, endLong;
    TextView txtPersonal, txtCardType;
    ViewPager viewPager;
    Bundle bundle;
    private GoogleMap mMap;
    private SupportMapFragment map;
    private LatLng s_latLng, d_latLng;
    private TextView txtNoPass;
    private String source, destination;
    private boolean isActivityVisible = false;
    private Polyline polyline;
    private ArrayList<LatLng> points = null;
    private List<GetProfileResponse.BusinessProfileListBean> business_profile_list;

    public static final void setSystemBarTheme(final Activity pActivity, final boolean pIsDark) {
        final int lFlags = pActivity.getWindow().getDecorView().getSystemUiVisibility();
        pActivity.getWindow().getDecorView().setSystemUiVisibility(pIsDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pool);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        readPref = new ReadPref(SelectPoolActivity.this);
        savePref = new SavePref(SelectPoolActivity.this);
        txtNoPass = findViewById(R.id.txtNoPass);
        txtPersonal = findViewById(R.id.txtPersonal);
        txtCardType = findViewById(R.id.txtCardType);
        etPromo = findViewById(R.id.etPromo);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        source = getIntent().getStringExtra("source");
        destination = getIntent().getStringExtra("dest");
        s_latLng = getLocationFromAddress(SelectPoolActivity.this, source);
        d_latLng = getLocationFromAddress(SelectPoolActivity.this, destination);
        startLat = String.valueOf(s_latLng.latitude);
        startLong = String.valueOf(s_latLng.longitude);
        endLat = String.valueOf(d_latLng.latitude);
        endLong = String.valueOf(d_latLng.longitude);
        Log.e("utrutiret", startLat);
        Log.e("utrutiret", startLong);
        Log.e("utrutiret", endLat);
        Log.e("utrutiret", endLong);

        getVehicleList(startLat, startLong, endLat, endLong);
        layoutType = findViewById(R.id.layoutType);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(SelectPoolActivity.this, true);
        map = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);
        txtNoPass.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentModalBottomSheet = new SelectPassengerBottomSheet();
                        fragmentModalBottomSheet.setCancelable(false);
                        fragmentModalBottomSheet.show(getSupportFragmentManager(), "BottomSheet Fragment");
                    }
                });
        layoutType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentModalBottomSheet1 = new DetailBottomSheet();
                //fragmentModalBottomSheet1.registerActivity(this);
                fragmentModalBottomSheet1.setCancelable(false);
                fragmentModalBottomSheet1.show(getSupportFragmentManager(), "BottomSheet Fragment");
            }
        });
        etPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SelectPoolActivity.this, PromoCodeActivity.class), 1);
            }
        });
        String userId = readPref.getUserId();
        setProfiles(userId);
    }

    private void setProfiles(String userId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<GetProfileResponse> getProfileResponseCall = service.getProfileList(userId);
        progressDialog = new ProgressDialog(SelectPoolActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        getProfileResponseCall.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        business_profile_list = response.body().getBusiness_profile_list();
                        String type = business_profile_list.get(0).getType();
                        if (type.equalsIgnoreCase("1")) {
                            txtPersonal.setText("Personal");
                        } else if (type.equalsIgnoreCase("2")) {
                            txtPersonal.setText("Business");
                        }
                        savePref.saveProfileType(txtPersonal.getText().toString());
                        if (business_profile_list.get(0).getCardList().size() > 0) {
                            txtCardType.setText(business_profile_list.get(0).getCardList().get(0).getBrand());
                        } else {
                           // txtCardType.setText("Cash");
                          // txtCardType.setText("No profile");
                        }
                    } else {
                        Toast.makeText(SelectPoolActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                Toast.makeText(SelectPoolActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void closeBottomSheet() {
        if (isActivityVisible)
            fragmentModalBottomSheet1.dismiss();
    }

    public void closeBottomSheet1() {
        if (isActivityVisible)
            fragmentModalBottomSheet.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Thread.interrupted();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityVisible = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                promoCode = data.getStringExtra("promoCode");
                promoValue = data.getStringExtra("promoValue");
                etPromo.setText(promoCode);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityVisible = false;
    }

    private void getVehicleList(String startLat, String startLong, String endLat, String endLong) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<VehicleDetailResponse> vehicleDetailResponseCall = service.getVehicles(startLat, startLong, endLat, endLong);
        vehicleDetailResponseCall.enqueue(new Callback<VehicleDetailResponse>() {
            @Override
            public void onResponse(Call<VehicleDetailResponse> call, Response<VehicleDetailResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        vehicledetails = response.body().getDetails();
                        List<Fragment> fragmentList = new ArrayList<>();
                        for (int i = 0; i < vehicledetails.size(); i++) {
                            SelectPoolFragment fragment = new SelectPoolFragment();
                            Bundle bundle = new Bundle();
                            String jsonList = new Gson().toJson(vehicledetails.get(i));
                            bundle.putString("vehicledetails", jsonList);
                            fragment.setArguments(bundle);
                            fragmentList.add(fragment);
                        }
                        CustomPagerAdapter pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), fragmentList);
                        viewPager.setAdapter(pagerAdapter);
                        vehicleTypeId = vehicledetails.get(0).getVehicleTypeId();
                        vehicleType = vehicledetails.get(0).getVehicleTypeName();
                        savePref.saveVehicleType(vehicleType);
                        Log.e("jhjhjhjhh", response.body().getMessage());
                    } else {
                        Log.e("jhjhjhjhh", response.body().getMessage());
                        Toast.makeText(SelectPoolActivity.this, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleDetailResponse> call, Throwable t) {
                Log.e("jhjhjhjhh", t.getMessage());
                Toast.makeText(SelectPoolActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                this, R.raw.maps_style);
        googleMap.setMapStyle(style);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(SelectPoolActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        return false;
                    }
                });
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        View mapView = map.getView();
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.25); // offset from edges of the map 10% of screen
        s_latLng = getLocationFromAddress(SelectPoolActivity.this, source);
        if (s_latLng != null) {


            int heights = 95;
            int widths = 70;
            BitmapDrawable bitmapdraws = (BitmapDrawable) getResources().getDrawable(R.drawable.current_user_marker);
            Bitmap bs = bitmapdraws.getBitmap();

            Bitmap smallMarkers = Bitmap.createScaledBitmap(bs, widths, heights, false);
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(s_latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarkers))
            );
            builder.include(marker.getPosition());

         /*   Marker marker = googleMap.addMarker(new MarkerOptions().position(s_latLng));
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
            builder.include(marker.getPosition());*/
        }
        d_latLng = getLocationFromAddress(SelectPoolActivity.this, destination);
        if (d_latLng != null) {
           /* Marker marker = googleMap.addMarker(new MarkerOptions().position(d_latLng));
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car));
            builder.include(marker.getPosition());*/


            int heights = 110;
            int widths = 80;
            BitmapDrawable bitmapdraws = (BitmapDrawable) getResources().getDrawable(R.drawable.car_marker);
            Bitmap bs = bitmapdraws.getBitmap();

            Bitmap smallMarkers = Bitmap.createScaledBitmap(bs, widths, heights, false);
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(d_latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarkers))
            );
            builder.include(marker.getPosition());
        }
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        if (s_latLng != null && d_latLng != null) {
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            googleMap.animateCamera(cu);
            String url = getDirectionsUrl(s_latLng, d_latLng);
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
        } else
            Toast.makeText(SelectPoolActivity.this, "" + getResources().getString(R.string.enter_address), Toast.LENGTH_SHORT).show();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String alternative = "alternatives=true";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyClkiUQxIeczQEjTrw-dy9h2fj9qjXDk_4";
        return url;
    }

    @SuppressLint("LongLogTag")
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public void ConfirmClick(View view) {
        String userId = readPref.getUserId();
        String start = source;
        String end = destination;
        startLat = String.valueOf(s_latLng.latitude);
        startLong = String.valueOf(s_latLng.longitude);
        endLat = String.valueOf(d_latLng.latitude);
        endLong = String.valueOf(d_latLng.longitude);
        String noPass = readPref.getNoPass();
        String vehicleId = vehicleTypeId;
        String subTypeId = readPref.getSubTypeId();
        String promoCode = promoValue;
        confirmBooking(userId, start, end, startLat, startLong, endLat, endLong, noPass, vehicleId, subTypeId, promoCode);

        Log.e("Gdhudheufg", userId);
        Log.e("Gdhudheufg", start);
        Log.e("Gdhudheufg", end);
        Log.e("Gdhudheufg", noPass);
        Log.e("Gdhudheufg", vehicleId);
        Log.e("Gdhudheufg", subTypeId);
        Log.e("Gdhudheufg", promoCode);
    }

    private void confirmBooking(String userId, String start, String end, String startLat, String startLong, String endLat, String endLong, String noPass, String vehicleId, String subTypeId, String promoCode) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ConfirmBookingResponse> confirmBookingResponseCall = service.booking(userId, start, end, startLat, startLong, endLat, endLong, noPass, vehicleId, subTypeId, promoCode);
        progressDialog = new ProgressDialog(SelectPoolActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        confirmBookingResponseCall.enqueue(new Callback<ConfirmBookingResponse>() {
            @Override
            public void onResponse(Call<ConfirmBookingResponse> call, Response<ConfirmBookingResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        Toast.makeText(SelectPoolActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SelectPoolActivity.this, FindingRideActivity.class);
                        savePref.saveBookingAmount(response.body().getResult().getDeductAmt());
                        startActivity(intent);
                    } else {
                        Toast.makeText(SelectPoolActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } else {


                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ConfirmBookingResponse> call, Throwable t) {
                Toast.makeText(SelectPoolActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(SelectPoolActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(SelectPoolActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SelectPoolActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(SelectPoolActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(SelectPoolActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(SelectPoolActivity.this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(SelectPoolActivity.this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void BackClick(View view) {
        finish();
    }

    @Override
    public void getTestData(Bundle bundle) {
        if (bundle != null) {
            String cardType = bundle.getString(Constants.CARDTYPE);
            String profileType = bundle.getString(Constants.PROFILETYPE);
            txtPersonal.setText(profileType);
            txtCardType.setText(cardType);
            savePref.saveProfileType(profileType);
            savePref.saveCardId(bundle.getString(Constants.CARDID));
        }
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            PolylineOptions lineOptions = null;
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
//                if (polyline != null) {
//                    polyline.remove();
//                }
//                lineOptions.addAll(points);
//                lineOptions.width(5);
//                lineOptions.color(Color.RED);
//                mMap.addPolyline(lineOptions);
//            }
                startAnim();
            }
        }

        private void startAnim() {
            if (mMap != null) {
                Collections.reverse(points);
                MapAnimator.getInstance().animateRoute(mMap, points);
            } else {
                Toast.makeText(getApplicationContext(), "Map not ready", Toast.LENGTH_LONG).show();
            }
        }
    }
}
