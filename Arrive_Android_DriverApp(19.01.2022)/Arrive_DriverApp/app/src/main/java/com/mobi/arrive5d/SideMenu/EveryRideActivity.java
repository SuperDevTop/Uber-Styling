package com.mobi.arrive5d.SideMenu;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mobi.arrive5d.R;

import static com.mobi.arrive5d.SideMenu.BilllingDetailsActivity.setSystemBarTheme;

public class EveryRideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_ride);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }


        setSystemBarTheme(EveryRideActivity.this, true);
    }

    public void BackClick(View view) {
        finish();
    }
}
