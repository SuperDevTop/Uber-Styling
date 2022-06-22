package com.foxy.arrive5.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.Validations;

public class ProfileSetupActivity extends AppCompatActivity {
    EditText txtBusinessEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        txtBusinessEmail = findViewById(R.id.txtBusinessEmail);
    }

    public void BackClick(View view) {
        finish();
    }

    public void PaymentClick(View view) {
        if (Validations.isValidEmail(txtBusinessEmail)) {
            Intent intent = new Intent(ProfileSetupActivity.this, DefaultPaymentActivity.class);
            intent.putExtra("business_email", txtBusinessEmail.getText().toString());
            startActivity(intent);
        }
    }
}
