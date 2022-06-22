package com.foxy.arrive5.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.IntroScreens.LoginActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.LogoutResponse;
import com.foxy.arrive5.Settings.EveryRideActivity;
import com.foxy.arrive5.Settings.ProfileSetupActivity;
import com.foxy.arrive5.utils.ReadPref;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {
    CircleImageView imgUser;
    TextView txtName, txtEmail, txtMobile, txtEvery_ride, txtBusinessProfile;
    ReadPref readPref;
    ImageView imgLogout;
    ProgressDialog progressDialog;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle(getResources().getString(R.string.action_settings));
        readPref = new ReadPref(getContext());
        imgUser = v.findViewById(R.id.imgUser);
        txtName = v.findViewById(R.id.txtName);
        txtEmail = v.findViewById(R.id.txtEmail);
        txtMobile = v.findViewById(R.id.txtMobile);
        imgLogout = v.findViewById(R.id.imgLogout);
        txtEvery_ride = v.findViewById(R.id.txtEvery_ride);
        txtBusinessProfile = v.findViewById(R.id.txtBusinessProfile);
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
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String driverId = readPref.getUserId();
                String type = "user";
                logout(driverId, type);
            }
        });
        txtBusinessProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileSetupActivity.class);
                getContext().startActivity(intent);
            }
        });
        return v;
    }

    private void logout(String driverId, String type) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<LogoutResponse> logoutResponseCall = service.logout(driverId, type);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        logoutResponseCall.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
