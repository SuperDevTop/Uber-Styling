package com.mobi.arrive5d.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.Bookinglist;

import java.util.List;

public class EarningAdapter extends RecyclerView.Adapter<EarningAdapter.ViewHolder> {

    private Context mContext;
    private List<Bookinglist> bookinglists;

    public EarningAdapter(Context mContext, List<Bookinglist> bookinglists) {
        this.mContext = mContext;
        this.bookinglists = bookinglists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.earning_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.money_txt.setText("$" + " " + bookinglists.get(position).getAmount());
        holder.name_txt.setText(bookinglists.get(position).getName());
        holder.to_txt.setText(bookinglists.get(position).getEndPoint());
        holder.from_txt.setText(bookinglists.get(position).getStartPoint());
        Glide.with(mContext).load(bookinglists.get(position).getImage()).into(holder.user_image);
    }

    @Override
    public int getItemCount() {
        return bookinglists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView from_txt;
        private TextView to_txt;
        private TextView name_txt;
        private TextView money_txt;
        private ImageView user_image;

        public ViewHolder(View itemView) {
            super(itemView);
            from_txt = itemView.findViewById(R.id.earning_row_from);
            to_txt = itemView.findViewById(R.id.earning_row_to);
            money_txt = itemView.findViewById(R.id.earning_row_money);
            name_txt = itemView.findViewById(R.id.earning_row_name);
            user_image = itemView.findViewById(R.id.earning_row_user_image);
        }
    }
}
