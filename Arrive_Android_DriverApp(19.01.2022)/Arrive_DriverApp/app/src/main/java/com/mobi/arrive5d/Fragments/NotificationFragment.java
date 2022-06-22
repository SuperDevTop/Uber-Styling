package com.mobi.arrive5d.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.NotificationResponse;
import com.mobi.arrive5d.adapter.NotificationAdapter;
import com.mobi.arrive5d.model.Notification;
import com.mobi.arrive5d.utils.ReadPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    String driverId;
    ReadPref readPref;
    ProgressDialog progressDialog;
    private List<Notification> mNotificationList;
    private NotificationAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notification_screen, container, false);
        readPref = new ReadPref(getContext());
        driverId = readPref.getDriverId();
        getActivity().setTitle(getResources().getString(R.string.notification));
        getNotifications(driverId);
        return v;
    }

    private void getNotifications(String driverId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<NotificationResponse> notificationResponseCall = service.getNotifications(driverId);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        notificationResponseCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        mNotificationList = response.body().getResult();
                        setAdapter();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setAdapter() {
        mRecyclerView = getActivity().findViewById(R.id.notification_screen_recyclerView);
        mAdapter = new NotificationAdapter(mNotificationList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.notification_divider));
        mRecyclerView.addItemDecoration(itemDecorator);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
