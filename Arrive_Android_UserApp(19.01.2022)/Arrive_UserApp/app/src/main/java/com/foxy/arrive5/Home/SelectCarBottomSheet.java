package com.foxy.arrive5.Home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Adapter.CustomPagerAdapter;
import com.foxy.arrive5.Fragments.SelectPoolFragment;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.VehicleDetail;
import com.foxy.arrive5.Response.VehicleDetailResponse;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.SavePref;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by parangat on 6/12/18.
 */

public class SelectCarBottomSheet extends BottomSheetDialogFragment {
    ArrayList<VehicleDetail> vehicledetails;
    String vehicleTypeId = "0";
    SavePref savePref;
    String vehicleType;
    ViewPager viewPager;
    ImageView imgDone;

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
    private BottomSheetCallback callback;

    public void registerActivity(ScheduleRideActivity activity) {
        callback = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = View.inflate(getContext(), R.layout.layout_car_bottom_sheet, null);

        String startLat = getArguments().getSerializable("startLat").toString();

        String startLong = getArguments().getSerializable("startLong").toString();

        String endLat = getArguments().getSerializable("endLat").toString();

        String endLong = getArguments().getSerializable("endLong").toString();

        Log.e("utewrgreg",startLat);
        Log.e("utewrgreg",startLong);
        Log.e("utewrgreg",endLat);
        Log.e("utewrgreg",endLong);

        savePref = new SavePref(getContext());
        viewPager = contentView.findViewById(R.id.viewPager);
        imgDone = contentView.findViewById(R.id.imgDone);
        getVehicleList(startLat, startLong, endLat, endLong);
        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReadPref readPref = new ReadPref(getActivity());
                callback.carTypeInfo(readPref.getCarType());
                dismiss();
            }
        });
        return contentView;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
    }

    private void getVehicleList(String startLat, String startLong, String endLat, String endLong) {

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Arrive");
        dialog.setMessage("Loading vehicles..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<VehicleDetailResponse> vehicleDetailResponseCall = service.getVehicles(startLat, startLong, endLat, endLong);
        vehicleDetailResponseCall.enqueue(new Callback<VehicleDetailResponse>() {
            @Override
            public void onResponse(Call<VehicleDetailResponse> call, Response<VehicleDetailResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        vehicledetails = response.body().getDetails();
                        List<Fragment> fragmentList = new ArrayList<>();
                        for (int i = 0; i < vehicledetails.size(); i++) {
                            SelectPoolFragment fragment = new SelectPoolFragment();
                            Bundle bundle = new Bundle();
                            String jsonList = new Gson().toJson(vehicledetails.get(i));
                            bundle.putString("vehicledetails", jsonList);
                            fragment.setArguments(bundle);
                            fragmentList.add(fragment);
                        }
                        CustomPagerAdapter pagerAdapter = new CustomPagerAdapter(getChildFragmentManager(), fragmentList);
                        viewPager.setAdapter(pagerAdapter);
                        vehicleTypeId = vehicledetails.get(0).getVehicleTypeId();
                        vehicleType = vehicledetails.get(0).getVehicleTypeName();
                        savePref.saveVehicleType(vehicleType);
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleDetailResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface BottomSheetCallback {
        public void carTypeInfo(String car_type);
    }

}
