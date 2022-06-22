package com.foxy.arrive5.IntroScreens;

import android.app.Activity;
import android.app.Dialog;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.CommonUtils;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.SavePref;
import com.foxy.arrive5.utils.Validations;

import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.ACTION_PICK;

public class CreateProfileActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 0;
    private static int CAMERA_REQUEST = 1;
    CircleImageView profile_image;
    EditText txtFName, txtLName;
    ImageView imgSignup;
    ReadPref readPref;
    SavePref pref;
    private Dialog dialogPhoto;
    private boolean isImageSelected = false;
    private File mSelectedFile = null;
    private String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        setupUI(findViewById(R.id.layout));
        pref = new SavePref(CreateProfileActivity.this);
        readPref = new ReadPref(CreateProfileActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        txtFName = findViewById(R.id.txtFName);
        txtLName = findViewById(R.id.txtLName);
        imgSignup = findViewById(R.id.imgSignup);
        imgSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.saveFname(txtFName.getText().toString());
                pref.saveLname(txtLName.getText().toString());
                pref.saveImage(CommonUtils.profileImg);
                if (Validations.hasText(txtFName) && Validations.hasText(txtLName)) {
                    Intent intent = new Intent(CreateProfileActivity.this, EmailActivity.class);
                    startActivity(intent);
                }
            }
        });

        profile_image = findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
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
