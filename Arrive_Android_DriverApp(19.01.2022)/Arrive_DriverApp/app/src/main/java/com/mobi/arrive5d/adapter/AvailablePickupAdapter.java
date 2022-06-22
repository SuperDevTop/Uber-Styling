package com.mobi.arrive5d.adapter;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.MainActivity;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.AddPickupResponse;
import com.mobi.arrive5d.Response.SchedulePickupsResponse;
import com.mobi.arrive5d.utils.ReadPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailablePickupAdapter extends RecyclerView.Adapter<AvailablePickupAdapter.ViewHolder> implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    double latitude, longitude;
    List<SchedulePickupsResponse.ScheduledPickupsBean> scheduled_pickups;
    ArrayList<LatLng> list = new ArrayList<>();
    ReadPref readPref;
    ProgressDialog progressDialog;
    private Context mContext;
    private GoogleMap mMap;

    public AvailablePickupAdapter(Context mContext, List<SchedulePickupsResponse.ScheduledPickupsBean> scheduled_pickups) {
        this.mContext = mContext;
        this.scheduled_pickups = scheduled_pickups;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_pickup_row, null);
        readPref = new ReadPref(mContext);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.from_txt.setText(scheduled_pickups.get(position).getStart_point());
        holder.fare_txt.setText(scheduled_pickups.get(position).getAmount());
        holder.to_txt.setText(scheduled_pickups.get(position).getEnd_point());
        holder.time_txt.setText(scheduled_pickups.get(position).getSchedule_time());
        holder.mapView.onCreate(null);
        holder.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (googleMap != null) {
                    holder.mapView.setClickable(false);
                    googleMap.clear();
                    googleMap.getUiSettings().setAllGesturesEnabled(false);
                    googleMap.getUiSettings().setMapToolbarEnabled(false);
                    LatLng latLng = new LatLng(Double.parseDouble(scheduled_pickups.get(position).getStart_point_lat()), Double.parseDouble(scheduled_pickups.get(position).getStart_point_long()));
                    LatLng latLng2 = new LatLng(Double.parseDouble(scheduled_pickups.get(position).getEnd_point_lat()), Double.parseDouble(scheduled_pickups.get(position).getEnd_point_long()));
                    CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(latLng2, 16);
                    googleMap.moveCamera(cu);
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    list.add(latLng);
                    list.add(latLng2);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng2);
                    googleMap.clear();
                    googleMap.addMarker(markerOptions);
                    list.clear();
                } else {

                }
            }
        });

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String driverId = readPref.getDriverId();
                String bookingId = scheduled_pickups.get(position).getId();
                addPickup(driverId, bookingId);
            }
        });
    }

    private void addPickup(String driverId, String bookingId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<AddPickupResponse> addPickupResponseCall = service.addPickup(driverId, bookingId);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        addPickupResponseCall.enqueue(new Callback<AddPickupResponse>() {
            @Override
            public void onResponse(Call<AddPickupResponse> call, Response<AddPickupResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {

                        Intent intent = new Intent(mContext, MainActivity.class);
                           mContext.startActivity(intent);
                          ((Activity) mContext).finish();
                    }
                    else {
                        Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<AddPickupResponse> call, Throwable t) {
                Toast.makeText(mContext, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduled_pickups.size();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(latitude, longitude));
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MapView mapView;
        ImageView imgAdd;
        private TextView from_txt;
        private TextView to_txt;
        private TextView time_txt;
        private TextView fare_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            from_txt = itemView.findViewById(R.id.available_pickup_row_from);
            to_txt = itemView.findViewById(R.id.available_pickup_row_to);
            time_txt = itemView.findViewById(R.id.available_pickup_row_time);
            fare_txt = itemView.findViewById(R.id.available_pickup_row_fee);
            mapView = (MapView) itemView.findViewById(R.id.map_view);
            imgAdd = itemView.findViewById(R.id.imgAdd);
        }
    }
}
