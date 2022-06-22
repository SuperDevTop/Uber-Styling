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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.Bookinglist;
import com.mobi.arrive5d.Response.DriverEarningResponse;
import com.mobi.arrive5d.Response.EarningFilter;
import com.mobi.arrive5d.Response.EarningFilterResponse;
import com.mobi.arrive5d.adapter.EarningAdapter;
import com.mobi.arrive5d.utils.ReadPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarningFragment extends Fragment {

    Spinner earning_screen_day_spinner;
    ArrayList<String> project = new ArrayList<>();
    EarningAdapter earningAdapter;
    String filterId;
    ReadPref readPref;
    TextView txtTotal, txtRides, txtHours;
    ProgressDialog progressDialog;
    private RecyclerView mRecyclerView;
    private List<EarningFilter> earningFilterList;
    private List<Bookinglist> bookinglists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.earning_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.earnings));
        findAllId();
        setFilterAdapter();
    }

    private void setFilterAdapter() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<EarningFilterResponse> earningFilterResponseCall = service.filterEarning();
        earningFilterResponseCall.enqueue(new Callback<EarningFilterResponse>() {
            @Override
            public void onResponse(Call<EarningFilterResponse> call, Response<EarningFilterResponse> response) {
                earningFilterList = response.body().getResult();
                for (int i = 0; i < earningFilterList.size(); i++) {
                    EarningFilter projectResult = earningFilterList.get(i);
                    project.add(projectResult.getType());
                    filterId = String.valueOf(projectResult.getId());
                }
                if (!project.isEmpty()) {
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.custom_textview_to_spinner, project);
                    adapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
                    earning_screen_day_spinner.setAdapter(adapter);
                    earning_screen_day_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            EarningFilter project = earningFilterList.get(position);
                            filterId = String.valueOf(project.getId());
                            String driverId = readPref.getDriverId();
                            getEarnings(driverId, filterId);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<EarningFilterResponse> call, Throwable t) {

            }
        });
    }

    private void getEarnings(String driverId, String filterId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<DriverEarningResponse> driverEarningResponseCall = service.earnings(driverId, filterId);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        driverEarningResponseCall.enqueue(new Callback<DriverEarningResponse>() {
            @Override
            public void onResponse(Call<DriverEarningResponse> call, Response<DriverEarningResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        bookinglists = response.body().getResult().getBookinglist();
                        txtTotal.setText("$" + " " + response.body().getResult().getTotalearnamount());
                        txtRides.setText(response.body().getResult().getTotalride() + "-" + "Rides");
                        txtHours.setText(response.body().getResult().getTotaltime() + "-" + "Hours");
                        setEarningAdapter();
                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DriverEarningResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void setEarningAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        earningAdapter = new EarningAdapter(getActivity(), bookinglists);
        mRecyclerView.setAdapter(earningAdapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.item_devider));
        mRecyclerView.addItemDecoration(itemDecorator);
    }

    private void findAllId() {
        txtTotal = getActivity().findViewById(R.id.txtTotal);
        txtRides = getActivity().findViewById(R.id.txtRides);
        txtHours = getActivity().findViewById(R.id.txtHours);
        readPref = new ReadPref(getActivity());
        earning_screen_day_spinner = getActivity().findViewById(R.id.earning_screen_day_spinner);
        mRecyclerView = getActivity().findViewById(R.id.earning_screen_recyclerView);
    }
}
