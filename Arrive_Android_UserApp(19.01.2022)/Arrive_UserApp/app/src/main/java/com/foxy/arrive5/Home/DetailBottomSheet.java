package com.foxy.arrive5.Home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Adapter.SelectProfileAdapter;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.GetProfileResponse;
import com.foxy.arrive5.utils.ReadPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBottomSheet extends BottomSheetDialogFragment {
    RecyclerView recycler_view;
    ReadPref readPref;
    ProgressDialog progressDialog;
    Bundle bundle;
    private List<GetProfileResponse.BusinessProfileListBean> business_profile_list;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_COLLAPSED: {
                    Log.d("BSB", "collapsed");
                }
                case BottomSheetBehavior.STATE_SETTLING: {
                    Log.d("BSB", "settling");
                }
                case BottomSheetBehavior.STATE_EXPANDED: {
                    Log.d("BSB", "expanded");
                }
                case BottomSheetBehavior.STATE_HIDDEN: {
                    Log.d("BSB", "hidden");
                    dismiss();
                }
                case BottomSheetBehavior.STATE_DRAGGING: {
                    Log.d("BSB", "dragging");
                }
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            Log.d("BSB", "sliding " + slideOffset);
        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.activity_detail_bottom_sheet, null);
        dialog.setContentView(contentView);
        readPref = new ReadPref(getContext());
        recycler_view = contentView.findViewById(R.id.recycler_view);
        String userId = readPref.getUserId();
        setProfiles(userId);
    }

    private void setProfiles(String userId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<GetProfileResponse> getProfileResponseCall = service.getProfileList(userId);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        getProfileResponseCall.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        business_profile_list = response.body().getBusiness_profile_list();
                        setProfileAdapter();
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setProfileAdapter() {
        SelectProfileAdapter profileAdaper = new SelectProfileAdapter(getContext(), business_profile_list);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(profileAdaper);
    }

}
