package com.foxy.arrive5.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.DriverList;
import com.foxy.arrive5.Response.ScheduleBookingResponse;
import com.foxy.arrive5.utils.ReadPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by parangat on 6/11/18.
 */

public class SelectDriverAdapter extends RecyclerView.Adapter<SelectDriverAdapter.ViewHolder> {
    ReadPref readPref;
    private Context context;
    private List<DriverList> driverLists;

    public SelectDriverAdapter(Context context, List<DriverList> driverLists) {
        this.context = context;
        this.driverLists = driverLists;
    }

    @Override
    public SelectDriverAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.driver_select, parent, false);
        readPref = new ReadPref(context);
        return new SelectDriverAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SelectDriverAdapter.ViewHolder holder, int position) {
        final DriverList notifications = driverLists.get(position);
        holder.driver_name.setText(notifications.getName());
        holder.driver_code.setText(notifications.getDriverCode());
        Glide.with(context).load(notifications.getImg()).into(holder.driver_img);
        holder.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = readPref.getUserId();
                String startPoint = readPref.getScheduleStart();
                String endPoint = readPref.getScheduleEnd();
                String startLat = readPref.getStartLat();
                String startLong = readPref.getStartLong();
                String endLat = readPref.getEndLat();
                String endLong = readPref.getEndLong();
                String date = readPref.getScheduleDate();
                String time = readPref.getScheduleTime();
                String subtypeId = readPref.getSubTypeId();
                String driverId = notifications.getDriverId();
                String amount = readPref.getScheduledAmount();
                scheduleBooking(userId, driverId, startPoint, endPoint, startLat, startLong, endLat, endLong, date, time, subtypeId, amount);
            }
        });
    }

    private void scheduleBooking(String userId, String driverId, String startPoint, String endPoint, String startLat, String startLong, String endLat, String endLong, String date, String time, String subtypeId, String amount) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ScheduleBookingResponse> scheduleBookingResponseCall = service.scheduleBooking(userId, driverId, startPoint, endPoint, startLat, startLong, endLat, endLong, date, time, subtypeId, amount);
        scheduleBookingResponseCall.enqueue(new Callback<ScheduleBookingResponse>() {
            @Override
            public void onResponse(Call<ScheduleBookingResponse> call, Response<ScheduleBookingResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ScheduleBookingResponse> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return driverLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView driver_name, driver_code;
        Button btnSend;
        private ImageView driver_img;

        public ViewHolder(View itemView) {
            super(itemView);
            driver_code = itemView.findViewById(R.id.driver_code);
            driver_name = itemView.findViewById(R.id.driver_name);
            driver_img = itemView.findViewById(R.id.driver_img);
            btnSend = itemView.findViewById(R.id.btnSend);
        }
    }
}
