package com.mobi.arrive5d.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.OurServicesResponse;
import com.mobi.arrive5d.adapter.MyTripAdapter;
import com.mobi.arrive5d.model.TripDetail;
import com.mobi.arrive5d.utils.ReadPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTripFragment extends Fragment {
    ReadPref readPref;
    ProgressDialog progressDialog;
    private RecyclerView mRecyclerView;
    private MyTripAdapter mTripAdapter;
    private List<TripDetail> mTripList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_trip_screen, container, false);
        mRecyclerView = view.findViewById(R.id.my_trip_screen_recyclerView);
        getActivity().setTitle(getResources().getString(R.string.my_trips));
        return view;
    }

    private void getTrips(String driverId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<OurServicesResponse> ourServicesResponseCall = service.ourServices(driverId);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        ourServicesResponseCall.enqueue(new Callback<OurServicesResponse>() {
            @Override
            public void onResponse(Call<OurServicesResponse> call, Response<OurServicesResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {

                        mTripList = response.body().getResult();

                        setTripsAdapter();
                    } else {

                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<OurServicesResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void setTripsAdapter() {
        mTripAdapter = new MyTripAdapter(getActivity(), mTripList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.item_devider));
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mTripAdapter);
        mTripAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        readPref = new ReadPref(getActivity());
        String driverId = readPref.getDriverId();
        Log.e("dfdshfsdf",driverId);
        getTrips(driverId);
    }
}
