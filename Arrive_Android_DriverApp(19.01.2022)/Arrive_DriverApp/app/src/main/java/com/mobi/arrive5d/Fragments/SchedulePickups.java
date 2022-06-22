package com.mobi.arrive5d.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.SchedulePickupsResponse;
import com.mobi.arrive5d.adapter.AvailablePickupAdapter;
import com.mobi.arrive5d.adapter.MyPickupAdapter;
import com.mobi.arrive5d.model.Schedule;
import com.mobi.arrive5d.utils.ReadPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulePickups extends Fragment implements View.OnClickListener {
    List<SchedulePickupsResponse.ScheduledPickupsBean> scheduled_pickups;
    ReadPref readPref;
    ProgressDialog progressDialog;
    private RecyclerView mAvailablePickupRecyclerView;
    private RecyclerView mMyPickupsRecylerView;
    private AvailablePickupAdapter mAvailablePickupAdapter;
    private MyPickupAdapter mMyPickupAdapter;
    private ArrayList<Schedule> mAvailablePickupList;
    private ArrayList<Schedule> mMyPickupList;
    private LinearLayout mAvailableLin;
    private LinearLayout mMyLin;
    private View mAvailableTab;
    private View mMyTab;
    private String driverId, type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scheduled_pickups, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.scheduled_pickup));
        findAllId();
        setClickListener();
        setRecyclerView();
        driverId = readPref.getDriverId();
        type = "1";
        populateDataForAvailablePickup(driverId, type);
    }

    private void findAllId() {
        mAvailablePickupRecyclerView = getActivity().findViewById(R.id.available_pcikup_recyclerView);
        mMyPickupsRecylerView = getActivity().findViewById(R.id.my_pickup_recyclerView);
        readPref = new ReadPref(getContext());
        mAvailableLin = getActivity().findViewById(R.id.lin_available_pickup);
        mMyLin = getActivity().findViewById(R.id.lin_my_pickup);
        mAvailableTab = getActivity().findViewById(R.id.schedule_available_tab);
        mMyTab = getActivity().findViewById(R.id.schedule_my_tab);
    }

    private void setRecyclerView() {
        mAvailablePickupRecyclerView.setHasFixedSize(true);
        mMyPickupsRecylerView.setHasFixedSize(true);
        mAvailablePickupList = new ArrayList<>();
        mMyPickupList = new ArrayList<>();
    }

    private void setClickListener() {
        mAvailableLin.setOnClickListener(this);
        mMyLin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_available_pickup:
                mMyPickupsRecylerView.setVisibility(View.GONE);
                mAvailablePickupRecyclerView.setVisibility(View.VISIBLE);
                mAvailableTab.setVisibility(View.VISIBLE);
                mMyTab.setVisibility(View.INVISIBLE);
                driverId = readPref.getDriverId();
                type = "1";
                populateDataForAvailablePickup(driverId, type);
                break;
            case R.id.lin_my_pickup:
                mAvailablePickupRecyclerView.setVisibility(View.GONE);
                mMyPickupsRecylerView.setVisibility(View.VISIBLE);
                mAvailableTab.setVisibility(View.INVISIBLE);
                mMyTab.setVisibility(View.VISIBLE);
                driverId = readPref.getDriverId();
                type = "2";
                populateDataForMyPickup(driverId, type);
                break;
        }
    }

    private void populateDataForAvailablePickup(String driverId, String type) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<SchedulePickupsResponse> schedulePickupsResponseCall = service.getScheduledPickups(driverId, type);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        schedulePickupsResponseCall.enqueue(new Callback<SchedulePickupsResponse>() {
            @Override
            public void onResponse(Call<SchedulePickupsResponse> call, Response<SchedulePickupsResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        scheduled_pickups = response.body().getScheduled_pickups();
                        setAvailableAdapter();
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SchedulePickupsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setAvailableAdapter() {
        mAvailablePickupAdapter = new AvailablePickupAdapter(getActivity(), scheduled_pickups);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAvailablePickupRecyclerView.setLayoutManager(layoutManager);
        mAvailablePickupRecyclerView.setAdapter(mAvailablePickupAdapter);

    }

    private void populateDataForMyPickup(String driverId, String type) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<SchedulePickupsResponse> schedulePickupsResponseCall = service.getScheduledPickups(driverId, type);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        schedulePickupsResponseCall.enqueue(new Callback<SchedulePickupsResponse>() {
            @Override
            public void onResponse(Call<SchedulePickupsResponse> call, Response<SchedulePickupsResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        scheduled_pickups = response.body().getScheduled_pickups();
                        setMyPickupAdapter();
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SchedulePickupsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setMyPickupAdapter() {
        mMyPickupAdapter = new MyPickupAdapter(getActivity(), scheduled_pickups);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        mMyPickupsRecylerView.setLayoutManager(layoutManager2);
        mMyPickupsRecylerView.setAdapter(mMyPickupAdapter);
    }
}

