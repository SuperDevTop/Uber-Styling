package com.foxy.arrive5.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.NotificationList;

import java.util.List;

/**
 * Created by parangat on 5/15/18.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private Context context;
    private List<NotificationList> notificationsList;

    public NotificationsAdapter(Context context, List<NotificationList> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.notifications, parent, false);
        return new NotificationsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NotificationsAdapter.ViewHolder holder, int position) {
        NotificationList notifications = notificationsList.get(position);
        holder.txtMessage.setText(notifications.getNotificationMsg());
        holder.txtTime.setText(notifications.getNotificationTime());
        holder.txtDate.setText(notifications.getNotificationDate());
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMessage, txtTime, txtDate;

        public ViewHolder(View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtMessage = itemView.findViewById(R.id.txtMessage);
        }
    }
}
