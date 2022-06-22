package com.mobi.arrive5d.IntroScreens;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
;
import com.bumptech.glide.Glide;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.utils.ReadPref;
import com.mobi.arrive5d.utils.TextProgressBar;
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DriverInfoActivity extends AppCompatActivity  {
    TextView ccp;
    String country;
    EditText edittext;
    Calendar myCalendar, myCalendar1;
    DatePickerDialog.OnDateSetListener date, date1;
    ReadPref readPref;
    EditText txtFName, txtMName, txtLName, txtSsn, txtlcn, expiry, txtAddress, txtAddress2, txtCity, txtState, txtZip;
    private TextProgressBar pbHorizontal = null;
    private int progress = 25;
    ImageView profile_image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        ccp = (TextView) findViewById(R.id.ccpCountry);
        ccp = (TextView) findViewById(R.id.ccpCountry);


        try {
            readPref = new ReadPref(DriverInfoActivity.this);
            if (getIntent().getStringExtra("isDirect").equalsIgnoreCase("1")) {
                profile_image = findViewById(R.id.profile_image);
                Log.e("dgirhegerg", "VVVVV" + readPref.getImage());

                Glide.with(this).load(readPref.getImage()).into(profile_image);
            } else {
                profile_image.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        pbHorizontal = (TextProgressBar) findViewById(R.id.pb_horizontal);
        country = null;
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        for (String provider : lm.getAllProviders()) {
            @SuppressWarnings("ResourceType") Location location = lm.getLastKnownLocation(provider);
            if (location != null) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null && addresses.size() > 0) {
                        country = addresses.get(0).getCountryName();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ccp.setText(country);
        if (progress < pbHorizontal.getMax()) {
            progress = progress + 25;
            postProgress(progress);
        }
        txtFName = findViewById(R.id.txtFName);
        txtMName = findViewById(R.id.txtMName);
        txtLName = findViewById(R.id.txtLName);
        txtSsn = findViewById(R.id.txtSsn);
        txtlcn = findViewById(R.id.txtlcn);
        txtAddress = findViewById(R.id.txtAddress);
        txtAddress2 = findViewById(R.id.txtAddress2);
        txtCity = findViewById(R.id.txtCity);
        txtState = findViewById(R.id.txtState);
        txtZip = findViewById(R.id.txtZip);
        expiry = findViewById(R.id.expiry);
        edittext = (EditText) findViewById(R.id.Birthday);
        myCalendar = Calendar.getInstance();
        myCalendar1 = Calendar.getInstance();
        txtFName.setText(readPref.getFirstName());
        txtLName.setText(readPref.getLastName());
        txtCity.setText(readPref.getCity());
        if (getIntent().getStringExtra("isDirect") != null) {
            if (getIntent().getStringExtra("isDirect").equalsIgnoreCase("1")) {
                txtMName.setText(readPref.getMiddleName());
                txtSsn.setText(readPref.getSSN());
                edittext.setText(readPref.getDob());
                txtlcn.setText(readPref.getLicenseNo());
                expiry.setText(readPref.getExpiryDate());
                txtAddress.setText(readPref.getAddress1());
                txtAddress2.setText(readPref.getAddress2());
                txtState.setText(readPref.getState());
                txtZip.setText(readPref.getZip());
            }
        }
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };


        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerFragmentDialog dialog=DatePickerFragmentDialog.newInstance(new DatePickerFragmentDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerFragmentDialog view, int year, int monthOfYear, int dayOfMonth) {


                         Log.e("retrewefr",""+year);
                         Log.e("retrewefr",""+monthOfYear++);
                         Log.e("retrewefr",""+dayOfMonth);

                         String strYear= String.valueOf(year);
                         String strMonth= String.valueOf(monthOfYear++);
                         String strDay= String.valueOf(dayOfMonth);
                         edittext.setText(strDay+"-"+strMonth+"-"+strYear);



                    }
                });

                dialog.setAccentColor(Color.parseColor("#2abbe7"));
                dialog.show(getSupportFragmentManager(),"Tag");

               /* new DatePickerDialog(DriverInfoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
            }
        });
        expiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DriverInfoActivity.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        expiry.setText(sdf.format(myCalendar1.getTime()));
    }

    private void postProgress(int progress) {
        String strProgress = String.valueOf(progress) + " %";
        pbHorizontal.setProgress(progress);
        if (progress == 0) {
            pbHorizontal.setSecondaryProgress(0);
        } else {
            pbHorizontal.setSecondaryProgress(progress);
        }
        pbHorizontal.setText(strProgress);
    }

    public void RequirementClick(View view) {
        String driverid = readPref.getDriverId();
        String drivingLicence = txtlcn.getText().toString();
        String expiredate = expiry.getText().toString();
        String address1 = txtAddress.getText().toString();
        String address2 = txtAddress2.getText().toString();
        String zipcode = txtZip.getText().toString();
        String dob = edittext.getText().toString();
        String social_secrityno = txtSsn.getText().toString();
        String middle = txtMName.getText().toString();
        String state = txtState.getText().toString();
        String isDirect = getIntent().getStringExtra("isDirect");

        if (txtFName.getText().toString().equals("")) {
            txtFName.setBackground(getResources().getDrawable(R.drawable.empty_field_back));
        } else if (txtLName.getText().toString().equals("")) {
            txtLName.setBackground(getResources().getDrawable(R.drawable.empty_field_back));
        } else if (txtSsn.getText().toString().equals("")) {
            txtSsn.setBackground(getResources().getDrawable(R.drawable.empty_field_back));
        } else if (drivingLicence.equals("")) {
            txtlcn.setBackground(getResources().getDrawable(R.drawable.empty_field_back));
        } else if (expiredate.equals("")) {
            expiry.setBackground(getResources().getDrawable(R.drawable.empty_field_back));
        } else if (address1.equals("")) {
            txtAddress.setBackground(getResources().getDrawable(R.drawable.empty_field_back));
        } else if (txtAddress2.equals("")) {
            txtAddress2.setBackground(getResources().getDrawable(R.drawable.empty_field_back));
        } else if (zipcode.equals("")) {
            txtZip.setBackground(getResources().getDrawable(R.drawable.empty_field_back));
        } else if (txtCity.getText().toString().equals("")) {
            txtCity.setBackground(getResources().getDrawable(R.drawable.empty_field_back));
        } else {

            Intent intent = new Intent(DriverInfoActivity.this, DrivingRequirementsActivity.class);
            intent.putExtra("driverId", driverid);
            intent.putExtra("isDirect", isDirect);
            intent.putExtra("drivingLicence", drivingLicence);
            intent.putExtra("expiredate", expiredate);
            intent.putExtra("address1", address1);
            intent.putExtra("address2", address2);
            intent.putExtra("zipcode", zipcode);
            intent.putExtra("dob", dob);
            intent.putExtra("social_secrityno", social_secrityno);
            intent.putExtra("middle", middle);
            intent.putExtra("state", state);
            startActivity(intent);
        }


    }

    public void BackClick(View view) {
        finish();
    }


}
