package com.foxy.arrive5.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.foxy.arrive5.R;

public class DefaultPaymentActivity extends AppCompatActivity {
    RadioButton radioButton1, radioButton2;
    String paymentType = "2";
    String businessEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_payment);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
    }

    public void ScheduleClick(View view) {
        if (radioButton1.isChecked()) {
            paymentType = "1";
        } else if (radioButton2.isChecked()) {
            paymentType = "2";
        }
        businessEmail = getIntent().getStringExtra("business_email");
        if (paymentType.equalsIgnoreCase("1")) {
            Intent intent = new Intent(DefaultPaymentActivity.this, CheckOutActivity.class);
            intent.putExtra("business_email", businessEmail);
            intent.putExtra("payment_type", paymentType);
            startActivity(intent);
        } else {
            Intent intent = new Intent(DefaultPaymentActivity.this, ScheduleReportActivity.class);
            intent.putExtra("business_email", businessEmail);
            intent.putExtra("payment_type", paymentType);
            startActivity(intent);
        }
    }

    public void BackClick(View view) {
        finish();
    }
}
