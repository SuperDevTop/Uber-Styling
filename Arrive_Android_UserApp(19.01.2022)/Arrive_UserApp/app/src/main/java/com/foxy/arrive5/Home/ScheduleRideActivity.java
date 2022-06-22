package com.foxy.arrive5.Home;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Adapter.AddressSpinnerAdapter;
import com.foxy.arrive5.IntroScreens.GPSTracker;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.FareEstimationResponse;
import com.foxy.arrive5.utils.AutoCompleteParser1;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.SavePref;
import com.google.android.gms.maps.model.LatLng;
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.foxy.arrive5.Home.RequestRideActivity.setSystemBarTheme;

public class ScheduleRideActivity extends AppCompatActivity implements View.OnClickListener, SelectCarBottomSheet.BottomSheetCallback {
    LinearLayout layoutDate, layoutTime;
    TextView txtDate, txtTime, txtAmount;
    ArrayAdapter adapter;
    ImageView imgCross, imgCross1;
    Spinner spinner_profile;
    ReadPref readPref;
    SavePref savePref;
    ProgressDialog progressDialog;
    String subtypeId = "1";
    String startLat, startLong, endLat, endLong;
    private AutoCompleteTextView source, destination;
    private LinearLayout destination_address, source_address;
    private TextView source_tv, dest_tv, txtChange, booking_car_type;
    private Handler handler;
    private ArrayList arrayListResult;
    private LatLng s_latLng, d_latLng;
    double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_ride);
        destination = findViewById(R.id.ed_destination_home);
        destination_address = findViewById(R.id.destination_address_home);
        source_tv = findViewById(R.id.source_address_home);
        dest_tv = findViewById(R.id.dest_add_home);
        imgCross = findViewById(R.id.imgCross);
        txtAmount = findViewById(R.id.txtAmount);
        txtChange = findViewById(R.id.txtChange);
        booking_car_type = findViewById(R.id.booking_car_type);
        readPref = new ReadPref(ScheduleRideActivity.this);
        savePref = new SavePref(ScheduleRideActivity.this);
        imgCross1 = findViewById(R.id.imgCross1);
        booking_car_type.setText(readPref.getCarType());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(ScheduleRideActivity.this, true);
        source_address = findViewById(R.id.source_ed);
        source = findViewById(R.id.ed_pickup);
        layoutDate = findViewById(R.id.layoutDate);
        layoutTime = findViewById(R.id.layoutTime);
        txtTime = findViewById(R.id.txtTime);
        txtDate = findViewById(R.id.txtDate);
        spinner_profile = findViewById(R.id.spinner_profile);
        spinnerCall();
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(ScheduleRideActivity.this);
            }
        });
        layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker(ScheduleRideActivity.this);
            }
        });
       GPSTracker gpsTracker = new GPSTracker(this);
        lat = gpsTracker.getLatitude();
        lng = gpsTracker.getLongitude();
        Log.e("hfjjgjff", lat + "");
        Log.e("hfjjgjff", lng + "");
        txtChange.setOnClickListener(this);
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
                        + "&location="+lat+","+lng +"&radius=50000&strictbounds&sensor=true&key="
                        + "AIzaSyClkiUQxIeczQEjTrw-dy9h2fj9qjXDk_4";
                if (searchText.length <= 1) {
                    arrayListResult = new ArrayList();
                    handler = new Handler();
                    AutoCompleteParser1 parse = new AutoCompleteParser1(ScheduleRideActivity.this, destination_address, dest_tv, url1, arrayListResult, adapter, destination, handler);
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
                        + "&location="+lat+","+lng+"&radius=50000&strictbounds&sensor=true&key="
                        + "AIzaSyClkiUQxIeczQEjTrw-dy9h2fj9qjXDk_4";
                if (searchText.length <= 1) {
                    arrayListResult = new ArrayList();
                    handler = new Handler();
                    AutoCompleteParser1 parse = new AutoCompleteParser1(ScheduleRideActivity.this, source_address, source_tv, url1, arrayListResult, adapter, source, handler);
                    parse.execute();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        source.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    imgCross.setVisibility(View.VISIBLE);
                } else {
                    imgCross.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    imgCross1.setVisibility(View.VISIBLE);
                } else {
                    imgCross1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source.setVisibility(View.VISIBLE);
                source_address.setVisibility(View.GONE);
                source.setText("");
            }
        });

        imgCross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destination.setVisibility(View.VISIBLE);
                destination_address.setVisibility(View.GONE);
                destination.setText("");
            }
        });
    }

    public void spinnerCall() {
        ArrayList<ItemData> list = new ArrayList<>();
        list.add(new ItemData("Personal"));
        list.add(new ItemData("Business"));
        AddressSpinnerAdapter adapter = new AddressSpinnerAdapter(this,
                R.layout.spinner_layout, R.id.txt, list);
        spinner_profile.setAdapter(adapter);
    }

    public void BackClick(View view) {
        finish();
    }

    public void openDatePicker(final Context context) {


        DatePickerFragmentDialog dialog=DatePickerFragmentDialog.newInstance(new DatePickerFragmentDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerFragmentDialog view, int year, int monthOfYear, int dayOfMonth) {


                Log.e("retrewefr",""+year);
                Log.e("retrewefr",""+monthOfYear++);
                Log.e("retrewefr",""+dayOfMonth);

                String strYear= String.valueOf(year);
                String strMonth= String.valueOf(monthOfYear++);
                String strDay= String.valueOf(dayOfMonth);
                //txtDate.setText(strDay+"-"+strMonth+"-"+strYear);
                txtDate.setText(strYear+"-"+strMonth+"-"+strDay);



            }
        });

        dialog.setAccentColor(Color.parseColor("#2abbe7"));
        dialog.show(getSupportFragmentManager(),"Tag");

        /*2019-010-17*/
        /*10:15*/

    }

    public void openTimePicker(Context context) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(context,R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour, min;
                if (hourOfDay < 10)
                    hour = "0" + hourOfDay + "";
                else
                    hour = "" + hourOfDay;
                if (minute < 10)
                    min = "0" + minute + "";
                else
                    min = "" + minute;
                txtTime.setText(new StringBuilder().append(hour).append(":").append(min));
            }
        }, mHour, mMinute, true);
        tpd.show();
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
            case R.id.txtChange:
                s_latLng = getLocationFromAddress(ScheduleRideActivity.this, source_tv.getText().toString());
                d_latLng = getLocationFromAddress(ScheduleRideActivity.this, dest_tv.getText().toString());
                if (s_latLng != null) {
                    startLat = String.valueOf(s_latLng.latitude);
                    startLong = String.valueOf(s_latLng.longitude);
                } else {
                    Toast.makeText(this, "Please enter Start Point", Toast.LENGTH_SHORT).show();
                }
                if (d_latLng != null) {
                    endLat = String.valueOf(d_latLng.latitude);
                    endLong = String.valueOf(d_latLng.longitude);
                } else {
                    Toast.makeText(this, "Please enter end point", Toast.LENGTH_SHORT).show();
                }
                if (s_latLng != null && d_latLng != null) {
                    SelectCarBottomSheet fragmentModalBottomSheet = new SelectCarBottomSheet();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("startLat", startLat);
                    bundle.putSerializable("startLong", startLong);
                    bundle.putSerializable("endLat", endLat);
                    bundle.putSerializable("endLong", endLong);
                    fragmentModalBottomSheet.setArguments(bundle);
                    fragmentModalBottomSheet.registerActivity(this);
                    fragmentModalBottomSheet.setCancelable(false);
                    fragmentModalBottomSheet.show(getSupportFragmentManager(), "BottomSheet Fragment");
                }
                break;
        }
    }

    public void ScheduleClick(View view) {
        String startPoint = source_tv.getText().toString();
        String endPoint = dest_tv.getText().toString();
        s_latLng = getLocationFromAddress(ScheduleRideActivity.this, source_tv.getText().toString());
        d_latLng = getLocationFromAddress(ScheduleRideActivity.this, dest_tv.getText().toString());
        if (s_latLng != null) {
            startLat = String.valueOf(s_latLng.latitude);
            startLong = String.valueOf(s_latLng.longitude);
        } else {
            Toast.makeText(this, "Please enter Start Point", Toast.LENGTH_SHORT).show();
        }
        if (d_latLng != null) {
            endLat = String.valueOf(d_latLng.latitude);
            endLong = String.valueOf(d_latLng.longitude);
        } else {
            Toast.makeText(this, "Please enter end point", Toast.LENGTH_SHORT).show();
        }
        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();
        subtypeId = readPref.getSubTypeId();
        savePref.saveScheduleStart(startPoint);
        savePref.saveScheduleEnd(endPoint);
        if (startLat != null || startLong != null) {
            savePref.saveStartLat(startLat);
            savePref.saveStartLong(startLong);
        }
        if (endLat != null || endLong != null) {
            savePref.saveEndLat(endLat);
            savePref.saveEndLong(endLong);
        }
        savePref.saveScheduleDate(date);
        savePref.saveScheduleTime(time);
        savePref.saveScheduledAmount(txtAmount.getText().toString().substring(1));
        Intent intent = new Intent(ScheduleRideActivity.this, SelectDriverActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        booking_car_type.setText(readPref.getCarType());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        booking_car_type.setText(readPref.getCarType());
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

    @Override
    public void carTypeInfo(String car_type) {
        booking_car_type.setText(car_type);
    }

    public void estimateClick(View view) {
        s_latLng = getLocationFromAddress(ScheduleRideActivity.this, source_tv.getText().toString());
        d_latLng = getLocationFromAddress(ScheduleRideActivity.this, dest_tv.getText().toString());
        if (s_latLng != null) {
            startLat = String.valueOf(s_latLng.latitude);
            startLong = String.valueOf(s_latLng.longitude);
        }

        if (d_latLng != null) {
            endLat = String.valueOf(d_latLng.latitude);
            endLong = String.valueOf(d_latLng.longitude);
        }

        subtypeId = readPref.getSubTypeId();
        if (s_latLng != null && d_latLng != null) {
            estimateFare(startLat, startLong, endLat, endLong, subtypeId);
        } else {
            Toast.makeText(this, "Please enter start and end point", Toast.LENGTH_SHORT).show();
        }
    }

    private void estimateFare(String startLat, String startLong, String endLat, String endLong, String subtypeId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<FareEstimationResponse> estimationResponseCall = service.estimateFare(startLat, startLong, endLat, endLong, subtypeId);
        progressDialog = new ProgressDialog(ScheduleRideActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        estimationResponseCall.enqueue(new Callback<FareEstimationResponse>() {
            @Override
            public void onResponse(Call<FareEstimationResponse> call, Response<FareEstimationResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        String estimatedPrice = response.body().getResult().toString();
                        txtAmount.setText("$" + " " + estimatedPrice);
                    } else {

                        Toast.makeText(ScheduleRideActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<FareEstimationResponse> call, Throwable t) {
                Toast.makeText(ScheduleRideActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
