package com.mobi.arrive5d.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.maps.model.PolygonOptions;
import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.CurrentBookingResponse;
import com.mobi.arrive5d.Response.HighZoneResponse;
import com.mobi.arrive5d.Response.Highpaying;
import com.mobi.arrive5d.Response.UpdateLatLongResponse;
import com.mobi.arrive5d.Response.UpdateStatusResponse;
import com.mobi.arrive5d.SideMenu.BilllingDetailsActivity;
import com.mobi.arrive5d.SideMenu.BookingDialogActivity;
import com.mobi.arrive5d.SideMenu.StartNavigationActivity;
import com.mobi.arrive5d.utils.ReadPref;
import com.mobi.arrive5d.utils.SavePref;
import com.mobi.arrive5d.utils.Validations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    double latitude, longitude;
    ReadPref readPref;
    SavePref savePref;
    String driverId, lat, lng;
    List<Highpaying> highpaying;
    Button btnOnline;
    ProgressDialog progressDialog;
    private GoogleMap mMap;
    private SupportMapFragment map;
    private ArrayList<LatLng> arrayPoints = new ArrayList<>();
    private boolean isActivityVisible = false;
    private List<CurrentBookingResponse.BookingBean> booking;
    private String mode;

        Handler handler;
        Runnable runnable;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public static final void setSystemBarTheme(final Activity pActivity, final boolean pIsDark) {
        final int lFlags = pActivity.getWindow().getDecorView().getSystemUiVisibility();
        pActivity.getWindow().getDecorView().setSystemUiVisibility(pIsDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupUI(view.findViewById(R.id.layout));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        btnOnline = view.findViewById(R.id.btnOnline);
        readPref = new ReadPref(getContext());
        savePref = new SavePref(getContext());
        driverId = readPref.getDriverId();
        lat = readPref.getLatitude();
        lng = readPref.getLongitude();
        update(driverId, lat, lng);
     //   getCurrentBooking(driverId);

        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                Log.e("payment", "cjecl");
                getCurrentBooking(driverId);
                handler.postDelayed(runnable, 2000);
            }
        }, 2000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(10000);  //1000ms = 1 sec
                        if (getActivity() == null)
                            return;

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isActivityVisible) {
                                    update(driverId, lat, lng);
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
        setSystemBarTheme(getActivity(), true);
        map = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);
        if (readPref.getOnlinestatus().equalsIgnoreCase("1")) {
            btnOnline.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button));
            btnOnline.setText("On");
        } else if (readPref.getOnlinestatus().equalsIgnoreCase("0") || readPref.getOnlinestatus().equalsIgnoreCase("")) {
            btnOnline.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button_red));
            btnOnline.setText("Off");
        }
        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readPref.getOnlinestatus().equalsIgnoreCase("1")) {
                    setOnline(driverId, "0");
                } else if (readPref.getOnlinestatus().equalsIgnoreCase("0") || readPref.getOnlinestatus().equalsIgnoreCase("")) {
                    setOnline(driverId, "1");
                }
            }
        });
        return view;
    }

    private void setOnline(String driverId, String s) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<UpdateStatusResponse> updateStatusResponseCall = service.updateStatus(s, driverId);
        progressDialog = new ProgressDialog(getActivity());
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
                        try {
                            if (isOnline.equalsIgnoreCase("1")) {
                                btnOnline.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button));
                                btnOnline.setText("On");
                            } else if (isOnline.equalsIgnoreCase("0")) {
                                btnOnline.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button_red));
                                btnOnline.setText("Off");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UpdateStatusResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void getCurrentBooking(String driverId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<CurrentBookingResponse> currentBookingResponseCall = service.getCurrentBooking(driverId);
        currentBookingResponseCall.enqueue(new Callback<CurrentBookingResponse>() {
            @Override
            public void onResponse(Call<CurrentBookingResponse> call, Response<CurrentBookingResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        booking = response.body().getBooking();
                        if (booking.size() > 0) {
                            mode = response.body().getBooking().get(0).getMode();
                            if (mode.equalsIgnoreCase("0")) {
                                savePref.saveUserId(response.body().getBooking().get(0).getUser_id());
                                savePref.saveName(response.body().getBooking().get(0).getUserName());
                                savePref.saveImage(response.body().getBooking().get(0).getUserImg());
                                savePref.saveStartPoint(response.body().getBooking().get(0).getStart_point());
                                savePref.saveEndPoint(response.body().getBooking().get(0).getEnd_point());
                                savePref.saveUserRating(response.body().getBooking().get(0).getUser_rating());
                                savePref.saveBookingId(response.body().getBooking().get(0).getId());
                                savePref.saveVehicleSubType(response.body().getBooking().get(0).getVehicleSubTypeName());
                                savePref.saveDuration(response.body().getBooking().get(0).getDuration());
                                handler.removeCallbacks(runnable);
                                Intent intent = new Intent(getContext(), BookingDialogActivity.class);
                                intent.putExtra("pushtag", response.body().getBooking().get(0).getPush_tag());
                                intent.putExtra("openActivity", true);
                                startActivity(intent);
                            } else if (mode.equalsIgnoreCase("1")) {
                                savePref.saveUserId(response.body().getBooking().get(0).getUser_id());
                                savePref.saveName(response.body().getBooking().get(0).getUserName());
                                savePref.saveImage(response.body().getBooking().get(0).getUserImg());
                                savePref.saveStartPoint(response.body().getBooking().get(0).getStart_point());
                                savePref.saveEndPoint(response.body().getBooking().get(0).getEnd_point());
                                savePref.saveUserRating(response.body().getBooking().get(0).getUser_rating());
                                savePref.saveBookingId(response.body().getBooking().get(0).getId());
                                savePref.saveVehicleSubType(response.body().getBooking().get(0).getVehicleSubTypeName());
                                savePref.saveDuration(response.body().getBooking().get(0).getDuration());
                                handler.removeCallbacks(runnable);
                                Intent intent = new Intent(getContext(), StartNavigationActivity.class);
                                startActivity(intent);
                            }

                            else if (mode.equalsIgnoreCase("10")) {
                                savePref.saveUserId(response.body().getBooking().get(0).getUser_id());
                                savePref.saveName(response.body().getBooking().get(0).getUserName());
                                savePref.saveImage(response.body().getBooking().get(0).getUserImg());
                                savePref.saveStartPoint(response.body().getBooking().get(0).getStart_point());
                                savePref.saveEndPoint(response.body().getBooking().get(0).getEnd_point());
                                savePref.saveUserRating(response.body().getBooking().get(0).getUser_rating());
                                savePref.saveBookingId(response.body().getBooking().get(0).getId());
                                savePref.saveVehicleSubType(response.body().getBooking().get(0).getVehicleSubTypeName());
                                savePref.saveDuration(response.body().getBooking().get(0).getDuration());
                                handler.removeCallbacks(runnable);
                                Intent intent = new Intent(getContext(), BilllingDetailsActivity.class);
                                startActivity(intent);
                            } else if (mode.equalsIgnoreCase("11")) {
                                savePref.saveUserId(response.body().getBooking().get(0).getUser_id());
                                savePref.saveName(response.body().getBooking().get(0).getUserName());
                                savePref.saveImage(response.body().getBooking().get(0).getUserImg());
                                savePref.saveStartPoint(response.body().getBooking().get(0).getStart_point());
                                savePref.saveEndPoint(response.body().getBooking().get(0).getEnd_point());
                                savePref.saveUserRating(response.body().getBooking().get(0).getUser_rating());
                                savePref.saveBookingId(response.body().getBooking().get(0).getId());
                                savePref.saveVehicleSubType(response.body().getBooking().get(0).getVehicleSubTypeName());
                                savePref.saveDuration(response.body().getBooking().get(0).getDuration());
                                handler.removeCallbacks(runnable);
                                Intent intent = new Intent(getContext(), BilllingDetailsActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                    else {

                        //Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CurrentBookingResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPayingZone() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<HighZoneResponse> highZoneResponseCall = service.getHighZones();
        highZoneResponseCall.enqueue(new Callback<HighZoneResponse>() {
            @Override
            public void onResponse(Call<HighZoneResponse> call, Response<HighZoneResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        highpaying = response.body().getHighpaying();
                        List<LatLng> positions = new ArrayList<>();
                        for (int i = 0; i < highpaying.size(); i++) {
                            positions.clear();
                            positions.add(new LatLng(Double.parseDouble(highpaying.get(i).getLatitude1()), Double.parseDouble(highpaying.get(i).getLongitude1())));
                            positions.add(new LatLng(Double.parseDouble(highpaying.get(i).getLatitude2()), Double.parseDouble(highpaying.get(i).getLongitude2())));
                            positions.add(new LatLng(Double.parseDouble(highpaying.get(i).getLatitude3()), Double.parseDouble(highpaying.get(i).getLongitude3())));
                            positions.add(new LatLng(Double.parseDouble(highpaying.get(i).getLatitude4()), Double.parseDouble(highpaying.get(i).getLongitude4())));
                            for (LatLng point : positions) {
                                arrayPoints.add(point);
                            }
                            countPolygonPoints();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<HighZoneResponse> call, Throwable t) {

            }
        });
    }
    public void countPolygonPoints() {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.geodesic(true);
        polygonOptions.addAll(arrayPoints);
        polygonOptions.strokeColor(getResources().getColor(R.color.light_pink));
        polygonOptions.strokeWidth(4);
        polygonOptions.fillColor(getResources().getColor(R.color.dark_pink));
        mMap.addPolygon(polygonOptions);
        addText(getContext(), mMap, highpaying.get(0).getHighBy(), 0, 15);
        arrayPoints.clear();
    }
    public Marker addText(final Context context, final GoogleMap map, final String text, final int padding,
                          final int fontSize) {
        Marker marker = null;
        if (context == null || map == null || text == null
                || fontSize <= 0) {
            return marker;
        }

        final TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(fontSize);
        final Paint paintText = textView.getPaint();
        final Rect boundsText = new Rect();
        paintText.getTextBounds(text, 0, textView.length(), boundsText);
        paintText.setTextAlign(Paint.Align.CENTER);
        final Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        final Bitmap bmpText = Bitmap.createBitmap(boundsText.width() + 2
                * padding, boundsText.height() + 2 * padding, conf);
        final Canvas canvasText = new Canvas(bmpText);
        paintText.setColor(Color.BLACK);
        canvasText.drawText(text, canvasText.getWidth() / 2,
                canvasText.getHeight() - padding - boundsText.bottom, paintText);
        final MarkerOptions markerOptions = new MarkerOptions()
                .position(getPolygonCenterPoint(arrayPoints))
                .icon(BitmapDescriptorFactory.fromBitmap(bmpText))
                .anchor(0.5f, 1);
        marker = map.addMarker(markerOptions);
        return marker;
    }

    private LatLng getPolygonCenterPoint(ArrayList<LatLng> polygonPointsList) {
        LatLng centerLatLng = null;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < polygonPointsList.size(); i++) {
            builder.include(polygonPointsList.get(i));
        }
        LatLngBounds bounds = builder.build();
        centerLatLng = bounds.getCenter();
        return centerLatLng;
    }

    private void update(String driverId, String lat, String lng) {
        final ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<UpdateLatLongResponse> updateLatLongResponseCall = service.updateLatLng(driverId, lat, lng);
        updateLatLongResponseCall.enqueue(new Callback<UpdateLatLongResponse>() {
            @Override
            public void onResponse(Call<UpdateLatLongResponse> call, Response<UpdateLatLongResponse> response) {
                if (response.isSuccessful()) {
                    //Log.e("response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        String avgRating = String.valueOf(response.body().getAvgRating());
                        savePref.saveDriverRating(avgRating);
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateLatLongResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        isActivityVisible = true;
        getPayingZone();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        isActivityVisible = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Thread.interrupted();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    Validations.hideSoftKeyboard(getActivity());
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
      MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                getActivity(), R.raw.maps_style);
        googleMap.setMapStyle(style);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
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
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        savePref.saveLatitue(String.valueOf(latitude));
        savePref.saveLonitutte(String.valueOf(longitude));
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
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
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        MenuItem item = menu.findItem(R.id.toggleservice);
        SwitchCompat switchCompat = (SwitchCompat) MenuItemCompat.getActionView(item);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
