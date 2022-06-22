package com.foxy.arrive5.SideMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Home.ConfirmScheduleRidePopup;
import com.foxy.arrive5.Home.FindingRideActivity;
import com.foxy.arrive5.Home.PaymentActivity;
import com.foxy.arrive5.Home.RequestRideActivity;
import com.foxy.arrive5.Home.ScheduleRideActivity;
import com.foxy.arrive5.IntroScreens.GPSTracker;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.CurrentBookingResponse;
import com.foxy.arrive5.Response.LocationUpdateResponse;
import com.foxy.arrive5.Response.SearchDetail;
import com.foxy.arrive5.Response.SearchDriverResponse;
import com.foxy.arrive5.utils.AppsContants;
import com.foxy.arrive5.utils.AutoCompleteParser;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.SavePref;
import com.foxy.arrive5.utils.Validations;
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
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.InfoWindowAdapter {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    AutoCompleteTextView autoComp;
    double latitude, longitude;
    ArrayAdapter adapter;
    ReadPref readPref;
    SavePref savePref;
    List<SearchDetail> searchDetailList;
    ImageView imgSchedule;
    String newLat, newLong;
    Marker marker, marker1;
    String mode;
    private GoogleMap mMap;
    private SupportMapFragment map;
    private Handler handler;
    private boolean isActivityVisible = false;
    private ArrayList arrayListResult;
    private List<CurrentBookingResponse.BookingBean> currentBookingList;
    double lat, lng;
    String id;

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

    public static Bitmap createCustomMarker(Context context, String _name) {
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        TextView txt_name = (TextView) marker.findViewById(R.id.name);
        txt_name.setText(_name);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);
        return bitmap;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle(getResources().getString(R.string.home));
        setupUI(view.findViewById(R.id.layout));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 30);
        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        System.out.println(df.format(now.getTime()));
        readPref = new ReadPref(getContext());
        savePref = new SavePref(getContext());
        imgSchedule = view.findViewById(R.id.imgSchedule);
        imgSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScheduleRideActivity.class);
                startActivity(intent);
            }
        });
        newLat = readPref.getLatitude();
        newLong = readPref.getLongitude();
        id = readPref.getUserId();
        String type = "user";
        updateLoc(newLat, newLong, type, id);

        getCurrentBooking(id);
        /*handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                Log.e("payment", "cjecl");

                handler.postDelayed(runnable, 2000);
            }
        }, 2000);
*/
        autoComp = view.findViewById(R.id.autoComp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(getActivity(), true);
        map = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);
        driverSearch(newLat, newLong);


        GPSTracker gpsTracker = new GPSTracker(getActivity());
        lat = gpsTracker.getLatitude();
        lng = gpsTracker.getLongitude();
        Log.e("hfjjgjff", lat + "");
        Log.e("hfjjgjff", lng + "");


        autoComp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] searchText = autoComp.getText().toString().split(",");
                String searchAddd = searchText[0];
                searchAddd = searchAddd.trim();
                searchAddd = searchAddd.replaceAll(" ", "%20");
                String url1 = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
                        + searchAddd
                        + "&location=" + lat + "," + lng + "&radius=80000&strictbounds&sensor=true" +
                        "&key=AIzaSyClkiUQxIeczQEjTrw-dy9h2fj9qjXDk_4";
                if (searchText.length <= 1) {
                    arrayListResult = new ArrayList();
                    handler = new Handler();
                    AutoCompleteParser parse = new AutoCompleteParser(getContext(), url1, arrayListResult, adapter, autoComp, handler);
                    parse.execute();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        autoComp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String text = autoComp.getText().toString();
                    Intent searchIntent = new Intent(getContext(), RequestRideActivity.class);
                    searchIntent.putExtra("text", text);
                    startActivity(searchIntent);
                    return true;
                }
                return false;
            }
        });
        return view;

    }

    private void getCurrentBooking(String id) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<CurrentBookingResponse> currentBookingResponseCall = service.getCurrentBooking(id);
        currentBookingResponseCall.enqueue(new Callback<CurrentBookingResponse>() {
            @Override
            public void onResponse(Call<CurrentBookingResponse> call, Response<CurrentBookingResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        currentBookingList = response.body().getBooking();
                        if (currentBookingList.size() > 0) {
                            mode = response.body().getBooking().get(0).getMode();

                            if (mode.equalsIgnoreCase("1")) {

                                if (response.body().getBooking().get(0).getPush_tag().equals("accept")) {

                                    if (response.body().getBooking().get(0).getBooking_type().equals("schedule later")) {

                                        Log.e("ifhjhjh", "if" + " " + response.body().getBooking().get(0).getPush_tag());
                                        //Intent intent = new Intent(getContext(), FindingRideActivity.class);
                                        Intent intent = new Intent(getContext(), ConfirmScheduleRidePopup.class);
                                        intent.putExtra("pushtag", response.body().getBooking().get(0).getPush_tag());
                                        intent.putExtra("openActivity", true);
                                        intent.putExtra("driverImage", response.body().getBooking().get(0).getDriverImg());
                                        intent.putExtra("driverName", response.body().getBooking().get(0).getDriverName());
                                        intent.putExtra("duration", response.body().getBooking().get(0).getDuration());
                                        intent.putExtra("driverPhone", response.body().getBooking().get(0).getMobile());
                                        intent.putExtra("bookingId", response.body().getBooking().get(0).getId());
                                        intent.putExtra("amount", response.body().getBooking().get(0).getAmount());
                                        intent.putExtra("driverId", response.body().getBooking().get(0).getDriver_id());
                                        intent.putExtra("startPoint", response.body().getBooking().get(0).getStart_point());
                                        intent.putExtra("endPoint", response.body().getBooking().get(0).getEnd_point());
                                        intent.putExtra("driverLat", response.body().getBooking().get(0).getDriver_latitude());
                                        intent.putExtra("driverLong", response.body().getBooking().get(0).getDriver_longitude());
                                        intent.putExtra("rating", response.body().getBooking().get(0).getDriver_rating());
                                        intent.putExtra("otp", response.body().getBooking().get(0).getOtp());
                                        intent.putExtra("carType", response.body().getBooking().get(0).getVehicleSubTypeName());
                                        intent.putExtra("carNo", response.body().getBooking().get(0).getCarNo());
                                        savePref.saveDriverName(response.body().getBooking().get(0).getDriverName());
                                        savePref.saveDriverMobile(response.body().getBooking().get(0).getMobile());
                                        savePref.saveBookingId(response.body().getBooking().get(0).getId());
                                        startActivity(intent);


                                    } else {
                                        AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                        String strBackStatus = AppsContants.sharedpreferences.getString(AppsContants.BackBtnStatus, "");
                                        Log.e("fghkfhfhfg", strBackStatus);

                                        if (!strBackStatus.equals("1")) {

                                            Log.e("ifhjhjh", "else" + " " + response.body().getBooking().get(0).getPush_tag());
                                            Intent intent = new Intent(getContext(), FindingRideActivity.class);
                                            intent.putExtra("pushtag", response.body().getBooking().get(0).getPush_tag());
                                            intent.putExtra("openActivity", true);
                                            intent.putExtra("driverImage", response.body().getBooking().get(0).getDriverImg());
                                            intent.putExtra("driverName", response.body().getBooking().get(0).getDriverName());
                                            intent.putExtra("duration", response.body().getBooking().get(0).getDuration());
                                            intent.putExtra("driverPhone", response.body().getBooking().get(0).getMobile());
                                            intent.putExtra("bookingId", response.body().getBooking().get(0).getId());
                                            intent.putExtra("amount", response.body().getBooking().get(0).getAmount());
                                            intent.putExtra("driverId", response.body().getBooking().get(0).getDriver_id());
                                            intent.putExtra("startPoint", response.body().getBooking().get(0).getStart_point());
                                            intent.putExtra("endPoint", response.body().getBooking().get(0).getEnd_point());
                                            intent.putExtra("driverLat", response.body().getBooking().get(0).getDriver_latitude());
                                            intent.putExtra("driverLong", response.body().getBooking().get(0).getDriver_longitude());
                                            intent.putExtra("rating", response.body().getBooking().get(0).getDriver_rating());
                                            intent.putExtra("otp", response.body().getBooking().get(0).getOtp());
                                            intent.putExtra("carType", response.body().getBooking().get(0).getVehicleSubTypeName());
                                            intent.putExtra("carNo", response.body().getBooking().get(0).getCarNo());
                                            savePref.saveDriverName(response.body().getBooking().get(0).getDriverName());
                                            savePref.saveDriverMobile(response.body().getBooking().get(0).getMobile());
                                            savePref.saveBookingId(response.body().getBooking().get(0).getId());
                                            startActivity(intent);
                                        }

                                    }
                                } else {
                                    Log.e("ifhjhjh", "if" + " " + response.body().getBooking().get(0).getPush_tag());
                                    Intent intent = new Intent(getContext(), FindingRideActivity.class);
                                    intent.putExtra("pushtag", response.body().getBooking().get(0).getPush_tag());
                                    intent.putExtra("openActivity", true);
                                    intent.putExtra("driverImage", response.body().getBooking().get(0).getDriverImg());
                                    intent.putExtra("driverName", response.body().getBooking().get(0).getDriverName());
                                    intent.putExtra("duration", response.body().getBooking().get(0).getDuration());
                                    intent.putExtra("driverPhone", response.body().getBooking().get(0).getMobile());
                                    intent.putExtra("bookingId", response.body().getBooking().get(0).getId());
                                    intent.putExtra("amount", response.body().getBooking().get(0).getAmount());
                                    intent.putExtra("driverId", response.body().getBooking().get(0).getDriver_id());
                                    intent.putExtra("startPoint", response.body().getBooking().get(0).getStart_point());
                                    intent.putExtra("endPoint", response.body().getBooking().get(0).getEnd_point());
                                    intent.putExtra("driverLat", response.body().getBooking().get(0).getDriver_latitude());
                                    intent.putExtra("driverLong", response.body().getBooking().get(0).getDriver_longitude());
                                    intent.putExtra("rating", response.body().getBooking().get(0).getDriver_rating());
                                    intent.putExtra("otp", response.body().getBooking().get(0).getOtp());
                                    intent.putExtra("carType", response.body().getBooking().get(0).getVehicleSubTypeName());
                                    intent.putExtra("carNo", response.body().getBooking().get(0).getCarNo());
                                    savePref.saveDriverName(response.body().getBooking().get(0).getDriverName());
                                    savePref.saveDriverMobile(response.body().getBooking().get(0).getMobile());
                                    savePref.saveBookingId(response.body().getBooking().get(0).getId());
                                    startActivity(intent);
                                }
                            } else if (mode.equalsIgnoreCase("7")) {

                                Log.e("ifhjhjh", "Else if 7" + " " + response.body());
                                Intent intent = new Intent(getContext(), FindingRideActivity.class);
                                intent.putExtra("pushtag", response.body().getBooking().get(0).getPush_tag());
                                intent.putExtra("openActivity", true);
                                intent.putExtra("driverImage", response.body().getBooking().get(0).getDriverImg());
                                intent.putExtra("driverName", response.body().getBooking().get(0).getDriverName());
                                intent.putExtra("duration", response.body().getBooking().get(0).getDuration());
                                intent.putExtra("driverPhone", response.body().getBooking().get(0).getMobile());
                                intent.putExtra("bookingId", response.body().getBooking().get(0).getId());
                                intent.putExtra("amount", response.body().getBooking().get(0).getAmount());
                                intent.putExtra("driverId", response.body().getBooking().get(0).getDriver_id());
                                intent.putExtra("startPoint", response.body().getBooking().get(0).getStart_point());
                                intent.putExtra("endPoint", response.body().getBooking().get(0).getEnd_point());
                                intent.putExtra("driverLat", response.body().getBooking().get(0).getDriver_latitude());
                                intent.putExtra("driverLong", response.body().getBooking().get(0).getDriver_longitude());
                                intent.putExtra("rating", response.body().getBooking().get(0).getDriver_rating());
                                intent.putExtra("otp", response.body().getBooking().get(0).getOtp());
                                intent.putExtra("carType", response.body().getBooking().get(0).getVehicleSubTypeName());
                                intent.putExtra("carNo", response.body().getBooking().get(0).getCarNo());
                                savePref.saveDriverName(response.body().getBooking().get(0).getDriverName());
                                savePref.saveDriverMobile(response.body().getBooking().get(0).getMobile());
                                savePref.saveBookingId(response.body().getBooking().get(0).getId());
                                startActivity(intent);
                            } else if (mode.equalsIgnoreCase("10")) {
                                Log.e("ifhjhjh", "Else if 10" + " " + response.body());
                                Intent intent = new Intent(getContext(), PaymentActivity.class);
                                intent.putExtra("pushtag", response.body().getBooking().get(0).getPush_tag());
                                intent.putExtra("openActivity", true);
                                intent.putExtra("driverImage", response.body().getBooking().get(0).getDriverImg());
                                intent.putExtra("driverName", response.body().getBooking().get(0).getDriverName());
                                intent.putExtra("duration", response.body().getBooking().get(0).getDuration());
                                intent.putExtra("driverPhone", response.body().getBooking().get(0).getMobile());
                                intent.putExtra("bookingId", response.body().getBooking().get(0).getId());
                                intent.putExtra("amount", response.body().getBooking().get(0).getAmount());
                                intent.putExtra("driverId", response.body().getBooking().get(0).getDriver_id());
                                intent.putExtra("startPoint", response.body().getBooking().get(0).getStart_point());
                                intent.putExtra("endPoint", response.body().getBooking().get(0).getEnd_point());
                                intent.putExtra("driverLat", response.body().getBooking().get(0).getDriver_latitude());
                                intent.putExtra("driverLong", response.body().getBooking().get(0).getDriver_longitude());
                                intent.putExtra("rating", response.body().getBooking().get(0).getDriver_rating());
                                intent.putExtra("otp", response.body().getBooking().get(0).getOtp());
                                intent.putExtra("carType", response.body().getBooking().get(0).getVehicleSubTypeName());
                                intent.putExtra("carNo", response.body().getBooking().get(0).getCarNo());
                                savePref.saveDriverName(response.body().getBooking().get(0).getDriverName());
                                savePref.saveDriverMobile(response.body().getBooking().get(0).getMobile());
                                savePref.saveBookingId(response.body().getBooking().get(0).getId());
                                startActivity(intent);
                            } else if (mode.equalsIgnoreCase("12")) {

                                Log.e("ifhjhjh", "Else if 12" + " " + response.body());
                                Intent intent = new Intent(getContext(), PaymentActivity.class);
                                intent.putExtra("pushtag", response.body().getBooking().get(0).getPush_tag());
                                intent.putExtra("openActivity", true);
                                intent.putExtra("driverImage", response.body().getBooking().get(0).getDriverImg());
                                intent.putExtra("driverName", response.body().getBooking().get(0).getDriverName());
                                intent.putExtra("duration", response.body().getBooking().get(0).getDuration());
                                intent.putExtra("driverPhone", response.body().getBooking().get(0).getMobile());
                                intent.putExtra("bookingId", response.body().getBooking().get(0).getId());
                                intent.putExtra("amount", response.body().getBooking().get(0).getAmount());
                                intent.putExtra("driverId", response.body().getBooking().get(0).getDriver_id());
                                intent.putExtra("startPoint", response.body().getBooking().get(0).getStart_point());
                                intent.putExtra("endPoint", response.body().getBooking().get(0).getEnd_point());
                                intent.putExtra("driverLat", response.body().getBooking().get(0).getDriver_latitude());
                                intent.putExtra("driverLong", response.body().getBooking().get(0).getDriver_longitude());
                                intent.putExtra("rating", response.body().getBooking().get(0).getDriver_rating());
                                intent.putExtra("otp", response.body().getBooking().get(0).getOtp());
                                intent.putExtra("carType", response.body().getBooking().get(0).getVehicleSubTypeName());
                                intent.putExtra("carNo", response.body().getBooking().get(0).getCarNo());
                                savePref.saveDriverName(response.body().getBooking().get(0).getDriverName());
                                savePref.saveDriverMobile(response.body().getBooking().get(0).getMobile());
                                savePref.saveBookingId(response.body().getBooking().get(0).getId());
                                startActivity(intent);
                            }
                        }
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<CurrentBookingResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLoc(String newLat, String newLong, String type, String id) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<LocationUpdateResponse> locationUpdateResponseCall = service.updateLoc(newLat, newLong, type, id);
        locationUpdateResponseCall.enqueue(new Callback<LocationUpdateResponse>() {
            @Override
            public void onResponse(Call<LocationUpdateResponse> call, Response<LocationUpdateResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    //Log.e("response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    if (status.equalsIgnoreCase("true")) {
                        String avgRating = String.valueOf(response.body().getAvg_rating());
                        savePref.saveUserRating(avgRating);
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LocationUpdateResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void driverSearch(String userLat, String userLong) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<SearchDriverResponse> searchDriverResponseCall = service.searchDriver(userLat, userLong);
        searchDriverResponseCall.enqueue(new Callback<SearchDriverResponse>() {
            @Override
            public void onResponse(Call<SearchDriverResponse> call, Response<SearchDriverResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        searchDetailList = response.body().getDetails();
                        if (mMap != null)
                            mMap.clear();
                        for (int i = 0; i < searchDetailList.size(); i++) {
                            double lati = Double.parseDouble(searchDetailList.get(i).getLatitude());
                            double longLat = Double.parseDouble(searchDetailList.get(i).getLongitude());

                            int heights = 112;
                            int widths = 80;
                            BitmapDrawable bitmapdraws = (BitmapDrawable) getResources().getDrawable(R.drawable.car_marker);
                            Bitmap bs = bitmapdraws.getBitmap();

                            Bitmap smallMarkers = Bitmap.createScaledBitmap(bs, widths, heights, false);
                            marker = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lati, longLat))
                                    .rotation((float) 180.0)
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarkers))
                            );

                        }
                        for (int i = 0; i < searchDetailList.size(); i++) {
                            double lati = Double.parseDouble(searchDetailList.get(i).getLatitude());
                            double longLat = Double.parseDouble(searchDetailList.get(i).getLongitude());
                            marker1 = mMap.addMarker(new MarkerOptions().position(new LatLng(lati, longLat)).
                                    icon(BitmapDescriptorFactory.fromBitmap(
                                            createCustomMarker(getContext(), searchDetailList.get(i).getTime()))));
                        }
                    } else {
                        Toast.makeText(getContext(), "" + getContext().getResources().getString(R.string.no_driver), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchDriverResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        mLocationRequest = new LocationRequest();
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
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(latitude, longitude));
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        autoComp.setText("");
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
    public void onResume() {
        super.onResume();
        isActivityVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isActivityVisible = false;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}

