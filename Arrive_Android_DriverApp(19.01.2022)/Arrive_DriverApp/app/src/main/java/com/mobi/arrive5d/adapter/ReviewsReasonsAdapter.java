package com.mobi.arrive5d.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobi.arrive5d.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewsReasonsAdapter extends RecyclerView.Adapter<ReviewsReasonsAdapter.ViewHolder> {
    List<String> commentList = new ArrayList<>();
    private Context context;

    public ReviewsReasonsAdapter(Context context, List<String> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public ReviewsReasonsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.reviews_reason, parent, false);
        return new ReviewsReasonsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ReviewsReasonsAdapter.ViewHolder holder, int position) {
        holder.txtReason.setText(commentList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtReason;

        public ViewHolder(View itemView) {
            super(itemView);
            txtReason = itemView.findViewById(R.id.txtReason);
        }
    }
}
