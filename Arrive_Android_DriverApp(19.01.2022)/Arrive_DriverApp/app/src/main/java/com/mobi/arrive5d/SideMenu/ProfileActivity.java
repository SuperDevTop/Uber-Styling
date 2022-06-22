package com.mobi.arrive5d.SideMenu;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.MainActivity;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.UpdateProfileResponse;
import com.mobi.arrive5d.utils.CommonUtils;
import com.mobi.arrive5d.utils.Constants;
import com.mobi.arrive5d.utils.PayLoad;
import com.mobi.arrive5d.utils.ReadPref;
import com.mobi.arrive5d.utils.SavePref;
import com.mobi.arrive5d.utils.Utils1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.ACTION_PICK;

public class ProfileActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 0;
    private static int CAMERA_REQUEST = 1;
    EditText txtName, txtMName, txtLName, txtEmail, txtMobile, txtAddress, txtCity, txtCountry;
    TextView txtRating;
    RatingBar ratingBar;
    ReadPref readPref;
    SavePref pref;
    Spinner spinner;
    String spinnerValue, valueId;
    CircleImageView overlapImage;
    ImageView imgEdit;
    ProgressDialog progressDialog;
    private Dialog dialogPhoto;
    private boolean isImageSelected = false;
    private File mSelectedFile = null;
    private String image = "";

    public static final void setSystemBarTheme(final Activity pActivity, final boolean pIsDark) {
        final int lFlags = pActivity.getWindow().getDecorView().getSystemUiVisibility();
        pActivity.getWindow().getDecorView().setSystemUiVisibility(pIsDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txtRating = findViewById(R.id.txtRating);
        spinner = (Spinner) findViewById(R.id.spinner);
        imgEdit = findViewById(R.id.imgEdit);
        txtName = findViewById(R.id.txtName);
        txtMName = findViewById(R.id.txtMName);
        txtLName = findViewById(R.id.txtLName);
        txtEmail = findViewById(R.id.txtEmail);
        txtMobile = findViewById(R.id.txtMobile);
        txtAddress = findViewById(R.id.txtAddress);
        txtCity = findViewById(R.id.txtCity);
        txtCountry = findViewById(R.id.txtCountry);
        readPref = new ReadPref(ProfileActivity.this);
        pref = new SavePref(ProfileActivity.this);
        ratingBar = findViewById(R.id.ratingBar);
        overlapImage = findViewById(R.id.overlapImage);
        valueId = readPref.getGender();
        txtName.setText(readPref.getFirstName());
        txtMName.setText(readPref.getMiddleName());
        txtLName.setText(readPref.getLastName());
        txtEmail.setText(readPref.getEmailid());
        txtMobile.setText(readPref.getMobile());
        txtAddress.setText(readPref.getAddress());
        txtCity.setText(readPref.getCity());
        txtCountry.setText(readPref.getCountry());
        image = readPref.getImage();
        final String[] items = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item1, items);
        adapter.setDropDownViewResource(R.layout.spinner_item1);
        spinner.setAdapter(adapter);
        spinner.setSelection(readPref.getGenderIndex());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pref.saveGenderIndex(position);
                spinnerValue = spinner.getSelectedItem().toString();
                if (spinnerValue.equalsIgnoreCase("Female")) {
                    valueId = "0";
                } else if (spinnerValue.equalsIgnoreCase("Male")) {
                    valueId = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Glide.with(this).load(readPref.getImage()).into(overlapImage);
        txtRating.setText(String.valueOf(ratingBar.getRating()));
        ratingBar.setRating(Float.parseFloat(readPref.getDriverRating()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(ProfileActivity.this, true);
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgEdit.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.edit).getConstantState()) {
                    txtName.setFocusableInTouchMode(true);
                    txtName.setSelection(txtName.getText().length());
                    txtMName.setFocusableInTouchMode(true);
                    txtMName.setSelection(txtMName.getText().length());
                    txtLName.setFocusableInTouchMode(true);
                    txtLName.setSelection(txtLName.getText().length());
                    txtEmail.setFocusableInTouchMode(true);
                    txtEmail.setSelection(txtEmail.getText().length());
                    txtMobile.setFocusableInTouchMode(false);
                    spinner.setEnabled(true);
                    txtMobile.setSelection(txtMobile.getText().length());
                    txtAddress.setFocusableInTouchMode(true);
                    txtAddress.setSelection(txtAddress.getText().length());
                    txtCity.setFocusableInTouchMode(true);
                    txtCity.setSelection(txtCity.getText().length());
                    txtCountry.setFocusableInTouchMode(true);
                    txtCountry.setSelection(txtCountry.getText().length());
                    overlapImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickImage();
                        }
                    });
                    imgEdit.setImageResource(R.drawable.tick1);
                } else if (imgEdit.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.tick1).getConstantState()) {
                    ApiService service = ApiClient.getClient().create(ApiService.class);
                    progressDialog = new ProgressDialog(ProfileActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    ArrayList<PayLoad> payLoads = new ArrayList<>();
                    payLoads.add(new PayLoad("driver_id", readPref.getDriverId(), Constants.MEDIA_TEXT));
                    payLoads.add(new PayLoad("first_name", txtName.getText().toString(), Constants.MEDIA_TEXT));
                    payLoads.add(new PayLoad("middle_name", txtMName.getText().toString(), Constants.MEDIA_TEXT));
                    payLoads.add(new PayLoad("last_name", txtLName.getText().toString(), Constants.MEDIA_TEXT));
                    payLoads.add(new PayLoad("city", txtCity.getText().toString(), Constants.MEDIA_TEXT));
                    payLoads.add(new PayLoad("email", txtEmail.getText().toString(), Constants.MEDIA_TEXT));
                    payLoads.add(new PayLoad("gender", valueId, Constants.MEDIA_TEXT));
                    payLoads.add(new PayLoad("address1", txtAddress.getText().toString(), Constants.MEDIA_TEXT));
                    payLoads.add(new PayLoad("country", txtCountry.getText().toString(), Constants.MEDIA_TEXT));
                    if (!CommonUtils.profileImg.isEmpty()) {
                        payLoads.add(new PayLoad("image", CommonUtils.profileImg, Constants.MEDIA_IMAGE));
                    }
                    service.updateProfile(Utils1.generateMultiPartBody(payLoads)).enqueue(new Callback<UpdateProfileResponse>() {
                        @Override
                        public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                            if (response.isSuccessful()) {
                                String status = response.body().getStatus();
                                if (status.equalsIgnoreCase("true")) {
                                    Toast.makeText(ProfileActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    String name = response.body().getResult().getFirstName() + " " + response.body().getResult().getLastName();
                                    String email = response.body().getResult().getEmail();
                                    String mobile = response.body().getResult().getMobile();
                                    String image = response.body().getResult().getImage();
                                    String city = response.body().getResult().getCity();
                                    String country = response.body().getResult().getCountry();
                                    String gender = response.body().getResult().getGender();
                                    String driverId = response.body().getResult().getId();
                                    String isOnline = response.body().getResult().getIsOnline();
                                    String address = response.body().getResult().getAddress();
                                    pref.saveFirstName(response.body().getResult().getFirstName());
                                    pref.saveMiddleName(response.body().getResult().getMiddleName());
                                    pref.saveLastName(response.body().getResult().getLastName());
                                    pref.saveAddress(address);
                                    pref.saveGender(gender);
                                    pref.saveCountry(country);
                                    pref.saveCity(city);
                                    pref.saveName(name);
                                    pref.saveEmail(email);
                                    pref.saveMobile(mobile);
                                    pref.saveImage(image);
                                    pref.saveLogin(true);
                                    pref.saveDriverId(driverId);
                                    pref.saveOnlineStatus(isOnline);
                                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ProfileActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                            Toast.makeText(ProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                    txtName.setFocusable(false);
                    txtName.setFocusableInTouchMode(false);
                    txtMName.setFocusableInTouchMode(false);
                    txtLName.setFocusableInTouchMode(false);
                    txtEmail.setFocusable(false);
                    txtEmail.setFocusableInTouchMode(false);
                    txtMobile.setFocusable(false);
                    spinner.setEnabled(false);
                    txtMobile.setFocusableInTouchMode(false);
                    txtAddress.setFocusable(false);
                    txtAddress.setFocusableInTouchMode(false);
                    txtCity.setFocusable(false);
                    txtCity.setFocusableInTouchMode(false);
                    txtCountry.setFocusable(false);
                    txtCountry.setFocusableInTouchMode(false);
                    imgEdit.setImageResource(R.drawable.edit);
                    overlapImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        spinner.setSelection(readPref.getGenderIndex());
    }

    public void pickImage() {
        dialogPhoto = new Dialog(ProfileActivity.this);
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
                Glide.with(ProfileActivity.this).load(selectedImage).into(overlapImage);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(getRealPathFromURI(ProfileActivity.this, selectedImage));
                Bitmap galleryphoto = BitmapFactory.decodeFile(imgDecodableString);
                if (file.exists()) {
                    mSelectedFile = file;
                    isImageSelected = true;
                    overlapImage.setImageBitmap(galleryphoto);
                } else {
                    isImageSelected = false;
                    mSelectedFile = null;
                }
                image = imgDecodableString.toString();
                CommonUtils.profileImg = image;
                dialogPhoto.dismiss();
            } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(ProfileActivity.this, photo);
                File finalFile = new File(getRealPathFromURI(tempUri));
                Log.i("data", "file name" + finalFile.getAbsolutePath());
                Log.i("data", "file name" + finalFile.getName());
                if (finalFile.exists()) {
                    mSelectedFile = finalFile;
                    isImageSelected = true;
                    overlapImage.setImageBitmap(photo);
                } else {
                    isImageSelected = false;
                    mSelectedFile = null;
                }
                image = mSelectedFile.toString();
                Glide.with(ProfileActivity.this).load(image).into(overlapImage);
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
