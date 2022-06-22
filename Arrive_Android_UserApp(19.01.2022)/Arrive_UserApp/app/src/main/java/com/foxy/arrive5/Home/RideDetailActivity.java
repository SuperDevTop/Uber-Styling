package com.foxy.arrive5.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.ContactListResponse;
import com.foxy.arrive5.Response.SplitFareResponse;
import com.foxy.arrive5.utils.ReadPref;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideDetailActivity extends AppCompatActivity implements Contact_Adapter.OnItemClick, OnMapReadyCallback
        , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private static ListView contact_listview;
    private static ArrayList<Contact_Model> arrayList;
    private static Contact_Adapter adapter;
    private static ProgressDialog pd;
    CircleImageView driver_img;
    ReadPref readPref;
    TextView driver_name, booking_car_color,
            txtRating, booking_car_type, booking_car_no,
            booking_otp, txtLocation, txtAmount, txtArrival,txtDuration;
    LinearLayout layoutCancel, layoutContact, layoutCard, layoutShare;
    String driverPhone, bookingId;
    LinearLayout layoutTrip;
    ImageView imgDone;
    private List List = new ArrayList();
    List<ContactListResponse.ContactListBean> contact;
    ProgressDialog progressDialog;

    private SupportMapFragment map;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    String Dlat;
    String Dlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);
        driver_img = findViewById(R.id.driver_img);
        driver_name = findViewById(R.id.driver_name);
        readPref = new ReadPref(RideDetailActivity.this);
        contact_listview = (ListView) findViewById(R.id.contact_listview);
        txtRating = findViewById(R.id.txtRating);
        imgDone = findViewById(R.id.imgDone);
        booking_car_type = findViewById(R.id.booking_car_type);
        txtDuration = findViewById(R.id.txtDuration);
        booking_car_no = findViewById(R.id.booking_car_no);
        booking_car_color = findViewById(R.id.booking_car_color);
        booking_otp = findViewById(R.id.booking_otp);
        txtLocation = findViewById(R.id.txtLocation);
        txtArrival = findViewById(R.id.txtArrival);
        txtAmount = findViewById(R.id.txtAmount);
        layoutCancel = findViewById(R.id.layoutCancel);
        layoutContact = findViewById(R.id.layoutContact);
        layoutCard = findViewById(R.id.layoutCard);
        layoutShare = findViewById(R.id.layoutShare);
        layoutTrip = findViewById(R.id.layoutTrip);
        String name = getIntent().getStringExtra("driverName");
        String image = getIntent().getStringExtra("driverImg");
        String rating = getIntent().getStringExtra("rate");
        String otp = getIntent().getStringExtra("OTP");
        String carType = getIntent().getStringExtra("type");
        String carNo = getIntent().getStringExtra("no");
        String start = getIntent().getStringExtra("start");
        String end = getIntent().getStringExtra("end");
        String duration = getIntent().getStringExtra("Duration");
        txtDuration.setText(duration);
        Dlat = getIntent().getStringExtra("DriverLat");
        Dlng = getIntent().getStringExtra("DriverLong");
        Log.e("jfghjfhdf", duration);
        bookingId = getIntent().getStringExtra("bookingId");
        Glide.with(RideDetailActivity.this).load(image).into(driver_img);
        driverPhone = getIntent().getStringExtra("driverPhone");
        driver_name.setText(name);
        txtRating.setText(rating);
        booking_car_type.setText(carType);
        String text = "<font color=#333>License plate</font> <font color=#2abbe7>" + carNo + "</font>";
        booking_car_no.setText(Html.fromHtml(text));


        String textColor = "<font color=#333>Color</font> <font color=#2abbe7>Red</font>";
        booking_car_color.setText(Html.fromHtml(textColor));

        booking_otp.setText(otp);
        txtLocation.setText(start);
        txtArrival.setText(end);
        txtAmount.setText(readPref.getBookingAmount());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        layoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RideDetailActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
        layoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RideDetailActivity.this, CancelRideActivity.class);
                startActivity(intent);
            }
        });

        layoutContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutTrip.setVisibility(View.VISIBLE);
                contact_listview.setVisibility(View.GONE);
                imgDone.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + driverPhone));
                startActivity(intent);
            }
        });
        layoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_TEXT, "Riding in Arrive5 driven by" + " " + readPref.getDriverName() + " " + (readPref.getDriverMobile()) + ".Final bill amount will be shown on driver device. Track the ride here: http://arrive5.pcthepro.com/track/325k1qr9ns");
                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });


        map = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(RideDetailActivity.this,
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
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        double latitude = Double.parseDouble(Dlat);
        double longitude = Double.parseDouble(Dlng);
        LatLng latLng = new LatLng(latitude, longitude);

        int heights = 110;
        int widths = 80;
        BitmapDrawable bitmapdraws = (BitmapDrawable) getResources().getDrawable(R.drawable.car_marker);
        Bitmap bs = bitmapdraws.getBitmap();

        Bitmap smallMarkers = Bitmap.createScaledBitmap(bs, widths, heights, false);
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkers))
        );
        builder.include(marker.getPosition());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(RideDetailActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    public void BackClick(View view) {
        finish();
    }

    public void FareSplitClick(View view) {
        layoutTrip.setVisibility(View.GONE);
        imgDone.setVisibility(View.VISIBLE);
        contact_listview.setVisibility(View.VISIBLE);
        new LoadContacts().execute();

    }

    private ArrayList<Contact_Model> readContacts() {
        ArrayList<Contact_Model> contactList = new ArrayList<Contact_Model>();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor contactsCursor = getContentResolver().query(uri, null, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
        if (contactsCursor.moveToFirst()) {
            do {
                long contctId = contactsCursor.getLong(contactsCursor
                        .getColumnIndex("_ID"));
                Uri dataUri = ContactsContract.Data.CONTENT_URI;
                Cursor dataCursor = getContentResolver().query(dataUri, null,
                        ContactsContract.Data.CONTACT_ID + " = " + contctId,
                        null, null);
                String displayName = "";
                String nickName = "";
                String mobilePhone = "";
                String contactNumbers = "";
                String contactOtherDetails = "";

                if (dataCursor.moveToFirst()) {
                    displayName = dataCursor
                            .getString(dataCursor
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));// get
                    do {
                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)) {
                            nickName = dataCursor.getString(dataCursor
                                    .getColumnIndex("data1")); // Get Nick Name
                            contactOtherDetails += "NickName : " + nickName;
                        }
                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            mobilePhone = dataCursor.getString(dataCursor
                                    .getColumnIndex("data1"));
                            //.replaceAll("[^0-9]", "");

//                            if (mobilePhone.length() == 12) {
//                                mobilePhone = mobilePhone.substring(2);
//                            } else if (mobilePhone.length() == 11) {
//                                mobilePhone = mobilePhone.substring(1);
//                            }
                            contactNumbers += mobilePhone;
                            break;
                        }
                    } while (dataCursor.moveToNext());
                    contactList.add(new Contact_Model(contactNumbers));
                }
            } while (contactsCursor.moveToNext());
        }
        return contactList;
    }

    private void getContactList(String listString) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ContactListResponse> contactListResponseCall = service.getContactList(listString);
        progressDialog = new ProgressDialog(RideDetailActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        contactListResponseCall.enqueue(new Callback<ContactListResponse>() {
            @Override
            public void onResponse(Call<ContactListResponse> call, Response<ContactListResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        contact = response.body().getContact_list();
                        if (contact.size() > 0) {
                            setContactAdapter();
                        } else {
                            Toast.makeText(RideDetailActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RideDetailActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ContactListResponse> call, Throwable t) {
                Toast.makeText(RideDetailActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setContactAdapter() {
        adapter = null;
        if (adapter == null) {
            adapter = new Contact_Adapter(RideDetailActivity.this, contact, this);
            contact_listview.setAdapter(adapter);// set adapter
        }
        adapter.notifyDataSetChanged();
    }

    public void DoneClick(View view) {
        Log.e("List", "" + List);
        //String joinedString = List.toString().replace(", ", ",").replaceAll("[\\[.\\]]", "");
        String joinedString = String.join(",", List);
        Log.e("String", "" + joinedString);
        splitFare(bookingId, joinedString);
    }

    private void splitFare(String bookingId, String joinedString) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<SplitFareResponse> splitFareResponseCall = service.splitFare(bookingId, joinedString);
        progressDialog = new ProgressDialog(RideDetailActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        splitFareResponseCall.enqueue(new Callback<SplitFareResponse>() {
            @Override
            public void onResponse(Call<SplitFareResponse> call, Response<SplitFareResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(RideDetailActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RideDetailActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RideDetailActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SplitFareResponse> call, Throwable t) {
                Toast.makeText(RideDetailActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(List myList) {
        List = myList;
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


    private class LoadContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            arrayList = readContacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (arrayList != null && arrayList.size() > 0) {
                Gson gson = new Gson();
                String listString = gson.toJson(
                        arrayList,
                        new TypeToken<ArrayList<Contact_Model>>() {
                        }.getType());
                getContactList(listString);
            } else {
                Toast.makeText(RideDetailActivity.this, "There are no contacts.",
                        Toast.LENGTH_LONG).show();
            }
            if (pd.isShowing())
                pd.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(RideDetailActivity.this, "Loading Contacts",
                    "Please Wait...");
        }
    }

}
