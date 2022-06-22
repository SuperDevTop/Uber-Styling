package com.mobi.arrive5d.IntroScreens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.ChangePasswordResponse.ChangePassword;
import com.mobi.arrive5d.utils.ReadPref;
import com.mobi.arrive5d.utils.Validations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity implements TextWatcher {
    EditText txtPassword, txtConfirmPass;
    ReadPref readPref;
    ImageView imgCross, imgCross1;
    ProgressDialog progressDialog;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        imgCross = findViewById(R.id.imgCross);
        imgCross1 = findViewById(R.id.imgCross1);
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPassword.setText("");

            }
        });
        imgCross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtConfirmPass.setText("");
            }
        });
        readPref = new ReadPref(ResetPasswordActivity.this);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPass = findViewById(R.id.txtConfirmPass);
        txtPassword.addTextChangedListener(this);
        txtConfirmPass.addTextChangedListener(this);
        id = readPref.getId();
    }

    public void BackClick(View view) {
        finish();
    }

    public void LoginClick(View view) {
        if (Validations.isValidPassword(txtPassword) && Validations.isValidPassword(txtConfirmPass) && Validations.doMatch(txtPassword, txtConfirmPass)) {
            String password = txtPassword.getText().toString();
            changePassword(id, password);
        }
    }

    private void changePassword(String id, String password) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ChangePassword> changePasswordResponseCall = service.changePassword(id, password);
        progressDialog = new ProgressDialog(ResetPasswordActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        changePasswordResponseCall.enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(ResetPasswordActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count > 0) {
            imgCross.setVisibility(View.VISIBLE);
            imgCross1.setVisibility(View.VISIBLE);
        } else {
            imgCross.setVisibility(View.GONE);
            imgCross1.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
