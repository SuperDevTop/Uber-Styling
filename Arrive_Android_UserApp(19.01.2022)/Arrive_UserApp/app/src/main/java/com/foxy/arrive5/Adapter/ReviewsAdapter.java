package com.foxy.arrive5.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.Reviews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parangat on 5/16/18.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    ReviewsReasonsAdapter reviewsReasonsAdapter;
    List<String> commentList = new ArrayList<>();
    private Context context;
    private List<Reviews> reviewsList;

    public ReviewsAdapter(Context context, List<Reviews> reviewsList) {
        this.context = context;
        this.reviewsList = reviewsList;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.reviews, parent, false);
        return new ReviewsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ReviewsAdapter.ViewHolder holder, int position) {
        Reviews issues = reviewsList.get(position);
        holder.driver_name.setText(issues.getDname());
        Glide.with(context).load(issues.getUserImg()).into(holder.driver_img);
        holder.txtRating.setText(issues.getRating());
        holder.ratingBar.setRating(Float.parseFloat(issues.getRating()));
        commentList = issues.getCommentList();
        reviewsReasonsAdapter = new ReviewsReasonsAdapter(context, commentList);
        holder.recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_view.setAdapter(reviewsReasonsAdapter);
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView driver_img;
        RatingBar ratingBar;
        RecyclerView recycler_view;
        private TextView driver_name, txtRating;

        public ViewHolder(View itemView) {
            super(itemView);
            driver_img = itemView.findViewById(R.id.driver_img);
            driver_name = itemView.findViewById(R.id.driver_name);
            txtRating = itemView.findViewById(R.id.txtRating);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            recycler_view = itemView.findViewById(R.id.recycler_view);
        }
    }
}
