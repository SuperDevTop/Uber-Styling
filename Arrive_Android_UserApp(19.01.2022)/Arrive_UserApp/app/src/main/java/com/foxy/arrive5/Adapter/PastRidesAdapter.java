package com.foxy.arrive5.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foxy.arrive5.Home.PastRideDetailsActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.BookingList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ADMIN on 1/10/2018.
 */

public class PastRidesAdapter extends RecyclerView.Adapter<PastRidesAdapter.PastViewHolder> {
    BookingList pastRideDetails;
    PastViewHolder holder;
    byte[] b;
    private Context context;
    private List<BookingList> pastRideDetailsArrayList;

    public PastRidesAdapter(Context context, List<BookingList> pastRideDetailsArrayList) {
        this.context = context;
        this.pastRideDetailsArrayList = pastRideDetailsArrayList;
    }

    @Override
    public PastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_past_rides, parent, false);
        return new PastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PastViewHolder holder, int position) {
        pastRideDetails = pastRideDetailsArrayList.get(position);
        holder.name.setText(pastRideDetails.getDriverName());
        holder.start.setText(pastRideDetails.getStartPoint());
        holder.end.setText(pastRideDetails.getEndPoint());
        holder.txtDate.setText(pastRideDetails.getScheduleDate());
        Glide.with(context).load(pastRideDetails.getUserImg()).into(holder.image);
        holder.type.setText(pastRideDetails.getVehicleModel());
        holder.no.setText(pastRideDetails.getCarNo());
        holder.amount.setText("$" + " " + pastRideDetails.getAmount());
        holder.tip.setText("$" + " " + pastRideDetails.getTip() + " " + "" + context.getResources().getString(R.string.Tip));
        holder.txtStatus.setText(pastRideDetails.getStatus());
        Glide.with(context).load(pastRideDetails.getUrl()).into(holder.imgMap);
    }

    @Override
    public int getItemCount() {
        return pastRideDetailsArrayList.size();
    }

    public class PastViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout see_details, ride;
        private CircleImageView image;
        private TextView name, start, end, txtDate, type, amount, tip, txtStatus, no;
        ImageView imgMap;

        public PastViewHolder(View itemView) {
            super(itemView);
            see_details = itemView.findViewById(R.id.past_ride);
            txtDate = itemView.findViewById(R.id.booking_date);
            image = itemView.findViewById(R.id.past_ride_img);
            start = itemView.findViewById(R.id.past_pickup_add);
            end = itemView.findViewById(R.id.past_drop_add);
            name = itemView.findViewById(R.id.past_ride_name);
            ride = itemView.findViewById(R.id.past_address);
            type = itemView.findViewById(R.id.booking_car_type);
            no = itemView.findViewById(R.id.booking_car_no);
            amount = itemView.findViewById(R.id.booking_amount);
            tip = itemView.findViewById(R.id.txtTip);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            imgMap = itemView.findViewById(R.id.imgMap);
            see_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PastRideDetailsActivity.class);
                    intent.putExtra("date", pastRideDetailsArrayList.get(getAdapterPosition()).getScheduleDate());
                    intent.putExtra("time", pastRideDetailsArrayList.get(getAdapterPosition()).getScheduleTime());
                    intent.putExtra("name", pastRideDetailsArrayList.get(getAdapterPosition()).getDriverName());
                    intent.putExtra("BitmapImage", pastRideDetailsArrayList.get(getAdapterPosition()).getUserImg());
                    intent.putExtra("start", pastRideDetailsArrayList.get(getAdapterPosition()).getStartPoint());
                    intent.putExtra("end", pastRideDetailsArrayList.get(getAdapterPosition()).getEndPoint());
                    intent.putExtra("amount", pastRideDetailsArrayList.get(getAdapterPosition()).getAmount());
                    intent.putExtra("tip", pastRideDetailsArrayList.get(getAdapterPosition()).getTip());
                    intent.putExtra("no", pastRideDetailsArrayList.get(getAdapterPosition()).getCarNo());
                    intent.putExtra("type", pastRideDetailsArrayList.get(getAdapterPosition()).getVehicleModel());
                    intent.putExtra("duration", pastRideDetailsArrayList.get(getAdapterPosition()).getDuration());
                    intent.putExtra("distance", pastRideDetailsArrayList.get(getAdapterPosition()).getDistance());
                    context.startActivity(intent);
                }
            });
        }
    }
}
