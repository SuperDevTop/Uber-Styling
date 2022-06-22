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
import com.foxy.arrive5.Adapter.NotificationsAdapter;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.NotificationList;
import com.foxy.arrive5.Response.NotificationListResponse;
import com.foxy.arrive5.utils.ReadPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {
    RecyclerView recycler_view;
    List<NotificationList> notificationsList = new ArrayList<>();
    ReadPref readPref;
    ProgressDialog progressDialog;

    public NotificationsFragment() {
    }

    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        getActivity().setTitle(getResources().getString(R.string.notification));
        readPref = new ReadPref(getContext());
        recycler_view = view.findViewById(R.id.recycler_view);
        String userId = readPref.getUserId();
        getNotifications(userId);
        return view;
    }

    private void getNotifications(String userId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<NotificationListResponse> notificationListResponseCall = service.notifyList(userId);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        notificationListResponseCall.enqueue(new Callback<NotificationListResponse>() {
            @Override
            public void onResponse(Call<NotificationListResponse> call, Response<NotificationListResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        notificationsList = response.body().getResult();
                        setNotifications();
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NotificationListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setNotifications() {
        NotificationsAdapter adapter = new NotificationsAdapter(getContext(), notificationsList);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(adapter);
    }
}
