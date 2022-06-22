package com.foxy.arrive5.Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foxy.arrive5.IntroScreens.GPSTracker;
import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.AppUtils;
import com.foxy.arrive5.utils.AutoCompleteParser1;
import com.foxy.arrive5.utils.DirectionsJSONParser;
import com.foxy.arrive5.utils.FetchAddressIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

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

public class RequestRideActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    protected String mAddressOutput;
    protected String mAreaOutput;
    protected String mCityOutput;
    protected String mStateOutput;
    ArrayAdapter adapter;
    private AutoCompleteTextView source, destination;
    private LinearLayout destination_address, source_address;
    private TextView source_tv, dest_tv;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private FrameLayout frameLayout;
    private Boolean frame_open = false;
    private Fragment home;
    private android.support.v4.app.FragmentTransaction ft;
    private GoogleMap googleMap;
    private Handler handler;
    private ArrayList arrayListResult;
    private LatLng mCenterLatLong;
    private AddressResultReceiver mResultReceiver;
    public static double latitude = 0;
    public static double longitude = 0;
    String dest, s;
    GPSTracker gpsTrack;
    private LatLng s_latLng, d_latLng;
    private ArrayList<LatLng> points = null;
    private Polyline polyline;

    public static final void setSystemBarTheme(final Activity pActivity, final boolean pIsDark) {
        final int lFlags = pActivity.getWindow().getDecorView().getSystemUiVisibility();
        pActivity.getWindow().getDecorView().setSystemUiVisibility(pIsDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ride);
        destination = findViewById(R.id.ed_destination_home);
        destination_address = findViewById(R.id.destination_address_home);
        source_tv = findViewById(R.id.source_address_home);
        dest_tv = findViewById(R.id.dest_add_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        dest = getIntent().getStringExtra("text");
        destination.setText(dest);
        dest_tv.setText(destination.getText().toString());
        gpsTrack = new GPSTracker(RequestRideActivity.this);
        if (gpsTrack.canGetLocation()) {
            latitude = gpsTrack.getLatitude();
            longitude = gpsTrack.getLongitude();
            s = getCompleteAddressString(latitude, longitude);
            source_tv.setText(s.trim());
        } else {
            gpsTrack.showSettingsAlert();
            Log.e("ShowAlert", "ShowAlert");
        }
        setSystemBarTheme(RequestRideActivity.this, true);
        frameLayout = findViewById(R.id.home_frame);
        source_address = findViewById(R.id.source_ed);
        source = findViewById(R.id.ed_pickup);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mResultReceiver = new AddressResultReceiver(new Handler());
        buildGoogleApiClient();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        source_tv.setOnClickListener(this);
        source_address.setOnClickListener(this);
        destination_address.setOnClickListener(this);
        destination.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    destination_address.setVisibility(View.VISIBLE);
                    destination.setVisibility(View.GONE);
                    dest_tv.setText(destination.getText().toString());
                }
            }
        });
        source.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    source.setVisibility(View.GONE);
                    source_address.setVisibility(View.VISIBLE);
                    source_tv.setText(source.getText().toString());
                }
            }
        });

        destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] searchText = destination.getText().toString().split(",");
                String searchAddd = searchText[0];
                searchAddd = searchAddd.trim();
                searchAddd = searchAddd.replaceAll(" ", "%20");
                String url1 = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
                        + searchAddd
                        + "&location=28.5839319,77.32310940000002&radius=50000&strictbounds&sensor=true&key="
                        + "AIzaSyClkiUQxIeczQEjTrw-dy9h2fj9qjXDk_4";
                if (searchText.length <= 1) {
                    arrayListResult = new ArrayList();
                    handler = new Handler();
                    AutoCompleteParser1 parse = new AutoCompleteParser1(RequestRideActivity.this, destination_address, dest_tv, url1, arrayListResult, adapter, destination, handler);
                    parse.execute();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        source.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] searchText = source.getText().toString().split(",");
                String searchAddd = searchText[0];
                searchAddd = searchAddd.trim();
                searchAddd = searchAddd.replaceAll(" ", "%20");
                String url1 = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
                        + searchAddd
                        + "&location=28.5839319,77.32310940000002&radius=50000&strictbounds&sensor=true&key="
                        + "AIzaSyClkiUQxIeczQEjTrw-dy9h2fj9qjXDk_4";
                if (searchText.length <= 1) {
                    arrayListResult = new ArrayList();
                    handler = new Handler();
                    AutoCompleteParser1 parse = new AutoCompleteParser1(RequestRideActivity.this, source_address, source_tv, url1, arrayListResult, adapter, source, handler);
                    parse.execute();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.source_ed:
                source.setVisibility(View.VISIBLE);
                source_address.setVisibility(View.GONE);
                source_tv.setText("");
                break;

            case R.id.destination_address_home:
                destination_address.setVisibility(View.GONE);
                destination.setVisibility(View.VISIBLE);
                dest_tv.setText("");
                break;

        }
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                this, R.raw.maps_style);
        googleMap.setMapStyle(style);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            googleMap.setMyLocationEnabled(true);
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.25); // offset from edges of the map 10% of screen
        s_latLng = getLocationFromAddress(RequestRideActivity.this, s);
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






          /*  Marker marker = googleMap.addMarker(new MarkerOptions().position(s_latLng));
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
            builder.include(marker.getPosition());*/
        }
        d_latLng = getLocationFromAddress(RequestRideActivity.this, dest);
        if (d_latLng != null) {


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


          /*  Marker marker = googleMap.addMarker(new MarkerOptions().position(d_latLng));
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car_marker));
            builder.include(marker.getPosition());*/
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
            Toast.makeText(RequestRideActivity.this, "" + getResources().getString(R.string.enter_address), Toast.LENGTH_SHORT).show();
//        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
//            @Override
//            public void onCameraChange(CameraPosition cameraPosition) {
//                Log.d("Camera postion change" + "", cameraPosition + "");
//                mCenterLatLong = cameraPosition.target;
//                googleMap.clear();
//                try {
//                    Location mLocation = new Location("");
//                    mLocation.setLatitude(mCenterLatLong.latitude);
//                    mLocation.setLongitude(mCenterLatLong.longitude);
//                    startIntentService(mLocation);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }

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
        Log.e("fgkjdyhtiy7reugre",parameters);
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyClkiUQxIeczQEjTrw-dy9h2fj9qjXDk_4";
        return url;
    }

    private void setLocation() {
        final double[] lat = new double[1];
        final double[] lon = new double[1];
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lat[0] = location.getLatitude();
                            lon[0] = location.getLongitude();
                            LatLng latLng = new LatLng(lat[0], lon[0]);
                            //Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng));
                            //marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin_icon));
                            CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(latLng, 14.0f);
                            googleMap.animateCamera(cu);
                            googleMap.getUiSettings().setScrollGesturesEnabled(true);
                            s = getCompleteAddressString(lat[0], lon[0]);
                            s = s.trim();
                            source_tv.setText(s);
                        }
                    }
                });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //setLocation();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                mGoogleApiClient);
//        if (mLastLocation != null) {
//            changeMap(mLastLocation);
//
//        } else
//            try {
//                LocationServices.FusedLocationApi.removeLocationUpdates(
//                        mGoogleApiClient, this);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        try {
//            LocationRequest mLocationRequest = new LocationRequest();
//            mLocationRequest.setInterval(10000);
//            mLocationRequest.setFastestInterval(5000);
//            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            LocationServices.FusedLocationApi.requestLocationUpdates(
//                    mGoogleApiClient, mLocationRequest, this);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    protected void startIntentService(Location mLocation) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(AppUtils.LocationConstants.RECEIVER, mResultReceiver);
        intent.putExtra(AppUtils.LocationConstants.LOCATION_DATA_EXTRA, mLocation);
        startService(intent);
    }

//    @Override
//    public void onLocationChanged(Location location) {
//        try {
//            if (location != null)
//                changeMap(location);
//            LocationServices.FusedLocationApi.removeLocationUpdates(
//                    mGoogleApiClient, this);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void changeMap(Location location) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (googleMap != null) {
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            LatLng latLong;
            latLong = new LatLng(location.getLatitude(), location.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong).zoom(19f).tilt(70).build();
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            startIntentService(location);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            mGoogleApiClient.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void displayAddressOutput() {
        try {
            if (mAreaOutput != null)
                source_tv.setText(mStateOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }
                } else {
                    finish();
                    System.exit(0);
                }
                return;
            }

        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.e("My Current address", strReturnedAddress.toString());
            } else {
                Log.e("My Current address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("My Current address", "Canont get Address!");
        }
        return strAdd;
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
                Log.e("sfgsdhfsdfsq",result.toString());
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
                lineOptions.width(10);
                lineOptions.color(Color.BLACK);
                googleMap.addPolyline(lineOptions);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        if (frame_open) {
            getSupportFragmentManager().beginTransaction().remove(home).commit();
            frame_open = false;
        } else {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    public void BackClick(View view) {
        finish();
    }

    public void ConfirmClick(View view) {
        Intent intent = new Intent(RequestRideActivity.this, SelectPoolActivity.class);
        intent.putExtra("source", source_tv.getText().toString());
        intent.putExtra("dest", dest_tv.getText().toString());
        startActivity(intent);
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            mAddressOutput = resultData.getString(AppUtils.LocationConstants.RESULT_DATA_KEY);
            mAreaOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_AREA);
            mCityOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_CITY);
            mStateOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_STREET);
            displayAddressOutput();
            if (resultCode == AppUtils.LocationConstants.SUCCESS_RESULT) {
            }
        }
    }
}
