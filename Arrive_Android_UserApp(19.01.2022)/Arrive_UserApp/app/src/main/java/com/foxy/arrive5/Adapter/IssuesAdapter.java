package com.foxy.arrive5.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.ReasonList;

import java.util.List;

/**
 * Created by parangat on 5/15/18.
 */

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.ViewHolder> {
    private Context context;
    private List<ReasonList> issuesList;

    public IssuesAdapter(Context context, List<ReasonList> issuesList) {
        this.context = context;
        this.issuesList = issuesList;
    }

    @Override
    public IssuesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.issues, parent, false);
        return new IssuesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final IssuesAdapter.ViewHolder holder, int position) {
        ReasonList issues = issuesList.get(position);
        holder.txtReason.setText(issues.getReason());
    }

    @Override
    public int getItemCount() {
        return issuesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtReason;

        public ViewHolder(View itemView) {
            super(itemView);
            txtReason = itemView.findViewById(R.id.txtReason);
        }
    }
}
