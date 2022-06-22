package com.foxy.arrive5.SideMenu;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.UpdateProfileResponse;
import com.foxy.arrive5.utils.CommonUtils;
import com.foxy.arrive5.utils.Constants;
import com.foxy.arrive5.utils.PayLoad;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.SavePref;
import com.foxy.arrive5.utils.Utils;
import com.foxy.arrive5.utils.Validations;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.ACTION_PICK;

public class EditProfileActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 0;
    private static int CAMERA_REQUEST = 1;
    EditText txtName, txtCity, txtMusic, txtAbout, txtLName;
    CircleImageView overlapImage;
    ReadPref readPref;
    SavePref pref;
    ProgressDialog progressDialog;
    private Dialog dialogPhoto;
    private boolean isImageSelected = false;
    private File mSelectedFile = null;
    private String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);
        setupUI(findViewById(R.id.layout));
        txtName = findViewById(R.id.txtName);
        txtCity = findViewById(R.id.txtCity);
        txtMusic = findViewById(R.id.txtMusic);
        txtAbout = findViewById(R.id.txtAbout);
        txtLName = findViewById(R.id.txtLName);
        overlapImage = findViewById(R.id.overlapImage);
        readPref = new ReadPref(EditProfileActivity.this);
        pref = new SavePref(EditProfileActivity.this);
        txtName.setText(readPref.getFname());
        txtLName.setText(readPref.getLname());
        txtCity.setText(readPref.getCity());
        txtMusic.setText(readPref.getMusic());
        txtAbout.setText(readPref.getAbout());
        Glide.with(EditProfileActivity.this).load(readPref.getImage()).into(overlapImage);
        overlapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
    }

    public void pickImage() {
        dialogPhoto = new Dialog(EditProfileActivity.this);
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
                Glide.with(EditProfileActivity.this).load(selectedImage).into(overlapImage);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(getRealPathFromURI(EditProfileActivity.this, selectedImage));
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
                Uri tempUri = getImageUri(EditProfileActivity.this, photo);
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
                Glide.with(EditProfileActivity.this).load(image).into(overlapImage);
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

    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    Validations.hideSoftKeyboard(EditProfileActivity.this);
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

    public void UpdateClick(View view) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        ArrayList<PayLoad> payLoads = new ArrayList<>();
        payLoads.add(new PayLoad("user_id", readPref.getUserId(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("first_name", txtName.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("city", txtCity.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("fav_music", txtMusic.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("about_me", txtAbout.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("last_name", txtLName.getText().toString(), Constants.MEDIA_TEXT));
        if (!CommonUtils.profileImg.isEmpty()) {
            payLoads.add(new PayLoad("image", CommonUtils.profileImg, Constants.MEDIA_IMAGE));
        }
        service.updateProfile(Utils.generateMultiPartBody(payLoads)).enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(EditProfileActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String name = response.body().getResult().getFirst_name();
                        String lName = response.body().getResult().getLast_name();
                        String image = response.body().getResult().getImg();
                        String city = response.body().getResult().getCity();
                        String music = response.body().getResult().getFav_music();
                        String about = response.body().getResult().getAbout_me();
                        pref.saveCity(city);
                        pref.saveMusic(music);
                        pref.saveAbout(about);
                        pref.saveFname(name);
                        pref.saveLname(lName);
                        pref.saveName(name + " " + lName);
                        pref.saveImage(image);
                        pref.saveLogin(true);
                        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EditProfileActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
