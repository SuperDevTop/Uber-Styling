package com.foxy.arrive5.IntroScreens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.CreateCustomerResponse;
import com.foxy.arrive5.Response.SignupResponse;
import com.foxy.arrive5.utils.CommonUtils;
import com.foxy.arrive5.utils.Constants;
import com.foxy.arrive5.utils.PayLoad;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.SavePref;
import com.foxy.arrive5.utils.Utils;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeActivity extends AppCompatActivity {
    TextView txtCode;
    SavePref pref;
    ReadPref readPref;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        pref = new SavePref(CodeActivity.this);
        readPref = new ReadPref(CodeActivity.this);
        txtCode = findViewById(R.id.txtCode);
    }

    public void BackClick(View view) {
        finish();
    }

    public void NextClick(View view) {
        addUser();
    }

    private void addUser() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        progressDialog = new ProgressDialog(CodeActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        ArrayList<PayLoad> payLoads = new ArrayList<>();
        payLoads.add(new PayLoad("firstname", readPref.getFname(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("lastname", readPref.getLname(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("emailid", readPref.getEmailid(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("mobileno", "+" + readPref.getCode() + readPref.getMobile(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("password", readPref.getPass(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("token", FirebaseInstanceId.getInstance().getToken(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("appplatform", "android", Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("code", txtCode.getText().toString().replaceAll("\\s+", ""), Constants.MEDIA_TEXT));
        if (!CommonUtils.profileImg.isEmpty()) {
            payLoads.add(new PayLoad("image", readPref.getImage(), Constants.MEDIA_IMAGE));
        }
        service.newUser(Utils.generateMultiPartBody(payLoads)).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        // Toast.makeText(CodeActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        //Log.e("response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                        pref.saveName(response.body().getDetails().getFirst_name() + " " + response.body().getDetails().getLast_name());
                        pref.saveEmail(response.body().getDetails().getEmail());
                        pref.saveMobile(response.body().getDetails().getMobile());
                        pref.saveImage(response.body().getDetails().getImage());
                        String userId = response.body().getDetails().getId();
                        String inviteCode = response.body().getDetails().getInvite_code();
                        pref.saveUserId(userId);
                        pref.saveLogin(true);
                        pref.saveInviteCode(inviteCode);
                        String city = response.body().getDetails().getCity();
                        String music = response.body().getDetails().getFav_music();
                        String about = response.body().getDetails().getAbout_me();
                        String totalPoints = response.body().getDetails().getTotal_points();
                        String usedPoints = response.body().getDetails().getUsed_point();
                        String availablePoints = response.body().getDetails().getPoints_available();
                        String cancelledPoints = response.body().getDetails().getCancelled_point();
                        String joinDate = response.body().getDetails().getJoin_date();
                        pref.saveFname(response.body().getDetails().getFirst_name());
                        pref.saveLname(response.body().getDetails().getLast_name());
                        pref.saveCity(city);
                        pref.saveMusic(music);
                        pref.saveAbout(about);
                        pref.saveTotalPoints(totalPoints);
                        pref.saveUsedPoint(usedPoints);
                        pref.savePointsAvailable(availablePoints);
                        pref.saveCancelledPoint(cancelledPoints);
                        pref.saveJoinDate(joinDate);
                        String desc = "";
                        createCustomer(userId, response.body().getDetails().getEmail(), desc);

                    } else {
                        Toast.makeText(CodeActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(CodeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void createCustomer(String userId, String email, String desc) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<CreateCustomerResponse> createCustomerResponseCall = service.createCustomer(userId, email, desc);
        progressDialog = new ProgressDialog(CodeActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        createCustomerResponseCall.enqueue(new Callback<CreateCustomerResponse>() {
            @Override
            public void onResponse(Call<CreateCustomerResponse> call, Response<CreateCustomerResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getResult();
                    if (status.equalsIgnoreCase("true")) {
                        //Toast.makeText(CodeActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        String custId = response.body().getStripe_user_id();
                        pref.saveCustomerId(custId);
                        Intent intent = new Intent(CodeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CodeActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CreateCustomerResponse> call, Throwable t) {
                Toast.makeText(CodeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
