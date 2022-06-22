package com.foxy.arrive5.IntroScreens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.foxy.arrive5.utils.Validations;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordActivity extends AppCompatActivity {
    EditText txtPassword;
    EditText txtConfirmPassword;
    SavePref pref;
    CheckBox simpleCheckBox;
    ReadPref readPref;
    EditText edtEmail;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        simpleCheckBox = findViewById(R.id.simpleCheckBox);
        readPref = new ReadPref(PasswordActivity.this);
        pref = new SavePref(PasswordActivity.this);
        txtPassword = findViewById(R.id.txtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        Log.e("dhfgurttt",readPref.getCreditCard());
        Log.e("dhfgurttt",readPref.getExpMonth());
        Log.e("dhfgurttt",readPref.getExpYear());
        Log.e("dhfgurttt",readPref.getCVV());
        Log.e("dhfgurttt",readPref.getPayPalEmail());
        Log.e("dhfgurttt",readPref.getPayPalPass());

        SpannableString spannableString = new SpannableString(getString(R.string.txt_create));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(PasswordActivity.this, PolicyActivity.class);
                intent.putExtra("isPolicy", "1");
                startActivity(intent);
            }
        };
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(PasswordActivity.this, PolicyActivity.class);
                intent.putExtra("isPolicy", "0");
                startActivity(intent);
            }
        };
        spannableString.setSpan(clickableSpan1, 38,
                55, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, 59,
                73, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        simpleCheckBox.setText(spannableString, TextView.BufferType.SPANNABLE);
        simpleCheckBox.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void BackClick(View view) {
        finish();
    }

    public void NextClick(View view) {
        if (Validations.isValidPassword(txtPassword) && simpleCheckBox.isChecked()) {

            String Password=txtPassword.getText().toString().trim();
            String strCPass=txtConfirmPassword.getText().toString().trim();
            String strEmail=edtEmail.getText().toString().trim();
            if(Password.equals(strCPass)){

                pref.savePaswword(txtPassword.getText().toString());
                pref.saveEmail(edtEmail.getText().toString());
//            Intent intent = new Intent(PasswordActivity.this, CodeActivity.class);
//            startActivity(intent);
                addUser();

            }
            else {
                Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(this, "Please accept policy first.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addUser() {

        ApiService service = ApiClient.getClient().create(ApiService.class);
        progressDialog = new ProgressDialog(PasswordActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        ArrayList<PayLoad> payLoads = new ArrayList<>();
        payLoads.add(new PayLoad("firstname", readPref.getFname(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("lastname", readPref.getLname(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("emailid", readPref.getEmailid(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("credit_card_no", readPref.getCreditCard(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("card_valid_month", readPref.getExpMonth(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("card_valid_year", readPref.getExpYear(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("cvv_no", readPref.getCVV(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("paypal_email", readPref.getPayPalEmail(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("paypal_password", readPref.getPayPalPass(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("mobileno", "+" + readPref.getCode() + readPref.getMobile(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("password", readPref.getPass(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("token", FirebaseInstanceId.getInstance().getToken(), Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("appplatform", "android", Constants.MEDIA_TEXT));
        payLoads.add(new PayLoad("code", "", Constants.MEDIA_TEXT));
        if (!CommonUtils.profileImg.isEmpty()){

            payLoads.add(new PayLoad("image", readPref.getImage(), Constants.MEDIA_IMAGE));
        }

        service.newUser(Utils.generateMultiPartBody(payLoads)).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                if (response.isSuccessful()) {

                    Log.e("dsfsdhfsdf", response.toString());
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

                        pref.saveCreditCardNum(response.body().getDetails().getCredit_card_no());
                        pref.saveExpMonth(response.body().getDetails().getCard_valid_month());
                        pref.saveExpYear(response.body().getDetails().getCard_valid_year());
                        pref.saveCVV(response.body().getDetails().getCvv_no());
                        pref.savePayPalEmail(response.body().getDetails().getPaypal_email());
                        pref.savePayPalPass(response.body().getDetails().getPaypal_password());

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
                        Toast.makeText(PasswordActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(PasswordActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void createCustomer(String userId, String email, String desc) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<CreateCustomerResponse> createCustomerResponseCall = service.createCustomer(userId, email, desc);
        progressDialog = new ProgressDialog(PasswordActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(true);
        createCustomerResponseCall.enqueue(new Callback<CreateCustomerResponse>() {
            @Override
            public void onResponse(Call<CreateCustomerResponse> call, Response<CreateCustomerResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getResult();
                    if (status.equalsIgnoreCase("true")) {
                        //Toast.makeText(CodeActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        String custId = response.body().getStripe_user_id();
                        pref.saveCustomerId(custId);
                        Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(PasswordActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CreateCustomerResponse> call, Throwable t) {
                Toast.makeText(PasswordActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
