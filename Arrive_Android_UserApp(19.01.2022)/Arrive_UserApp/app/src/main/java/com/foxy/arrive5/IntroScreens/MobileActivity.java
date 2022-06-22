package com.foxy.arrive5.IntroScreens;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.Validations;

public class MobileActivity extends AppCompatActivity {
    EditText txtMobile;
    ReadPref readPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        readPref = new ReadPref(MobileActivity.this);
        txtMobile = findViewById(R.id.txtMobile);
        txtMobile.setText("+" + readPref.getCode() + readPref.getMobile());
    }

    public void NextClick(View view) {
        if (Validations.isValidPhone(txtMobile)) {
            Intent intent = new Intent(MobileActivity.this, PasswordActivity.class);
            startActivity(intent);
        }
    }

    public void BackClick(View view) {
        finish();
    }
}
