package com.foxy.arrive5.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Adapter.ReviewsAdapter;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.Reviews;
import com.foxy.arrive5.Response.ReviewsListResponse;
import com.foxy.arrive5.utils.ReadPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingReviewsFragment extends Fragment {
    RecyclerView recycler_view;
    ReviewsAdapter reviewsAdapter;
    ReadPref readPref;
    ProgressDialog progressDialog;
    List<Reviews> reviewsList = new ArrayList<>();

    public RatingReviewsFragment() {
    }

    public static RatingReviewsFragment newInstance() {
        RatingReviewsFragment fragment = new RatingReviewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_reviews, container, false);
        getActivity().setTitle(getResources().getString(R.string.rating_reviews));
        recycler_view = view.findViewById(R.id.recycler_view);
        readPref = new ReadPref(getContext());
        String userId = readPref.getUserId();
        getReviewsList(userId);
        return view;
    }

    private void getReviewsList(String userId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ReviewsListResponse> reviewsListResponseCall = service.reviewsList(userId);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        reviewsListResponseCall.enqueue(new Callback<ReviewsListResponse>() {
            @Override
            public void onResponse(Call<ReviewsListResponse> call, Response<ReviewsListResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        reviewsList = response.body().getMessage();
                        setReviewsAdapter();
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ReviewsListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setReviewsAdapter() {
        reviewsAdapter = new ReviewsAdapter(getContext(), reviewsList);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(reviewsAdapter);
    }
}
