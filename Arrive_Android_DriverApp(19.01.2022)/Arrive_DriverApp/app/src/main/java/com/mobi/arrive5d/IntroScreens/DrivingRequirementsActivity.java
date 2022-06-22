package com.mobi.arrive5d.IntroScreens;

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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.MainActivity;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.UpdateDriverResponse;
import com.mobi.arrive5d.utils.Constants;
import com.mobi.arrive5d.utils.PayLoad;
import com.mobi.arrive5d.utils.ReadPref;
import com.mobi.arrive5d.utils.SavePref;
import com.mobi.arrive5d.utils.TextProgressBar;
import com.mobi.arrive5d.utils.Utils1;
import com.mobi.arrive5d.utils.Validations;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.ACTION_PICK;

public class DrivingRequirementsActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    EditText txtLicNo, txtVehRegl;
    String driverId, drivingLicense, expiry, add1, add2, zip, dob, ssn, middleName, state;
    TextView txtInsurance, txtLicense, txtAadharPhoto, txtVehicleIns;
    String image;
    Bitmap mLicenceBitmap;
    Bitmap mInsuranceBitmap;
    Bitmap mAdharBitmap;
    Bitmap mVehicleBitmap;
    SavePref savePref;
    ReadPref readPref;
    private TextProgressBar pbHorizontal = null;
    private int progress = 50;
    private Dialog dialogphoto;
    private boolean isImageSelected = false;
    private File mSelectedFile = null;
    private int INSURANCE_IMAGE_REQUEST_CODE_CAMERA = 101;
    private int LICENCE_IMAGE_REQUEST_CODE_CAMERA = 102;
    private int PHOTO_IMAGE_REQUEST_CODE_CAMERA = 103;
    private int VEHICLE_IMAGE_REQUEST_CODE_CAMERA = 104;
    private int INSURANCE_IMAGE_REQUEST_CODE_GALLERY = 105;
    private int LICENCE_IMAGE_REQUEST_CODE_GALLERY = 106;
    private int PHOTO_IMAGE_REQUEST_CODE_GALLERY = 107;
    private int VEHICLE_IMAGE_REQUEST_CODE_GALLERY = 108;
    private String mLicenceImagePath = "";
    private String mInsuranceImagePath = "";
    private String mPhotoImagePath = "";
    private String mVehicleImagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_requirements);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        pbHorizontal = (TextProgressBar) findViewById(R.id.pb_horizontal);
        if (progress < pbHorizontal.getMax()) {
            progress = progress + 25;
            postProgress(progress);
        }
        savePref = new SavePref(DrivingRequirementsActivity.this);
        readPref = new ReadPref(DrivingRequirementsActivity.this);
        txtLicNo = findViewById(R.id.txtLicNo);
        txtVehRegl = findViewById(R.id.txtVehReg);
        txtInsurance = findViewById(R.id.txtInsurance);
        txtLicense = findViewById(R.id.txtLicense);
        txtAadharPhoto = findViewById(R.id.txtAadharPhoto);
        txtVehicleIns = findViewById(R.id.txtVehicleIns);
        driverId = getIntent().getStringExtra("driverId");
        drivingLicense = getIntent().getStringExtra("drivingLicence");
        expiry = getIntent().getStringExtra("expiredate");
        add1 = getIntent().getStringExtra("address1");
        add2 = getIntent().getStringExtra("address2");
        zip = getIntent().getStringExtra("zipcode");
        dob = getIntent().getStringExtra("dob");
        ssn = getIntent().getStringExtra("social_secrityno");
        middleName = getIntent().getStringExtra("middle");
        state = getIntent().getStringExtra("state");
        txtInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customChoosePhoto("I");

            }
        });
        txtLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                customChoosePhoto("L");
            }
        });

        txtAadharPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customChoosePhoto("A");
            }
        });
        txtVehicleIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customChoosePhoto("V");

            }
        });
        if (getIntent().getStringExtra("isDirect") != null) {
            if (getIntent().getStringExtra("isDirect").equalsIgnoreCase("1")) {
                txtLicNo.setText(readPref.getLicensePlate());
                txtVehRegl.setText(readPref.getVehicleReg());
                txtInsurance.setText(readPref.getInsuranceImg());
                txtLicense.setText(readPref.getDrivingLicenseImg());
                txtAadharPhoto.setText(readPref.getAdharImg());
                txtVehicleIns.setText(readPref.getVehicleImg());
            }
        }
    }

    private int getCameraCode(String tag) {
        if (tag.equalsIgnoreCase("I")) {
            return INSURANCE_IMAGE_REQUEST_CODE_CAMERA;
        } else if (tag.equalsIgnoreCase("L")) {
            return LICENCE_IMAGE_REQUEST_CODE_CAMERA;
        } else if (tag.equalsIgnoreCase("A")) {
            return PHOTO_IMAGE_REQUEST_CODE_CAMERA;
        } else {
            return VEHICLE_IMAGE_REQUEST_CODE_CAMERA;
        }
    }

    private int getGalleryCode(String tag) {
        if (tag.equalsIgnoreCase("I")) {
            return INSURANCE_IMAGE_REQUEST_CODE_GALLERY;
        } else if (tag.equalsIgnoreCase("L")) {
            return LICENCE_IMAGE_REQUEST_CODE_GALLERY;
        } else if (tag.equalsIgnoreCase("A")) {
            return PHOTO_IMAGE_REQUEST_CODE_GALLERY;
        } else {
            return VEHICLE_IMAGE_REQUEST_CODE_GALLERY;
        }
    }

    private void customChoosePhoto(final String tag) {
        dialogphoto = new Dialog(DrivingRequirementsActivity.this);
        dialogphoto.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogphoto.setContentView(R.layout.choosephoto);
        RelativeLayout rl_camera = dialogphoto.findViewById(R.id.rl_camera);
        rl_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraimage(getCameraCode(tag));
                dialogphoto.dismiss();
            }
        });
        RelativeLayout rl_gallery = dialogphoto.findViewById(R.id.rl_gallery);
        rl_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleimage(getGalleryCode(tag));
                dialogphoto.dismiss();
            }
        });
        dialogphoto.show();
    }

    public void seleimage(int code) {
        Intent i = new Intent(ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, code);
    }

    public void cameraimage(int code) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, code);
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
            if (requestCode == LICENCE_IMAGE_REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && null != data) {
                mLicenceImagePath = getImagePathFromGallery(data, requestCode);
                txtLicense.setText(mLicenceImagePath);
                dialogphoto.dismiss();
            } else if (requestCode == INSURANCE_IMAGE_REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
                mInsuranceImagePath = getImagePathFromGallery(data, requestCode);
                txtInsurance.setText(mInsuranceImagePath);
                dialogphoto.dismiss();
            } else if (requestCode == PHOTO_IMAGE_REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
                mPhotoImagePath = getImagePathFromGallery(data, requestCode);
                txtAadharPhoto.setText(mPhotoImagePath);
                dialogphoto.dismiss();
            } else if (requestCode == VEHICLE_IMAGE_REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
                mVehicleImagePath = getImagePathFromGallery(data, requestCode);
                txtVehicleIns.setText(mVehicleImagePath);
                dialogphoto.dismiss();
            } else if (requestCode == LICENCE_IMAGE_REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
                mLicenceBitmap = (Bitmap) data.getExtras().get("data");
                mLicenceImagePath = getImagePathFromCamera(mLicenceBitmap);
                txtLicense.setText(mLicenceImagePath);
                dialogphoto.dismiss();
            } else if (requestCode == INSURANCE_IMAGE_REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
                mInsuranceBitmap = (Bitmap) data.getExtras().get("data");
                mInsuranceImagePath = getImagePathFromCamera(mInsuranceBitmap);
                txtInsurance.setText(mInsuranceImagePath);
                dialogphoto.dismiss();
            } else if (requestCode == PHOTO_IMAGE_REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
                mAdharBitmap = (Bitmap) data.getExtras().get("data");
                mPhotoImagePath = getImagePathFromCamera(mAdharBitmap);
                txtAadharPhoto.setText(mPhotoImagePath);
                dialogphoto.dismiss();
            } else if (requestCode == VEHICLE_IMAGE_REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
                mVehicleBitmap = (Bitmap) data.getExtras().get("data");
                mVehicleImagePath = getImagePathFromCamera(mVehicleBitmap);
                txtVehicleIns.setText(mVehicleImagePath);
                dialogphoto.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getImagePathFromCamera(Bitmap photo) {
        Uri tempUri = getImageUri(DrivingRequirementsActivity.this, photo);
        File finalFile = new File(getRealPathFromURI(tempUri));
        Log.i("data", "file name" + finalFile.getAbsolutePath());
        Log.i("data", "file name" + finalFile.getName());
        if (finalFile.exists()) {
            mSelectedFile = finalFile;
            isImageSelected = true;
        } else {
            isImageSelected = false;
            mSelectedFile = null;
        }
        image = mSelectedFile.toString();
        return image;
    }


    private String getImagePathFromGallery(Intent data, int requestCode) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        File file = new File(getRealPathFromURI(DrivingRequirementsActivity.this, selectedImage));
        Log.i("data", "file name" + file.getAbsolutePath());
        Log.i("data", "file name" + file.getName());
        if (requestCode == LICENCE_IMAGE_REQUEST_CODE_GALLERY)
            mLicenceBitmap = BitmapFactory.decodeFile(imgDecodableString);
        else if (requestCode == VEHICLE_IMAGE_REQUEST_CODE_GALLERY)
            mVehicleBitmap = BitmapFactory.decodeFile(imgDecodableString);
        else if (requestCode == INSURANCE_IMAGE_REQUEST_CODE_GALLERY)
            mInsuranceBitmap = BitmapFactory.decodeFile(imgDecodableString);
        else
            mAdharBitmap = BitmapFactory.decodeFile(imgDecodableString);
        if (file.exists()) {
            mSelectedFile = file;
            isImageSelected = true;
        } else {
            isImageSelected = false;
            mSelectedFile = null;
        }
        image = imgDecodableString.toString();
        return image;
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

    public void BackClick(View view) {
        finish();
    }

    public void SubmitClick(View view) {
        updateUser();
    }

    private void updateUser() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        ArrayList<PayLoad> payLoads = new ArrayList<>();
        payLoads.add(new PayLoad("driverid", driverId, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("driving_licence", drivingLicense, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("expiredate", expiry, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("address1", add1, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("address2", add2, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("zipcode", zip, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("dob", dob, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("social_secrityno", ssn, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("middle_name", middleName, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("state", state, Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("licence_plate", txtLicNo.getText().toString(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("vechile_reg", txtVehRegl.getText().toString(), Constants.MEDIA_TEXT));
        if (Validations.hasText(mInsuranceImagePath)) {
            payLoads.add(new PayLoad("insuarance_img", mInsuranceImagePath, Constants.MEDIA_IMAGE));
        } else {
            payLoads.add(new PayLoad("insuarance_img", "", Constants.MEDIA_TEXT));
        }
        if (Validations.hasText(mLicenceImagePath)) {
            payLoads.add(new PayLoad("licence_img", mLicenceImagePath, Constants.MEDIA_IMAGE));
        } else {
            payLoads.add(new PayLoad("licence_img", "", Constants.MEDIA_TEXT));
        }
        if (Validations.hasText(mPhotoImagePath)) {
            payLoads.add(new PayLoad("adhar_img", mPhotoImagePath, Constants.MEDIA_IMAGE));
        } else {
            payLoads.add(new PayLoad("adhar_img", "", Constants.MEDIA_TEXT));
        }
        if (Validations.hasText(mVehicleImagePath)) {
            payLoads.add(new PayLoad("vechile_img", mVehicleImagePath, Constants.MEDIA_IMAGE));
        } else {
            payLoads.add(new PayLoad("vechile_img", "", Constants.MEDIA_TEXT));
        }
        progressDialog = new ProgressDialog(DrivingRequirementsActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        service.updateDriver(Utils1.generateMultiPartBody(payLoads)).enqueue(new Callback<UpdateDriverResponse>() {
            @Override
            public void onResponse(Call<UpdateDriverResponse> call, Response<UpdateDriverResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        savePref.saveLogin(true);
                        savePref.saveEmail(response.body().getDetails().getEmail());
                        savePref.saveFirstName(response.body().getDetails().getFirstName());
                        savePref.saveMiddleName(response.body().getDetails().getMiddleName());
                        savePref.saveLastName(response.body().getDetails().getLastName());
                        savePref.saveName(response.body().getDetails().getFirstName() + " " + response.body().getDetails().getMiddleName() + " " + response.body().getDetails().getLastName());
                        savePref.saveSSN(response.body().getDetails().getSocialSecrityno());
                        savePref.saveDob(response.body().getDetails().getDob());
                        savePref.saveLicenseNo(response.body().getDetails().getDrivingLicence());
                        savePref.saveExpiryDate(response.body().getDetails().getExpiredate());
                        savePref.saveAddress1(response.body().getDetails().getAddress1());
                        savePref.saveAddress2(response.body().getDetails().getAddress2());
                        savePref.saveAddress(response.body().getDetails().getAddress());
                        savePref.saveMobile(response.body().getDetails().getMobile());
                        savePref.saveImage(response.body().getDetails().getImage());
                        savePref.saveZip(response.body().getDetails().getZipcode());
                        savePref.saveState(response.body().getDetails().getState());
                        savePref.saveCity(response.body().getDetails().getCity());
                        savePref.saveCountry(response.body().getDetails().getCountry());
                        savePref.saveLicensePlate(response.body().getDetails().getLicencePlate());
                        savePref.saveVehicleReg(response.body().getDetails().getVechileReg());
                        savePref.saveInsuranceImg(response.body().getDetails().getInsuaranceImg());
                        savePref.saveDrivingLicenseImg(response.body().getDetails().getLicenceImg());
                        savePref.saveAdharImg(response.body().getDetails().getAdharImg());
                        savePref.saveVehicleImg(response.body().getDetails().getVechileImg());
                        Intent intent = new Intent(DrivingRequirementsActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(DrivingRequirementsActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UpdateDriverResponse> call, Throwable t) {
                Toast.makeText(DrivingRequirementsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
