package com.foxy.arrive5.SideMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.ReadPref;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    TextView txtName, txtmonth, txtCity, txtmusic, txtInterest, txtTp, txtUp, txtCp, txtAp;
    CircleImageView overlapImage;
    ReadPref readPref;
    RatingBar ratingBar;
    ImageView imgEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        txtName = findViewById(R.id.txtName);
        txtmonth = findViewById(R.id.txtmonth);
        txtCity = findViewById(R.id.txtCity);
        txtmusic = findViewById(R.id.txtmusic);
        txtInterest = findViewById(R.id.txtInterest);
        ratingBar = findViewById(R.id.ratingBar);
        txtTp = findViewById(R.id.txtTp);
        txtUp = findViewById(R.id.txtUp);
        txtCp = findViewById(R.id.txtCp);
        txtAp = findViewById(R.id.txtAp);
        imgEdit = findViewById(R.id.imgEdit);
        overlapImage = findViewById(R.id.overlapImage);
        readPref = new ReadPref(ProfileActivity.this);
        txtName.setText(readPref.getName());
        txtmonth.setText(readPref.getJoinDate());
        txtCity.setText(readPref.getCity());
        txtmusic.setText(readPref.getMusic());
        txtInterest.setText(readPref.getAbout());
        txtTp.setText(readPref.getTotalPoints());
        txtUp.setText(readPref.getUsedPoint());
        txtCp.setText(readPref.getCancelledPoint());
        txtAp.setText(readPref.getPointsAvailable());
        ratingBar.setRating(Float.parseFloat(readPref.getUserRating()));
        Glide.with(ProfileActivity.this).load(readPref.getImage()).into(overlapImage);
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    public void BackClick(View view) {
        finish();
    }
}
