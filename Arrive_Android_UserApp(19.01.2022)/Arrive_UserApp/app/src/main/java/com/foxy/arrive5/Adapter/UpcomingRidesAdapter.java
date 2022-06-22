package com.foxy.arrive5.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.BookingList;

import java.util.List;

public class UpcomingRidesAdapter extends RecyclerView.Adapter<UpcomingRidesAdapter.UpcomingViewHolder> {
    private Context context;
    private List<BookingList> upcomingRideDetailsArrayList;

    public UpcomingRidesAdapter(Context context, List<BookingList> upcomingRideDetailsArrayList) {
        this.context = context;
        this.upcomingRideDetailsArrayList = upcomingRideDetailsArrayList;
    }

    @Override
    public UpcomingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_upcoming_rides, parent, false);
        return new UpcomingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UpcomingViewHolder holder, int position) {
        BookingList upcomingRideDetails = upcomingRideDetailsArrayList.get(position);
        holder.start.setText(upcomingRideDetails.getStartPoint());
        holder.end.setText(upcomingRideDetails.getEndPoint());
        holder.txtDate.setText(upcomingRideDetails.getScheduleDate());
        holder.type.setText(upcomingRideDetails.getVehicleModel());
        holder.amount.setText("$" + " " + upcomingRideDetails.getAmount());
    }

    @Override
    public int getItemCount() {
        return upcomingRideDetailsArrayList.size();
    }

    public class UpcomingViewHolder extends RecyclerView.ViewHolder {
        private TextView start, end, txtDate, type, amount;

        public UpcomingViewHolder(View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.booking_date);
            type = itemView.findViewById(R.id.booking_car_type);
            amount = itemView.findViewById(R.id.booking_amount);
            start = itemView.findViewById(R.id.past_pickup_add);
            end = itemView.findViewById(R.id.past_drop_add);
        }
    }
}
