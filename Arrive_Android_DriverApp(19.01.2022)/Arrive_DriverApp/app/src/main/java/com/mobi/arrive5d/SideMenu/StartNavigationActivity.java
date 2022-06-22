package com.mobi.arrive5d.SideMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.utils.DirectionsJSONParser;
import com.mobi.arrive5d.utils.ReadPref;

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

public class StartNavigationActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    ImageView arrive;
    Marker mCurrLocationMarker;
    ReadPref readPref;
    String strAdd = "";
    TextView txtAddress, txtPickup;
    LocationRequest mLocationRequest;
    double latitude, longitude;
    LatLng p1 = null;
    private GoogleMap mMap;
    private LatLng s_latLng, d_latLng;
    private String isDrop = "0";

    public static final void setSystemBarTheme(final Activity pActivity, final boolean pIsDark) {
        final int lFlags = pActivity.getWindow().getDecorView().getSystemUiVisibility();
        pActivity.getWindow().getDecorView().setSystemUiVisibility(pIsDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_navigation);
        readPref = new ReadPref(StartNavigationActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(StartNavigationActivity.this, true);
        arrive = findViewById(R.id.arrive);
        txtAddress = findViewById(R.id.txtAddress);
        txtPickup = findViewById(R.id.txtPickup);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);
        if (getIntent().getStringExtra("isDrop") != null) {
            isDrop = getIntent().getStringExtra("isDrop");
        }

        else {
            isDrop = "0";
        }
        if (isDrop.equalsIgnoreCase("1")) {
            arrive.setImageResource(R.drawable.confirm_drop_button);
            txtPickup.setText(getResources().getString(R.string.drop_off_loc));
            txtAddress.setText(readPref.getEndPoint());

        } else {
            arrive.setImageResource(R.drawable.arrive_for_client_button);
            txtPickup.setText(getResources().getString(R.string.pickup_loc));
            txtAddress.setText(readPref.getStartPoint());
        }
    }

    public void ArriveClick(View view) {
        Intent intent = new Intent(StartNavigationActivity.this, ConfirmArrivalActivity.class);
        if (arrive.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.confirm_drop_button).getConstantState()) {
            intent.putExtra("isDrop", "1");
        } else if (arrive.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.arrive_for_client_button).getConstantState()) {
            intent.putExtra("isDrop", "0");
        }

        startActivity(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(StartNavigationActivity.this,
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
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
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
                strAdd = strReturnedAddress.toString();
                Log.w("Current address", strReturnedAddress.toString());
            } else {
                Log.w("Current address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current address", "Canont get Address!");
        }
        return strAdd;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(StartNavigationActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(StartNavigationActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(StartNavigationActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(StartNavigationActivity.this,
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
                    if (ContextCompat.checkSelfPermission(StartNavigationActivity.this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(StartNavigationActivity.this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                this, R.raw.maps_style);
        googleMap.setMapStyle(style);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(StartNavigationActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
            }
        } else {
            buildGoogleApiClient();
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.25); // offset from edges of the map 10% of screen
        getCompleteAddressString(Double.parseDouble(readPref.getLatitude()), Double.parseDouble(readPref.getLongitude()));
        s_latLng = getLocationFromAddress(StartNavigationActivity.this, strAdd);
        if (s_latLng != null) {
          /* Marker marker = googleMap.addMarker(new MarkerOptions().position(s_latLng));
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car));
            builder.include(marker.getPosition());*/

          int heights = 110;
            int widths = 80;
            BitmapDrawable bitmapdraws = (BitmapDrawable) getResources().getDrawable(R.drawable.car_marker);
            Bitmap bs = bitmapdraws.getBitmap();

            Bitmap smallMarkers = Bitmap.createScaledBitmap(bs, widths, heights, false);
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(s_latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarkers))
            );
            builder.include(marker.getPosition());
        }
        if (isDrop.equalsIgnoreCase("1")) {
            d_latLng = getLocationFromAddress(StartNavigationActivity.this, readPref.getEndPoint());
        } else if (isDrop.equalsIgnoreCase("0")) {
            d_latLng = getLocationFromAddress(StartNavigationActivity.this, readPref.getStartPoint());
        }
        if (d_latLng != null) {
         /* Marker marker = googleMap.addMarker(new MarkerOptions().position(d_latLng));
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.loc));
            builder.include(marker.getPosition());*/


            int heights = 95;
            int widths = 70;
            BitmapDrawable bitmapdraws = (BitmapDrawable) getResources().getDrawable(R.drawable.current_user_marker);
            Bitmap bs = bitmapdraws.getBitmap();

            Bitmap smallMarkers = Bitmap.createScaledBitmap(bs, widths, heights, false);
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(d_latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarkers))
            );
            builder.include(marker.getPosition());
        }
        if (s_latLng != null && d_latLng != null) {
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            googleMap.animateCamera(cu);
            String url = getDirectionsUrl(s_latLng, d_latLng);
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
        } else
            Toast.makeText(StartNavigationActivity.this, "Enter Source and Destination Address", Toast.LENGTH_SHORT).show();

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
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

    public void NavigateClick(View view) {
        Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
                .parse("http://maps.google.com/maps?saddr="
                        + readPref.getLatitude() + ","
                        + readPref.getLongitude() + "&daddr="
                        + p1.latitude + "," + p1.longitude + "&mode=driving"));
        try {
            startActivity(navigation);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="
                        + readPref.getLatitude() + ","
                        + readPref.getLongitude() + "&daddr="
                        + p1.latitude + "," + p1.longitude + "&mode=driving"));
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(StartNavigationActivity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(StartNavigationActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void RideInfoClick(View view) {
        Intent intent = new Intent(StartNavigationActivity.this, RideOverViewActivity.class);
        intent.putExtra("user_mobile", getIntent().getStringExtra("user_mobile"));
        startActivity(intent);
    }

    public void BackClick(View view) {
        finish();
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
