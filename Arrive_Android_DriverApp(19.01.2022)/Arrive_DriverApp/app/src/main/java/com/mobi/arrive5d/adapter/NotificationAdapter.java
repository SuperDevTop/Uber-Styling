package com.mobi.arrive5d.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobi.arrive5d.R;
import com.mobi.arrive5d.model.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification> mNotificationList;
    private Context mContext;

    public NotificationAdapter(List<Notification> mNotificationList, Context mContext) {
        this.mNotificationList = mNotificationList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.msg_txt.setText(mNotificationList.get(position).getNotificationMsg());
        holder.date_txt.setText(mNotificationList.get(position).getNotificationDate());
        holder.time_txt.setText(mNotificationList.get(position).getNotificationTime());
    }

    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView msg_txt;
        private TextView date_txt;
        private TextView time_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            msg_txt = itemView.findViewById(R.id.notification_row_msg);
            date_txt = itemView.findViewById(R.id.notification_row_date);
            time_txt = itemView.findViewById(R.id.notification_row_time);
        }
    }
}
