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
import com.foxy.arrive5.Adapter.PastRidesAdapter;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.BookingList;
import com.foxy.arrive5.Response.BookingListResponse;
import com.foxy.arrive5.utils.ReadPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastRidesFragment extends Fragment {
    ReadPref readPref;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private List<BookingList> pastRideDetailsArrayList;
    private TextView no_ride;

    public PastRidesFragment() {
    }

    public static PastRidesFragment newInstance() {
        PastRidesFragment fragment = new PastRidesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_rides, container, false);
        recyclerView = view.findViewById(R.id.past_rides_rv);
        no_ride = view.findViewById(R.id.no_past_ride);
        readPref = new ReadPref(getContext());
        String userId = readPref.getUserId();
        String type = "past";
        getPastRides(userId, type);
        return view;
    }

    private void getPastRides(String userId, String type) {
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
                        pastRideDetailsArrayList = response.body().getResult();
                        setPastAdapter();
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

    private void setPastAdapter() {
        PastRidesAdapter userReviewsAdapter = new PastRidesAdapter(getActivity(), pastRideDetailsArrayList);
        if (pastRideDetailsArrayList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            no_ride.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            no_ride.setVisibility(View.VISIBLE);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(userReviewsAdapter);
    }

}
