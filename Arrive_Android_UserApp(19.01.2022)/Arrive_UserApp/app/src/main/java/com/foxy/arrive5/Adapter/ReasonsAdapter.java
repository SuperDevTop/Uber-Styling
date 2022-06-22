package com.foxy.arrive5.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.RatingComments;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parangat on 5/2/18.
 */

public class ReasonsAdapter extends RecyclerView.Adapter<ReasonsAdapter.MyViewHolder> {
    ArrayList<String> mylist = new ArrayList<String>();
    private Context context;
    private List<List<RatingComments>> reasons;
    private TextView checkedRadioButton;
    private int rating_position = 0;

    public ReasonsAdapter(Context context, List<List<RatingComments>> reasons) {
        this.context = context;
        this.reasons = reasons;
        checkedRadioButton = new TextView(context);
    }

    public void setRatingType(int rating_position) {
        this.rating_position = rating_position;
    }

    @Override
    public ReasonsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ReasonsAdapter.MyViewHolder(inflater.inflate(R.layout.layout_reason, parent, false));
    }

    @Override
    public void onBindViewHolder(final ReasonsAdapter.MyViewHolder holder, final int position) {
        holder.txtReason.setText(reasons.get(rating_position).get(position).getComment());
        holder.txtReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedRadioButton != holder.txtReason) {
                    if (checkedRadioButton != null) {
                        holder.txtReason.setTextColor(context.getResources().getColor(R.color.whiteColor));
                        holder.txtReason.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_textview_selected));
                        checkedRadioButton = holder.txtReason;
                    }
                    if (mylist.contains(reasons.get(rating_position).get(position).getId())) {
                        holder.txtReason.setTextColor(context.getResources().getColor(R.color.grey));
                        holder.txtReason.setBackground(context.getResources().getDrawable(R.drawable.button_text_color));
                        mylist.remove(reasons.get(rating_position).get(position).getId());
                    } else {
                        holder.txtReason.setTextColor(context.getResources().getColor(R.color.whiteColor));
                        holder.txtReason.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_textview_selected));
                        mylist.add(reasons.get(rating_position).get(position).getId());
                    }
                }
            }
        });
    }

    public ArrayList<String> getArrayList() {
        return mylist;
    }

    @Override
    public int getItemCount() {
        return reasons.get(0).size();
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtReason;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtReason = itemView.findViewById(R.id.txtReason);
        }


    }
}
