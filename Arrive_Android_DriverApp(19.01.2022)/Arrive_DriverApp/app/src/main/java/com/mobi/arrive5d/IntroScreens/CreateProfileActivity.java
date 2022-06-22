package com.mobi.arrive5d.IntroScreens;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.GsonBuilder;
import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.SignupResponse.Signup;
import com.mobi.arrive5d.utils.CommonUtils;
import com.mobi.arrive5d.utils.Constants;
import com.mobi.arrive5d.utils.GPSTracker;
import com.mobi.arrive5d.utils.PayLoad;
import com.mobi.arrive5d.utils.ReadPref;
import com.mobi.arrive5d.utils.SavePref;
import com.mobi.arrive5d.utils.Utils1;
import com.mobi.arrive5d.utils.Validations;
import com.rilixtech.CountryCodePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.ACTION_PICK;

public class CreateProfileActivity extends AppCompatActivity implements Listener {
    private static int RESULT_LOAD_IMG = 0;
    private static int CAMERA_REQUEST = 1;
    EasyWayLocation easyWayLocation;
    CountryCodePicker ccp;
    CountryCodePicker ccp1;
    CircleImageView profile_image;
    EditText txtMobile, txtFName, txtLName, txtEmail, txtPassword, txtCode, txtCity, txtConfirmPassword;
    ImageView imgSignup;
    ReadPref readPref;
    SavePref pref;
    ProgressDialog progressDialog;
    Spinner spinner;
    String spinnerValue, valueId = "0";
    String country = "India";
    double latitude, longitude;
    String code = "";
    GPSTracker gpsTrack;
    private Dialog dialogPhoto;
    private boolean isImageSelected = false;
    private File mSelectedFile = null;
    private String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        spinner = (Spinner) findViewById(R.id.spinner);
        easyWayLocation = new EasyWayLocation(this);
        easyWayLocation.setListener(this);
        String[] items = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, items);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinnerValue = spinner.getSelectedItem().toString();
        if (spinnerValue.equalsIgnoreCase("Female")) {
            valueId = "0";
        } else if (spinnerValue.equalsIgnoreCase("Male")) {
            valueId = "1";
        }
        setupUI(findViewById(R.id.layout));
        pref = new SavePref(CreateProfileActivity.this);
        readPref = new ReadPref(CreateProfileActivity.this);
        ccp = findViewById(R.id.ccp);
        ccp1 = findViewById(R.id.ccp1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        txtMobile = findViewById(R.id.txtMobile);
        txtMobile.setText(readPref.getMobile());
        txtFName = findViewById(R.id.txtFName);
        txtLName = findViewById(R.id.txtLName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtCode = findViewById(R.id.txtCode);
        txtCity = findViewById(R.id.txtCity);
        imgSignup = findViewById(R.id.imgSignup);
        imgSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtConfirmPassword.getText().toString().equals(txtPassword.getText().toString())) {
                    addUser();
                } else {

                    Toast.makeText(CreateProfileActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
                }


            }
        });
        txtMobile.setClickable(false);
        String country_name = null;
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        for (String provider : lm.getAllProviders()) {
            @SuppressWarnings("ResourceType") Location location = lm.getLastKnownLocation(provider);
            if (location != null) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null && addresses.size() > 0) {
                        country_name = addresses.get(0).getCountryName();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        gpsTrack = new GPSTracker(CreateProfileActivity.this);
        if (gpsTrack.canGetLocation()) {
            latitude = gpsTrack.getLatitude();
            longitude = gpsTrack.getLongitude();
            Log.e("GPSLat", "" + latitude);
            Log.e("GPSLong", "" + longitude);
        } else {
            gpsTrack.showSettingsAlert();
            Log.e("ShowAlert", "ShowAlert");
        }
        String countryCode = getAddress(CreateProfileActivity.this, latitude, longitude);
        String[] rl = getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(countryCode.trim())) {
                code = g[0];
                break;
            }
        }
        Log.e("countryCode", "" + countryCode);
        Log.e("Code", "" + code);
        ccp1.setCountryForNameCode(countryCode);
        profile_image = findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
        ccp.setCountryForPhoneCode(Integer.parseInt(readPref.getCode()));
    }

    public String getAddress(Context ctx, double latitude, double longitude) {
        String region_code = null;
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                region_code = address.getCountryCode();
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        return region_code;
    }

    @Override
    public void locationOn() {
        easyWayLocation.beginUpdates();
        latitude = easyWayLocation.getLatitude();
        longitude = easyWayLocation.getLongitude();
    }

    @Override
    public void onPositionChanged() {
    }

    @Override
    public void locationCancelled() {
    }


    @Override
    protected void onResume() {
        super.onResume();
        easyWayLocation.beginUpdates();
    }

    @Override
    protected void onPause() {
        easyWayLocation.endUpdates();
        super.onPause();
    }

    private void addUser() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        progressDialog = new ProgressDialog(CreateProfileActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        ArrayList<PayLoad> payLoads = new ArrayList<>();
        payLoads.add(new PayLoad("firstname", txtFName.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("lastname", txtLName.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("emailid", txtEmail.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("mobileno", "+" + readPref.getCode() + readPref.getMobile(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("password", txtPassword.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("token", FirebaseInstanceId.getInstance().getToken(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("appplatform", "android", Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("refcode", txtCode.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("country", country, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("city", txtCity.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("gender", valueId, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("lat", String.valueOf(latitude), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("lng", String.valueOf(longitude), Constants.MEDIA_TEXT));
        if (!CommonUtils.profileImg.isEmpty()) {
            payLoads.add(new PayLoad("image", CommonUtils.profileImg, Constants.MEDIA_IMAGE));
        }
        try {


            service.newUser(Utils1.generateMultiPartBody(payLoads)).enqueue(new Callback<Signup>() {
                @Override
                public void onResponse(Call<Signup> call, Response<Signup> response) {
                    if (response.isSuccessful()) {
                        String status = response.body().getStatus();
                        if (status.equalsIgnoreCase("true")) {
                            // Log.e("response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                            pref.saveName(response.body().getDetails().getFirstName() + " " + response.body().getDetails().getLastName());
                            pref.saveEmail(response.body().getDetails().getEmail());
                            pref.saveDriverId(response.body().getDetails().getId());
                            pref.saveFirstName(response.body().getDetails().getFirstName());
                            pref.saveLastName(response.body().getDetails().getLastName());
                            pref.saveMobile(response.body().getDetails().getMobile());
                            pref.saveImage(response.body().getDetails().getImage());
                            pref.saveCity(response.body().getDetails().getCity());
                            pref.saveCountry(response.body().getDetails().getCountry());
                            Intent intent = new Intent(CreateProfileActivity.this, VehicleInfoActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(CreateProfileActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<Signup> call, Throwable t) {
                    Toast.makeText(CreateProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    Validations.hideSoftKeyboard(CreateProfileActivity.this);
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

    public void pickImage() {
        dialogPhoto = new Dialog(CreateProfileActivity.this);
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
                Glide.with(CreateProfileActivity.this).load(selectedImage).into(profile_image);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(getRealPathFromURI(CreateProfileActivity.this, selectedImage));
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
                Uri tempUri = getImageUri(CreateProfileActivity.this, photo);
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
                Glide.with(CreateProfileActivity.this).load(image).into(profile_image);
                CommonUtils.profileImg = image;
                dialogPhoto.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BackClick(View view) {
        finish();
    }
}
