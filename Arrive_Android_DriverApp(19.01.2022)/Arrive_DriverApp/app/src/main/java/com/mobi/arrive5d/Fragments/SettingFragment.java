package com.mobi.arrive5d.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.SideMenu.EveryRideActivity;
import com.mobi.arrive5d.utils.ReadPref;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingFragment extends Fragment {
    CircleImageView imgUser;
    TextView txtName, txtEmail, txtMobile, txtEvery_ride;
    ReadPref readPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setting_screen, container, false);
        readPref = new ReadPref(getContext());
        imgUser = v.findViewById(R.id.imgUser);
        txtName = v.findViewById(R.id.txtName);
        txtEmail = v.findViewById(R.id.txtEmail);
        txtMobile = v.findViewById(R.id.txtMobile);
        txtEvery_ride = v.findViewById(R.id.txtEvery_ride);
        String image = readPref.getImage();
        Glide.with(getContext()).load(image).into(imgUser);
        String name = readPref.getName();
        txtName.setText(name);
        String email = readPref.getEmailid();
        txtEmail.setText(email);
        String mobile = readPref.getMobile();
        txtMobile.setText(mobile);
        txtEvery_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EveryRideActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
