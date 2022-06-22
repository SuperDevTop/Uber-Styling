package com.foxy.arrive5.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Adapter.UpcomingRidesAdapter;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.BookingList;
import com.foxy.arrive5.Response.BookingListResponse;
import com.foxy.arrive5.utils.ReadPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingRidesFragment extends Fragment {
    ReadPref readPref;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private List<BookingList> upcomingRideDetailsArrayList;
    private TextView no_ride;

    public UpcomingRidesFragment() {
    }

    public static UpcomingRidesFragment newInstance() {
        UpcomingRidesFragment fragment = new UpcomingRidesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upcoming_rides, container, false);
        recyclerView = v.findViewById(R.id.upcoming_rides_rv);
        readPref = new ReadPref(getContext());
        String userId = readPref.getUserId();
        String type = "upcoming";
        getUpcomingRides(userId, type);
        no_ride = v.findViewById(R.id.no_upcoming_ride);
        return v;
    }

    private void getUpcomingRides(String userId, String type) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<BookingListResponse> bookingListResponseCall = service.bookingList(userId, type);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        bookingListResponseCall.enqueue(new Callback<BookingListResponse>() {
            @Override
            public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        upcomingRideDetailsArrayList = response.body().getResult();
                        setUpcomingAdapter();
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BookingListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setUpcomingAdapter() {
        if (upcomingRideDetailsArrayList.size() == 0) {
            no_ride.setVisibility(View.VISIBLE);
        } else {
            no_ride.setVisibility(View.GONE);
            UpcomingRidesAdapter RidesAdapter = new UpcomingRidesAdapter(getActivity(), upcomingRideDetailsArrayList);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.setAdapter(RidesAdapter);
        }
    }

}
