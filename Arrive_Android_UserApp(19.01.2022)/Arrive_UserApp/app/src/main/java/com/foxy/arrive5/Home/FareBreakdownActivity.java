package com.foxy.arrive5.Home;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.foxy.arrive5.R;

import static com.foxy.arrive5.Home.RequestRideActivity.setSystemBarTheme;

public class FareBreakdownActivity extends AppCompatActivity {
    TextView txtBasicFare, txtBookingFare, txtMinFare, txtChargeMin, txtChargeMile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_breakdown);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.darkBg));
        }
        txtBasicFare = findViewById(R.id.txtBasicFare);
        txtBookingFare = findViewById(R.id.txtBookingFare);
        txtMinFare = findViewById(R.id.txtMinFare);
        txtChargeMin = findViewById(R.id.txtChargeMin);
        txtChargeMile = findViewById(R.id.txtChargeMile);
        String basicFare = getIntent().getStringExtra("basicFare");
        String bookingFare = getIntent().getStringExtra("bookingFare");
        String minFare = getIntent().getStringExtra("minFare");
        String chargePerMin = getIntent().getStringExtra("chargePerMin");
        String chargePerMile = getIntent().getStringExtra("chargePerMile");
        txtBasicFare.setText(basicFare);
        txtBookingFare.setText(bookingFare);
        txtMinFare.setText(minFare);
        txtChargeMin.setText(chargePerMin);
        txtChargeMile.setText(chargePerMile);
        setSystemBarTheme(FareBreakdownActivity.this, true);
    }

    public void BackClick(View view) {
        finish();
    }
}
