package com.foxy.arrive5.Home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.ReasonList;
import com.foxy.arrive5.Response.ReasonListResponse;
import com.foxy.arrive5.Response.SubmitReasonResponse;
import com.foxy.arrive5.utils.DirectionsJSONParser;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.SavePref;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.foxy.arrive5.Home.RateTripActivity.setSystemBarTheme;

public class SelectIssueActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ProgressDialog progressDialog;
    List<ReasonList> issuesList;
    RadioButton rdbtn;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    double latitude, longitude;
    Marker marker, marker1;
    RadioGroup radioGroup;
    SavePref savePref;
    ReadPref readPref;
    LocationRequest mLocationRequest;
    String reason;
    GoogleApiClient mGoogleApiClient;
    String start, end;
    TextView txtTime, booking_car_type, booking_car_no, booking_amount, txtStart, txtArrival;
    private GoogleMap mMap;
    private SupportMapFragment map;
    private LatLng s_latLng, d_latLng;
    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_issue);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(SelectIssueActivity.this, true);
        radioGroup = findViewById(R.id.radioGroup);
        savePref = new SavePref(SelectIssueActivity.this);
        readPref = new ReadPref(SelectIssueActivity.this);
        txtTime = findViewById(R.id.txtTime);
        booking_car_type = findViewById(R.id.booking_car_type);
        booking_car_no = findViewById(R.id.booking_car_no);
        booking_amount = findViewById(R.id.booking_amount);
        txtStart = findViewById(R.id.txtStart);
        txtArrival = findViewById(R.id.txtArrival);
        String type = "user";
        getIssues(type);
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        String time = getIntent().getStringExtra("time");
        String carType = getIntent().getStringExtra("carType");
        String carNo = getIntent().getStringExtra("carNo");
        String amount = getIntent().getStringExtra("amount");
        txtTime.setText(time);
        booking_car_type.setText(carType);
        booking_car_no.setText(carNo);
        booking_amount.setText("$" + " " + amount);
        txtStart.setText(start);
        txtArrival.setText(end);
        map = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                reason = issuesList.get(checkedId).getId();
                savePref.saveReasonId(reason);
            }
        });
    }

    private void getIssues(String type) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ReasonListResponse> reasonListResponseCall = service.reasonsList(type);
        progressDialog = new ProgressDialog(SelectIssueActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        reasonListResponseCall.enqueue(new Callback<ReasonListResponse>() {
            @Override
            public void onResponse(Call<ReasonListResponse> call, Response<ReasonListResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        issuesList = response.body().getResult();
                        for (int i = 0; i < issuesList.size(); i++) {
                            rdbtn = new RadioButton(SelectIssueActivity.this);
                            rdbtn.setId(i);
                            rdbtn.setText(issuesList.get(i).getReason());
                            if (Build.VERSION.SDK_INT >= 21) {
                                ColorStateList colorStateList = new ColorStateList(
                                        new int[][]{
                                                new int[]{-android.R.attr.state_enabled}, //disabled
                                                new int[]{android.R.attr.state_enabled} //enabled
                                        },
                                        new int[]{
                                                Color.BLACK //disabled
                                                , Color.WHITE //enabled
                                        }
                                );
                                rdbtn.setButtonTintList(colorStateList);
                            }
                            rdbtn.setTextColor(ContextCompat.getColorStateList(SelectIssueActivity.this, R.color.whiteColor));
                            radioGroup.addView(rdbtn);
                        }
                    } else {
                        Toast.makeText(SelectIssueActivity.this, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ReasonListResponse> call, Throwable t) {
                Toast.makeText(SelectIssueActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void BackClick(View view) {
        finish();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(SelectIssueActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(latitude, longitude));
        LatLng latLng = new LatLng(latitude, longitude);
        final String lat = String.valueOf(latitude);
        final String longi = String.valueOf(longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(SelectIssueActivity.this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(SelectIssueActivity.this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(SelectIssueActivity.this,
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
        s_latLng = getLocationFromAddress(SelectIssueActivity.this, start);
        if (s_latLng != null) {
            marker = mMap.addMarker(new MarkerOptions().position(s_latLng));
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
            builder.include(marker.getPosition());
        }
        d_latLng = getLocationFromAddress(SelectIssueActivity.this, end);
        if (d_latLng != null) {
            marker1 = mMap.addMarker(new MarkerOptions().position(d_latLng));
            marker1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car));
            builder.include(marker1.getPosition());
        }
        builder.include(marker1.getPosition());
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        if (s_latLng != null && d_latLng != null) {
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            mMap.animateCamera(cu);
            String url = getDirectionsUrl(s_latLng, d_latLng);
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
        } else
            Toast.makeText(SelectIssueActivity.this, "" + getResources().getString(R.string.enter_address), Toast.LENGTH_SHORT).show();
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

    public void SubmitClick(View view) {
        String userId = readPref.getUserId();
        String driverId = getIntent().getStringExtra("driverId");
        String bookingId = getIntent().getStringExtra("bookingId");
        String reasonId = reason;
        String image = "";
        String comment = "";
        String type = "user";
        submitReason(userId, bookingId, driverId, reasonId, image, comment, type);
    }

    private void submitReason(String userId, String bookingId, String driverId, String reasonId, String image, String comment, String type) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<SubmitReasonResponse> submitReasonResponseCall = service.submitReason(userId, bookingId, driverId, reasonId, image, comment, type);
        progressDialog = new ProgressDialog(SelectIssueActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        submitReasonResponseCall.enqueue(new Callback<SubmitReasonResponse>() {
            @Override
            public void onResponse(Call<SubmitReasonResponse> call, Response<SubmitReasonResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(SelectIssueActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SelectIssueActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SelectIssueActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SubmitReasonResponse> call, Throwable t) {
                Toast.makeText(SelectIssueActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(SelectIssueActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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
            ArrayList<LatLng> points = null;
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
                if (polyline != null) {
                    polyline.remove();
                }
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(Color.RED);
                mMap.addPolyline(lineOptions);
            }
        }
    }
}
