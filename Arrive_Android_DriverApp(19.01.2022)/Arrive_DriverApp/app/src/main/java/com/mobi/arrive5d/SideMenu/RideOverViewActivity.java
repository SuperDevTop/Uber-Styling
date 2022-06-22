package com.mobi.arrive5d.SideMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.utils.ReadPref;

public class RideOverViewActivity extends AppCompatActivity {
    ImageView imgUser;
    TextView txtName, txtPickupLoc, txtDropoffLoc, txtRating;
    RatingBar ratingBar;
    ReadPref readPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_over_view);
        readPref = new ReadPref(RideOverViewActivity.this);
        imgUser = findViewById(R.id.imgUser);
        txtName = findViewById(R.id.txtName);
        txtRating = findViewById(R.id.txtRating);
        ratingBar = findViewById(R.id.ratingBar);
        txtPickupLoc = findViewById(R.id.txtPickupLoc);
        txtDropoffLoc = findViewById(R.id.txtDropoffLoc);
        txtRating.setText(readPref.getUserRating());
        Glide.with(RideOverViewActivity.this).load(readPref.getImage()).into(imgUser);
        txtName.setText(readPref.getName());
        txtPickupLoc.setText(readPref.getStartPoint());
        txtDropoffLoc.setText(readPref.getEndPoint());
        ratingBar.setRating(Float.parseFloat(readPref.getUserRating()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
    }

    public void BackClick(View view) {
        finish();
    }

    public void CancelClick(View view) {
        Intent intent = new Intent(RideOverViewActivity.this, CancelRideActivity.class);
        startActivity(intent);
    }

    public void CallClick(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + getIntent().getStringExtra("user_mobile")));
        startActivity(intent);
    }
}
