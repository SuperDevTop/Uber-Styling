package com.foxy.arrive5.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easywaylocation.Listener;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.AppsContants;
import com.foxy.arrive5.utils.Constants;
import com.foxy.arrive5.utils.DirectionsJSONParser;
import com.foxy.arrive5.utils.ReadPref;
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
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindingRideActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, Listener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    double latitude, longitude;
    ReadPref readPref;
    RelativeLayout layoutFind;
    FrameLayout layoutBooking;
    CircleImageView driver_img;
    TextView driver_name, booking_car_type, booking_car_no, booking_otp, txtRating;
    String driverLat, driverLong, source = "", destination = "", otp, rating, carType, carNo, driverImg, driverName, driverPhone, bookingId;
    private GoogleMap mMap;
    private SupportMapFragment map;
    private LatLng s_latLng, d_latLng;
    String duration = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_ride);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        readPref = new ReadPref(FindingRideActivity.this);
        layoutFind = findViewById(R.id.layoutFind);
        layoutBooking = findViewById(R.id.layoutBooking);
        driver_name = findViewById(R.id.driver_name);
        driver_img = findViewById(R.id.driver_img);
        booking_car_type = findViewById(R.id.booking_car_type);
        booking_car_no = findViewById(R.id.booking_car_no);
        booking_otp = findViewById(R.id.booking_otp);
        txtRating = findViewById(R.id.txtRating);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        map = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);
        if (getIntent().hasExtra("pushtag") || getIntent().hasExtra("openActivity")) {
            String tag = getIntent().getStringExtra("pushtag");
            if (tag != null) {
                if (tag.equalsIgnoreCase("accept") || tag.equalsIgnoreCase("arrived")) {
                    OpenBottomActivity();
                }
            }
        }
    }

    private void OpenBottomActivity() {
        layoutFind.setVisibility(View.GONE);
        layoutBooking.setVisibility(View.VISIBLE);
        driverImg = getIntent().getStringExtra("driverImage");
        driverName = getIntent().getStringExtra("driverName");
        Glide.with(FindingRideActivity.this).load(driverImg).into(driver_img);
        driver_name.setText(driverName);
        driverLat = getIntent().getStringExtra("driverLat");
        driverLong = getIntent().getStringExtra("driverLong");
        source = getIntent().getStringExtra("startPoint");
        destination = getIntent().getStringExtra("endPoint");
        duration = getIntent().getStringExtra("duration");
        otp = getIntent().getStringExtra("otp");
        rating = getIntent().getStringExtra("rating");
        carType = getIntent().getStringExtra("carType");
        carNo = getIntent().getStringExtra("carNo");
        driverPhone = getIntent().getStringExtra("driverPhone");
        bookingId = getIntent().getStringExtra("bookingId");
        booking_car_type.setText(carType);
        booking_car_no.setText(carNo);
        booking_otp.setText(otp);
        txtRating.setText(rating);
        layoutBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindingRideActivity.this, RideDetailActivity.class);
                intent.putExtra("driverName", driverName);
                intent.putExtra("driverImg", driverImg);
                intent.putExtra("rate", rating);
                intent.putExtra("OTP", otp);
                intent.putExtra("type", carType);
                intent.putExtra("no", carNo);
                intent.putExtra("start", source);
                intent.putExtra("end", destination);
                intent.putExtra("driverPhone", driverPhone);
                intent.putExtra("bookingId", bookingId);
                intent.putExtra("DriverLat", driverLat);
                intent.putExtra("DriverLong", driverLong);
                intent.putExtra("Duration", duration);
                startActivity(intent);
            }
        });
    }

    @Override
    public void locationOn() {

    }

    @Override
    public void onPositionChanged() {

    }

    @Override
    public void locationCancelled() {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1000);
//        mLocationRequest.setFastestInterval(1000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        if (ContextCompat.checkSelfPermission(FindingRideActivity.this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        }
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(FindingRideActivity.this,
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
        if (getIntent().hasExtra("pushtag") || getIntent().hasExtra("openActivity")) {
            String tag = getIntent().getStringExtra("pushtag");
            if (tag != null) {
                if (tag.equalsIgnoreCase("accept") || tag.equalsIgnoreCase("arrived")) {
                    getCompleteAddressString(latitude, longitude);
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    int padding = (int) (width * 0.25); // offset from edges of the map 10% of screen
                    s_latLng = getLocationFromAddress(FindingRideActivity.this, source);
                    if (s_latLng != null) {
                        /*Marker marker = googleMap.addMarker(new MarkerOptions().position(s_latLng));
                        builder.include(marker.getPosition());*/

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
                    }
                    d_latLng = getLocationFromAddress(FindingRideActivity.this, destination);
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
                        Toast.makeText(FindingRideActivity.this, "" + getResources().getString(R.string.enter_address), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                source = strReturnedAddress.toString();
                Log.w("loction address", strReturnedAddress.toString());
            } else {
                Log.w("My  address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("address", "Canont get Address!");
        }
        return source;
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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(FindingRideActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(FindingRideActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(FindingRideActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(FindingRideActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(FindingRideActivity.this,
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
                    if (ContextCompat.checkSelfPermission(FindingRideActivity.this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(FindingRideActivity.this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void FinishClick(View view) {

       // Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
        editor.putString(AppsContants.BackBtnStatus, "1");
        editor.commit();

        Intent intent = new Intent(FindingRideActivity.this, MainActivity.class);
        startActivity(intent);
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
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLACK);
                mMap.addPolyline(lineOptions);
            }
        }
    }
}
