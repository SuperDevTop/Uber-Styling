package com.mobi.arrive5d.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.SelectIssueActivity;
import com.mobi.arrive5d.model.TripDetail;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyTripAdapter extends RecyclerView.Adapter<MyTripAdapter.ViewHolder> {
    private Context mContext;
    private List<TripDetail> mTripList;

    public MyTripAdapter(Context mContext, List<TripDetail> mTripList) {
        this.mContext = mContext;
        this.mTripList = mTripList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_trip_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name_txt.setText(mTripList.get(position).getUserName());
        holder.from_txt.setText(mTripList.get(position).getStartPoint());
        holder.to_txt.setText(mTripList.get(position).getEndPoint());
        Glide.with(mContext).load(mTripList.get(position).getUserImg()).into(holder.my_trip_row_user_image);
        String mode = mTripList.get(position).getMode();
        if (mode.equalsIgnoreCase("0") || mode.equalsIgnoreCase("1") ||
                mode.equalsIgnoreCase("6") || mode.equalsIgnoreCase("5")
                || mode.equalsIgnoreCase("7")) {
            holder.txt1.setVisibility(View.VISIBLE);
            holder.txt2.setVisibility(View.GONE);
            holder.txt3.setVisibility(View.GONE);
            holder.txt4.setVisibility(View.GONE);
        }
        if (mode.equalsIgnoreCase("2") || mode.equalsIgnoreCase("3")
                || mode.equalsIgnoreCase("9")) {
            holder.txt1.setVisibility(View.GONE);
            holder.txt2.setVisibility(View.VISIBLE);
            holder.txt3.setVisibility(View.GONE);
            holder.txt4.setVisibility(View.GONE);
        }
        if (mode.equalsIgnoreCase("4")) {
            holder.txt1.setVisibility(View.GONE);
            holder.txt2.setVisibility(View.GONE);
            holder.txt3.setVisibility(View.VISIBLE);
            holder.txt4.setVisibility(View.VISIBLE);
        }

        holder.txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SelectIssueActivity.class);
                intent.putExtra("start", mTripList.get(position).getStartPoint());
                intent.putExtra("end", mTripList.get(position).getEndPoint());
                intent.putExtra("date", mTripList.get(position).getScheduleDate());
                intent.putExtra("time", mTripList.get(position).getScheduleTime());
                intent.putExtra("userId", mTripList.get(position).getUserId());
                intent.putExtra("bookingId", mTripList.get(position).getBookingId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTripList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name_txt;
        private TextView from_txt;
        private TextView to_txt, txt1, txt2, txt3, txt4;
        private CircleImageView my_trip_row_user_image;

        public ViewHolder(View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.my_trip_row_name);
            from_txt = itemView.findViewById(R.id.my_trip_row_from);
            to_txt = itemView.findViewById(R.id.my_trip_row_to);
            my_trip_row_user_image = itemView.findViewById(R.id.my_trip_row_user_image);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            txt3 = itemView.findViewById(R.id.txt3);
            txt4 = itemView.findViewById(R.id.txt4);
        }
    }
}
