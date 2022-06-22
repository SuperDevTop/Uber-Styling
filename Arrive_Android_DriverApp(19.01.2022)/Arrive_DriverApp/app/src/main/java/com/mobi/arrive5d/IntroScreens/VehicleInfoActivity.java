package com.mobi.arrive5d.IntroScreens;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.MainActivity;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.ColorList;
import com.mobi.arrive5d.Response.GetColorResponse;
import com.mobi.arrive5d.Response.GetModelsResponse;
import com.mobi.arrive5d.Response.GetVehicleYearResponse;
import com.mobi.arrive5d.Response.ModelList;
import com.mobi.arrive5d.Response.UpdateInfoResponse;
import com.mobi.arrive5d.Response.VehicleList;
import com.mobi.arrive5d.Response.VehicleMakeResponse;
import com.mobi.arrive5d.Response.VehicleModelResponse;
import com.mobi.arrive5d.Response.VehicleSubTypeDetail;
import com.mobi.arrive5d.Response.VehicleSubTypeResponse;
import com.mobi.arrive5d.Response.VehicleTypeResponse;
import com.mobi.arrive5d.utils.ReadPref;
import com.mobi.arrive5d.utils.SavePref;
import com.mobi.arrive5d.utils.TextProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuku.ambilwarna.AmbilWarnaDialog;

import static android.content.Intent.ACTION_PICK;


public class VehicleInfoActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 0;
    private static int CAMERA_REQUEST = 1;
    FrameLayout profile_image_frame;
    List<ColorList> colorLists;
    ArrayList<String> project = new ArrayList<>();
    ArrayList<String> project1 = new ArrayList<>();
    ArrayList<String> project2 = new ArrayList<>();
    ArrayList<String> project3 = new ArrayList<>();
    ArrayList<String> project4 = new ArrayList<>();
    ArrayList<String> project5 = new ArrayList<>();
    ProgressDialog progressDialog;
    Spinner spinner_color, spinner_model, spinner_vehicle_type, spinner_vehicle_class, spinner_year, spinner_make, spinner_no_of_doors, spinner_no_belts;
    String languageId;
    ReadPref readPref;
    SavePref savePref;
    List<String> vehicleImages;
    List<GetVehicleYearResponse.DetailBean> yearList;
    List<VehicleModelResponse.DetailBean> modelList;
    List<VehicleMakeResponse.DetailBean> makeList;

    String[] permissions = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.RECEIVE_SMS,
            android.Manifest.permission.READ_SMS
    };

    List<ModelList> modelsList;
    List<VehicleList> vehicleLists;
    List<VehicleSubTypeDetail> vehicleSubTypeDetailList;
    String vehicileSubtypeId, modelid, colorid, yearId, makeId;
    ArrayList<String> image_string = new ArrayList<String>();
    ArrayList<String> pickedPhotoNames = new ArrayList<>();
    String noDoors, noBelts;
    private int currentColor;
    private Dialog dialogPhoto;
    private boolean isImageSelected = false;
    private File mSelectedFile = null;
    private String image = "";
    private Context mContext;
    private TextProgressBar pbHorizontal = null;
    private int progress = 0;
    private ViewGroup mSelectedImagesContainer;
    ImageView profile_image;

    String strArrayYears[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }


        strArrayYears = new String[]{"Select year","1985", "1986", "1987", "1988", "1989", "1990", "1991",
                "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000"
                , "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008",
                "2009", "2010", "2012", "2013", "2014", "2015", "2016", "2017", "2018",
                "2019", "2020", "2021", "2022", "2023", "2024", "2025"};

        mContext = VehicleInfoActivity.this;
        readPref = new ReadPref(VehicleInfoActivity.this);
        savePref = new SavePref(VehicleInfoActivity.this);
        profile_image = findViewById(R.id.profile_image);

        Log.e("setuiyewtwe", "ghghghg" + "  " + readPref.getVehicleImg());

        mSelectedImagesContainer = (ViewGroup) findViewById(R.id.selected_photos_container);
        View getImages = findViewById(R.id.profile_image_frame);
        checkPermissions();
        getImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        spinner_vehicle_class = findViewById(R.id.spinner_vehicle_class);
        spinner_color = findViewById(R.id.spinner_color);
        spinner_model = findViewById(R.id.spinner_model);
        spinner_vehicle_type = findViewById(R.id.spinner_vehicle_type);
        spinner_year = findViewById(R.id.spinner_year);
        spinner_make = findViewById(R.id.spinner_make);
        spinner_no_of_doors = findViewById(R.id.spinner_no_of_doors);
        spinner_no_belts = findViewById(R.id.spinner_no_belts);
        pbHorizontal = (TextProgressBar) findViewById(R.id.pb_horizontal);
        profile_image_frame = findViewById(R.id.profile_image_frame);
        if (progress < pbHorizontal.getMax()) {
            progress = progress + 25;
            postProgress(progress);
        }

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2008; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        if (getIntent().getStringExtra("isDirect") != null) {
            if (getIntent().getStringExtra("isDirect").equalsIgnoreCase("1")) {
                image_string = (ArrayList<String>) readPref.getVehicleImages("image_uris");
            }
        }
        if (image_string != null) {
            showStringMedia();
        }
        noOfDoors();
        noOfBelts();

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_textview_to_spinner, years);
//        spinner_year.setAdapter(adapter);
//        spinner_year.setSelection(readPref.getYearIndex());
//        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                savePref.saveYearIndex(position);
//                savePref.saveYear(spinner_year.getSelectedItem().toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        ArrayList<String> years1 = new ArrayList<String>();
//        int thisYear1 = Calendar.getInstance().get(Calendar.YEAR);
//        for (int i = 2008; i <= thisYear1; i++) {
//            years1.add(Integer.toString(i));
//        }
        years1.add("Ford");
        years1.add("Toyota");
        years1.add("Hyundai");

//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.custom_textview_to_spinner, years1);
//        spinner_make.setAdapter(adapter1);
//        spinner_make.setSelection(readPref.getMakeIndex());
//        spinner_make.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                savePref.saveMakeIndex(position);
//                savePref.saveMake(spinner_make.getSelectedItem().toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        getVehicleColor();
        getVehicleType();
        //getModels();/*phle se commnent h*/
        // getVehicleSubtypes(languageId);
        getVehicleYears();
        getVehicleMakes(yearId);
        // getVehicleModels(makeId, yearId);
        spinner_no_of_doors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                savePref.saveDoorsIndex(position);
                savePref.saveDoors(spinner_no_of_doors.getSelectedItem().toString());
                noDoors = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_no_belts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                savePref.saveBeltsIndex(position);
                savePref.saveBelts(spinner_no_belts.getSelectedItem().toString());
                noBelts = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getVehicleMakes(final String yearId) {
      System.out.println(yearId);
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<VehicleMakeResponse> vehicleMakeResponseCall = service.getVehicleMakes(yearId);
        vehicleMakeResponseCall.enqueue(new Callback<VehicleMakeResponse>() {
            @Override
            public void onResponse(Call<VehicleMakeResponse> call, Response<VehicleMakeResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        String status = response.body().getStatus();
                        project5=new ArrayList<>();
                        if (status.equalsIgnoreCase("true")) {
                            makeList = response.body().getDetail();
                            for (int i = 0; i < makeList.size(); i++) {
                                VehicleMakeResponse.DetailBean projectResult = makeList.get(i);
                                project5.add(projectResult.getMake());
                                makeId = projectResult.getId();
                            }
                            if (!project5.isEmpty()) {
                                ArrayAdapter adapter = new ArrayAdapter(VehicleInfoActivity.this, R.layout.custom_textview_to_spinner, project5);
                                adapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
                                spinner_make.setAdapter(adapter);
                                spinner_make.setSelection(readPref.getMakeIndex());
                                spinner_make.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        savePref.saveMakeIndex(position);
                                        savePref.saveMake(spinner_make.getSelectedItem().toString());
                                        VehicleMakeResponse.DetailBean project = makeList.get(position);
                                       // makeId = project.getMake();
                                        makeId = project.getId();
                                        Log.e("fgkhjfgkhfg",makeId);
                                        getVehicleModels(makeId, yearId);


                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> arg0) {

                                    }
                                });
                            }
                        } else {
                            Toast.makeText(VehicleInfoActivity.this, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<VehicleMakeResponse> call, Throwable t) {
                Toast.makeText(VehicleInfoActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getVehicleModels(String makeId, String yearId) {

        Log.e("weatymlmi", "Make" + makeId);
        Log.e("weatymlmi", "Year" + yearId);

        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<VehicleModelResponse> vehicleModelResponseCall = service.getVehicleModels(makeId);
        vehicleModelResponseCall.enqueue(new Callback<VehicleModelResponse>() {
            @Override
            public void onResponse(Call<VehicleModelResponse> call, Response<VehicleModelResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        String status = response.body().getStatus();
                        project1=new ArrayList<>();
                        if (status.equalsIgnoreCase("true")) {
                            modelList = response.body().getDetail();
                            for (int i = 0; i < modelList.size(); i++) {
                                VehicleModelResponse.DetailBean projectResult = modelList.get(i);
                                project1.add(projectResult.getModel());
                                modelid = projectResult.getId();
                            }
                            if (!project1.isEmpty()) {

                                ArrayAdapter adapter = new ArrayAdapter(VehicleInfoActivity.this, R.layout.custom_textview_to_spinner, project1);
                                adapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
                                spinner_model.setAdapter(adapter);
                                //spinner_model.setSelection(readPref.getModelIndex());

                                spinner_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        savePref.saveModelIndex(position);
                                        savePref.saveModel(spinner_model.getSelectedItem().toString());
                                        VehicleModelResponse.DetailBean project = modelList.get(position);
                                        modelid = project.getId();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> arg0) {

                                    }
                                });
                            }
                        } else {
                            Toast.makeText(VehicleInfoActivity.this, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("ergfhfesdfs",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<VehicleModelResponse> call, Throwable t) {

                Toast.makeText(VehicleInfoActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getVehicleYears() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<GetVehicleYearResponse> getVehicleYearResponseCall = service.getVehicles();
        getVehicleYearResponseCall.enqueue(new Callback<GetVehicleYearResponse>() {
            @Override
            public void onResponse(Call<GetVehicleYearResponse> call, Response<GetVehicleYearResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        yearList = response.body().getDetail();

                        for (int i = 0; i < yearList.size(); i++) {
                            GetVehicleYearResponse.DetailBean detailBean = yearList.get(i);
                            project4.add(detailBean.getYear());
                            yearId = detailBean.getYear();
                        }
                        if (!project4.isEmpty()) {
                            ArrayAdapter adapter = new ArrayAdapter(VehicleInfoActivity.this, R.layout.custom_textview_to_spinner, project4);
                            adapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
                            spinner_year.setAdapter(adapter);
                            spinner_year.setSelection(readPref.getYearIndex());
                            spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    savePref.saveYearIndex(position);
                                    savePref.saveYear(spinner_year.getSelectedItem().toString());
                                    GetVehicleYearResponse.DetailBean project = yearList.get(position);
                                    yearId = project.getYear();
                                    Log.e("ghghghghj",yearId);
                                    getVehicleMakes(yearId);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {

                                }
                            });
                        }
                    } else {
                        Toast.makeText(VehicleInfoActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetVehicleYearResponse> call, Throwable t) {
                Toast.makeText(VehicleInfoActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pickImage() {
        dialogPhoto = new Dialog(VehicleInfoActivity.this);
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

    private void noOfBelts() {
        List<String> list1 = new ArrayList<String>();
        list1.add("4");
        list1.add("5");
        list1.add("6");
        list1.add("7");
        list1.add("8");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.custom_textview_to_spinner, list1);
        spinner_no_belts.setAdapter(dataAdapter);
        spinner_no_belts.setSelection(readPref.getBeltsIndex());
    }

    private void noOfDoors() {
        List<String> list1 = new ArrayList<String>();
        list1.add("4");
        list1.add("5");
        list1.add("6");
        list1.add("7");
        list1.add("8");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_textview_to_spinner, list1);

        spinner_no_of_doors.setAdapter(adapter);

        spinner_no_of_doors.setSelection(readPref.getDoorsIndex());
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK && null != intent) {
                Uri selectedImage = intent.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(getRealPathFromURI(this, selectedImage));
                Bitmap galleryphoto = BitmapFactory.decodeFile(imgDecodableString);
                galleryphoto = getResizedBitmap(galleryphoto, 200, 200);
                if (file.exists()) {
                    mSelectedFile = file;
                    isImageSelected = true;
                    showPickedPhoto(galleryphoto);
                } else {
                    isImageSelected = false;
                    mSelectedFile = null;
                }
                image = imgDecodableString.toString();
                pickedPhotoNames.add(image);
                dialogPhoto.dismiss();
            } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) intent.getExtras().get("data");
                photo = getResizedBitmap(photo, 200, 200);
                Uri tempUri = getImageUri(this, photo);
                File finalFile = new File(getRealPathFromURI(tempUri));
                Log.i("data", "file name" + finalFile.getAbsolutePath());
                Log.i("data", "file name" + finalFile.getName());
                if (finalFile.exists()) {
                    mSelectedFile = finalFile;
                    isImageSelected = true;
                    showPickedPhoto(photo);
                } else {

                    isImageSelected = false;
                    mSelectedFile = null;
                }
                image = mSelectedFile.toString();
                pickedPhotoNames.add(image);
                dialogPhoto.dismiss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    private void showPickedPhoto(Bitmap pickedPhoto) {
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(pickedPhoto);
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mSelectedImagesContainer.addView(iv);
    }

    private void showStringMedia() {
        mSelectedImagesContainer.removeAllViews();
        if (image_string.size() >= 1) {
            mSelectedImagesContainer.setVisibility(View.VISIBLE);
        }
        int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        for (String uri : image_string) {

            View imageHolder = LayoutInflater.from(this).inflate(R.layout.image_item, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.media_image);
            Glide.with(this)
                    .load(uri.toString())
                    .into(thumbnail);
            mSelectedImagesContainer.addView(imageHolder);
            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(wdpx, htpx));
        }
    }
    private void getVehicleSubtypes(String languageId) {

        Log.e("weatymlmi","Lang"+languageId);
        // Toast.makeText(VehicleInfoActivity.this, "Virend"+languageId, Toast.LENGTH_SHORT).show();
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<VehicleSubTypeResponse> subTypeResponseCall = service.getVehiclesSub(languageId);
        subTypeResponseCall.enqueue(new Callback<VehicleSubTypeResponse>() {
            @Override
            public void onResponse(Call<VehicleSubTypeResponse> call, Response<VehicleSubTypeResponse> response) {
                if (response.isSuccessful()) {
                    Log.e("Virend", response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("true")) {

                        //   Log.e("Virend","True");

                        vehicleSubTypeDetailList = response.body().getDetail();
                        project3.clear();

                        for (int i = 0; i < vehicleSubTypeDetailList.size(); i++) {
                            VehicleSubTypeDetail projectResult = vehicleSubTypeDetailList.get(i);
                            project3.add(projectResult.getVehicleModel());
                            Log.e("Virend", projectResult.getVehicleModel());
                            vehicileSubtypeId = projectResult.getId();
                        }
                        if (!project3.isEmpty()) {
                            ArrayAdapter adapter = new ArrayAdapter(VehicleInfoActivity.this, R.layout.custom_textview_to_spinner, project3);
                            adapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
                            spinner_vehicle_type.setAdapter(adapter);
                            //  spinner_vehicle_type.setSelection(readPref.getVehicleSubtypeIndex());
                            spinner_vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    savePref.saveVehicleSubtypeIndex(position);
                                    savePref.saveVehicleSubType(spinner_vehicle_type.getSelectedItem().toString());
                                    VehicleSubTypeDetail project = vehicleSubTypeDetailList.get(position);
                                    vehicileSubtypeId = project.getId();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleSubTypeResponse> call, Throwable t) {

            }
        });
    }

    /*Vehicle class*/
    private void getVehicleType() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<VehicleTypeResponse> vehicleTypeResponseCall = service.getVehicleType();
        vehicleTypeResponseCall.enqueue(new Callback<VehicleTypeResponse>() {
            @Override
            public void onResponse(Call<VehicleTypeResponse> call, Response<VehicleTypeResponse> response) {
                vehicleLists = response.body().getDetail();
                for (int i = 0; i < vehicleLists.size(); i++) {
                    VehicleList projectResult = vehicleLists.get(i);
                    project2.add(projectResult.getVehicleType());
                    languageId = projectResult.getId();
                }

                if (!project2.isEmpty()) {
                    ArrayAdapter adapter = new ArrayAdapter(VehicleInfoActivity.this, R.layout.custom_textview_to_spinner, project2);
                    spinner_vehicle_class.setAdapter(adapter);
                    spinner_vehicle_class.setSelection(readPref.getVehicleClassIndex());
                    spinner_vehicle_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            savePref.saveVehicleClassIndex(position);
                            savePref.saveVehicleType(spinner_vehicle_class.getSelectedItem().toString());
                            VehicleList project = vehicleLists.get(position);
                            languageId = project.getId();
                            getVehicleSubtypes(languageId);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<VehicleTypeResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        spinner_vehicle_class.setSelection(getIndex(spinner_vehicle_class, readPref.getVehicleType()));
        spinner_vehicle_type.setSelection(getIndex(spinner_vehicle_type, readPref.getVehicleSubType()));
        spinner_no_of_doors.setSelection(getIndex(spinner_no_of_doors, readPref.getDoors()));
        spinner_no_belts.setSelection(getIndex(spinner_no_belts, readPref.getBelts()));
        spinner_model.setSelection(getIndex(spinner_model, readPref.getModel()));
        spinner_color.setSelection(getIndex(spinner_color, readPref.getColor()));
        spinner_year.setSelection(getIndex(spinner_year, readPref.getYear()));
        spinner_make.setSelection(getIndex(spinner_make, readPref.getMake()));
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }

    private void getModels() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<GetModelsResponse> getModelsResponseCall = service.getModels();
        getModelsResponseCall.enqueue(new Callback<GetModelsResponse>() {
            @Override
            public void onResponse(Call<GetModelsResponse> call, Response<GetModelsResponse> response) {
                modelsList = response.body().getDetail();
                for (int i = 0; i < modelsList.size(); i++) {
                    ModelList projectResult = modelsList.get(i);
                    project1.add(projectResult.getModelname());
                    modelid = projectResult.getId();
                }
                if (!project1.isEmpty()) {
                    ArrayAdapter adapter = new ArrayAdapter(VehicleInfoActivity.this, R.layout.custom_textview_to_spinner, project1);
                    adapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
                    spinner_model.setAdapter(adapter);
                    spinner_model.setSelection(readPref.getModelIndex());
                    spinner_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            savePref.saveModelIndex(position);
                            savePref.saveModel(spinner_model.getSelectedItem().toString());
                            ModelList project = modelsList.get(position);
                            modelid = project.getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GetModelsResponse> call, Throwable t) {

            }
        });
    }

    private void getVehicleColor() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<GetColorResponse> getColorResponseCall = service.geColors();
        getColorResponseCall.enqueue(new Callback<GetColorResponse>() {
            @Override
            public void onResponse(Call<GetColorResponse> call, Response<GetColorResponse> response) {
                colorLists = response.body().getDetail();
                for (int i = 0; i < colorLists.size(); i++) {
                    ColorList projectResult = colorLists.get(i);
                    project.add(projectResult.getColorName());
                    colorid = projectResult.getId();
                }

                if(!project.isEmpty()) {

                    ArrayAdapter adapter = new ArrayAdapter(VehicleInfoActivity.this, R.layout.custom_textview_to_spinner, project);
                    adapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
                    spinner_color.setAdapter(adapter);
                    spinner_color.setSelection(readPref.getColorIndex());
                    spinner_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            savePref.saveColorIndex(position);
                            savePref.saveColor(spinner_color.getSelectedItem().toString());
                            ColorList project = colorLists.get(position);
                            colorid = project.getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<GetColorResponse> call, Throwable t) {

            }
        });
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
        if (getIntent().getStringExtra("isDirect").equalsIgnoreCase("1")) {
            Intent intent = new Intent(VehicleInfoActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            finish();
        }
    }

    public void DriverInfoClick(View view) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (int i = 0; i < pickedPhotoNames.size(); i++) {
            File file = new File(pickedPhotoNames.get(i));
            builder.addFormDataPart("image[]", file.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }

        builder.addFormDataPart("driverid", readPref.getDriverId());
        builder.addFormDataPart("vechile_type", languageId);
        builder.addFormDataPart("vechile_subtype", vehicileSubtypeId);
        builder.addFormDataPart("modelid", modelid);
        builder.addFormDataPart("colorid", colorid);
        builder.addFormDataPart("year",yearId);
        builder.addFormDataPart("make",makeId);
        builder.addFormDataPart("noofdoor", noDoors);
        builder.addFormDataPart("noofsbelt", noBelts);
        MultipartBody requestBody = builder.build();
        progressDialog = new ProgressDialog(VehicleInfoActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        service.updateInfo(requestBody).enqueue(new Callback<UpdateInfoResponse>() {
            @Override
            public void onResponse(Call<UpdateInfoResponse> call, Response<UpdateInfoResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        //Log.e("response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                        savePref.saveDoors(response.body().getResult().getNoofdoor());
                        savePref.saveBelts(response.body().getResult().getNoofsbelt());
                        savePref.saveVehicleType(response.body().getResult().getVechileTypeName());
                        savePref.saveVehicleSubType(response.body().getResult().getVechileSubtypeName());
                        savePref.saveModel(response.body().getResult().getModelName());
                        savePref.saveColor(response.body().getResult().getColorName());
                        vehicleImages = response.body().getResult().getVehicleImages();
                        savePref.saveVehicleImages(vehicleImages, "image_uris");
                        String isDirect = getIntent().getStringExtra("isDirect");
                        Intent intent = new Intent(VehicleInfoActivity.this, DriverInfoActivity.class);
                        intent.putExtra("isDirect", isDirect);
                        startActivity(intent);
                    } else {
                        Toast.makeText(VehicleInfoActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<UpdateInfoResponse> call, Throwable t) {
                Toast.makeText(VehicleInfoActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        //openDialog(false);

    }

    private void openDialog(boolean b) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, b, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                Toast.makeText(VehicleInfoActivity.this, "#" + Integer.toHexString(currentColor), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getApplicationContext(), "Action canceled!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}