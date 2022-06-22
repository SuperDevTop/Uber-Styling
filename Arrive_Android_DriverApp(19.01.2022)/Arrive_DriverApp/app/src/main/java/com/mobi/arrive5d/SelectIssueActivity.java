package com.mobi.arrive5d;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.Response.ReasonListResponse;
import com.mobi.arrive5d.Response.ReasonsList;
import com.mobi.arrive5d.Response.SubmitReasonResponse;
import com.mobi.arrive5d.utils.CommonUtils;
import com.mobi.arrive5d.utils.Constants;
import com.mobi.arrive5d.utils.DirectionsJSONParser;
import com.mobi.arrive5d.utils.PayLoad;
import com.mobi.arrive5d.utils.ReadPref;
import com.mobi.arrive5d.utils.SavePref;
import com.mobi.arrive5d.utils.Utils1;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.ACTION_PICK;

public class SelectIssueActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static int RESULT_LOAD_IMG = 0;
    private static int CAMERA_REQUEST = 1;
    List<ReasonsList> issuesList;
    ProgressDialog progressDialog;
    CircleImageView profile_image;
    ReadPref readPref;
    EditText etRemark;
    RadioGroup radioGroup;
    TextView txtTime, booking_startPoint, booking_endPoint;
    RadioButton rdbtn;
    SavePref savePref;
    String reason;
    String start, end;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    double latitude, longitude;
    private boolean isImageSelected = false;
    private File mSelectedFile = null;
    private String image = "";
    private Dialog dialogPhoto;
    private LatLng s_latLng, d_latLng;
    private GoogleMap mMap;
    private Polyline polyline;
    private SupportMapFragment map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_issue);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        radioGroup = findViewById(R.id.radioGroup);
        savePref = new SavePref(SelectIssueActivity.this);
        etRemark = findViewById(R.id.etRemark);
        readPref = new ReadPref(SelectIssueActivity.this);
        txtTime = findViewById(R.id.txtTime);
        booking_startPoint = findViewById(R.id.booking_startPoint);
        booking_endPoint = findViewById(R.id.booking_endPoint);
        profile_image = findViewById(R.id.profile_image);
        map = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        booking_startPoint.setText(start);
        booking_endPoint.setText(end);
        txtTime.setText(date + " " + "at" + " " + time);
        String type = "driver";
        getIssues(type);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                reason = issuesList.get(checkedId).getId();
                savePref.saveReasonId(reason);
            }
        });
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
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
            Marker marker = googleMap.addMarker(new MarkerOptions().position(s_latLng));
            builder.include(marker.getPosition());
        }
        d_latLng = getLocationFromAddress(SelectIssueActivity.this, end);
        if (d_latLng != null) {
            Marker marker = googleMap.addMarker(new MarkerOptions().position(d_latLng));
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.loc));
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
            Toast.makeText(SelectIssueActivity.this, "" + getResources().getString(R.string.enter_address), Toast.LENGTH_SHORT).show();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(SelectIssueActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void pickImage() {
        dialogPhoto = new Dialog(SelectIssueActivity.this);
        dialogPhoto.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPhoto.setContentView(R.layout.layout_choose_photo);
        LinearLayout rl_camera = dialogPhoto.findViewById(R.id.camera_ll);
        rl_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraImage();
                dialogPhoto.dismiss();
            }
        });
        LinearLayout rl_gallery = dialogPhoto.findViewById(R.id.gallery_ll);
        rl_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                dialogPhoto.dismiss();
            }
        });
        dialogPhoto.show();
    }

    private void selectImage() {
        Intent i = new Intent(ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMG);
    }

    private void cameraImage() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                Glide.with(SelectIssueActivity.this).load(selectedImage).into(profile_image);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(getRealPathFromURI(SelectIssueActivity.this, selectedImage));
                Bitmap galleryphoto = BitmapFactory.decodeFile(imgDecodableString);
                if (file.exists()) {
                    mSelectedFile = file;
                    isImageSelected = true;
                    profile_image.setImageBitmap(galleryphoto);
                } else {
                    isImageSelected = false;
                    mSelectedFile = null;
                }
                image = imgDecodableString.toString();
                CommonUtils.profileImg = image;
                dialogPhoto.dismiss();
            } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(SelectIssueActivity.this, photo);
                File finalFile = new File(getRealPathFromURI(tempUri));
                Log.i("data", "file name" + finalFile.getAbsolutePath());
                Log.i("data", "file name" + finalFile.getName());
                if (finalFile.exists()) {
                    mSelectedFile = finalFile;
                    isImageSelected = true;
                    profile_image.setImageBitmap(photo);
                } else {
                    isImageSelected = false;
                    mSelectedFile = null;
                }
                image = mSelectedFile.toString();
                Glide.with(SelectIssueActivity.this).load(image).into(profile_image);
                CommonUtils.profileImg = image;
                dialogPhoto.dismiss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void SubmitClick(View view) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        progressDialog = new ProgressDialog(SelectIssueActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        ArrayList<PayLoad> payLoads = new ArrayList<>();
        payLoads.add(new PayLoad("user_id", getIntent().getStringExtra("userId"), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("booking_id", getIntent().getStringExtra("bookingId"), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("driver_id", readPref.getDriverId(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("reason_id", readPref.getReasonId(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("comment", etRemark.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("type", "driver", Constants.MEDIA_TEXT));
        if (!CommonUtils.profileImg.isEmpty()) {
            payLoads.add(new PayLoad("image", CommonUtils.profileImg, Constants.MEDIA_IMAGE));
        }
        service.submitReason(Utils1.generateMultiPartBody(payLoads)).enqueue(new Callback<SubmitReasonResponse>() {
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
